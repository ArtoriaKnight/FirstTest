package com.tutu2.util;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author pillar
 *         2017/6/13
 */
public class HttpUtil {

    public static String doGet(String url) throws IOException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDefaultUseCaches(false);
            if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
                //得到输入流
                InputStream is = httpURLConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while (-1 != (len = is.read(buffer))) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }
                return baos.toString("utf-8");
            }
        }catch (Exception e){
                e.printStackTrace();
            return null;
        }finally {
            if (httpURLConnection!=null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

    public static String doPost(String url,String params){
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream bos = null;
        PrintWriter printWriter = null;
        OutputStream os = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            os = httpURLConnection.getOutputStream();
                printWriter = new PrintWriter(os);
                printWriter.write(params);
                printWriter.flush();
                bis = new BufferedInputStream(httpURLConnection.getInputStream());
                bos = new ByteArrayOutputStream();
                int len;
                byte[] arr = new byte[1024];
                while ((len = bis.read(arr)) != -1) {
                    bos.write(arr, 0, len);
                    bos.flush();
                }
                bos.close();
                return bos.toString("GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (bos !=null) {
                    bos.close();
                }
            }catch (IOException e){
            }
            try {
                if (bis !=null) {
                    bis.close();
                }
            }catch (IOException e){
            }
            if (httpURLConnection!=null) {
                httpURLConnection.disconnect();
            }
            if (printWriter != null) {
                printWriter.close();
            }
            try {
                if (os!=null) {
                    os.close();
                }
            }catch (IOException o){}
        }
        return null;
    }

    public static String doPostHttps(String url,String params) {
        HttpsURLConnection httpURLConnection = null;
        ByteArrayOutputStream bos = null;
        BufferedInputStream bis = null;
        PrintWriter printWriter = null;
        OutputStream os = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            httpURLConnection = (HttpsURLConnection)  new URL(url).openConnection();
            httpURLConnection.setSSLSocketFactory(ssf);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(10000);//读取超时 单位毫秒
            // 获取URLConnection对象对应的输出流
            os = httpURLConnection.getOutputStream();
            printWriter = new PrintWriter(os);
            // 发送请求参数
            printWriter.write(params);//post的参数
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            bis = new BufferedInputStream(httpURLConnection.getInputStream());
            bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (bos !=null) {
                    bos.close();
                }
            }catch (IOException e){
            }
            try {
                if (bis !=null) {
                    bis.close();
                }
            }catch (IOException e){
            }
            if (httpURLConnection!=null) {
                httpURLConnection.disconnect();
            }
            if (printWriter != null) {
                printWriter.close();
            }
            try {
                if (os!=null) {
                    os.close();
                }
            }catch (IOException o){}
        }
        return null;
    }

    public static String doGetHttps(String url) {
        HttpsURLConnection httpURLConnection = null;
        ByteArrayOutputStream bos = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            httpURLConnection = (HttpsURLConnection)  new URL(url).openConnection();
            httpURLConnection.setSSLSocketFactory(ssf);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(10000);//读取超时 单位毫秒
            //开始获取数据
            bis = new BufferedInputStream(httpURLConnection.getInputStream());
            bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        } catch (Exception e) {
          e.printStackTrace();
        }finally {
            try {
                if (bos !=null) {
                    bos.close();
                }
            }catch (IOException e){
            }
            try {
                if (bis !=null) {
                    bis.close();
                }
            }catch (IOException e){
            }
            if (httpURLConnection!=null) {
                httpURLConnection.disconnect();
            }
            try {
                if (os!=null) {
                    os.close();
                }
            }catch (IOException o){}
        }
        return null;
    }

    /**
     * http post下载文件
     * @param url
     * @param params
     */
    public static void doPostFile(String url, String params,String fileName) {
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        PrintWriter printWriter = null;
        OutputStream os = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            os = httpURLConnection.getOutputStream();
            printWriter = new PrintWriter(os);
            printWriter.write(params);
            printWriter.flush();
            bis = new BufferedInputStream(httpURLConnection.getInputStream());
            File file = new File(fileName);
            fos = new FileOutputStream(file);
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                fos.write(arr, 0, len);
                fos.flush();
            }
            fos.close();
        } catch (Exception e) {
          e.printStackTrace();
        }finally {
            try {
                if (fos !=null) {
                    fos.close();
                }
            }catch (IOException e){
            }
            try {
                if (bis !=null) {
                    bis.close();
                }
            }catch (IOException e){
            }
            if (httpURLConnection!=null) {
                httpURLConnection.disconnect();
            }
            if (printWriter != null) {
                printWriter.close();
            }
            try {
                if (os!=null) {
                    os.close();
                }
            }catch (IOException o){}
        }
    }
}
