package com.winnie.droolsruledemo1;

import com.winnie.droolsruledemo1.entity.QueryParam;
import com.winnie.droolsruledemo1.service.RuleDemoService;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DroolsRuleConfigApplicationTests {
    @Resource
    private RuleDemoService ruleEngineService;

    private Logger logger = LoggerFactory.getLogger(DroolsRuleConfigApplicationTests.class);

    @Test
    void contextLoads() {
    }

    @Test
    void testDroolsConfig(){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        //由于将a KieBase和a KieSession标记为默认
        // returns KBase1
        KieBase kieBase = kieContainer.getKieBase();
        // returns KSession2_1
        KieSession kieSession = kieContainer.newKieSession();

        KieBase kieBase1 = kieContainer.getKieBase("KBase2");
        KieSession kieSession21 = kieContainer.newKieSession("KSession2_1");
        StatelessKieSession kieSession22 = kieContainer.newStatelessKieSession("KSession2_2");
        KieSession kieSession31 = kieContainer.newKieSession("KSession3_1");

        logger.info("kieBase = {}", kieBase);
        logger.info("kieBase1 = {}", kieBase1);
        logger.info("kieSession = {}", kieSession);
        logger.info("kieSession2_1 = {}", kieSession21);
        logger.info("kieSession2_2 = {}", kieSession22);
        logger.info("kieSession3_1 = {}", kieSession31);

        ReleaseId releaseId = kieServices.newReleaseId( "com.winnie", "drools-rule-config", "0.0.1-SNAPSHOT" );
        KieContainer kieContainer1 = kieServices.newKieContainer( releaseId );
        logger.info("kieContainer1 = {}", kieContainer1);


        insertData(kieSession);
        kieSession.dispose();
    }

    private void insertData( KieSession kieSession){
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("-");
        Integer result = 0;

        kieSession.insert(queryParam1);
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.insert(result);
        kieSession.fireAllRules();
        logger.info("queryParam1.result = {}", queryParam1.getResult());
    }
}
