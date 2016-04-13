package com.metaui.eshop.api.taobao;

import com.metaui.core.util.UString;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author wei_jc
 * @since 1.0.0
 * 2016/4/13.
 */
public class WebUtils {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";
//    private static final Certificate verisign;
    private static boolean ignoreSSLCheck;

    private WebUtils() {
    }

    public static void setIgnoreSSLCheck(boolean ignoreSSLCheck) {
        ignoreSSLCheck = ignoreSSLCheck;
    }

    public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, "UTF-8", connectTimeout, readTimeout);
    }

    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, charset, connectTimeout, readTimeout, (Map)null);
    }

    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        byte[] content = new byte[0];
        if(query != null) {
            content = query.getBytes(charset);
        }

        return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
        return _doPost(url, ctype, content, connectTimeout, readTimeout, (Map)null);
    }

    private static String _doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;

        try {
            Map map;
            try {
                conn = getConnection(new URL(url), "POST", ctype, headerMap);
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (IOException var17) {
                map = getParamsFromUrl(url);
//                TaobaoLogger.logCommError(var17, url, (String)map.get("app_key"), (String)map.get("method"), content);
                throw var17;
            }

            try {
                out = conn.getOutputStream();
                out.write(content);
                rsp = getResponseAsString(conn);
            } catch (IOException var16) {
                map = getParamsFromUrl(url);
//                TaobaoLogger.logCommError(var16, conn, (String)map.get("app_key"), (String)map.get("method"), content);
                throw var16;
            }
        } finally {
            if(out != null) {
                out.close();
            }

            if(conn != null) {
                conn.disconnect();
            }

        }

        return rsp;
    }

    /*public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, int connectTimeout, int readTimeout) throws IOException {
        return fileParams != null && !fileParams.isEmpty()?doPost(url, params, fileParams, "UTF-8", connectTimeout, readTimeout):doPost(url, params, "UTF-8", connectTimeout, readTimeout);
    }*/

    /*public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, fileParams, charset, connectTimeout, readTimeout, (Map)null);
    }*/

    /*public static String doPost(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        return fileParams != null && !fileParams.isEmpty()?_doPostWithFile(url, params, fileParams, charset, connectTimeout, readTimeout, headerMap):doPost(url, params, charset, connectTimeout, readTimeout, headerMap);
    }*/

    /*private static String _doPostWithFile(String url, Map<String, String> params, Map<String, FileItem> fileParams, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String boundary = String.valueOf(System.nanoTime());
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;

        try {
            Map map;
            try {
                String e = "multipart/form-data;charset=" + charset + ";boundary=" + boundary;
                conn = getConnection(new URL(url), "POST", e, headerMap);
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (IOException var23) {
                map = getParamsFromUrl(url);
                TaobaoLogger.logCommError(var23, url, (String)map.get("app_key"), (String)map.get("method"), params);
                throw var23;
            }

            try {
                out = conn.getOutputStream();
                byte[] e1 = ("\r\n--" + boundary + "\r\n").getBytes(charset);
                Set map1 = params.entrySet();
                Iterator fileEntrySet = map1.iterator();

                while(fileEntrySet.hasNext()) {
                    Map.Entry endBoundaryBytes = (Map.Entry)fileEntrySet.next();
                    byte[] fileEntry = getTextEntry((String)endBoundaryBytes.getKey(), (String)endBoundaryBytes.getValue(), charset);
                    out.write(e1);
                    out.write(fileEntry);
                }

                Set fileEntrySet1 = fileParams.entrySet();
                Iterator endBoundaryBytes1 = fileEntrySet1.iterator();

                while(endBoundaryBytes1.hasNext()) {
                    Map.Entry fileEntry1 = (Map.Entry)endBoundaryBytes1.next();
                    FileItem fileItem = (FileItem)fileEntry1.getValue();
                    if(fileItem.getContent() != null) {
                        byte[] fileBytes = getFileEntry((String)fileEntry1.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);
                        out.write(e1);
                        out.write(fileBytes);
                        out.write(fileItem.getContent());
                    }
                }

                byte[] endBoundaryBytes2 = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
                out.write(endBoundaryBytes2);
                rsp = getResponseAsString(conn);
            } catch (IOException var24) {
                map = getParamsFromUrl(url);
                TaobaoLogger.logCommError(var24, conn, (String)map.get("app_key"), (String)map.get("method"), params);
                throw var24;
            }
        } finally {
            if(out != null) {
                out.close();
            }

            if(conn != null) {
                conn.disconnect();
            }

        }

        return rsp;
    }*/

    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\";filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
        return entry.toString().getBytes(charset);
    }

    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, "UTF-8");
    }

    public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;

        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);

            Map map;
            try {
                conn = getConnection(buildGetUrl(url, query), "GET", ctype, (Map)null);
            } catch (IOException var15) {
                map = getParamsFromUrl(url);
//                TaobaoLogger.logCommError(var15, url, (String)map.get("app_key"), (String)map.get("method"), params);
                throw var15;
            }

            try {
                rsp = getResponseAsString(conn);
            } catch (IOException var14) {
                map = getParamsFromUrl(url);
//                TaobaoLogger.logCommError(var14, conn, (String)map.get("app_key"), (String)map.get("method"), params);
                throw var14;
            }
        } finally {
            if(conn != null) {
                conn.disconnect();
            }

        }

        return rsp;
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap) throws IOException {
        Object conn = (HttpURLConnection)url.openConnection();
        if(conn instanceof HttpsURLConnection) {
            HttpsURLConnection i$ = (HttpsURLConnection)conn;
            SSLContext entry;
            if(ignoreSSLCheck) {
                try {
                    entry = SSLContext.getInstance("TLS");
                    entry.init((KeyManager[])null, new TrustManager[]{new WebUtils.TrustAllTrustManager()}, new SecureRandom());
                    i$.setSSLSocketFactory(entry.getSocketFactory());
                    i$.setHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
                } catch (Exception var8) {
                    throw new IOException(var8);
                }
            } else {
                try {
                    entry = SSLContext.getInstance("TLS");
                    entry.init((KeyManager[])null, new TrustManager[]{new WebUtils.VerisignTrustManager()}, new SecureRandom());
                    i$.setSSLSocketFactory(entry.getSocketFactory());
                } catch (Exception var7) {
                    throw new IOException(var7);
                }
            }

            conn = i$;
        }

        ((HttpURLConnection)conn).setRequestMethod(method);
        ((HttpURLConnection)conn).setDoInput(true);
        ((HttpURLConnection)conn).setDoOutput(true);
        ((HttpURLConnection)conn).setRequestProperty("Accept", "text/xml,text/javascript");
        ((HttpURLConnection)conn).setRequestProperty("User-Agent", "top-sdk-java");
        ((HttpURLConnection)conn).setRequestProperty("Content-Type", ctype);
        if(headerMap != null) {
            Iterator i$1 = headerMap.entrySet().iterator();

            while(i$1.hasNext()) {
                Map.Entry entry1 = (Map.Entry)i$1.next();
                ((HttpURLConnection)conn).setRequestProperty((String)entry1.getKey(), (String)entry1.getValue());
            }
        }

        return (HttpURLConnection)conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if(UString.isEmpty(query)) {
            return url;
        } else {
            if(UString.isEmpty(url.getQuery())) {
                if(strUrl.endsWith("?")) {
                    strUrl = strUrl + query;
                } else {
                    strUrl = strUrl + "?" + query;
                }
            } else if(strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }

            return new URL(strUrl);
        }
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if(params != null && !params.isEmpty()) {
            StringBuilder query = new StringBuilder();
            Set entries = params.entrySet();
            boolean hasParam = false;
            Iterator i$ = entries.iterator();

            while(i$.hasNext()) {
                Map.Entry entry = (Map.Entry)i$.next();
                String name = (String)entry.getKey();
                String value = (String)entry.getValue();
                if(UString.isNotEmpty(new String[]{name, value})) {
                    if(hasParam) {
                        query.append("&");
                    } else {
                        hasParam = true;
                    }

                    query.append(name).append("=").append(URLEncoder.encode(value, charset));
                }
            }

            return query.toString();
        } else {
            return null;
        }
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if(es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if(UString.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            InputStreamReader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();
            char[] buff = new char[1024];
            boolean read = false;

            int read1;
            while((read1 = reader.read(buff)) > 0) {
                response.append(buff, 0, read1);
            }

            String var6 = response.toString();
            return var6;
        } finally {
            if(stream != null) {
                stream.close();
            }

        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = "UTF-8";
        if(!UString.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            String[] arr$ = params;
            int len$ = params.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String param = arr$[i$];
                param = param.trim();
                if(param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if(pair.length == 2 && !UString.isEmpty(pair[1])) {
                        charset = pair[1].trim();
                    }
                    break;
                }
            }
        }

        return charset;
    }

    public static String decode(String value) {
        return decode(value, "UTF-8");
    }

    public static String encode(String value) {
        return encode(value, "UTF-8");
    }

    public static String decode(String value, String charset) {
        String result = null;
        if(!UString.isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException var4) {
                throw new RuntimeException(var4);
            }
        }

        return result;
    }

    public static String encode(String value, String charset) {
        String result = null;
        if(!UString.isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException var4) {
                throw new RuntimeException(var4);
            }
        }

        return result;
    }

    private static Map<String, String> getParamsFromUrl(String url) {
        Object map = null;
        if(url != null && url.indexOf(63) != -1) {
            map = splitUrlQuery(url.substring(url.indexOf(63) + 1));
        }

        if(map == null) {
            map = new HashMap();
        }

        return (Map)map;
    }

    public static Map<String, String> splitUrlQuery(String query) {
        HashMap result = new HashMap();
        String[] pairs = query.split("&");
        if(pairs != null && pairs.length > 0) {
            String[] arr$ = pairs;
            int len$ = pairs.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String pair = arr$[i$];
                String[] param = pair.split("=", 2);
                if(param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

    /*static {
        InputStream input = null;

        try {
            CertificateFactory e = CertificateFactory.getInstance("X.509");
            input = WebUtils.class.getResourceAsStream("/verisign.crt");
            verisign = e.generateCertificate(input);
        } catch (Exception var10) {
            throw new RuntimeException(var10);
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException var9) {
                    ;
                }
            }

        }

    }*/

    public static class TrustAllTrustManager implements X509TrustManager {
        public TrustAllTrustManager() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    public static class VerisignTrustManager implements X509TrustManager {
        public VerisignTrustManager() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            X509Certificate taobao = null;
            X509Certificate[] e = chain;
            int len$ = chain.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                X509Certificate cert = e[i$];
                cert.checkValidity();

                try {
                    String e1 = cert.getSubjectX500Principal().getName();
                    LdapName ldapDN = new LdapName(e1);
                    Iterator i$1 = ldapDN.getRdns().iterator();

                    while(i$1.hasNext()) {
                        Rdn rdn = (Rdn)i$1.next();
                        if("CN".equals(rdn.getType()) && "*.taobao.com".equals(rdn.getValue())) {
                            taobao = cert;
                            break;
                        }
                    }
                } catch (Exception var13) {
                    throw new CertificateException(var13);
                }
            }

            if(taobao != null) {
                /*try {
                    taobao.verify(WebUtils.verisign.getPublicKey());
                } catch (Exception var12) {
                    throw new CertificateException(var12);
                }*/
            } else {
//                throw new CertificateException("Taobao.com certificate not exists!");
            }
        }
    }
}
