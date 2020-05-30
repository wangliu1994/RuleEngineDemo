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
        //通过打印日志可以看到
        //在drools中，这个传递数据进去的对象，术语叫 Fact对象。Fact对象是一个普通的java bean，
        // 规则中可以对当前的对象进行任何的读写操作，调用该对象提供的方法，当一个java bean插入到workingMemory中，
        // 规则使用的是原有对象的引用，规则通过对fact对象的读写，实现对应用数据的读写，对于其中的属性，需要提供getter setter访问器
        logger.info("queryParam1.result = {}", queryParam1.getResult());

        //通过打印日志可以看到， Integer result在规则中被赋了其他值，不是本身了，所以获取不到
        logger.info("result = {}", result);
    }

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
        logger.info("queryParam4.result = {}", result);
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
    void testStateful() {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");
        Integer result = 0;

        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession3_1");

        kieSession.insert(queryParam1);
        kieSession.setGlobal("ruleDemoService", ruleEngineService);
        kieSession.setGlobal("resultParam", result);
        kieSession.fireAllRules();
        kieSession.dispose();

        logger.info("queryParam1.result = {}", queryParam1.getResult());
        logger.info("result = {}", result);


        QueryResults resultsRows = kieSession.getQueryResults("num in add");
        logger.info("queryResult.size: " + resultsRows.size());
        for ( QueryResultsRow row : resultsRows ) {
            QueryParam queryParam = ( QueryParam ) row.get( "queryParam" );
            logger.info("queryParam: " + queryParam.toString());
        }
    }

    @Test
    void testStateless() {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");
        Integer result = 0;

        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession("KSession3_2");

        List<Command> cmds = new ArrayList<>();
        cmds.add(CommandFactory.newInsert(queryParam1,"queryParam"));
        cmds.add(CommandFactory.newSetGlobal("ruleDemoService", ruleEngineService, true));
        cmds.add(CommandFactory.newSetGlobal("resultParam", result, true));

        logger.info("queryParam1.result = {}", queryParam1.getResult());
        ExecutionResults results = kieSession.execute(CommandFactory.newBatchExecution(cmds));


        QueryParam resultParam = (QueryParam) results.getValue("queryParam");
        Integer resultData = (Integer) results.getValue("resultParam");
        logger.info("resultParam.result = {}", resultParam.getResult());
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        logger.info("result = {}", resultData);
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
