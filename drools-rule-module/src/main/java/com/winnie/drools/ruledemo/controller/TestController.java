package com.winnie.drools.ruledemo.controller;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.service.RuleDemoService;
import io.swagger.annotations.ApiOperation;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/testRule")
    @ApiOperation("有状态的kieSession")
    public Integer testRule(@RequestBody QueryParam queryParam) {
        Integer result = 0;

        KieServices kieServices = KieServices.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("KSession1_1");

        kieSession.insert(queryParam);
        logger.info("queryParam.result = {}", queryParam.getResult());
        kieSession.insert(result);

        kieSession.fireAllRules();
        logger.info("queryParam.result = {}", queryParam.getResult());
        kieSession.dispose();

        return queryParam.getResult();
    }

    @PostMapping("/testStateless")
    @ApiOperation("无状态的kieSession")
    public Integer testStateless(@RequestBody QueryParam queryParam) {
        Integer result = 0;

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
//        ReleaseId releaseId = kieServices.newReleaseId( "com.winnie", "drools-rule-module", "0.0.1-SNAPSHOT" );
//        KieContainer kieContainer = kieServices.newKieContainer( releaseId );

//        KieScanner kScanner = kieServices.newKieScanner(kieContainer);
//        kScanner.start( 1000L );
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession("KSession1_2");

        List<Command> cmds = new ArrayList<>();
        cmds.add(CommandFactory.newInsert(queryParam,"queryParam"));
        cmds.add(CommandFactory.newInsert(result, "resultParam"));
        cmds.add(CommandFactory.newInsert(ruleEngineService, "ruleDemoService"));

        logger.info("queryParam1.result = {}", queryParam.getResult());
        ExecutionResults results = kieSession.execute(CommandFactory.newBatchExecution(cmds));
        QueryParam resultParam = (QueryParam) results.getValue("queryParam");
        logger.info("queryParam1.result = {}", resultParam.getResult());

        return queryParam.getResult();
    }
}
