package com.winnie.drools.ruleconfig;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.service.RuleDemoService;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.command.Command;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/6/3
 * @desc
 */
@SpringBootTest
public class KieModuleTest {

    @Resource
    private RuleDemoService ruleEngineService;

    private Logger logger = LoggerFactory.getLogger(KieModuleTest.class);

    @Test
    void testKieModule() {
        KieServices kieServices = KieServices.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        KieResources kieResources = kieServices.getResources();
        KieRepository kieRepository = kieServices.getRepository();

        //本行代码会导致"rule error"
//        kieFileSystem.write("src/main/resources/rules/rule.drl", "hello");
        kieFileSystem.write("src/main/resources/rules/rule1.drl",
                kieResources.newFileSystemResource(new File("src/main/resources/action_check_rule1.drl")));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("rule error");
        }

        KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
        KieSession kieSession = kieContainer.newKieSession();
        insertData(kieSession);
    }


    @Test
    void testKieModule1() {
        KieServices kieServices = KieServices.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        KieResources kieResources = kieServices.getResources();

        ReleaseId releaseId = kieServices.newReleaseId("com.winnie", "drools", "1.0.0");
        kieFileSystem.generateAndWritePomXML(releaseId);

        KieModuleModel kModuleModel = kieServices.newKieModuleModel();
        kModuleModel.newKieBaseModel("kieBase")
                .setDefault(true)
                .newKieSessionModel("kieSession")
                .setDefault(true);
        kieFileSystem.writeKModuleXML(kModuleModel.toXML());


        kieFileSystem.write("src/main/resources/rules/rule", "hello");
        kieFileSystem.write("src/main/resources/rule1.drl",
                kieResources.newFileSystemResource(new File("src/main/resources/action_check_rule1.drl")));
        //不指定pathname的话，默认的会加载成: "src/main/resources/文件全路径"和"src/main/resources/文件全路径+.properties"两个文件
//        kieFileSystem.write(kieResources.newFileSystemResource(new File("src/main/resources/action_check_rule1.drl")));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("rule error");
        }

        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        KieSession kieSession = kieContainer.newKieSession("kieSession");
        insertData(kieSession);
    }


    @Test
    void testKieModuleStateLess() {
        KieServices kieServices = KieServices.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        KieResources kieResources = kieServices.getResources();

        ReleaseId releaseId = kieServices.newReleaseId("com.winnie", "drools", "1.0.0");
        kieFileSystem.generateAndWritePomXML(releaseId);

        KieModuleModel kModuleModel = kieServices.newKieModuleModel();
        KieBaseModel kieBaseModel = kModuleModel.newKieBaseModel("kieBase")
                .setDefault(true)
                //对应写入规则文件在resources以下的目录
                .addPackage("rules");
        kieBaseModel.newKieSessionModel("kieSession")
                .setDefault(true);
        kieBaseModel.newKieSessionModel("kieSession1")
                .setDefault(true).setType(KieSessionModel.KieSessionType.STATELESS);

        kieFileSystem.writeKModuleXML(kModuleModel.toXML());
        kieFileSystem.write("src/main/resources/rules/rule1.drl",
                kieResources.newFileSystemResource(new File("src/main/resources/action_check_rule1.drl")));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("rule error");
        }

        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession();
        insertData(kieSession);
    }

    private void insertData(KieSession kieSession) {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");
        Integer result = 0;

        kieSession.insert(queryParam1);
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.setGlobal("ruleDemoService", ruleEngineService);
        kieSession.setGlobal("resultParam", result);
        kieSession.fireAllRules();
        logger.info("queryParam1.result = {}", queryParam1.getResult());
    }

    private void insertData(StatelessKieSession kieSession) {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");
        Integer result = 0;

        List<Command> cmds = new ArrayList<>();
        cmds.add(CommandFactory.newInsert(queryParam1, "queryParam"));
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

}
