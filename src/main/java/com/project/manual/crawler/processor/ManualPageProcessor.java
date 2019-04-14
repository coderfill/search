package com.project.manual.crawler.processor;

import com.project.base.logger.LoggerBaseSupport;
import com.project.solr.index.service.IndexService;
import com.project.manual.crawler.info.ManualConfig;
import com.project.solr.index.info.ManualIndexFileInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.*;

/**
 * GRC在线开发手册页面爬虫处理类
 *
 * @author ftc
 * @date 2019-04-01
 */
@Service("manual")
public class ManualPageProcessor extends LoggerBaseSupport implements PageProcessor, InitializingBean {

    protected Site site = null;

    protected String cookie = null;

    protected Set<String> detailUrlSet = new HashSet<String>();

    protected Map<String, String> headerMap = new HashMap<String, String>();


    @Autowired
    @Qualifier("manualIndexService")
    private IndexService indexService;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    public void process(Page page) {
        String htmlContent = page.getHtml()
                                .xpath("//div[@id='viewValue']//textarea[@name='viewHtmlValue']")
                                .get()
                                .replace("&lt;", "<")
                                .replace("&gt;",">")
                                .replace("textarea", "div");

        Elements trs = Jsoup.parse(htmlContent).getElementsByAttributeValue("valign", "top");
        trs.forEach(tr -> {
            Elements tds = tr.getElementsByAttribute("nowrap");
            ManualIndexFileInfo indexFileInfo = new ManualIndexFileInfo();

            indexFileInfo.setId(UUID.randomUUID().toString());
            indexFileInfo.setTitle(tds.get(0).getElementsByTag("a").text());
            indexFileInfo.setVersion(tds.get(1).getElementsByTag("a").text());
            indexFileInfo.setType(tds.get(2).getElementsByTag("a").text());
            indexFileInfo.setAuthor(tds.get(3).getElementsByTag("a").text());
            indexFileInfo.setCreateTime(tds.get(4).getElementsByTag("a").text());

            String detail_url = tds.get(0).getElementsByTag("a").attr("href");
            indexFileInfo.setUrl(ManualConfig.domain + detail_url.substring(1));
            if (!detailUrlSet.contains(detail_url)) {
                logger.debug("------------------------------");
                detailUrlSet.add(detail_url);
                crawlerDetail(indexFileInfo);
            }
        });
        System.out.println(detailUrlSet.size());
    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 获取cookie
     * @return
     */
    public String getCookie() {
        return cookie;
    }

    protected void init() {
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headerMap.put("Accept-Encoding", "gzip, deflate");
        headerMap.put("Accept-Language", "zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        headerMap.put("Cache-Control", "max-age=0");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap.put("Host", "dmserver.smartdot.com.cn");
        headerMap.put("Origin", "http://dmserver.smartdot.com.cn");
        headerMap.put("Referer", "http://dmserver.smartdot.com.cn/product/grcv5/xmzd.nsf/frmindex");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        cookie = loginAndGetCookie();
        site = Site.me().setRetrySleepTime(3).setSleepTime(1000).setTimeOut(5000);
        setSite();
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
            Set<Map.Entry<String, String>> entries = headerMap.entrySet();
            for (Map.Entry entry : entries) {
                httpPost.setHeader(entry.getKey().toString(), entry.getValue().toString());
            }
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
        Set<Map.Entry<String, String>> entries = headerMap.entrySet();
        for (Map.Entry entry : entries) {
            site.addHeader(entry.getKey().toString(), entry.getValue().toString());
        }
        site.addCookie("Cookie", cookie);
        site.addHeader("Cookie", cookie);
    }

    protected void crawlerDetail(ManualIndexFileInfo indexFileInfo) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String detail_url = indexFileInfo.getUrl();
        HttpGet httpGet = new HttpGet(detail_url);
        Set<Map.Entry<String, String>> entries = headerMap.entrySet();
        for (Map.Entry entry : entries) {
            httpGet.setHeader(entry.getKey().toString(), entry.getValue().toString());
        }
        httpGet.setHeader("Cookie", cookie);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            String content = EntityUtils.toString(response.getEntity(), "utf-8");
            Document doc = Jsoup.parse(content);
            String keyword = "";
            String description = "";
            Elements trs = doc.getElementsByClass("tableClass-singleRow").get(0).getElementsByTag("tr");
            switch (indexFileInfo.getType()) {
                case "常见问题":
                    description = trs.get(0).getElementsByClass("tdContent").text();
                    keyword = trs.get(4).getElementsByClass("tdContent").text();
                    indexFileInfo.setDescription(description);
                    indexFileInfo.setKeyword(keyword);
                    break;
                case "公告":
                    indexFileInfo.setTitle("公告");
                    break;
                case "平台API":
                    description = trs.get(11).getElementsByClass("tdContent").text();
                    keyword = trs.get(14).getElementsByClass("tdContent").text();
                    indexFileInfo.setDescription(description);
                    indexFileInfo.setKeyword(keyword);
                    break;
                case "开发帮助":
                    keyword = trs.get(5).getElementsByClass("tdContent").text();
                    indexFileInfo.setDescription(description);
                    indexFileInfo.setKeyword(keyword);
                    break;
                default:
                    break;

            }
            logger.debug(indexFileInfo.toString());
            indexService.addIndex(indexFileInfo);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("crawler url:" + detail_url + ", error, message:" +e.getMessage());
        }

    }
}
