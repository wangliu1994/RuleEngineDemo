package com.winnie.drools.ruledemo;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.entity.ResultParam;
import com.winnie.common.service.RuleDemoService;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/6/2
 * @desc
 */
public class RuleCheckTest {
    @Resource
    private RuleDemoService ruleEngineService;

    private Logger logger = LoggerFactory.getLogger(DroolsRuleConfigApplicationTests.class);

    @Test
    void testDroolsAdd() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession2_1");

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");
        Integer result = 0;

        kieSession.insert(queryParam1);
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.insert(result);
        kieSession.insert(ruleEngineService);

        kieSession.fireAllRules();
        kieSession.dispose();
        //通过打印日志可以看到
        //在drools中，这个传递数据进去的对象，术语叫 Fact对象。Fact对象是一个普通的java bean，
        // 规则中可以对当前的对象进行任何的读写操作，调用该对象提供的方法，当一个java bean插入到workingMemory中，
        // 规则使用的是原有对象的引用，规则通过对fact对象的读写，实现对应用数据的读写，对于其中的属性，需要提供getter setter访问器
        logger.info("queryParam1.result = {}", queryParam1.getResult());

        //通过打印日志可以看到， Integer result在规则中被赋了其他值，不是本身了，所以获取不到
//        logger.info("result = {}", result);
    }

    @Test
    void testDroolsAdd1() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession3_1");

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");
        Integer result = 0;

        kieSession.setGlobal("ruleDemoService", ruleEngineService);
        kieSession.setGlobal("resultParam", result);
        kieSession.insert(queryParam1);

        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.fireAllRules();
        kieSession.dispose();
        //通过打印日志可以看到
        //在drools中，这个传递数据进去的对象，术语叫 Fact对象。Fact对象是一个普通的java bean，
        // 规则中可以对当前的对象进行任何的读写操作，调用该对象提供的方法，当一个java bean插入到workingMemory中，
        // 规则使用的是原有对象的引用，规则通过对fact对象的读写，实现对应用数据的读写，对于其中的属性，需要提供getter setter访问器
        logger.info("queryParam1.result = {}", queryParam1.getResult());

        //通过打印日志可以看到， Integer result在规则中被赋了其他值，不是本身了，所以获取不到
        logger.info("result = {}", result);
    }

    @Test
    void testDroolsSub() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession2_1");

        QueryParam queryParam2 = new QueryParam();
        queryParam2.setParam1(10);
        queryParam2.setParam2(5);
        queryParam2.setParamSign("-");

        kieSession.insert(queryParam2);
        kieSession.insert(ruleEngineService);
        kieSession.fireAllRules();
        kieSession.dispose();

        logger.info("queryParam2.result = {}", queryParam2.getResult());
    }

    @Test
    void testDroolsSub1() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession3_1");

        QueryParam queryParam2 = new QueryParam();
        queryParam2.setParam1(10);
        queryParam2.setParam2(5);
        queryParam2.setParamSign("-");
        Integer result = 0;

        kieSession.setGlobal("ruleDemoService", ruleEngineService);
        kieSession.setGlobal("resultParam", result);
        kieSession.insert(queryParam2);
        kieSession.fireAllRules();
        kieSession.dispose();

        logger.info("queryParam2.result = {}", queryParam2.getResult());
    }

    @Test
    void testDroolsMul() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession2_1");

        QueryParam queryParam3 = new QueryParam();
        queryParam3.setParam1(10);
        queryParam3.setParam2(5);
        queryParam3.setParamSign("*");
        ResultParam resultParam = new ResultParam();

        kieSession.insert(queryParam3);
        kieSession.insert(ruleEngineService);
        kieSession.insert(resultParam);

        kieSession.fireAllRules();
        kieSession.dispose();
        logger.info("queryParam3.result = {}", resultParam.getResult());
    }

    @Test
    void testDroolsMul1() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession3_1");

        QueryParam queryParam3 = new QueryParam();
        queryParam3.setParam1(10);
        queryParam3.setParam2(5);
        queryParam3.setParamSign("*");
        Integer result = 0;

        kieSession.setGlobal("ruleDemoService", ruleEngineService);
        kieSession.setGlobal("resultParam", result);
        kieSession.insert(queryParam3);
        kieSession.fireAllRules();
        kieSession.dispose();
        logger.info("queryParam3.result = {}", result);
    }

    @Test
    void testDroolsDiv() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession2_1");

        QueryParam queryParam4 = new QueryParam();
        queryParam4.setParam1(10);
        queryParam4.setParam2(5);
        queryParam4.setParamSign("/");
        ResultParam resultParam = new ResultParam();

        kieSession.insert(queryParam4);
        kieSession.insert(ruleEngineService);
        kieSession.insert(resultParam);
        kieSession.fireAllRules();
        kieSession.dispose();
        logger.info("queryParam4.result = {}", resultParam.getResult());
    }

    @Test
    void testDroolsDiv1() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession3_1");

        QueryParam queryParam4 = new QueryParam();
        queryParam4.setParam1(10);
        queryParam4.setParam2(5);
        queryParam4.setParamSign("/");
        Integer result = 0;

        kieSession.setGlobal("ruleDemoService", ruleEngineService);
        kieSession.setGlobal("resultParam", result);
        kieSession.insert(queryParam4);
        kieSession.fireAllRules();
        kieSession.dispose();
        logger.info("queryParam4.result = {}", result);
    }

}
