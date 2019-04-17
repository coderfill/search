package com.project.base.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ftc
 * @date 2019-04-15
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("/search");
    }
}
