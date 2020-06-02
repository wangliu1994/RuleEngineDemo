package com.winnie.droolsruleuse;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.service.RuleDemoService;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;

@SpringBootTest
class DroolsRuleUseApplicationTests {
    private Logger logger = LoggerFactory.getLogger(DroolsRuleUseApplicationTests.class);

    @Resource
    private RuleDemoService ruleEngineService;

    /**
     * 从Maven仓库里面获取对应的jar
     */
    @Test
    void testRuleFromRepository() {
        KieServices kieServices = KieServices.Factory.get();
        ReleaseId releaseId = kieServices.newReleaseId( "com.winnie", "drools-rule-module", "0.0.1-SNAPSHOT" );
        KieContainer kieContainer = kieServices.newKieContainer( releaseId );
        logger.info("kieContainer = {}", kieContainer);
        insertData(kieContainer);
    }

    /**
     * 直接读取jar文件
     */
    @Test
    void testRuleFromRepository1() {
        KieServices kieServices = KieServices.Factory.get();
        KieRepository kieRepository = kieServices.getRepository();
        KieModule kieModule = kieRepository.addKieModule(kieServices.getResources().newFileSystemResource(getFile()));
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        logger.info("kieContainer = {}", kieContainer);
        insertData(kieContainer);
    }

    private File getFile(){
        File folder = new File("").getAbsoluteFile();
        File targetFolder = new File(folder, "target");
        if (!targetFolder.exists()) {
            throw new RuntimeException("The target folder does not exist, please build project " + "drools-rule-use" + " first");
        }

        for (String str : targetFolder.list()) {
            if (str.startsWith("drools-rule-module") && !str.endsWith("-sources.jar")
                    && !str.endsWith("-tests.jar") && !str.endsWith("-javadoc.jar")) {
                return new File(targetFolder, str);
            }
        }
        throw new RuntimeException("The target jar does not exist, please build project " + "drools-rule-use" + " first");
    }

    private void insertData(KieContainer kieContainer){
        KieSession kieSession = kieContainer.newKieSession();
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+++");
        Integer result = 0;

        kieSession.insert(queryParam1);
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.insert(result);
        kieSession.insert(ruleEngineService);
        kieSession.fireAllRules();
        logger.info("queryParam1.result = {}", queryParam1.getResult());
    }
}
