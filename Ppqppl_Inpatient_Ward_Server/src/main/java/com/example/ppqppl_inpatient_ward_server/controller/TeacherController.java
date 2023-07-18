package com.example.ppqppl_inpatient_ward_server.controller;

import com.example.ppqppl_inpatient_ward_server.entity.Admin;
import com.example.ppqppl_inpatient_ward_server.entity.User;
import com.example.ppqppl_inpatient_ward_server.repository.AdminRepository;
import com.example.ppqppl_inpatient_ward_server.repository.UserRepository;
import com.example.ppqppl_inpatient_ward_server.utils.Judgeuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeacherController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @RequestMapping("/teacher")
    public String findById(ModelMap map){
        String id = Judgeuser.admin.id;
        Admin user = null;
        user = adminRepository.findById(id).orElse(null);
        map.addAttribute("usernow",user);
        map.addAttribute("regmsg","管理自己的用户信息");
        return "teacher";
    }
    @RequestMapping("/updatteacher")
//    @ResponseBody
    public String save(Admin usernow){
        adminRepository.save(usernow);
        System.out.println(usernow.id);
        return "redirect:/teacher";
    }
}
