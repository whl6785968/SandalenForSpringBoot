package com.sandalen.sandalen.service;

import com.sandalen.sandalen.ConfigMapper.RptMapper;
import com.sandalen.sandalen.entities.RptCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RptService {
    @Autowired
    private RptMapper rptMapper;
    //Cacheable的属性
    //可以使用 Spel表达式
    //#id
    //keyGenerator = "myKeyGenerator"
    //condition = "#a0>1" : 第一个参数大于1才缓存
    //unless = "#a0 == 2":否定缓存 ，如果第一参数等于2不缓存
    @Cacheable(cacheNames = {"emp"},key = "#root.methodName")
    public List<RptCount> getRptCount(){
        List<RptCount> rptCoutList = rptMapper.getRptCoutList();

        return rptCoutList;
    }
}
