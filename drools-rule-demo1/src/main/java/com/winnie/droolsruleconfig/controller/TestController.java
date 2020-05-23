package com.winnie.droolsruleconfig.controller;

import com.winnie.droolsruleconfig.entity.QueryParam;
import com.winnie.droolsruleconfig.service.RuleDemoService;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
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
    @Resource
    private RuleDemoService ruleEngineService;

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("test")
    public void test(){
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("++");
        Integer result = 0;

        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession();

        kieSession.insert(queryParam1);
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.insert(result);

        kieSession.fireAllRules();
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.dispose();
    }
}
