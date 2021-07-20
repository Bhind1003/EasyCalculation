package com.example.calculation.util;

import android.accounts.NetworkErrorException;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtils {
    public static final String myIp="http://47.117.142.177:9604/";//192.168.52.21
    @Nullable
    public static String post(String url, String content) {
        HttpURLConnection conn = null;
        try {
            // 创建一个URL对象
            URL mURL = new URL(url);
            // 调用URL的openConnection()方法,获取HttpURLConnection对象
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("POST");// 设置请求方法为post
            //设置请求头
//            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");//设置参数类型是json格式
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("logType", "base");
            conn.setReadTimeout(5000);// 设置读取超时为5秒
            conn.setConnectTimeout(10000);// 设置连接网络超时为10秒
            conn.setDoOutput(true);// 设置此方法,允许向服务器输出内容

            // post请求的参数
            // 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
            OutputStream out = conn.getOutputStream();// 获得一个输出流,向服务器写数据
            out.write(content.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                return getStringFromInputStream(is);
            } else {
                throw new NetworkErrorException("response status is " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }
        return null;
    }

    @Nullable
    public static String get(String url) {
        HttpURLConnection conn = null;
        try {
            // 利用string url构建URL对象
            URL mURL = new URL(url);// 创建一个URL对象
            conn = (HttpURLConnection) mURL.openConnection();// 调用URL的openConnection()方法,获取HttpURLConnection对象

            conn.setRequestMethod("GET");// 设置请求方法为GET
            conn.setReadTimeout(5000);// 设置读取超时为5秒
            conn.setConnectTimeout(10000);// 设置连接网络超时为10秒

            int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
            if (responseCode == 200) {// 成功得到请求结果

                InputStream is = conn.getInputStream();
                return getStringFromInputStream(is);
            } else {
                throw new NetworkErrorException("response status is " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();// 关闭连接
            }
        }
        return null;
    }

    @NotNull
    private static String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }
}
