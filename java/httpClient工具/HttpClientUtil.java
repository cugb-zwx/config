

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

public class HttpClientUtil {

    /**
     * http get请求
     *
     * @param httpGet
     * @return
     */
    public static String httpGet(HttpGet httpGet) {
        return send(httpGet);
    }

    /**
     * http post请求
     *
     * @param httpPost
     * @return
     */
    public static String httpPost(HttpPost httpPost) {
        return send(httpPost);
    }

    public static String send(HttpRequestBase request) {
        if (request == null)
            return "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            CookieStore cookieStore = PermissionManager.getCookieStore();
            CloseableHttpResponse response;
            if (cookieStore == null) response = httpclient.execute(request);
            else {
                HttpClientContext context = ParseUtil.createHttpClientContext(cookieStore);
                response = httpclient.execute(request, context);
            }
            setCookieStore(response);
            return ParseUtil.getRespContent(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static void setCookieStore(HttpResponse httpResponse) {
        CookieStore context = ParseUtil.parseCookieStore(httpResponse);
        RequestThreadUtil.setCookieStore(context);
    }


    public interface HttpClientDownLoadProgress {
        void onProgress(int progress);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     */
    public void download(final String url, final String filePath) {
        httpDownloadFile(url, filePath, null, null);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     * @param progress 进度回调
     */
    public void download(final String url, final String filePath,
                         final HttpClientDownLoadProgress progress) {
        httpDownloadFile(url, filePath, progress, null);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     */
    private void httpDownloadFile(String url, String filePath,
                                  HttpClientDownLoadProgress progress, Map<String, String> headMap) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            ParseUtil.setRequestHeads(httpGet, headMap);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                HttpEntity httpEntity = response1.getEntity();
                long contentLength = httpEntity.getContentLength();
                InputStream is = httpEntity.getContent();
                // 根据InputStream 下载文件
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int r;
                long totalRead = 0;
                while ((r = is.read(buffer)) > 0) {
                    output.write(buffer, 0, r);
                    totalRead += r;
                    if (progress != null) {// 回调进度
                        progress.onProgress((int) (totalRead * 100 / contentLength));
                    }
                }
                FileOutputStream fos = new FileOutputStream(filePath);
                output.writeTo(fos);
                output.flush();
                output.close();
                fos.close();
                EntityUtils.consume(httpEntity);
            } finally {
                response1.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件
     *
     * @param serverUrl       服务器地址
     * @param localFilePath   本地文件路径
     * @param serverFieldName
     * @param params
     * @return
     * @throws Exception
     */
    public String uploadFileImpl(String serverUrl, String localFilePath,
                                 String serverFieldName, Map<String, String> params)
            throws Exception {
        String respStr;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(serverUrl);
            FileBody binFileBody = new FileBody(new File(localFilePath));

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                    .create();
            // add the file params
            multipartEntityBuilder.addPart(serverFieldName, binFileBody);
            // 设置上传的其他参数
            setUploadParams(multipartEntityBuilder, params);

            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            respStr = ParseUtil.getRespContent(response);
        } finally {
            httpclient.close();
        }
        return respStr;
    }

    /**
     * 设置上传文件时所附带的其他参数
     *
     * @param multipartEntityBuilder
     * @param params
     */
    private void setUploadParams(MultipartEntityBuilder multipartEntityBuilder,
                                 Map<String, String> params) {
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                multipartEntityBuilder
                        .addPart(key, new StringBody(params.get(key),
                                ContentType.TEXT_PLAIN));
            }
        }
    }

}
