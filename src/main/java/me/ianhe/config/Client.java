package me.ianhe.config;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author iHelin
 * @since 2017/9/20 13:40
 */
public class Client {
    private boolean loginflag = false;
    private String ptuiCBurl = "";
    private String ptwebqq = "";
    private String psessionid = "";
    private String clientid = "53999199";
    private String appid = "";
    private String getCookieurl = "";
    private String iamgeImg = "/Users/iHelin/Downloads";
    private String SmartQQUrl = "https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&target=self&style=16&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001";
    private String Referer = "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";
    private String httpsReferer = "https://d1.web2.qq.com/cfproxy.html?v=20151105001&callback=1";
    private String mainPage = "http://w.qq.com/";
    private String sign = "";
    private String JsVer = "";
    private String MiBaoCss = "m_webqq";
    private String qrsig = "";

    public CloseableHttpClient pageMain(CloseableHttpClient httpClient) {
        HttpGet httpget = new HttpGet(mainPage);
        httpget.setHeader("Accept", "application/javascript, */*;q=0.8");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
        try {
            CloseableHttpResponse response = httpClient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("正在获取登陆页面");
        return httpClient;
    }

    public CloseableHttpClient getConfig(CloseableHttpClient httpClient) {
        HttpGet httpget = new HttpGet(SmartQQUrl);
        httpget.setHeader("Accept", "application/javascript, */*;q=0.8");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
        httpget.setHeader("Referer", mainPage);
        String html = "";
        try {
            CloseableHttpResponse response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(html);

        Document doc = Jsoup.parse(html);
        Element element = doc.select("input[name=aid]").first();
        appid = element.val();
        System.out.println("获取appid成功：" + appid);

        String regex = "(g_login_sig=encodeURIComponent\\(\")(\\d+)(\"\\))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(html);
        if (m.find()) {
            sign = m.group(2);
            System.out.println("获取sign成功：" + sign);
        }

        regex = "(g_pt_version=encodeURIComponent\\(\")(\\d+)(\"\\))";
        p = Pattern.compile(regex);
        m = p.matcher(html);
        if (m.find()) {
            JsVer = m.group(2);
            System.out.println("获取JsVer成功：" + JsVer);
        }

        /*regex = "(g_mibao_css=encodeURIComponent\\(\")(.+)(\"\\))";
        p = Pattern.compile(regex);
        m = p.matcher(html);
        if (m.find()) {
            MiBaoCss = m.group(2);
            System.out.println("获取MiBaoCss成功：" + MiBaoCss);
        }*/
        return httpClient;
    }

    public CloseableHttpClient getErweima(CloseableHttpClient httpClient) {
        Random random = new Random();
        HttpGet httpget = new HttpGet("https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=0.377023" + random.nextInt(10) + "35550823" + random.nextInt(10));
        httpget.setHeader("Accept", "application/javascript, */*;q=0.8");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");

        System.out.println("获取二维码：Executing request " + httpget.getURI());//开始
        FileOutputStream fos;
        try {
            CloseableHttpResponse response = httpClient.execute(httpget);
            qrsig = HttpTool.getCookie("qrsig", response);
            System.out.println(qrsig);

            System.out.println(response.getStatusLine());
            InputStream inputStream = response.getEntity().getContent();
            File file = new File(this.iamgeImg);
            if (!file.exists()) {
                file.mkdirs();
            }
            fos = new FileOutputStream("/Users/iHelin/Downloads/test.jpg");
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(data)) != -1) {
                fos.write(data, 0, len);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("二维码获取成功！");


        return httpClient;
    }

    /**
     * 检查是否登录成功
     *
     * @param httpClient
     * @return
     */
    public CloseableHttpClient checkLogin(CloseableHttpClient httpClient) {
        int e = 0;
        for (char i : qrsig.toCharArray()) {
            e += (e << 5) + (int) i;
        }
        int ptqrtoken = 2147483647 & e;

        HttpGet httpget = new HttpGet("https://ssl.ptlogin2.qq.com/ptqrlogin?ptqrtoken=" + ptqrtoken + "&webqq_type=10&remember_uin=1&login2qq=1&aid=" + appid + "&u1=http%3A%2F%2Fw.qq.com%2Fproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=0-0-62857&mibao_css=" + MiBaoCss + "&t=undefined&g=1&js_type=0&js_ver=" + JsVer + "&login_sig=" + sign + "&pt_randsalt=0");
        System.out.println("检查是否登录成功：Executing request " + httpget.getURI());//开始
        String html;
        try {
            CloseableHttpResponse response = httpClient.execute(httpget);
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity, "utf-8");
            //ptuiCB('0','0','http://ptlogin4.web2.qq.com/check_sig?pttype=1&uin=1069478446&service=ptqrlogin&nodirect=0&ptsigx=afde2a7fe5f26485b976c9f0f0d87c1ebf27706be0d7b9a0fbab5df1d9e5ec9fb1df62d5cdef526fa7e2df2b3ce2dd84fad270fbdfc90bdd4da0308f73a337fd&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html%3Flogin2qq%3D1%26webqq_type%3D10&f_url=&ptlang=2052&ptredirect=100&aid=501004106&daid=164&j_later=0&low_login_hour=0&regmaster=0&pt_login_type=3&pt_aid=0&pt_aaid=16&pt_light=0&pt_3rd_aid=0','0','登录成功！', 'lonter');
            if (html.indexOf("登录成功") != -1) {
                System.out.println("登录成功");
//                this.appid = HttpTool.getaid(html);
                this.loginflag = true;
                int start = html.indexOf("http:");
                int end = html.indexOf("pt_3rd_aid");
                this.ptuiCBurl = html.substring(start, end + 12);
                System.out.println(this.ptuiCBurl);
                this.ptwebqq = HttpTool.getCookie("ptwebqq", response);
                System.out.println("this.ptwebqq:" + this.ptwebqq);
            }
            System.out.println(html);
        } catch (ClientProtocolException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return httpClient;
    }

    public CloseableHttpClient getSessionID(CloseableHttpClient httpClient) {
        HttpPost httppost = new HttpPost("http://d1.web2.qq.com/channel/login2");
        String html;
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("r", "{\"ptwebqq\":\"" + this.ptwebqq + "\",\"clientid\":53999199,\"psessionid\":\"\",\"status\":\"online\"}"));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("获取sessionID");
            HttpResponse response = httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity, "utf-8");
            System.out.println(html);
            int start = html.indexOf("psessionid\":\"") + 13;
            html = html.substring(start);
            int end = html.indexOf("\"");
            html = html.substring(0, end);
            this.psessionid = html;
            System.out.println(html);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpClient;
    }

    /**
     * 获得登录参数
     *
     * @param httpclient
     * @return
     */
    public CloseableHttpClient getPara(CloseableHttpClient httpclient) {
        HttpGet httpget = new HttpGet(this.ptuiCBurl);
        System.out.println("获得登录参数： Executing request " + httpget.getURI());//开始
        String html;
        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity, "utf-8");
            System.out.println(html);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpclient;
    }

    /**
     * 获得消息 需要对Header进行设置！
     *
     * @param httpClient
     */
    public String getMsg(CloseableHttpClient httpClient) {

        HttpPost httpPost = new HttpPost("http://d1.web2.qq.com/channel/poll2");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Origin", "http://d1.web2.qq.com");
        httpPost.setHeader("Referer", "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36 QQBrowser/4.2.4763.400");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("r", "{\"ptwebqq\":\"" + this.ptwebqq + "\",\"clientid\":53999199,\"psessionid\":\"" + this.psessionid + "\",\"key\":\"\"}"));
        String html = "";
        try {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(uefEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity, "utf-8");
            System.out.println("接收消息：" + html + "\t" + html.length());
            if (html.length() > 130) {
                String group_code = HttpTool.getgroup_code(html);//判断群
                System.out.println("group_code:" + group_code);
                String msg = HttpTool.getmsgtext(html);
                System.out.println(msg);
                sendMsg(httpClient, "呵呵", group_code);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    public void sendMsg(CloseableHttpClient httpClient, String sms, String group_code) {
        HttpPost httpPost = new HttpPost("http://d1.web2.qq.com/channel/send_qun_msg2");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Origin", "http://d1.web2.qq.com");
        httpPost.setHeader("Referer", "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36 QQBrowser/4.2.4763.400");
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("r", "{\"group_uin\":" + group_code + ",\"content\":\"[\\\"" + sms + "\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":525,\"clientid\":53999199,\"msg_id\":52370001,\"psessionid\":\"" + this.psessionid + "\"}"));
        System.out.println("发送的参数：" + formparams.get(0).getValue());
        String html;
        try {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(uefEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity, "utf-8");
            System.out.println("发送结果：" + html);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 此函数运行，等待二维码生成后，扫描二维码即可登陆
     * bug：目前所有模拟登陆都存在的bug，需要先用浏览器打开smart qq，扫描登陆后再运行本程序
     * 即想本程序正常运行，需要先打开浏览器登录smart qq，然后再用本程序登陆，本程序登陆后
     * 就不用管用浏览器登录的那个了。市面上很多模拟登陆都存在这个问题哦，不止我的，这是cookie
     * 跨域了还是咋地，搞不懂
     *
     * @param args
     */
    public static void main(String[] args) {
        Client client = new Client();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        httpClient = client.pageMain(httpClient);
        httpClient = client.getConfig(httpClient);

        httpClient = client.getErweima(httpClient);
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.checkLogin(httpClient);
            if (client.loginflag)
                break;
        }

        httpClient = client.getPara(httpClient);
        httpClient = client.getSessionID(httpClient);
        for (int i = 0; ; i++) {
            client.getMsg(httpClient);
        }
    }
}
