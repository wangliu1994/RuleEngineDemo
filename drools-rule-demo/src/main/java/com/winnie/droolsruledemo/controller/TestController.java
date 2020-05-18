package com.winnie.droolsruledemo.controller;

import com.winnie.droolsruledemo.entity.QueryParam;
import com.winnie.droolsruledemo.service.RuleDemoService;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/18
 * @desc
 */
@RestController
public class TestController {
    @Resource
    private KieSession kieSession;

    @Resource
    private RuleDemoService ruleEngineService;

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("test")
    public void test(){
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("++");
        Integer result = 0;

        kieSession.insert(queryParam1);
        logger.info("queryParam1.result = {}", queryParam1.getResult());
        kieSession.insert(result);

        kieSession.fireAllRules();
        //通过打印日志可以看到
        //在drools中，这个传递数据进去的对象，术语叫 Fact对象。Fact对象是一个普通的java bean，
        // 规则中可以对当前的对象进行任何的读写操作，调用该对象提供的方法，当一个java bean插入到workingMemory中，
        // 规则使用的是原有对象的引用，规则通过对fact对象的读写，实现对应用数据的读写，对于其中的属性，需要提供getter setter访问器
        logger.info("queryParam1.result = {}", queryParam1.getResult());

        //通过打印日志可以看到， Integer result在规则中被赋了其他值，不是本身了，所以获取不到
        logger.info("result = {}", result);
    }
}
