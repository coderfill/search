package com.project.manual.web;

import com.project.base.logger.LoggerBaseSupport;
import com.project.manual.service.ManualService;
import com.project.solr.search.info.base.ResultInfo;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ftc
 * @date 2019-04-01
 */
@Controller
@RequestMapping(value = "/grcManual")
public class ManualController extends LoggerBaseSupport {

    /**
     * 此地址是在线开发手册搜索结果的地址，搜索关键字为*，并且不对搜索结果进行分页
     */
    protected static final String target_page_url = "http://dmserver.smartdot.com.cn/product/grcv5/xmzd.nsf/vwalldocformyviewsearch?searchview&searchorder=3&view=vwalldocformyviewsearch&query=*";

    @Autowired
    private ManualService manualService;


    @RequestMapping(value = "/spider.do")
    public void spider() {
        try {
            manualService.crawler(target_page_url);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("crawler " + target_page_url + ", error");
        }
    }


    @RequestMapping(value = "/search.do")
    public String search(String keyword, Model model) {
        try {
            ResultInfo resultInfo = manualService.searchByKeyword(keyword);
            model.addAttribute("resultInfo",resultInfo);
            return "resultList";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 访问在线开发手册详情页面
     * @param detailUrl 详情页url
     */
    @RequestMapping(value = "/detail.do")
    public void openDetailUrl(HttpServletRequest request, HttpServletResponse response, String detailUrl) {
        String test_url = "http://dmserver.smartdot.com.cn/product/grcv5/xmzd.nsf/7d385ced8df3fd4948257e270021a592/191f601df7a4f2534825821e001e5ebe?OpenDocument&Highlight=0,*";
        try {
            manualService.setResponse(response);
            response.sendRedirect(test_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
