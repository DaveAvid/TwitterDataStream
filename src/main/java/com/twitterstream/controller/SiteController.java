//package com.twitterstream.controller;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.SortDefault;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//public class SiteController {
//    @RequestMapping("/")
//    public String list(ModelMap model, @SortDefault("username") Pageable pageable){
//        model.addAttribute("page", userService.find(pageable));
//
//        return "index";
//    }
//}
