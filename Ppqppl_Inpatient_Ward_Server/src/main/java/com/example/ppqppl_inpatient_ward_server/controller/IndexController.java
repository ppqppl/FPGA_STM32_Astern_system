package com.example.ppqppl_inpatient_ward_server.controller;

import com.example.ppqppl_inpatient_ward_server.utils.IfLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("")
    public String showindex(ModelMap map){
        return "redirect:/login";
    }

    @RequestMapping("/index")
    public String showmain(ModelMap map){
        if(IfLogin.IF_Login == true) {
            return "index";
        }
        return "redirect:/login";
    }

}
