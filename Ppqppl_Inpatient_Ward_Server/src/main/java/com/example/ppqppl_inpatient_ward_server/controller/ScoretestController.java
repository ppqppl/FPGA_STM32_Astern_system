package com.example.ppqppl_inpatient_ward_server.controller;

import com.example.ppqppl_inpatient_ward_server.entity.Score;
import com.example.ppqppl_inpatient_ward_server.entity.Testtime;
import com.example.ppqppl_inpatient_ward_server.repository.TesttimeRepository;
import com.example.ppqppl_inpatient_ward_server.utils.PageSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ScoretestController {

    PageSelect page = new PageSelect();

    @Autowired
    TesttimeRepository testtimeRepository;

    static Page<Testtime> all = null;

    @RequestMapping("/scoretest")
    public String score(ModelMap map){
        if(all == null){
            all = testtimeRepository.findAll(PageRequest.of(0,10));
        }
        int jump = all.getNumber();
        map.addAttribute("testtimedatainfo",all);
        map.addAttribute("jumppage",jump);
        return "scoretest";
    }

    @RequestMapping("/scoretestshow")
    public String index(@RequestParam(value="pagenum",defaultValue="0")int pagenum,
                        @RequestParam(value="pagesize",defaultValue="10")int pagesize, ModelMap map)
    {
        all = testtimeRepository.findAll(PageRequest.of(pagenum,pagesize));
        map.addAttribute("testtimedatainfo",all);
//        map.addAttribute("pagenum",all.getNumber());
//        map.addAttribute("pagesize",all.getNumberOfElements());
//        map.addAttribute("pagetotal",all.getTotalPages());
//        map.addAttribute("pagesizetotal",all.getTotalElements());
        return "redirect:/scoretest";
    }

    @PostMapping("/scoretestjump")
    public String jump(@RequestParam(value="jump",defaultValue="0")int pagenum,
                       @RequestParam(value="pagesize",defaultValue="10")int pagesize, ModelMap map){
        pagenum -= 1;
        if(pagenum<0){
            pagenum = 0;
        }
        if(pagenum>all.getTotalPages()){
            pagenum = all.getTotalPages()-1;
        }
        all = testtimeRepository.findAll(PageRequest.of(pagenum,pagesize));
        map.addAttribute("testtimedatainfo",all);
        return "redirect:/scoretest";
    }

    @RequestMapping("/scoretestdata")
    public List<Testtime> scoretestdata(){
        return testtimeRepository.findAll();
    }

}
