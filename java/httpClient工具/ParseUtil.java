

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

public class ParseUtil {

    public static HttpRequestBase parseMethod() {
        HttpServletRequest request = RequestThreadUtil.getRequest();
        String type = request.getMethod();
        if ("post".equalsIgnoreCase(type)) return postMethod(request.getRequestURI(), request);
        else return getMethod(request.getRequestURI(), request);
    }

    public static HttpGet getMethod(String uri) {
        return getMethod(uri, Collections.emptyMap(), Collections.emptyMap());
    }

    public static HttpGet getMethod(String uri, Map<String, String> paramMap) {
        return getMethod(uri, paramMap, Collections.emptyMap());
    }

    public static HttpGet getMethod(String uri, HttpServletRequest request) {
        return getMethod(uri, getParamMap(request), getHeadMap(request));
    }

    /**
     * 返回get方法 填充前台传送来的参数
     *
     * @param uri      要请求的接口地址
     * @param paramMap 请求参数
     * @author piper
     * @data 2018/7/3 11:19
     */
    public static HttpGet getMethod(String uri, Map<String, String> paramMap, Map<String, String> headMap) {
        try {
            URIBuilder builder = new URIBuilder(parseUri(uri));
            //将前台的参数放到我的请求里面
            setGetParams(builder, paramMap);
            HttpGet httpGet = new HttpGet(builder.build());
            //将前台的header放到我的请求里面
            setRequestHeads(httpGet, headMap);
            return httpGet;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置httpGet的param
     *
     * @param builder
     * @param paramMap
     */
    private static void setGetParams(URIBuilder builder, Map<String, String> paramMap) {
        paramMap.keySet().forEach(name -> {
            builder.setParameter(name, paramMap.get(name));
        });
    }

    /**
     * 设置httpGet的HEAD
     *
     * @param httpRequestBase
     * @param headMap
     */
    public static void setRequestHeads(HttpRequestBase httpRequestBase, Map<String, String> headMap) {
//        headMap.keySet().forEach(name -> {
//            httpRequestBase.addHeader(name, headMap.get(name));
//        });
        Map<String, String> mustHeadMap = getHeadMap();
        mustHeadMap.keySet().forEach(name -> httpRequestBase.addHeader(name, mustHeadMap.get(name)));
    }

    private static Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        if (map == null) return Collections.emptyMap();
        Map<String, String> paramMap = new HashMap<>();
        map.entrySet().forEach(item -> paramMap.put(item.getKey(), item.getValue()[0]));
        return paramMap;
    }

    /**
     * 返回post方法
     *
     * @param uri     要请求的地址
     * @param request 前台请求对象
     * @author piper
     * @data 2018/7/3 11:19
     */
    public static HttpPost postMethod(String uri, HttpServletRequest request) {
        HttpPost httpPost = new HttpPost(parseUri(uri));
        StringEntity entity;
        if (request.getContentType() != null && request.getContentType().contains("json")) {
            entity = jsonData(request);  //填充json数据
        } else {
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            entity = formData(request);  //填充form数据
        }
        setRequestHeads(httpPost, getHeadMap(request));
        httpPost.setEntity(entity);
        return httpPost;
    }

    private static String parseUri(String path) {
        return RequestThreadUtil.getSystemUrl() + path;
    }

    public static HttpPost postMethod(String uri) {
        return postMethod(uri, Collections.emptyMap(), Collections.emptyMap());
    }

    public static HttpPost postMethod(String uri, Map<String, String> paramMap) {
        return postMethod(uri, paramMap, Collections.emptyMap());
    }

    /**
     * 返回post方法
     *
     * @param uri      要请求的地址
     * @param paramMap 请求参数
     * @param headMap  请求header
     * @author piper
     * @data 2018/7/3 11:19
     */
    public static HttpPost postMethod(String uri, Map<String, String> paramMap, Map<String, String> headMap) {
        StringEntity entity = setPostParams(paramMap);
        HttpPost httpPost = new HttpPost(uri);
        setRequestHeads(httpPost, headMap);
        httpPost.setEntity(entity);
        return httpPost;
    }

    /**
     * 设置httpGet的param
     *
     * @param paramMap
     */
    private static UrlEncodedFormEntity setPostParams(Map<String, String> paramMap) {
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        try {
            List<NameValuePair> pairs = new ArrayList<>();  //存储参数
            paramMap.keySet().forEach(name -> {
                pairs.add(new BasicNameValuePair(name, paramMap.get(name)));
            });
            //根据参数创建参数体，以便放到post方法中
            urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlEncodedFormEntity;
    }

    /**
     * 处理post请求 form数据 填充form数据
     *
     * @param request 前台请求
     * @author piper
     * @data 2018/7/17 18:05
     */
    private static UrlEncodedFormEntity formData(HttpServletRequest request) {
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        try {
            List<NameValuePair> pairs = new ArrayList<>();  //存储参数
            Enumeration<String> params = request.getParameterNames();  //获取前台传来的参数
            while (params.hasMoreElements()) {
                String name = params.nextElement();
                pairs.add(new BasicNameValuePair(name, request.getParameter(name)));
            }
            //根据参数创建参数体，以便放到post方法中
            urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, request.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlEncodedFormEntity;
    }

    /**
     * 处理post请求 json数据
     *
     * @param request 前台请求
     * @author piper
     * @data 2018/7/17 18:05
     */
    private static StringEntity jsonData(HttpServletRequest request) {
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(request.getInputStream(), request.getCharacterEncoding());
            BufferedReader reader = new BufferedReader(is);
            //将json数据放到String中
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            //根据json数据创建请求体
            return new StringEntity(sb.toString(), request.getCharacterEncoding());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getRespContent(CloseableHttpResponse response) throws Exception {
        String respStr;
        try {
            HttpEntity resEntity = response.getEntity();
            respStr = getRespString(resEntity);
            EntityUtils.consume(resEntity);
        } finally {
            response.close();
        }
        return respStr;
    }


    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    public static String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }

    public static Map<String, String> getHeadMap(HttpServletRequest request) {
        Enumeration<String> names = request.getHeaderNames();
        if (names == null || !names.hasMoreElements())
            return Collections.emptyMap();
        Map<String, String> map = new HashMap<>();
        if (names.hasMoreElements()) {
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                if (name.equalsIgnoreCase("Content-Length")) continue;
                map.put(name, request.getHeader(name));
            }
        }
        return map;
    }

    public static Map<String, String> getHeadMap() {
        HashMap<String, String> map = new HashMap<>();
        //标记用户登录身份的会话
        map.put("cookie", "SESSION=" + PermissionManager.getShareSessionId());
        //标记用户登录身份的租户
        map.put("tenant", PermissionManager.getTenant());
        //标记用户登录身份的令牌
        map.put("token", PermissionManager.getRequestToken());
        //标记请求来自于服务器，非浏览器
        map.put(RequestConstant.server_http_request_key, RequestConstant.server_http_request_mark);
        //标记请求来的服务器主机信息
        map.put("EAC-host", RequestThreadUtil.getRequestHost());
        return map;
    }

    public static HttpClientContext createHttpClientContext(CookieStore cookieStore) {
        HttpClientContext context = HttpClientContext.create();
        Registry<CookieSpecProvider> registry = RegistryBuilder
                .<CookieSpecProvider>create()
                .register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider())
                .register(CookieSpecs.DEFAULT,
                        new DefaultCookieSpecProvider()).build();
        context.setCookieSpecRegistry(registry);
        context.setCookieStore(cookieStore);
        return context;
    }

    public static CookieStore parseCookieStore(HttpResponse httpResponse) {
        CookieStore cookieStore = new BasicCookieStore();
        // JSESSIONID
        Header[] headers = httpResponse.getHeaders("Set-Cookie");
        if (headers != null && headers.length > 0) {
            for (Header header : headers) {
                String setCookie = header.getValue();
                String[] cookieInfo = setCookie.split(";")[0].split("=");
                // 新建一个Cookie
                BasicClientCookie cookie = new BasicClientCookie(cookieInfo[0], cookieInfo[1]);
                cookie.setVersion(0);
                cookie.setDomain("127.0.0.1");
                cookieStore.addCookie(cookie);
            }
        }
        return cookieStore;
    }
}
