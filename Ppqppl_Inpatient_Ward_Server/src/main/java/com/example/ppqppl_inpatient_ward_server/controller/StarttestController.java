package com.example.ppqppl_inpatient_ward_server.controller;

import com.example.ppqppl_inpatient_ward_server.entity.Score;
import com.example.ppqppl_inpatient_ward_server.entity.Testtime;
import com.example.ppqppl_inpatient_ward_server.repository.StarttestRepository;
import com.example.ppqppl_inpatient_ward_server.repository.TesttimeRepository;
import com.example.ppqppl_inpatient_ward_server.utils.Car_Score;
import com.example.ppqppl_inpatient_ward_server.utils.Judgeuser;
import com.example.ppqppl_inpatient_ward_server.utils.UDPReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StarttestController {

    @Autowired
    StarttestRepository starttestTepository;
    @Autowired
    TesttimeRepository testtimeRepository;

    List<Score> scores = null;
    List<Testtime> testtimes = null;
    Score s1 = null;

    public void savetesttime(){
        System.out.println("savetesttime");
        testtimes = testtimeRepository.findAll();
        Judgeuser.testtime.code = testtimes.size() + 1;
        testtimes = null;
        testtimes = testtimeRepository.findAllById(Judgeuser.testtime.id);
        int num = testtimes.size();
        Judgeuser.testtime.num = num + 1;
        testtimeRepository.save(Judgeuser.testtime);
        Judgeuser.testtime = new Testtime();
        testtimes = null;
    }

    @RequestMapping("/starttest")
    public String findAllById(ModelMap map){
        testtimes = testtimeRepository.findAllById(Judgeuser.user.id);
        int num = testtimes.size();
        map.addAttribute("scores",testtimes);
        map.addAttribute("times",num);
        testtimes = null;
        return "starttest";
    }

    @RequestMapping("/test")
    public String starttest(ModelMap map){
        Score s1 = new Score();
        s1.id = Judgeuser.user.id;
        map.addAttribute("newscore",s1);
        map.addAttribute("mmm", UDPReceiver.mmm);
        return "test";
    }

    @GetMapping("/getdis")
    @ResponseBody
    public String getdis() {
//        System.out.println(UDPReceiver.mmm);
        return UDPReceiver.mmm;
    }

    @RequestMapping("/doscore")
    public String dologin(Score score, ModelMap map){
        System.out.println("jkljkl");
        String dis = score.distance;
        System.out.println(dis);
        double score1 = 0.0;
        if(!dis.equals("stop") && !dis.equals("000")) {
            s1 = starttestTepository.findById(score.id).orElse(null);
            if(s1 != null) {
                double dis1 = Double.parseDouble(s1.distance);
                score1 = Car_Score.score(dis1);
            }
            double dis2 = Double.parseDouble(score.distance);
            double score2 = Car_Score.score(dis2);
            score.score = Double.toString(score2);
            Testtime t = new Testtime();
            t.id = score.id;
            t.time = score.time;
            t.score = score.score;
            t.distance = score.distance;
            Judgeuser.testtime = t;
            if(s1 == null || score1<score2) {
//                score.score = Double.toString(score2);
                starttestTepository.save(score);
//                Testtime t = new Testtime();
//                t.id = score.id;
//                t.time = score.time;
//                Judgeuser.testtime = t;
            }
            savetesttime();
//            else {
////                score.score = Double.toString(score2);
////                Testtime t = new Testtime();
////                t.id = score.id;
////                t.time = score.time;
////                Judgeuser.testtime = t;
//                savetesttime();
//            }
        }
        return "redirect:/starttest";
    }

    @RequestMapping("/teststm32")
    public String teststm32(ModelMap map){
        Score s1 = new Score();
        s1.id = Judgeuser.user.id;
        map.addAttribute("newscore",s1);
        map.addAttribute("mmm", UDPReceiver.mmm);
        return "teststm32";
    }

    @RequestMapping("/testfpga")
    public String testfpga(ModelMap map){
        Score s1 = new Score();
        s1.id = Judgeuser.user.id;
        map.addAttribute("newscore",s1);
        map.addAttribute("mmm", UDPReceiver.mmm);
        return "testfpga";
    }

}
