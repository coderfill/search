package com.project.spider.manual.service.impl;

import com.project.spider.manual.processor.ManualPageProcessor;
import com.project.spider.manual.service.GRCManaualService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.http.HttpHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GRC在线开发手册爬虫服务接口实现类
 *
 * @author ftc
 * @date 2019-04-02
 */
@Service
public class GRCManaualServiceImpl implements GRCManaualService, InitializingBean {

    /**
     * 在线开发手册登录地址
     */
    protected static final String login_url = "http://dmserver.smartdot.com.cn/names.nsf?Login";

    /**
     * 进行爬虫操作的账号
     */
    protected static final String crawler_username = "fangzz";

    /**
     * 进行爬虫操作的密码
     */
    protected static final String crawler_password = "fausto@100804";

    /**
     * 登录成功后的跳转地址
     */
    protected static final String redirect_to = "/indishare/securtrac.nsf/agttrac?openagent&url=/product/grcv5/xmzd.nsf/frmindex";


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public String getPageHtmlContent(String target_url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(target_url);
        String cookie = loginAndGetCookie();

        ManualPageProcessor manualPageProcessor = new ManualPageProcessor();
        manualPageProcessor.setCookie("Cookie", cookie);
        manualPageProcessor.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        manualPageProcessor.setHeader("Accept-Encoding", "gzip, deflate");
        manualPageProcessor.setHeader("Accept-Language", "zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        manualPageProcessor.setHeader("Cache-Control", "max-age=0");
        manualPageProcessor.setHeader("Connection", "keep-alive");
        manualPageProcessor.setHeader("Content-Type", "application/x-www-form-urlencoded");
        manualPageProcessor.setHeader("Host", "dmserver.smartdot.com.cn");
        manualPageProcessor.setHeader("Origin", "http://dmserver.smartdot.com.cn");
        manualPageProcessor.setHeader("Referer", "http://dmserver.smartdot.com.cn/product/grcv5/xmzd.nsf/frmindex");
        manualPageProcessor.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        manualPageProcessor.setHeader("Cookie", cookie);

        Spider.create(manualPageProcessor).addUrl(target_url).run();

        return null;
    }


    /**
     * 模拟浏览器登录操作，并获取对应用户的cookie
     * @return
     * @throws IOException
     */
    protected String loginAndGetCookie() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(login_url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair username = new BasicNameValuePair("Username", crawler_username);
        NameValuePair password = new BasicNameValuePair("Password", crawler_password);
        NameValuePair redirectTo = new BasicNameValuePair("RedirectTo", redirect_to);

        params.add(username);
        params.add(password);
        params.add(redirectTo);

        HttpEntity entity = new UrlEncodedFormEntity(params, "utf-8");
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Accept-Language", "zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        httpPost.setHeader("Cache-Control", "max-age=0");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Host", "dmserver.smartdot.com.cn");
        httpPost.setHeader("Origin", "http://dmserver.smartdot.com.cn");
        httpPost.setHeader("Referer", "http://dmserver.smartdot.com.cn/product/grcv5/xmzd.nsf/frmindex");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        httpPost.setEntity(entity);

        HttpResponse response = client.execute(httpPost);
        /**
         * 从{@link HttpResponse}中获取cookie
         */
        String cookie = response.getHeaders("Set-Cookie")[0].getValue();
        return cookie;
    }
}
