package com.example.easyruledemo;

import com.example.easyruledemo.entity.QueryParam;
import com.example.easyruledemo.rule.*;
import com.example.easyruledemo.service.RuleDemoService;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class EasyRuleDemoApplicationTests {

    @Resource
    private RuleDemoService ruleDemoService;

    @Test
    void contextLoads() {
    }


//    @Test
//    void testAddRule(){
//        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
//                .withSkipOnFirstAppliedRule(true).withSilentMode(true).build();
//
//        QueryParam queryParam1 = new QueryParam();
//        queryParam1.setParam1(10);
//        queryParam1.setParam2(5);
//        queryParam1.setParamSign("+");
//
//        AddRule addRule = new AddRule();
//        SubRule subRule = new SubRule();
//        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
//        MulRule mulRule = new MulRule();
//        DivRule divRule = new DivRule();
//
//        addRule.setParam(queryParam1, ruleDemoService);
//        subRule.setParam(queryParam1, ruleDemoService);
//        mulRule.setParam(queryParam1, ruleDemoService);
//        divRule.setParam(queryParam1, ruleDemoService);
//
//        rulesEngine.registerRule(addRule);
//        rulesEngine.registerRule(subRule);
//        rulesEngine.registerRule(addSubRule);
//        rulesEngine.registerRule(mulRule);
//        rulesEngine.registerRule(divRule);
//
//        rulesEngine.fireRules();
//    }

    @Test
    void testAddRule(){
        RulesEngineParameters parameters = new RulesEngineParameters()
                // 如果第一个规则满足条件，后面的规则将不再执行
                .skipOnFirstAppliedRule(true);
        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");
        Facts facts = new Facts();
        facts.put("number", queryParam1);

        Rules rules = new Rules();
        AddRule1 addRule = new AddRule1();
        SubRule1 subRule = new SubRule1();
        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
        MulRule1 mulRule = new MulRule1();
        DivRule1 divRule = new DivRule1();

        rules.register(addRule);
        rules.register(subRule);
        rules.register(addSubRule);
        rules.register(mulRule);
        rules.register(divRule);
        rulesEngine.fire(rules, facts);
    }

//    @Test
//    void testSubRule(){
//        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
//                .withSkipOnFirstAppliedRule(true).withSilentMode(true).build();
//
//        QueryParam queryParam1 = new QueryParam();
//        queryParam1.setParam1(10);
//        queryParam1.setParam2(5);
//        queryParam1.setParamSign("-");
//
//        AddRule addRule = new AddRule();
//        SubRule subRule = new SubRule();
//        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
//        MulRule mulRule = new MulRule();
//        DivRule divRule = new DivRule();
//
//        addRule.setParam(queryParam1, ruleDemoService);
//        subRule.setParam(queryParam1, ruleDemoService);
//        mulRule.setParam(queryParam1, ruleDemoService);
//        divRule.setParam(queryParam1, ruleDemoService);
//
//        rulesEngine.registerRule(addRule);
//        rulesEngine.registerRule(subRule);
//        rulesEngine.registerRule(addSubRule);
//        rulesEngine.registerRule(mulRule);
//        rulesEngine.registerRule(divRule);
//
//        rulesEngine.fireRules();
//    }
//
//    @Test
//    void testMulRule(){
//        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
//                .withSkipOnFirstAppliedRule(true).withSilentMode(true).build();
//
//        QueryParam queryParam1 = new QueryParam();
//        queryParam1.setParam1(10);
//        queryParam1.setParam2(5);
//        queryParam1.setParamSign("*");
//
//        AddRule addRule = new AddRule();
//        SubRule subRule = new SubRule();
//        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
//        MulRule mulRule = new MulRule();
//        DivRule divRule = new DivRule();
//
//        addRule.setParam(queryParam1, ruleDemoService);
//        subRule.setParam(queryParam1, ruleDemoService);
//        mulRule.setParam(queryParam1, ruleDemoService);
//        divRule.setParam(queryParam1, ruleDemoService);
//
//        rulesEngine.registerRule(addRule);
//        rulesEngine.registerRule(subRule);
//        rulesEngine.registerRule(addSubRule);
//        rulesEngine.registerRule(mulRule);
//        rulesEngine.registerRule(divRule);
//
//        rulesEngine.fireRules();
//    }
//
//    @Test
//    void testDivRule(){
//        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
//                // 如果第一个规则满足条件，后面的规则将不再执行
//                .withSkipOnFirstAppliedRule(true)
//                .withSilentMode(true).build();
//
//        QueryParam queryParam1 = new QueryParam();
//        queryParam1.setParam1(10);
//        queryParam1.setParam2(5);
//        queryParam1.setParamSign("/");
//
//        AddRule addRule = new AddRule();
//        SubRule subRule = new SubRule();
//        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
//        MulRule mulRule = new MulRule();
//        DivRule divRule = new DivRule();
//
//        addRule.setParam(queryParam1, ruleDemoService);
//        subRule.setParam(queryParam1, ruleDemoService);
//        mulRule.setParam(queryParam1, ruleDemoService);
//        divRule.setParam(queryParam1, ruleDemoService);
//
//        rulesEngine.registerRule(addRule);
//        rulesEngine.registerRule(subRule);
//        rulesEngine.registerRule(addSubRule);
//        rulesEngine.registerRule(mulRule);
//        rulesEngine.registerRule(divRule);
//
//        rulesEngine.fireRules();
//    }
}
