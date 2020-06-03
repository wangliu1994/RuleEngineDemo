package com.winnie.drools.ruledemo;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.service.RuleDemoService;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/6/2
 * @desc
 */
@SpringBootTest
public class KieActionTest {
    @Resource
    private RuleDemoService ruleEngineService;

    private Logger logger = LoggerFactory.getLogger(DroolsRuleConfigApplicationTests.class);

    @Test
    void testAction1() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession4_1");

        QueryParam queryParam4 = new QueryParam();
        queryParam4.setParam1(10);
        queryParam4.setParam2(5);
        queryParam4.setParamSign("+");
        Integer result = 0;

        kieSession.setGlobal("ruleDemoService", ruleEngineService);
        kieSession.setGlobal("resultParam", result);
        FactHandle handle = kieSession.insert(queryParam4);
        kieSession.fireAllRules();
        logger.info("queryParam4.result = {}", result);

        queryParam4.setParamSign("-");
        kieSession.update(handle, queryParam4);
        kieSession.fireAllRules();
        logger.info("queryParam4.result = {}", result);

        kieSession.dispose();
    }

    @Test
    void testAction2() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession4_1");

        QueryParam queryParam4 = new QueryParam();
        queryParam4.setParam1(10);
        queryParam4.setParam2(5);
        queryParam4.setParamSign("*");
        Integer result = 0;

        kieSession.setGlobal("ruleDemoService", ruleEngineService);
        kieSession.setGlobal("resultParam", result);
        FactHandle handle = kieSession.insert(queryParam4);
        kieSession.fireAllRules();
        logger.info("queryParam4.result = {}", result);

        kieSession.dispose();
    }

    @Test
    void testAction3() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession4_1");

        QueryParam queryParam4 = new QueryParam();
        queryParam4.setParam1(10);
        queryParam4.setParam2(5);
        queryParam4.setParamSign("/");
        Integer result = 0;

        kieSession.setGlobal("ruleDemoService", ruleEngineService);
        kieSession.setGlobal("resultParam", result);
        FactHandle handle = kieSession.insert(queryParam4);
        kieSession.fireAllRules();
        logger.info("queryParam4.result = {}", result);

        kieSession.dispose();
    }
}
