package com.example.ppqppl_inpatient_ward_server.controller;

import com.example.ppqppl_inpatient_ward_server.utils.IfLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index1Controller {

    @RequestMapping("/index1")
    public String showmain(ModelMap map){
        if(IfLogin.IF_Login == true) {
            StudentController.all = null;
            ScoreController.all = null;
            ScoretestController.all = null;
            return "index1";
        }
        return "redirect:/login";
    }
}
