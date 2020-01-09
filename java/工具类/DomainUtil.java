
/**
 * Title:
 * Project: 
 * Description:
 * Date: 2019-10-18
 * Copyright: Copyright (c) 2020
 * Company: 
 *
 * @author zwx
 * @version 1.0
 */

public class DomainUtil {
    private static final Logger log = LoggerFactory.getLogger(DomainUtil.class);

    public static <T extends NoIdBaseDomain> SyncResult initBaseDomain(T obj) {
        Date time = new Date();
        if (StringUtils.isBlank(obj.getId())) {
            obj.setCreateTime(time);
            obj.setStatus(EntityStatus.NORMAL);
            obj.setSynVer(0);
        }
        obj.setUpdateTime(time);
        obj.setTenant(PermissionManager.getTenant());
        obj.setFromSystemId(PermissionManager.getSystemId());
        return ResultUtil.returnSuccess(obj);
    }

    public static <T extends NoIdBaseDomain> SyncResult updateBaseDomain(T oldObj, T obj) {
        //把obj中非空字段复制到oldObj中
        copyProperties(obj, oldObj);
        Integer synVer = oldObj.getSynVer();
        if (synVer == null) {
            oldObj.setSynVer(0);
        } else {
            oldObj.setSynVer(synVer + 1);
        }
        return ResultUtil.returnSuccess(oldObj);
    }

    public static <T extends BaseTreeDomain> SyncResult initBaseTreeDomain(T obj, BaseTreeService<T> service) {
        SyncResult result = initBaseDomain(obj);
        if (result.isFail())
            return result;
        if (obj.getRootNode()) {
            obj.setCode(String.format("%02d", Integer.parseInt(obj.getCode())));
            obj.setCascode(obj.getCode());
            return ResultUtil.returnSuccess(obj);
        }
        T oldObj = null;
        if (StringUtils.isNotBlank(obj.getId())) {
            oldObj = service.getInfo(obj.getId());
        }
        //处理父子关系并保存
        String parentId = obj.getParentId();
        if (StringUtils.isBlank(parentId)) {
            BaseTreeDomain parent = obj.getParent();
            if (parent != null) {
                parentId = parent.getId();
                obj.setParentId(parentId);
            }
        }
        if (StringUtils.isBlank(parentId) && oldObj != null) {
            parentId = oldObj.getParentId();
        }
        //处理code与cascade
        if (StringUtils.isNotBlank(parentId)) {
            String code = obj.getCode();
            if (StringUtils.isBlank(code) && oldObj != null) {
                code = oldObj.getCode();
            }
            if (StringUtils.isBlank(code)) {
                String msg = "code无效";
                LoggerUtil.logError(log, "initBaseTreeDomain", obj, msg);
                return ResultUtil.returnFail(msg);
            }
            Integer codeValue = Integer.parseInt(code);
            Integer goodcode = service.checkCode(parentId, codeValue, obj.getTenant());
            if (!codeValue.equals(goodcode)) {
                String msg = "编码过大，请使用 " + goodcode + " 或以下的正整数";
                LoggerUtil.logError(log, "initBaseTreeDomain", obj, msg);
                return ResultUtil.returnFail(msg);
            }
            obj.setCode(String.format("%02d", goodcode));
            BaseTreeDomain parent = service.getInfo(parentId);
            if (parent == null) {
                String msg = "父节点无效";
                LoggerUtil.logError(log, "initBaseTreeDomain", obj, msg);
                return ResultUtil.returnFail(msg);
            }
            //设置信息完善的父节点
            obj.setParent(parent);
            obj.setCascode(parent.getCascode() + "-" + obj.getCode());
        }
        return ResultUtil.returnSuccess(obj);
    }

    public static <T extends NoIdBaseDomain> Map<String, List<T>> compareList(List<T> oldList, List<T> newList) {
        //list转map
        Map<String, T> oldMap = listToMap(oldList);
        Map<String, T> newMap = listToMap(newList);
        //记录删除的项
        List<T> delList = new ArrayList<>();
        Iterator<String> oldIt = oldMap.keySet().iterator();
        while (oldIt.hasNext()) {
            String key = oldIt.next();
            if (newMap.containsKey(key))
                continue;
            delList.add(oldMap.get(key));
        }
        //记录新增的项
        List<T> addList = new ArrayList<>();
        Iterator<String> newIt = newMap.keySet().iterator();
        while (newIt.hasNext()) {
            String key = newIt.next();
            if (oldMap.containsKey(key))
                continue;
            addList.add(newMap.get(key));
        }
        Map<String, List<T>> resultMap = new HashMap<>();
        resultMap.put("add", addList);
        resultMap.put("del", delList);
        return resultMap;
    }

    public static <T extends NoIdBaseDomain> Map<String, T> listToMap(List<T> list) {
        if (list == null)
            return new HashMap<>(0);
        Map<String, T> map = new HashMap<>();
        for (T obj : list) {
            map.put(obj.getId(), obj);
        }
        return map;
    }

    public static <T extends NoIdBaseDomain> Map<String, T> listToMap(List<T> list, ListToMapFunction function) {
        if (list == null)
            return new HashMap<>(0);
        Map<String, T> map = new HashMap<>();
        for (T obj : list) {
            map.put(function.map(obj), obj);
        }
        return map;
    }

    public static <T extends NoIdBaseDomain> String listToStr(List<T> list, ListToStrFunction function) {
        if (list == null || list.size() == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (T obj : list) {
            if (sb.length() > 0) sb.append(",");
            sb.append(function.toStr(obj));
        }
        return sb.toString();
    }

    public static <T extends NoIdBaseDomain> void initTenant(T obj) {
        if (StringUtils.isBlank(obj.getTenant())) {
            obj.setTenant(PermissionManager.getTenant());
        }
    }

    public static <T extends NoIdBaseDomain> List<String> parseIdsToList(List<T> list) {
        if (list == null || list.size() == 0)
            return Collections.emptyList();
        List<String> ids = new ArrayList<>(list.size());
        for (NoIdBaseDomain item : list) {
            ids.add(item.getId());
        }
        return ids;
    }

    public static <T extends NoIdBaseDomain> List<String> parseToList(List<T> list, ListToStrFunction<T> function) {
        if (list == null || list.size() == 0)
            return Collections.emptyList();
        List<String> ids = new ArrayList<>(list.size());
        for (T item : list) {
            ids.add(function.toStr(item));
        }
        return ids;
    }

    public static <T extends NoIdBaseDomain> String parseId(T obj) {
        if (obj == null)
            return "";
        String id = obj.getId();
        if (StringUtils.isBlank(id))
            return "";
        return id;
    }

    public static Set<String> listToSet(List<String> list) {
        if (list == null || list.size() == 0) return Collections.emptySet();
        Set<String> set = new HashSet<>();
        for (String s : list) {
            set.add(s);
        }
        return set;
    }

    public static <T extends NoIdBaseDomain> Set<String> listToSet(List<T> list, ListToStrFunction<T> function) {
        if (list == null || list.size() == 0) return Collections.emptySet();
        Set<String> set = new HashSet<>();
        for (T item : list) {
            set.add(function.toStr(item));
        }
        return set;
    }

    public static <T extends NoIdBaseDomain> String listToIds(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (NoIdBaseDomain obj : list) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(obj.getId());
        }
        return sb.toString();
    }

    public static String listToStr(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public static List<String> strToList(String str) {
        return strToList(str, ",");
    }

    public static List<String> strToList(String str, String split) {
        if (StringUtils.isBlank(str)) return Collections.emptyList();
        StringTokenizer stringTokenizer = new StringTokenizer(str, split);
        int len = stringTokenizer.countTokens();
        List<String> list = new ArrayList<>(len);
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return list;
    }

    public static <T> void copyProperties(T source, T target) {
        if (source == null)
            return;
        Class<?> tClass = source.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(tClass);
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(tClass, targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (value != null) {
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null)
            return true;
        if (obj1 == null || obj2 == null)
            return false;
        return obj1.equals(obj2);
    }
}
