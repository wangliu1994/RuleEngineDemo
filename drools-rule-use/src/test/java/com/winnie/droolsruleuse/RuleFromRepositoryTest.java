package com.winnie.droolsruleuse;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.service.RuleDemoService;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/6/2
 * @desc
 */
public class RuleFromRepositoryTest {
    private Logger logger = LoggerFactory.getLogger(DroolsRuleUseApplicationTests.class);

    @Autowired
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
        Resource resource = kieServices.getResources().newFileSystemResource(getFile("drools-rule-module"));
        KieModule kieModule = kieRepository.addKieModule(resource);
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        logger.info("kieContainer = {}", kieContainer);
        insertData(kieContainer);
    }

    @Test
    void testRuleFromRepository2() {
        KieServices kieServices = KieServices.Factory.get();
        KieRepository kieRepository = kieServices.getRepository();
        Resource resource1 = kieServices.getResources().newFileSystemResource(getFile("drools-rule-module"));
        Resource resource2 = kieServices.getResources().newFileSystemResource(getFile("drools-rule-module1"));
        KieModule kieModule = kieRepository.addKieModule(resource1, resource2);
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        logger.info("kieContainer = {}", kieContainer);
        insertData(kieContainer);
    }

    private File getFile(String moduleName){
        File currentFolder = new File("").getAbsoluteFile();
        File parentFolder = currentFolder.getParentFile();
        File moduleFolder = new File(parentFolder, moduleName);
        File targetFolder = new File(moduleFolder, "/target");
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
