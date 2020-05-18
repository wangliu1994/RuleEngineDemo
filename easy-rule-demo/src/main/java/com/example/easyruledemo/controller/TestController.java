package com.example.easyruledemo.controller;

import com.example.easyruledemo.service.RuleDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/18
 * @desc
 */
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    private RuleDemoService ruleDemoService;

    @GetMapping("test")
    public void test(){
//        2.X版本用法
//        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
//                .withSkipOnFirstAppliedRule(true).withSilentMode(true).build();
//
//        QueryParam queryParam1 = new QueryParam();
//        queryParam1.setParam1(10);
//        queryParam1.setParam2(5);
//        queryParam1.setParamSign("+ -");
//
//        AddRule addRule = new AddRule();
//        SubRule subRule = new SubRule();
//        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
//        MulRule mulRule = new MulRule();
//        DivRule divRule = new DivRule();
//
//        addRule.setParam(queryParam1, ruleDemoService);
//        subRule.setParam(queryParam1, ruleDemoService);
//        mulRule.setParam(queryParam1, ruleDemoService);
//        divRule.setParam(queryParam1, ruleDemoService);
//
//
//        rulesEngine.registerRule(addRule);
//        rulesEngine.registerRule(subRule);
//        rulesEngine.registerRule(addSubRule);
//        rulesEngine.registerRule(mulRule);
//        rulesEngine.registerRule(divRule);
//
//        rulesEngine.fireRules();
    }
}
