package com.example.ppqppl_inpatient_ward_server.controller;

import com.example.ppqppl_inpatient_ward_server.entity.Score;
import com.example.ppqppl_inpatient_ward_server.entity.Testtime;
import com.example.ppqppl_inpatient_ward_server.entity.User;
import com.example.ppqppl_inpatient_ward_server.repository.StarttestRepository;
import com.example.ppqppl_inpatient_ward_server.repository.TesttimeRepository;
import com.example.ppqppl_inpatient_ward_server.repository.UserRepository;
import com.example.ppqppl_inpatient_ward_server.utils.Judgeuser;
import com.example.ppqppl_inpatient_ward_server.utils.PageSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityTransaction;
import java.util.List;

@Controller
public class StudentController {

    PageSelect page = new PageSelect();
    static Page<User> all = null;

    String personmsg = "管理你的用户信息";

    @Autowired
    UserRepository studentRepository;

    @Autowired
    StarttestRepository starttestRepository;

    @Autowired
    TesttimeRepository testtimeRepository;

    List<User> users = null;

    @RequestMapping("/student")
    public String findById(ModelMap map){
        String id = Judgeuser.user.id;
        User user = null;
        user = studentRepository.findById(id).orElse(null);
        map.addAttribute("usernow",user);
        map.addAttribute("regmsg","管理自己的用户信息");
        return "student";
    }

    @RequestMapping("/updatestudent")
//    @ResponseBody
    public String save(User usernow){
        studentRepository.save(usernow);
        System.out.println(usernow.id);
        return "redirect:/student";
    }





    @RequestMapping("/studentmanage")
    public String studentmanage(ModelMap map){
        if(all == null){
            all = studentRepository.findAll(PageRequest.of(0,10));
        }
        int jump = all.getNumber();
        map.addAttribute("studentdatainfo",all);
        map.addAttribute("jumppage",jump);
        return "studentmanage";
    }

    @RequestMapping("/studentmanageshow")
    public String index(@RequestParam(value="pagenum",defaultValue="0")int pagenum,
                        @RequestParam(value="pagesize",defaultValue="10")int pagesize, ModelMap map)
    {
        all = studentRepository.findAll(PageRequest.of(pagenum,pagesize));
        map.addAttribute("studentdatainfo",all);
//        map.addAttribute("pagenum",all.getNumber());
//        map.addAttribute("pagesize",all.getNumberOfElements());
//        map.addAttribute("pagetotal",all.getTotalPages());
//        map.addAttribute("pagesizetotal",all.getTotalElements());
        return "redirect:/studentmanage";
    }

    @PostMapping("/studentmanagejump")
    public String jump(@RequestParam(value="jump",defaultValue="0")int pagenum,
                       @RequestParam(value="pagesize",defaultValue="10")int pagesize, ModelMap map){
        pagenum -= 1;
        if(pagenum<0){
            pagenum = 0;
        }
        if(pagenum>all.getTotalPages()){
            pagenum = all.getTotalPages()-1;
        }
        all = studentRepository.findAll(PageRequest.of(pagenum,pagesize));
        map.addAttribute("studentdatainfo",all);
        return "redirect:/studentmanage";
    }

    @RequestMapping("/studentdata")
    public List<User> studentdata(){
        return studentRepository.findAll();
    }

//    @Transactional
//    public void deleteAllById(String id) {
//        testtimeRepository.deleteAllById(id);
//    }

    @RequestMapping("/delstu")
    public String delstu(@RequestParam(value="stuid",defaultValue="0")String stuid,ModelMap map)
    {
        List<User> u1 = studentRepository.findAllById(stuid);
        List<Score> s1 = starttestRepository.findAllById(stuid);
        List<Testtime> t1 = testtimeRepository.findAllById(stuid);

        if(u1!=null&&u1.size()!=0) {
            studentRepository.deleteById(stuid);
            if(s1!=null&&s1.size()!=0) {
                starttestRepository.deleteById(stuid);
            }
            if(t1!=null&&t1.size()!=0) {
                testtimeRepository.deleteAllById(stuid);
            }
        }
        all = null;
        return "redirect:/studentmanage";
    }
}
