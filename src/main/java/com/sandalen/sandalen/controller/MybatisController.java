package com.sandalen.sandalen.controller;

import com.sandalen.sandalen.AnnotationMapper.RptCountMapper;
import com.sandalen.sandalen.ConfigMapper.RptMapper;
import com.sandalen.sandalen.entities.RptCount;
import com.sandalen.sandalen.service.RptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MybatisController {
  @Autowired
  private RptService rptService;
  /*  @Autowired
    private RptCountMapper rptCountMapper;

    @RequestMapping("/getRpt")
    public List<RptCount> getRpt(){
        List<RptCount> rptCount = rptCountMapper.getRptCount();
        return rptCount;
    }*/

  @Autowired
    private RptMapper rptMapper;


  @RequestMapping("/getRpt2")
    public List<RptCount> getRpt2(){
    List<RptCount> rptCount = rptService.getRptCount();
    return rptCount;
  }
}
