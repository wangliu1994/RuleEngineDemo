package com.example.easyrule2xdemo;

import com.example.easyrule2xdemo.entity.QueryParam;
import com.example.easyrule2xdemo.rule.*;
import com.example.easyrule2xdemo.service.RuleDemoService;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class EasyRule2xDemoApplicationTests {
    private Logger logger = LoggerFactory.getLogger(EasyRule2xDemoApplicationTests.class);

    @Resource
    private RuleDemoService ruleDemoService;

    @Test
    void contextLoads() {
    }


    @Test
    void testAddRule(){
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .withSkipOnFirstAppliedRule(true).withSilentMode(true).build();

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");

        AddRule addRule = new AddRule();
        SubRule subRule = new SubRule();
        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
        MulRule mulRule = new MulRule();
        DivRule divRule = new DivRule();

        addRule.setParam(queryParam1, ruleDemoService);
        subRule.setParam(queryParam1, ruleDemoService);
        mulRule.setParam(queryParam1, ruleDemoService);
        divRule.setParam(queryParam1, ruleDemoService);

        rulesEngine.registerRule(addRule);
        rulesEngine.registerRule(subRule);
        rulesEngine.registerRule(addSubRule);
        rulesEngine.registerRule(mulRule);
        rulesEngine.registerRule(divRule);

        rulesEngine.fireRules();
    }


    @Test
    void testSubRule(){
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .withSkipOnFirstAppliedRule(true).withSilentMode(true).build();

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("-");

        AddRule addRule = new AddRule();
        SubRule subRule = new SubRule();
        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
        MulRule mulRule = new MulRule();
        DivRule divRule = new DivRule();

        addRule.setParam(queryParam1, ruleDemoService);
        subRule.setParam(queryParam1, ruleDemoService);
        mulRule.setParam(queryParam1, ruleDemoService);
        divRule.setParam(queryParam1, ruleDemoService);

        rulesEngine.registerRule(addRule);
        rulesEngine.registerRule(subRule);
        rulesEngine.registerRule(addSubRule);
        rulesEngine.registerRule(mulRule);
        rulesEngine.registerRule(divRule);

        rulesEngine.fireRules();
    }

    @Test
    void testMulRule(){
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .withSkipOnFirstAppliedRule(true).withSilentMode(true).build();

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("*");

        AddRule addRule = new AddRule();
        SubRule subRule = new SubRule();
        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
        MulRule mulRule = new MulRule();
        DivRule divRule = new DivRule();

        addRule.setParam(queryParam1, ruleDemoService);
        subRule.setParam(queryParam1, ruleDemoService);
        mulRule.setParam(queryParam1, ruleDemoService);
        divRule.setParam(queryParam1, ruleDemoService);

        rulesEngine.registerRule(addRule);
        rulesEngine.registerRule(subRule);
        rulesEngine.registerRule(addSubRule);
        rulesEngine.registerRule(mulRule);
        rulesEngine.registerRule(divRule);

        rulesEngine.fireRules();
    }

    @Test
    void testDivRule(){
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                // 如果第一个规则满足条件，后面的规则将不再执行
                .withSkipOnFirstAppliedRule(true)
                .withSilentMode(true).build();

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("/");

        AddRule addRule = new AddRule();
        SubRule subRule = new SubRule();
        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
        MulRule mulRule = new MulRule();
        DivRule divRule = new DivRule();

        addRule.setParam(queryParam1, ruleDemoService);
        subRule.setParam(queryParam1, ruleDemoService);
        mulRule.setParam(queryParam1, ruleDemoService);
        divRule.setParam(queryParam1, ruleDemoService);

        rulesEngine.registerRule(addRule);
        rulesEngine.registerRule(subRule);
        rulesEngine.registerRule(addSubRule);
        rulesEngine.registerRule(mulRule);
        rulesEngine.registerRule(divRule);

        rulesEngine.fireRules();
    }
}
