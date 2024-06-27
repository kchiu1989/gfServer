package com.gf.biz.common.util;


import cn.hutool.core.bean.BeanUtil;
import com.gf.biz.common.entity.BOContext;
import com.gf.biz.common.entity.BOContextImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String post(String url, StringEntity entity) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = postForm(url, entity);
        String body = "";
        body = invoke(httpClient, post);
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("HttpClientService post error", e);
        }
        return body;
    }

    private static List<NameValuePair> getNameValuePairList(Map<String, String> map) {
        List<NameValuePair> list = new ArrayList<>();
        for (String key : map.keySet()) {
            list.add(new BasicNameValuePair(key, map.get(key)));
        }

        return list;
    }

    /**
     * ContentType.URLENCODED.getHeader()
     *
     * @param map
     * @param url
     * @param headerMap
     * @return
     * @throws Exception
     */
    public static String postFormUrlEncoded(String url, Map<String, String> headerMap, Map<String, String> formMap) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        CloseableHttpResponse response = null;
        InputStream in = null;
        BufferedReader br = null;
        String result = "";
        try {
            List<NameValuePair> nameValuePairs = getNameValuePairList(formMap);
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            /*发送json数据需要设置contentType*/
            //urlEncodedFormEntity.setContentType(contentType);
            post.setEntity(urlEncodedFormEntity);
            //post.setHeader("Content-Type", contentType);
            post.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

            if (headerMap != null) {
                Set<Map.Entry<String, String>> headerEntries = headerMap.entrySet();
                for (Map.Entry<String, String> headerEntry : headerEntries) {
                    post.setHeader(headerEntry.getKey(), headerEntry.getValue());
                }
            }

            // 请求参数配置
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(60000)
                    .setConnectionRequestTimeout(10000).build();
            post.setConfig(requestConfig);
            response = httpClient.execute(post);
			/*in = response.getEntity().getContent();
			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuilder strber= new StringBuilder();
			String line = null;
			while((line = br.readLine())!=null){
				strber.append(line+'\n');
			}
			result = strber.toString();*/
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                if (StringUtils.isBlank(result)) result = "服务器异常";
                throw new Exception(result);
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 响应的结果
                String secretContent = EntityUtils.toString(entity, "UTF-8");
                return secretContent;
            }

        } catch (Exception e) {
            logger.error("postFormUrlEncoded error", e);
            //System.err.println("调用接口出错：：：：：：：：：：：："+e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            if (br != null) {
                br.close();
            }
            if (in != null) {
                in.close();
            }
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
        return result;
    }

    /**
     * @param url           post请求url
     * @param fileParamName 文件参数名称
     * @param multipartFile 文件
     * @param paramMap      表单里其他参数
     * @return
     */
    public static String doPostFormData(String url, String fileParamName, MultipartFile multipartFile, Map<String, String> paramMap) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpPost实例
        HttpPost httpPost = new HttpPost(url);
        //httpPost.setHeader("key", value);  // 这里放请求头参数

        // 请求参数配置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(60000)
                .setConnectionRequestTimeout(10000).build();
        httpPost.setConfig(requestConfig);

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            if (multipartFile != null) {
                String fileName = multipartFile.getOriginalFilename();
                // 文件流
                builder.addBinaryBody(fileParamName, multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);
            }


            //表单中其他参数
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                builder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.create("text/plain", Consts.UTF_8)));
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 返回
                String res = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                return res;
            }
        } catch (Exception e) {
            logger.error("调用HttpPost失败！", e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("关闭HttpPost连接失败！", e);
                }
            }
        }
        return null;
    }


    /**
     * doPostJson
     *
     * @param url
     * @param json
     * @return
     */
    public static BOContext doPostJson(String url, String json) {
        // 创建Httpclient对象
        BOContext<String, Object> callRlt = new BOContextImpl<String, Object>();
        CloseableHttpClient httpClient = HttpSend.getInstance().getSslClient();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {

            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            callRlt.put("code", "1");
            callRlt.put("httpCode", String.valueOf(statusCode));
            callRlt.put("httpMsg", resultString);
        } catch (Exception e) {
            logger.error("doPostJson", e);
            callRlt.put("code", "0");
            //callRlt.put("httpCode",String.valueOf(statusCode));
            callRlt.put("msg", "doPostJson error:" + e.getMessage());
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                logger.error("close error", e);
            }
        }

        return callRlt;
    }


    public static String postSFAPI(String url, String xml, String verifyCode) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        List parameters = new ArrayList();
        parameters.add(new BasicNameValuePair("xml", xml));
        parameters.add(new BasicNameValuePair("verifyCode", verifyCode));

        HttpPost post = postForm(url, new UrlEncodedFormEntity(parameters, Charset.forName("UTF-8")));
        String body = "";
        body = invoke(httpClient, post);
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("HttpClientService post error", e);
        }
        return body;
    }

    public static String doGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(60000)
                .setConnectionRequestTimeout(10000).build();

        get.setConfig(requestConfig);

        String body = invoke(httpClient, get);
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("HttpClientService get error", e);
        }
        return body;
    }

    public static String getSso(String url) {
        SSLContext sslContext = SSLContexts.createDefault();
        SSLConnectionSocketFactory sslsf = getSslConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        HttpGet get = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30 * 1000)
                .setConnectTimeout(30 * 1000)
                .build();
        get.setConfig(requestConfig);
        String body = "";
        body = invoke(httpClient, get);
        try {
            logger.info("开始关闭CloseableHttpClient***************");
            httpClient.close();
        } catch (IOException e) {
            logger.error("HttpClientService get error", e);
        }
        return body;
    }

    private static SSLConnectionSocketFactory getSslConnectionSocketFactory(SSLContext sslContext) {
        return new SSLConnectionSocketFactory(sslContext) {

            @Override
            protected void prepareSocket(SSLSocket socket) {

                int port = socket.getPort();
                logger.info("ssoget访问地址port====" + port);
                if (443 == port) {
                    socket.setEnabledProtocols(new String[]{"TLSv1.2"});
                } else {
                    socket.setEnabledProtocols(new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"});
                }
            }
        };
    }

    public static String doGetWithHeader(String url, Map<String, String> headerMap) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        // 请求参数配置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(60000)
                .setConnectionRequestTimeout(10000).build();

        get.setConfig(requestConfig);

        for (String str : headerMap.keySet()) {
            get.setHeader(str, headerMap.get(str));
        }
        logger.info("getUrl:{}" , url);
        String responseStr = invoke(httpClient, get);
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("HttpClientService get error", e);
        }finally{
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("关闭Http GET 连接失败！", e);
                }
            }
        }
        return responseStr;
    }

    public String delete(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete delete = new HttpDelete(url);
        String body = "";
        body = invoke(httpClient, delete);
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("HttpClientService get error", e);
        }
        return body;
    }

    public static String invoke(CloseableHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = sendRequest(httpclient, httpost);
        String body = "";
        int statusCode = response.getStatusLine().getStatusCode();
        logger.info("响应状态statusCode==={}", statusCode);
        if (statusCode == 200) {
            body = parseResponse(response);
        }
        return body;
    }

    private static String parseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String body = "";
        try {
            if (entity != null)
                body = EntityUtils.toString(entity, "UTF-8");
        } catch (ParseException e) {
            logger.error("HttpClientService paseResponse error", e);
        } catch (IOException e) {
            logger.error("HttpClientService paseResponse error", e);
        }
        return body;
    }

    private static HttpResponse sendRequest(CloseableHttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            logger.error("HttpClientService sendRequest error", e);
        } catch (IOException e) {
            logger.error("HttpClientService sendRequest error", e);
        }
        return response;
    }

    public static HttpPost postForm(String url, StringEntity entity) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        return httpPost;
    }


    /**
     * 创建信任https协议的请求，若失败则返回默认http协议
     *
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault() {

        try {
            TrustStrategy trustStrategy = new TrustStrategy() {

                // 淇′换鎵�湁
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            };

            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, trustStrategy).build();

            SSLConnectionSocketFactory sslsf = getSslConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * 使用post方式提交http请求，需要调用者自己关闭CloseableHttpResponse
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 请求参数编码
     * @return CloseableHttpResponse
     * @throws IOException
     */
    public static CloseableHttpResponse post(String url, Map<String, String> params, String charset)
            throws IOException {

        CloseableHttpClient httpclient = createSSLClientDefault();
        HttpPost httpPost = buildHttpPost(url, params, charset);
        return httpclient.execute(httpPost);
    }

    /**
     * 构建post请求数据
     *
     * @param url
     * @param params
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static HttpPost buildHttpPost(String url, Map<String, String> params, String charset)
            throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        if (params == null || params.isEmpty()) {
            return httpPost;
        }
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        String value = "";
        for (String key : params.keySet()) {
            value = params.get(key) == null ? "" : params.get(key);
            nvps.add(new BasicNameValuePair(key, value));
        }

        if (charset != null) {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
        } else {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        }
        return httpPost;
    }

    /**
     * 使用post方式提交http请求,该方法会自己关闭流
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 参数值的编码格式
     * @return String 返回报文体
     * @throws IOException
     */
    public static String postReturnBodyAsString(String url, Map<String, String> params, String charset)
            throws IOException {
        String content = "";
        CloseableHttpResponse response = null;
        try {
            response = post(url, params, charset);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
        } finally {
            HttpClientUtils.closeQuietly(response);
            return content;
        }
    }


    /**
     * 使用post方式提交http请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charsetName 请求参数编码
     * @return Object
     * @throws IOException
     */
    public static String postJsonUrl(String url, String params, String charsetName) throws Exception {


        if (params == null || params.isEmpty()) {
            return null;
        }

        if(StringUtils.isBlank(charsetName)){
            charsetName="UTF-8";
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpPost实例
        HttpPost httpPost = new HttpPost(url);
        //httpPost.setHeader("key", value);  // 这里放请求头参数

        // 请求参数配置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(600000).setConnectTimeout(60000)
                .setConnectionRequestTimeout(10000).build();
        httpPost.setConfig(requestConfig);
        try {

            httpPost.setEntity(new StringEntity(params,charsetName));

            httpPost.setHeader("Content-Type", "application/json");
            httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 返回
                String res = EntityUtils.toString(response.getEntity(), Charset.forName(charsetName));
                logger.info("post result:{}",res);
                return res;
            }
        } catch (Exception e) {
            logger.error("postJsonUrl error", e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("关闭HttpPost连接失败！", e);
                }
            }
        }
        return null;

    }

    public static byte[] downloadPdfIO(String pdfUrl) throws IOException {
        URL url;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        try {
            url = new URL(pdfUrl);
            is = url.openStream();
            byte[] data = new byte[1024];
            outStream = new ByteArrayOutputStream();

            int count = -1;
            while ((count = is.read(data, 0, 1024)) != -1) {
                outStream.write(data, 0, count);
            }
            data = null;
            return outStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (outStream != null) {
                outStream.close();
            }
        }
        return null;
    }

//	public static byte[] downloadPdfIO(String url, String params) throws Exception	{
//		
//		HttpPost httpPost = new HttpPost(url);
//		if (params == null || params.isEmpty()) {
//			return null;
//		}
//		InputStream ins = null;
//		ByteArrayOutputStream outStream = null;
//		httpPost.setEntity(new StringEntity(params));
//		httpPost.setHeader("Content-Type", "application/json");
//		try {
//			HttpClient client = HttpClients.createDefault();  
//			HttpResponse response = client.execute(httpPost);  
//			HttpEntity entity = response.getEntity(); 
//			ins = entity.getContent();
//			byte[] data = new byte[1024];
//			 outStream = new ByteArrayOutputStream();
//             /*int count = -1;
//             while ((count = ins.read(data, 0, 1024)) != -1) {
//                 outStream.write(data, 0, count);
//             }*/
//             
//
// 			FileOutputStream fout = null;
// 			try {
// 				fout = new FileOutputStream(new File("F:/logs/cc.pdf"));
// 				int i;
// 				while((i = ins.read(data)) != -1) {
// 					fout.write(data, 0, i);
// 					outStream.write(data, 0, i);
// 				}
// 			} catch(IOException e) {
// 				e.printStackTrace();
// 			} finally {
// 				if(fout != null) {
// 					fout.close();
// 				}
// 				if(outStream != null) {
// 					outStream.close();
// 				}
// 			}
//             
//             
//             data = null;
//			return outStream.toByteArray();
//		} catch(Exception e) {
//			e.printStackTrace();
//		} finally {
//			 if (ins != null) {
//	                ins.close();
//	            }
//	            if (outStream != null) {
//	                outStream.close();
//	            }
//		}
//		return null;
//	}

    /**
     * @param serverUrl:接口地址
     * @param clientId：应用标识
     * @param accessToken
     * @param nowStr：当前时间戳
     * @param signature：签名信息
     * @param bodyStr：请求参数
     * @return
     * @throws IOException
     */
    public static String postByParams(String serverUrl, String clientId, String accessToken, String nowStr,
                                      String signature, String bodyStr) throws IOException {


        CloseableHttpClient httpclient = HttpSend.getInstance().getSslClient();//createSSLClientDefault();

        HttpPost httpPost = new HttpPost(serverUrl);

        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("bestsign-client-id", clientId);

        // 对请求进行签名的当前时间戳
        httpPost.setHeader("bestsign-sign-timestamp", nowStr);

        // 对请求签名的签名类型
        httpPost.setHeader("bestsign-signature-type", "RSA256");
        // 格式：bearer < Token >
        httpPost.setHeader("Authorization", "bearer " + accessToken);
        httpPost.setHeader("bestsign-signature", signature);

        // HttpPost的body部分
        StringEntity se = new StringEntity(bodyStr, "UTF-8");
        httpPost.setEntity(se);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 401) {
                // token失效
                return "401";
            }
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            logger.error("execute http post error", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                logger.error("response error", e);
            }

        }
        return null;
    }

    public static byte[] postStream(Map<String, Object> params) {
        CloseableHttpClient client = HttpSend.getInstance().getSslClient();//createSSLClientDefault();
        HttpPost httpPost = new HttpPost(params.get("serverUrl").toString());
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("bestsign-client-id", params.get("clientId").toString());

        // 对请求进行签名的当前时间戳
        httpPost.setHeader("bestsign-sign-timestamp", params.get("nowStr").toString());

        // 对请求签名的签名类型
        httpPost.setHeader("bestsign-signature-type", "RSA256");
        // 格式：bearer < Token >
        httpPost.setHeader("Authorization", "bearer " + params.get("accessToken"));
        httpPost.setHeader("bestsign-signature", params.get("signature").toString());

        InputStream inputStream = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            StringEntity entity = new StringEntity(params.get("bodyStr").toString(), "UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = client.execute(httpPost);

            inputStream = response.getEntity().getContent();
            response.getHeaders("Content-Type");
            if (BeanUtil.isEmpty(response.getHeaders("Content-Type")) || !"application/pdf".equals(response.getHeaders("Content-Type").toString())) {
                logger.info("文件流返回格式不正确");
            }
            int n = 0;
            byte[] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (Exception e) {
            logger.info("文件流读取失败，异常信息：");
            e.printStackTrace();
        }
        return output.toByteArray();
    }


    @SuppressWarnings({"unused", "null"})
    public static byte[] getStreamImage(Map<String, Object> params) throws IOException {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpSend.getInstance().getClient();//HttpClients.createDefault();
        CloseableHttpResponse response = null;

        InputStream inputStream = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            //建立一个NameValuePair数组，用于存储欲传送的参数
            //paramList.add(new BasicNameValuePair("contractId",params.get("contractId")+""));
            paramList.add(new BasicNameValuePair("orderIndex", "1"));
            paramList.add(new BasicNameValuePair("pageNum", "1"));
            URI uri = new URIBuilder(params.get("serverUrl").toString()).setParameters(paramList).build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.addHeader("content-type", "application/json");
            httpGet.addHeader("bestsign-client-id", params.get("clientId").toString());
            httpGet.addHeader("bestsign-sign-timestamp", params.get("nowStr").toString());
            httpGet.addHeader("bestsign-signature-type", "RSA256");
            httpGet.addHeader("Authorization", "bearer " + params.get("accessToken"));
            httpGet.addHeader("bestsign-signature", params.get("signature").toString());

            // 执行http get请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                if (BeanUtil.isEmpty(response.getHeaders("Content-Type")) || !"image/jpeg".equals(response.getHeaders("Content-Type").toString())) {
                    logger.info("文件流返回格式不正确");
                }

                int n = 0;
                byte[] buffer = new byte[1024];
                while (-1 != (n = inputStream.read(buffer))) {
                    output.write(buffer, 0, n);
                }

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }

        return output.toByteArray();


    }


    public static byte[] postStreamImg(Map<String, Object> params) {
        CloseableHttpClient client = HttpSend.getInstance().getSslClient();//createSSLClientDefault();
        HttpPost httpPost = new HttpPost(params.get("serverUrl").toString());
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("bestsign-client-id", params.get("clientId").toString());

        // 对请求进行签名的当前时间戳
        httpPost.setHeader("bestsign-sign-timestamp", params.get("nowStr").toString());

        // 对请求签名的签名类型
        httpPost.setHeader("bestsign-signature-type", "RSA256");
        // 格式：bearer < Token >
        httpPost.setHeader("Authorization", "bearer " + params.get("accessToken"));
        httpPost.setHeader("bestsign-signature", params.get("signature").toString());

        InputStream inputStream = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            StringEntity entity = new StringEntity(params.get("bodyStr").toString(), "UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = client.execute(httpPost);

            inputStream = response.getEntity().getContent();
//            response.getHeaders("Content-Type");
//            if(BeanUtil.isNullOrEmpty(response.getHeaders("Content-Type")) || !"application/pdf".equals(response.getHeaders("Content-Type").toString())) {
//            	logger.info("文件流返回格式不正确");
//            }
            int n = 0;
            byte[] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (Exception e) {
            logger.info("文件流读取失败，异常信息：");
            e.printStackTrace();
        }
        return output.toByteArray();
    }

    public static class HttpSend {

        private final PoolingHttpClientConnectionManager connPoolMng;
        private final RequestConfig requestConfig;
        private volatile static HttpSend httpSendInstance;
        private final static int maxTotal = 90;


        private HttpSend() {
            //初始化http连接池
            connPoolMng = new PoolingHttpClientConnectionManager();
            connPoolMng.setMaxTotal(maxTotal);
            connPoolMng.setDefaultMaxPerRoute(maxTotal / 12);
            //初始化请求超时控制参数
            requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000) //连接超时时间
                    .setConnectionRequestTimeout(5000) //从线程池中获取线程超时时间
                    .setSocketTimeout(10 * 1000) //设置数据超时时间
                    .build();
        }

        private static HttpSend getInstance() {
            if (httpSendInstance == null) {
                synchronized (HttpSend.class) {
                    if (httpSendInstance == null) {
                        httpSendInstance = new HttpSend();
                    }
                }
            }
            return httpSendInstance;
        }

        /**
         * 获取client客户端
         *
         * @return
         */
        public CloseableHttpClient getClient() {
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connPoolMng)
                    .setDefaultRequestConfig(requestConfig)
                    .build();
            return httpClient;
        }

        public CloseableHttpClient getSslClient() {
            TrustStrategy trustStrategy = new TrustStrategy() {


                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            };

            try {


                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                        null, trustStrategy).build();

                SSLConnectionSocketFactory sslsf = getSslConnectionSocketFactory(sslContext);
                CloseableHttpClient httpClient = HttpClients.custom()
                        .setConnectionManager(connPoolMng)
                        .setDefaultRequestConfig(requestConfig)
                        .setSSLSocketFactory(sslsf)
                        .build();
                return httpClient;
            } catch (Exception e) {
                logger.error("loadTrustMaterial error", e);
            }


            return null;
        }

    }

}
