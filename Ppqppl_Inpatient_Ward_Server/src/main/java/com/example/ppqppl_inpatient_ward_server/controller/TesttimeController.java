package com.example.ppqppl_inpatient_ward_server.controller;

import com.example.ppqppl_inpatient_ward_server.entity.Testtime;
import com.example.ppqppl_inpatient_ward_server.repository.TesttimeRepository;
import com.example.ppqppl_inpatient_ward_server.utils.Judgeuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class TesttimeController {
    @Autowired
    TesttimeRepository testtimeRepository;

    List<Testtime> testtimes = null;

    @RequestMapping("/savetesttime")
    public String savetesttime(ModelMap map){
        System.out.println("savetesttime");
        testtimes = testtimeRepository.findAll();
        Judgeuser.testtime.code = testtimes.size();
        testtimes = testtimeRepository.findAllById(Judgeuser.testtime.id);
        int num = testtimes.size();
        Judgeuser.testtime.num = num;
        testtimeRepository.save(Judgeuser.testtime);
        Judgeuser.testtime = new Testtime();
        return "redirect:/starttest";
    }
}
