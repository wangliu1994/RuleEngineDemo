package com.winnie.drools.ruleconfig.config;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
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
    public KieFileSystem kieFileSystem() throws FileNotFoundException {
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

//    @Bean
//    public KieFileSystem kieFileSystem() throws IOException {
//        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//        Resource[] files = resourcePatternResolver.getResources("classpath*:" + rulesPath + "*.*");
//        String path = null;
//        for (Resource file : files) {
//            path = rulesPath + file.getFilename();
//            log.info("path="+path);
//            kieFileSystem.write(ResourceFactory.newClassPathResource(path, "UTF-8"));
//        }
//        return kieFileSystem;
//    }

    @Bean
    public KieContainer kieContainer() throws IOException {
        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    @Bean
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    @Bean
    public KieSession kieSession() throws IOException {
        return kieContainer().newKieSession();
    }
}
