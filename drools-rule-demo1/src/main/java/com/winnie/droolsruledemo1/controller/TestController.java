package com.winnie.droolsruledemo1.controller;

import com.winnie.droolsruledemo1.entity.QueryParam;
import com.winnie.droolsruledemo1.service.RuleDemoService;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/18
 * @desc
 */
@RestController
public class TestController {
    @Resource
    private RuleDemoService ruleEngineService;

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("test")
    public void test() {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("++");
        Integer result = 0;

        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession();

        kieSession.insert(queryParam1);
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.insert(result);

        kieSession.fireAllRules();
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.dispose();
    }

    @GetMapping("testStateless")
    public void testStateless() {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("++");
        Integer result = 0;

        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession("KSession1_2");

        List<Command> cmds = new ArrayList<>();
        cmds.add(CommandFactory.newInsert(queryParam1,"queryParam"));
        cmds.add(CommandFactory.newInsert(result, "resultParam"));
        cmds.add(CommandFactory.newInsert(ruleEngineService, "ruleDemoService"));

        logger.info("queryParam1.result = {}", queryParam1.getResult());
        ExecutionResults results = kieSession.execute(CommandFactory.newBatchExecution(cmds));
        QueryParam resultParam = (QueryParam) results.getValue("queryParam");
        logger.info("queryParam1.result = {}", resultParam.getResult());
    }
}
