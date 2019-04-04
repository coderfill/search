package com.project.spider.manual.processor;

import com.project.base.LoggerBaseSupport;
import com.project.spider.manual.config.ManualConfig;
import com.project.spider.manual.config.ManualUrlStroage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GRC在线开发手册页面爬虫处理类
 *
 * @author ftc
 * @date 2019-04-01
 */
@Service("manual")
public class ManualPageProcessor extends LoggerBaseSupport implements PageProcessor, InitializingBean {

    protected Site site = null;

    protected volatile static String cookie = null;


    @Autowired
    private AsyncTaskExecutor taskExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        cookie = loginAndGetCookie();
        site = Site.me().setRetrySleepTime(3).setSleepTime(1000).setTimeOut(5000);
        setSite();
    }

    @Override
    public void process(Page page) {
        String htmlContent = page.getHtml()
                                .xpath("//div[@id='viewValue']//textarea[@name='viewHtmlValue']")
                                .get()
                                .replace("&lt;", "<")
                                .replace("&gt;","/>")
                                .replace("textarea", "table");
        Document doc = Jsoup.parse(htmlContent);
        Elements elements = doc.select("a[href]");
        ManualUrlStroage stroage =  new ManualUrlStroage();
        taskExecutor.submit(() ->{
            try {
                stroage.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        elements.forEach(element -> {
            try {
                stroage.push(element.attr("href"));
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("add doc url:" + element.attr("href") + " error, message:" + e.getMessage());
            }
        });
    }

    @Override
    public Site getSite() {
        return site;
    }


    /**
     * 模拟浏览器登录操作，并获取对应用户的cookie
     * @return
     * @throws IOException
     */
    protected String loginAndGetCookie() {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(ManualConfig.login_url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair username = new BasicNameValuePair("Username", ManualConfig.crawler_username);
        NameValuePair password = new BasicNameValuePair("Password", ManualConfig.crawler_password);
        NameValuePair redirectTo = new BasicNameValuePair("RedirectTo", ManualConfig.redirect_to);

        params.add(username);
        params.add(password);
        params.add(redirectTo);
        try {
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
            logger.debug("get cookie from [" + ManualConfig.login_url + "] success, value:" + cookie);
            return cookie;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("get cookie from [" + ManualConfig.login_url + "] error, message:" + e.getMessage());
        }
        return null;
    }

    protected void setSite() {
        site.addCookie("Cookie", cookie);
        site.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        site.addHeader("Accept-Encoding", "gzip, deflate");
        site.addHeader("Accept-Language", "zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        site.addHeader("Cache-Control", "max-age=0");
        site.addHeader("Connection", "keep-alive");
        site.addHeader("Content-Type", "application/x-www-form-urlencoded");
        site.addHeader("Host", "dmserver.smartdot.com.cn");
        site.addHeader("Origin", "http://dmserver.smartdot.com.cn");
        site.addHeader("Referer", "http://dmserver.smartdot.com.cn/product/grcv5/xmzd.nsf/frmindex");
        site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        site.addHeader("Cookie", cookie);
    }
}
