

/**
 * Title:
 * Project: 
 * Description:
 * Date: 2019-11-20
 * Copyright: Copyright (c) 2020
 * Company: 
 *
 * @author zwx
 * @version 1.0
 */
@Aspect
@Component
public class CenterControlAespect {
    //切入点为使用了HttpRequest注解的任何地方
    @Pointcut("@annotation(com.sec.eac.base.HttpRedirect.annotation.CenterControl)")
    public void httpRequestRedirect() {
    }

    @Before(value = "httpRequestRedirect()")
    public void before() {
    }


    @After(value = "httpRequestRedirect()")
    public void after() {
    }

    @Around("httpRequestRedirect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("CenterControl--------->around");
        if (PermissionManager.isAdminAccount()) {
            if ("false".equals(Boot.GLOBAL.getProperty("CENTER_CONTROL"))) {
                throw new CustomException("当前系统未向当前用户开放该权限");
            }
        }
        return joinPoint.proceed();
    }

}
