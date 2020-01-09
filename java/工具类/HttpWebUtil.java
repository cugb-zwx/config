

/**
 * Title:
 * Project: 
 * Description:
 * Date: 2019-11-21
 * Copyright: Copyright (c) 2020
 * Company: 
 *
 * @author zwx
 * @version 1.0
 */

public class HttpWebUtil {

    public static String getValue(HttpServletRequest request, String name) {
        //从request的参数中获取
        String value = request.getParameter(name);
        if (checkValue(value)) return value;
        //从header中获取
        value = request.getHeader(name);
        if (checkValue(value)) return value;
        //从session中获取
        HttpSession session = request.getSession();
        if (session != null) {
            Object t = session.getAttribute(name);
            if (t != null) value = t.toString();
        }
        if (checkValue(value)) return value;
        //从cookies中获取
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (RequestConstant.getKey().equals(cookie.getName())) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }

    private static boolean checkValue(String value) {
        return StringUtils.isNotBlank(value) && !"unknown".equalsIgnoreCase(value);
    }

    public static void setSessionSystem(HttpSession session, String systemId) {
        setSessionSystem(session, systemId, null);
    }

    public static void setSessionSystem(HttpSession session, String systemId, RegisteredSystemEntity entity) {
        RegisteredSystemMap map = (RegisteredSystemMap) session.getAttribute(RequestConstant.getKey());
        if (map == null) {
            map = new RegisteredSystemMap();
        }
        if (entity == null) map.addEntity(systemId);
        else map.addEntity(systemId, entity);
        //把RegisteredSystemEntity放入map并设置systemId为当前的systemId
        session.setAttribute(RequestConstant.getKey(), map);
    }

    //获取Session中设置的当前用户使用的系统的ID
    public static String getSessionSystemId(HttpSession session) {
        if (session == null) return PermissionManager.getSystemId();
        RegisteredSystemMap map = (RegisteredSystemMap) session.getAttribute(RequestConstant.getKey());
        if (map == null) return PermissionManager.getSystemId();
        String systemId = map.getSystemId();
        if (StringUtils.isBlank(systemId)) return PermissionManager.getSystemId();
        return systemId;
    }

    public static RegisteredSystemMap getRegisteredSystemMap(HttpSession session) {
        if (session == null)
            return null;
        return (RegisteredSystemMap) session.getAttribute(RequestConstant.getKey());
    }

    public static void addSessionIdCookie(HttpServletResponse response, HttpServletRequest request) {
        //创建Cookie
        Cookie cookie = new Cookie("SESSION", request.getSession().getId());
        //设置Cookie的父路径
        cookie.setPath(request.getContextPath() + "/");
        cookie.setMaxAge(30 * 24 * 60 * 60);
        //加入Cookie到响应头
        response.addCookie(cookie);
    }
}
