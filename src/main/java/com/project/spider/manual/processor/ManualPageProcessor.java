package com.project.spider.manual.processor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * GRC在线开发手册页面爬虫处理类
 *
 * @author ftc
 * @date 2019-04-01
 */
public class ManualPageProcessor implements PageProcessor {

    private ConcurrentLinkedQueue<String> urlQueue = new ConcurrentLinkedQueue<String>();



    protected Site site = Site.me().setRetrySleepTime(3).setSleepTime(1000).setTimeOut(5000);

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
        elements.forEach(element -> {
            if (!urlQueue.contains(element.attr("href"))) {
                urlQueue.add(element.attr("href"));
                System.out.println(element.attr("href"));
            }
        });
        System.out.println("total url size:" + urlQueue.size());
}

    @Override
    public Site getSite() {
        return site;
    }

    public void setCookie(String key, String cookie) {
        site.addCookie(key, cookie);
    }

    public void setHeader(String key, String value) {
        site.addHeader(key, value);
    }
}
