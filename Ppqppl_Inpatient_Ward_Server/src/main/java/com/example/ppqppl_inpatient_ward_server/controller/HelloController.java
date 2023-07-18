package com.example.ppqppl_inpatient_ward_server.controller;


import com.example.ppqppl_inpatient_ward_server.utils.UDPReceiver;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.stream.events.Comment;

//@Component
@Controller
public class HelloController {
//    @RequestMapping(value="/hello",method = RequestMethod.GET)
//    public String showHello(){
//        return "Hello ppqppl";
//    }

//    @RequestMapping("/hello")
//    public String showindex(ModelMap map) {
//        map.addAttribute("mmm", UDPReceiver.mmm);
//        return "hello";
//    }

//    @GetMapping("/test")
//    @ResponseBody
//    public String test() {
//        return "test";
//    }

    //提交评论表单，点击发布按钮走的post方法
//    @PostMapping("/comments")
//    public String post(Comment comment, HttpSession session) {
//        return "redirect:/startget";
//    }
    //该方法负责局部刷新页面的评论区
//    @GetMapping("/comments")
//    public String comments(Model map) {
//        map.addAttribute("mmm",UDPReceiver.mmm);
//        return "redirect:/hello";
//    }
//
//    @GetMapping("/test")
//    @ResponseBody
//    public String test() {
//        return UDPReceiver.mmm;
//    }



}
