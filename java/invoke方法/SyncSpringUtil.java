
/**
 * Title:
 * Project: DTS
 * Description: 调用Spring IOC中的方法
 * Date: 2019-11-11
 * Copyright: Copyright (c) 2020
 * Company:  
 *
 * @author zwx
 * @version 1.0
 */
@Component
//@Transactional
public class SyncSpringUtil {
    private static final Logger log = LoggerFactory.getLogger(SyncSpringUtil.class);
    private static ConcurrentMap<String, Method> methodCache = new ConcurrentHashMap<>();

    /**
     * 通过反射执行指定方法
     *
     * @param beanName   指定 spring bean
     * @param methodName 指定方法名
     * @param param      指定参数
     * @return ret            执行结果
     */
    public static SyncResult invokeMethod(String beanName, String methodName, Object param) {
        if (StringUtils.isBlank(beanName)) {
            log.error("任务[" + beanName + ":" + methodName + "]----未执行，spring bean 未指定");
            return ResultUtil.returnFail("spring bean 未指定");
        }
        Object object;
        try {
            object = SpringContext.getObject(beanName);
        } catch (Exception e) {
            log.error("任务[" + beanName + ":" + methodName + "]----未执行，spring bean 获取失败", e);
            return ResultUtil.returnFail("spring bean 获取失败");
        }
        if (object == null) {
            log.error("任务[" + beanName + ":" + methodName + "]----未执行，spring bean 未获取");
            return ResultUtil.returnFail("spring bean 未获取");
        }
        Class clazz = object.getClass();

        if (StringUtils.isBlank(methodName)) {
            log.error("任务[" + beanName + ":" + methodName + "]----未执行，method 未指定");
            return ResultUtil.returnFail("method 未指定");
        }
        Method method;
        try {
            method = getInvokeMethod(beanName, methodName, param, clazz.getDeclaredMethods());
        } catch (Exception e) {
            log.error("任务[" + beanName + ":" + methodName + "]----未执行，method 获取失败", e);
            return ResultUtil.returnFail("method 获取失败");
        }
        if (method == null) {
            log.error("任务[" + beanName + ":" + methodName + "]----未执行，method 未获取");
            return ResultUtil.returnFail("method 未获取");
        }
        try {
            return (SyncResult) method.invoke(object, param);
        } catch (Exception e) {
            log.error("任务[" + beanName + ":" + methodName + "]----执行失败", e);
            return ResultUtil.returnFail("执行失败");
        }
    }

    private static Method getInvokeMethod(String beanName, String methodName, Object param, Method[] methods) {
        String cacheKey = getCacheKey(beanName, methodName, param.getClass().getName());
        Method method = methodCache.get(cacheKey);
        if (method == null) {
            for (Method m : methods) {
                if (m.getName().equals(methodName) && isParentClass(SyncResult.class, m.getReturnType())) {
                    Class<?>[] pClassArray = m.getParameterTypes();
                    if (pClassArray.length == 1) {
                        if (isParentClass(pClassArray[0], param.getClass())) {
                            method = m;
                            break;
                        }
                    }
                }
            }
            if (method != null) {
                methodCache.put(cacheKey, method);
            }
        }
        return method;
    }

    private static String getCacheKey(String beanName, String methodName, String paramType) {
        if (StringUtils.isBlank(beanName) || StringUtils.isBlank(methodName) || StringUtils.isBlank(paramType)) {
            return null;
        }
        return new StringBuilder().append(beanName).append("-").append(methodName).append("-").append(paramType).toString();
    }

    private static boolean isParentClass(Class<?> pClass, Class<?> cClass) {
        if (pClass != null && cClass != null && pClass.isAssignableFrom(cClass)) {
            return true;
        }
        return false;
    }
}
