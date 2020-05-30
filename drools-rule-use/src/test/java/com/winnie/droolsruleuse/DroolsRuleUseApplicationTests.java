package com.winnie.droolsruleuse;

import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DroolsRuleUseApplicationTests {
    private Logger logger = LoggerFactory.getLogger(DroolsRuleUseApplicationTests.class);

    @Test
    void testRuleFromRepository() {
        KieServices kieServices = KieServices.Factory.get();
        ReleaseId releaseId = kieServices.newReleaseId( "com.winnie", "drools-rule-module", "0.0.1-SNAPSHOT" );
        KieContainer kieContainer = kieServices.newKieContainer( releaseId );
        logger.info("kieContainer = {}", kieContainer);
    }
}
