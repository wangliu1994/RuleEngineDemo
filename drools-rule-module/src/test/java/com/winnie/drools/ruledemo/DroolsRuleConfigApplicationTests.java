package com.winnie.drools.ruledemo;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.entity.ResultParam;
import com.winnie.common.service.RuleDemoService;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.command.Command;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@SuppressWarnings("unused")
class DroolsRuleConfigApplicationTests {
    @Resource
    private RuleDemoService ruleEngineService;

    private Logger logger = LoggerFactory.getLogger(DroolsRuleConfigApplicationTests.class);

    @Test
    void contextLoads() {
    }


    @Test
    void testDrools() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession2_1");

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        Integer result = 0;

        kieSession.insert(queryParam1);
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.insert(result);

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
    void testDroolsConfig(){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        //由于将a KieBase和a KieSession标记为默认
        // returns KBase1
        KieBase kieBase = kieContainer.getKieBase();
        // returns KSession1_1
        KieSession kieSession = kieContainer.newKieSession();

        KieBase kieBase1 = kieContainer.getKieBase("KBase2");
        KieSession kieSession11 = kieContainer.newKieSession("KSession1_1");
        StatelessKieSession kieSession12 = kieContainer.newStatelessKieSession("KSession1_2");
        KieSession kieSession21 = kieContainer.newKieSession("KSession2_1");

        logger.info("kieBase = {}", kieBase);
        logger.info("kieBase1 = {}", kieBase1);
        logger.info("kieSession = {}", kieSession);
        logger.info("kieSession2_1 = {}", kieSession11);
        logger.info("kieSession2_2 = {}", kieSession12);
        logger.info("kieSession3_1 = {}", kieSession21);

        insertData(kieSession21);
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
        kieSession.insert(ruleEngineService);
        kieSession.fireAllRules();
        logger.info("queryParam1.result = {}", queryParam1.getResult());
    }

    @Test
    void testDroolsFile(){
        KieServices kieServices = KieServices.Factory.get();
        KieRepository kr = kieServices.getRepository();

        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel( "KBase1")
                .setDefault( true )
                .setEqualsBehavior( EqualityBehaviorOption.EQUALITY );

        KieSessionModel kieSessionModel = kieBaseModel1.newKieSessionModel( "KSession1" )
                .setDefault( true )
                .setType( KieSessionModel.KieSessionType.STATEFUL );

        KieBaseModel kieBaseModel2 = kieModuleModel.newKieBaseModel( "KBase12")
                .setDefault( true )
                .setEqualsBehavior( EqualityBehaviorOption.EQUALITY );
        KieSessionModel kieSessionModel1 = kieBaseModel2.newKieSessionModel( "KSession12" )
                .setDefault( true )
                .setType( KieSessionModel.KieSessionType.STATEFUL );

        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.writeKModuleXML(kieModuleModel.toXML());


        kieServices.newKieBuilder(kfs).buildAll();
        KieContainer kieContainer = kieServices.newKieContainer(kr.getDefaultReleaseId());
    }
}
