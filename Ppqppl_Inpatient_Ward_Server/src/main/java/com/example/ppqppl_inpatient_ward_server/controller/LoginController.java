package com.example.ppqppl_inpatient_ward_server.controller;

import com.example.ppqppl_inpatient_ward_server.entity.Admin;
import com.example.ppqppl_inpatient_ward_server.entity.User;
import com.example.ppqppl_inpatient_ward_server.repository.AdminRepository;
import com.example.ppqppl_inpatient_ward_server.repository.LoginRepository;
import com.example.ppqppl_inpatient_ward_server.utils.IfLogin;
import com.example.ppqppl_inpatient_ward_server.utils.Judgeuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController {

    String loginmsg = "登录使用数据管理系统！";

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    AdminRepository adminRepository;

    List<User> users = null;

    @RequestMapping("/login")
    public String login(ModelMap map){
        users = loginRepository.findAll();
        map.addAttribute("newuser",new User());
        map.addAttribute("loginmsg",loginmsg);
        IfLogin.IF_Login = false;
        Judgeuser.user = null;
        Judgeuser.admin = null;
        return "login";
    }

    @RequestMapping("/dologin")
    public String dologin(User user,ModelMap map){
        List<User> myusers = null;
        List<Admin> myadmins = null;
        if(user.getKind().equals("考生")) {
            myusers = loginRepository.findByAccount(user.getAccount());
            if(myusers == null||myusers.size() == 0){
                loginmsg = "用户账号不存在！";
                map.addAttribute("loginmsg",loginmsg);
                Judgeuser.user = null;
                return "redirect:/login";
            }
            else{
                myusers = null;
                myusers = loginRepository.findByAccountAndPwd(user.getAccount(),user.getPwd());
                if(myusers == null||myusers.size()==0){
                    loginmsg = "用户密码错误！";
                    map.addAttribute("loginmsg",loginmsg);
                    Judgeuser.user = null;
                    return "redirect:/login";
                }
                else{
                    loginmsg = "登录使用数据管理系统！";
                    map.addAttribute("loginmsg",loginmsg);
                    IfLogin.IF_Login = true;
                    Judgeuser.user = myusers.get(0);
                    return "redirect:/index";
                }
            }
        }
        else{
            myadmins = adminRepository.findByAccount(user.getAccount());

            if(myadmins == null||myadmins.size() == 0){
                loginmsg = "用户账号不存在！";
                map.addAttribute("loginmsg",loginmsg);
                Judgeuser.admin = null;
                return "redirect:/login";
            }
            else{
                myadmins = null;
                myadmins = adminRepository.findByAccountAndPwd(user.getAccount(),user.getPwd());
                if(myadmins == null||myadmins.size()==0){
                    loginmsg = "用户密码错误！";
                    map.addAttribute("loginmsg",loginmsg);
                    Judgeuser.admin = null;
                    return "redirect:/login";
                }
                else{
                    loginmsg = "登录使用数据管理系统！";
                    map.addAttribute("loginmsg",loginmsg);
                    IfLogin.IF_Login = true;
                    Judgeuser.admin = myadmins.get(0);
                    return "redirect:/index1";
                }
            }
        }
    }

}
