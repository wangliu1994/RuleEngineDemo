package com.example.drooldemo.controller;

import com.example.drooldemo.entity.QueryParam;
import com.example.drooldemo.service.RuleDemoService;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/6
 * @desc
 */
@RestController
public class RuleDemoController {
    @Resource
    private KieSession kieSession;

    @Resource
    private RuleDemoService ruleEngineService;

    @RequestMapping("/param")
    public void doParam() {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");

        QueryParam queryParam2 = new QueryParam();
        queryParam2.setParam1(10);
        queryParam2.setParam2(5);
        queryParam2.setParamSign("-");


        QueryParam queryParam3 = new QueryParam();
        queryParam3.setParam1(10);
        queryParam3.setParam2(5);
        queryParam3.setParamSign("*");

        QueryParam queryParam4 = new QueryParam();
        queryParam4.setParam1(10);
        queryParam4.setParam2(5);
        queryParam4.setParamSign("/");

        kieSession.insert(queryParam1);
        kieSession.insert(queryParam2);
        kieSession.insert(queryParam3);
        kieSession.insert(queryParam4);
//        kieSession.insert(ruleEngineService);

        Integer result = 0;
        kieSession.insert(result);
        kieSession.fireAllRules();
    }
}
