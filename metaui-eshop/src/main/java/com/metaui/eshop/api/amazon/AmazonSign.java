package com.metaui.eshop.api.amazon;

import com.metaui.core.util.JSoupParser;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;


/**
 * @author wei_jc
 * @since 1.0
 */
public class AmazonSign {
    private static final String CHARACTER_ENCODING = "UTF-8";
    final static String ALGORITHM = "HmacSHA256";

    public static void main(String[] args) throws Exception {
        // Change this secret key to yours
        String secretKey = "WOw+6dMYD2W/Qt5i3FzM0TNMOe4jpGEACmhEvlSW";

        // Use the endpoint for your marketplace
        String serviceUrl = "https://mws.amazonservices.com.cn/Orders/2013-09-01";

        // Create set of parameters needed and store in a map
        HashMap<String, String> parameters = new HashMap<String, String>();

        System.out.println("\u6ca1\u6709\u7528\u5e10\u53f7\u767b\u5f55");

        // Add required parameters. Change these as needed.
        parameters.put("AWSAccessKeyId", urlEncode("AKIAIUPF5HHXOHP33PXQ"));
        parameters.put("Action", urlEncode("GetOrder"));
//        parameters.put("MWSAuthToken", urlEncode(""));
        parameters.put("SellerId", urlEncode("A2BJRKK636DQMN"));
        parameters.put("SignatureMethod", urlEncode(ALGORITHM));
        parameters.put("SignatureVersion", urlEncode("2"));
//        parameters.put("SubmittedFromDate", urlEncode("2013-05-01T12:00:00Z"));
        parameters.put("Timestamp", urlEncode("2016-04-11T21:42:52Z"));
        parameters.put("Version", urlEncode("2013-09-01"));

        System.out.println(new Date());
        // Format the parameters as they will appear in final format
        // (without the signature parameter)
        String formattedParameters = calculateStringToSignV2(parameters, serviceUrl);

        String signature = sign(formattedParameters, secretKey);

        // Add signature to the parameters and display final results
//        parameters.put("Signature", urlEncode(signature));
        parameters.put("Signature", "g/YTA9HMsbWJZ7v9YBmQewD6FtkDD8SlWKZYVQNpgjY=");
        parameters.put("AmazonOrderId.Id.1", "C02-3104734-8660044");
        String url = calculateStringToSignV2(parameters, serviceUrl);
        System.out.println(url);
        url = "https://mws.amazonservices.com.cn/Orders/2013-09-01?AWSAccessKeyId=AKIAIUPF5HHXOHP33PXQ&Action=GetOrder&SellerId=A2BJRKK636DQMN&SignatureVersion=2&Timestamp=2016-04-11T02%3A44%3A48Z&Version=2013-09-01&Signature=8W0iiMV0Jtea%2BnukhZnqfb1pa6zIUEeRmmA6vOh2tWI%3D&SignatureMethod=HmacSHA256&AmazonOrderId.Id.1=C03-6051918-4780805";
        JSoupParser parser = new JSoupParser("https://mws.amazonservices.com.cn/Orders/2013-09-01");
        Map<String, String> headers = new HashMap<>();
        headers.put("x-amazon-user-agent", "AmazonJavascriptScratchpad/1.0 (Language=Javascript)\n");
        headers.put("X-Requested-With", "XMLHttpRequest\n");
        Map<String, String> cookies = new HashMap<>();
        cookies.put("session-id-time-cn", "1460962800l");
        cookies.put("session-id-cn", "475-2855409-1672501");
        cookies.put("ubid-acbcn", "475-9817303-5467017");
        cookies.put("csm-hit", "32.13|1460410865762");
        Document doc = parser.parse(parameters,headers, cookies);
//        Document doc = Jsoup.connect(serviceUrl).header("x-amazon-user-agent", "AmazonJavascriptScratchpad/1.0 (Language=Javascript)").data(parameters).get();
        System.out.println(doc.html());
    }

    /* If Signature Version is 2, string to sign is based on following:
    *
    *    1. The HTTP Request Method followed by an ASCII newline (%0A)
    *
    *    2. The HTTP Host header in the form of lowercase host,
    *       followed by an ASCII newline.
    *
    *    3. The URL encoded HTTP absolute path component of the URI
    *       (up to but not including the query string parameters);
    *       if this is empty use a forward '/'. This parameter is followed
    *       by an ASCII newline.
    *
    *    4. The concatenation of all query string components (names and
    *       values) as UTF-8 characters which are URL encoded as per RFC
    *       3986 (hex characters MUST be uppercase), sorted using
    *       lexicographic byte ordering. Parameter names are separated from
    *       their values by the '=' character (ASCII character 61), even if
    *       the value is empty. Pairs of parameter and values are separated
    *       by the '&' character (ASCII code 38).
    *
    */
    private static String calculateStringToSignV2(
            Map<String, String> parameters, String serviceUrl)
            throws SignatureException, URISyntaxException {
        // Sort the parameters alphabetically by storing
        // in TreeMap structure
        Map<String, String> sorted = new TreeMap<String, String>();
        sorted.putAll(parameters);

        // Set endpoint value
        URI endpoint = new URI(serviceUrl.toLowerCase());

        // Create flattened (String) representation
        StringBuilder data = new StringBuilder();
        data.append("POST\n");
        data.append(endpoint.getHost());
        data.append("\n/");
        data.append("\n");

        Iterator<Map.Entry<String, String>> pairs =
                sorted.entrySet().iterator();
        while (pairs.hasNext()) {
            Map.Entry<String, String> pair = pairs.next();
            if (pair.getValue() != null) {
                data.append(pair.getKey() + "=" + pair.getValue());
            } else {
                data.append(pair.getKey() + "=");
            }

            // Delimit parameters with ampersand (&)
            if (pairs.hasNext()) {
                data.append("&");
            }
        }

        return data.toString();
    }

    /*
     * Sign the text with the given secret key and convert to base64
     */
    private static String sign(String data, String secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException,
            IllegalStateException, UnsupportedEncodingException {
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(new SecretKeySpec(secretKey.getBytes(CHARACTER_ENCODING),
                ALGORITHM));
        byte[] signature = mac.doFinal(data.getBytes(CHARACTER_ENCODING));
        String signatureBase64 = new String(Base64.encodeBase64(signature),
                CHARACTER_ENCODING);
        return new String(signatureBase64);
    }

    private static String urlEncode(String rawValue) {
        String value = (rawValue == null) ? "" : rawValue;
        String encoded = null;

        try {
            encoded = URLEncoder.encode(value, CHARACTER_ENCODING)
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unknown encoding: " + CHARACTER_ENCODING);
            e.printStackTrace();
        }

        return encoded;
    }
}

