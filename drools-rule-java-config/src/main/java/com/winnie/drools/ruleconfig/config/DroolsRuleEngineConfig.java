package com.winnie.drools.ruleconfig.config;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/6
 * @desc
 */
@Configuration
@Slf4j
public class DroolsRuleEngineConfig{
    private final KieServices kieServices = KieServices.get();

    @Value("${drools.rules-path}")
    private String rulesPath;

    @Bean
    public KieContainer kieContainer() throws IOException {
        KieFileSystem kieFileSystem = kieFileSystem();
        ReleaseId releaseId = kieServices.newReleaseId(
                "com.winnie", "drools", "1.0.0");
        kieFileSystem.generateAndWritePomXML(releaseId);
        KieModuleModel kModuleModel = kieServices.newKieModuleModel();
        kModuleModel.newKieBaseModel("kieBase")
                .setDefault(true)
                .newKieSessionModel("kieSession")
                .setDefault(true);
        kieFileSystem.writeKModuleXML(kModuleModel.toXML());

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            log.error(kieBuilder.getResults().getMessages().toString());
            throw new IllegalStateException("规则文件编译报错");
        }
        return kieServices.newKieContainer(releaseId);
    }


    private KieFileSystem kieFileSystem() throws FileNotFoundException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        File folder = new File(rulesPath);
        //可能是配置的相对路径
        if (!folder.exists()) {
            folder = new File("drools-rule-java-config", rulesPath);
        }
        if (!folder.exists() || !folder.isDirectory()) {
            throw new FileNotFoundException("配置的规则文件路径不存在，请检查配置");
        }

        File[] ruleFiles = folder.listFiles();
        if (ruleFiles == null) {
            return kieFileSystem;
        }
        for (File file : ruleFiles) {
            String path = file.getPath();
            if (path.endsWith(".drl")) {
                kieFileSystem.write("src/main/resources/" + file.getName(),
                        ResourceFactory.newFileResource(path));
                log.info("找到名为{}的规则文件", path);
            }
        }
        return kieFileSystem;
    }
}
