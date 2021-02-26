package org.oraclemei.demo01;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description:
 * @author: meiyc
 * @createDate: 2021/2/25 20:04
 * @version: 1.0
 */
public class HttpRequestTest {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:9200/books/_search?pretty=true");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (con.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String str = null;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
        }else{
            System.out.println("出现异常");
        }
    }
}
