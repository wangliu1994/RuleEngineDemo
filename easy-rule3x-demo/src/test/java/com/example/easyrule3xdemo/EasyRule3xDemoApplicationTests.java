package com.example.easyrule3xdemo;

import com.winnie.common.entity.QueryParam;
import com.example.easyrule3xdemo.rule.*;
import com.winnie.common.service.RuleDemoService;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RulesEngineParameters;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileReader;

@SpringBootTest
class EasyRule3xDemoApplicationTests {

    private Logger logger = LoggerFactory.getLogger(EasyRule3xDemoApplicationTests.class);

    @Resource
    private RuleDemoService ruleDemoService;


    @Test
    void contextLoads() {
    }

    @Test
    void testAddRule() {
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
        facts.put("service", ruleDemoService);

        Rules rules = new Rules();
        AddRule addRule = new AddRule();
        SubRule subRule = new SubRule();
        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
        MulRule mulRule = new MulRule();
        DivRule divRule = new DivRule();

        rules.register(addRule);
        rules.register(subRule);
        rules.register(addSubRule);
        rules.register(mulRule);
        rules.register(divRule);
        rulesEngine.fire(rules, facts);
        logger.info("result = {}",queryParam1.getResult());
    }

    @Test
    void testSubRule() {
        RulesEngineParameters parameters = new RulesEngineParameters()
                // 如果第一个规则满足条件，后面的规则将不再执行
                .skipOnFirstAppliedRule(true);
        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("-");
        Facts facts = new Facts();
        facts.put("number", queryParam1);
        facts.put("service", ruleDemoService);

        Rules rules = new Rules();
        AddRule addRule = new AddRule();
        SubRule subRule = new SubRule();
        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
        MulRule mulRule = new MulRule();
        DivRule divRule = new DivRule();

        rules.register(addRule);
        rules.register(subRule);
        rules.register(addSubRule);
        rules.register(mulRule);
        rules.register(divRule);
        rulesEngine.fire(rules, facts);
    }

    @Test
    void testMulRule() {
        RulesEngineParameters parameters = new RulesEngineParameters()
                // 如果第一个规则满足条件，后面的规则将不再执行
                .skipOnFirstAppliedRule(true);
        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("*");
        Facts facts = new Facts();
        facts.put("number", queryParam1);
        facts.put("service", ruleDemoService);

        Rules rules = new Rules();
        AddRule addRule = new AddRule();
        SubRule subRule = new SubRule();
        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
        MulRule mulRule = new MulRule();
        DivRule divRule = new DivRule();

        rules.register(addRule);
        rules.register(subRule);
        rules.register(addSubRule);
        rules.register(mulRule);
        rules.register(divRule);
        rulesEngine.fire(rules, facts);
    }

    @Test
    void testDivRule() {
        RulesEngineParameters parameters = new RulesEngineParameters()
                // 如果第一个规则满足条件，后面的规则将不再执行
                .skipOnFirstAppliedRule(true);
        RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("/");
        Facts facts = new Facts();
        facts.put("number", queryParam1);
        facts.put("service", ruleDemoService);

        Rules rules = new Rules();
        AddRule addRule = new AddRule();
        SubRule subRule = new SubRule();
        AddSubRule addSubRule = new AddSubRule(subRule, addRule);
        MulRule mulRule = new MulRule();
        DivRule divRule = new DivRule();

        rules.register(addRule);
        rules.register(subRule);
        rules.register(addSubRule);
        rules.register(mulRule);
        rules.register(divRule);
        rulesEngine.fire(rules, facts);
    }

    /**
     * 基于MVEL表达式的编程模型
     */
    @Test
    void testMVELAddRule() {
        Rule addRule = new MVELRule()
                .name("add rule")
                .description("加法规则引擎")
                .priority(1)
                .when("\"+\".equals(number.getParamSign())")
//        .then("logger.info(\"加法规则, result = {}\",service.addParam(number));");
                .then("System.out.println(\"加法规则, result = \" +  service.addParam(number))");

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");
        Facts facts = new Facts();
        facts.put("number", queryParam1);
        facts.put("service", ruleDemoService);

        Rules rules = new Rules();
        rules.register(addRule);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }

    @Test
    void testMVELSubRule() throws FileNotFoundException {
        Rule subRule = MVELRuleFactory.createRuleFrom(new FileReader("sub-rule.yml"));

        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("-");
        Facts facts = new Facts();
        facts.put("number", queryParam1);
        facts.put("service", ruleDemoService);

        Rules rules = new Rules();
        rules.register(subRule);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }
}
