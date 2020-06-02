package com.winnie.drools.ruledemo;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.service.RuleDemoService;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
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

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/6/2
 * @desc
 */
@SpringBootTest
public class KieSessionTest {
    @Resource
    private RuleDemoService ruleEngineService;

    private Logger logger = LoggerFactory.getLogger(DroolsRuleConfigApplicationTests.class);

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
}
