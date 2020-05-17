package com.example.drooldemo.controller;

import com.example.drooldemo.entity.QueryParam;
import com.example.drooldemo.entity.ResultParam;
import com.example.drooldemo.service.RuleDemoService;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/6
 * @desc
 */
@RestController
public class RuleDemoController {
    @Resource
    private KieSession kieSession;

    @Resource
    private RuleDemoService ruleEngineService;

    @RequestMapping("/param")
    public void doParam() {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParam1(10);
        queryParam1.setParam2(5);
        queryParam1.setParamSign("+");

        QueryParam queryParam2 = new QueryParam();
        queryParam2.setParam1(10);
        queryParam2.setParam2(5);
        queryParam2.setParamSign("-");


        QueryParam queryParam3 = new QueryParam();
        queryParam3.setParam1(10);
        queryParam3.setParam2(5);
        queryParam3.setParamSign("*");

        QueryParam queryParam4 = new QueryParam();
        queryParam4.setParam1(10);
        queryParam4.setParam2(5);
        queryParam4.setParamSign("/");

        Integer result = 0;
        ResultParam resultParam = new ResultParam();

        kieSession.insert(queryParam1);
        System.out.println(queryParam1.getResult());
        kieSession.insert(queryParam2);
        kieSession.insert(queryParam3);
        kieSession.insert(queryParam4);
        //ruleEngineService也可以在这里插入，即在drl写规则时直接使用,见param_check_2.drl
        kieSession.insert(ruleEngineService);


        kieSession.insert(result);
        kieSession.insert(resultParam);

        kieSession.fireAllRules();
        //通过打印日志可以看到，Java
        //在drools中，这个传递数据进去的对象，术语叫 Fact对象。Fact对象是一个普通的java bean，
        // 规则中可以对当前的对象进行任何的读写操作，调用该对象提供的方法，当一个java bean插入到workingMemory中，
        // 规则使用的是原有对象的引用，规则通过对fact对象的读写，实现对应用数据的读写，对于其中的属性，需要提供getter setter访问器
        System.out.println("queryParam1.result = " + queryParam1.getResult());
        System.out.println("queryParam2.result = " + queryParam2.getResult());
        System.out.println("queryParam3.result = " + resultParam.getResult());
        System.out.println("queryParam4.result = " + resultParam.getResult());

        //通过打印日志可以看到， Integer result在规则中被赋了其他值，不是本身了，所以获取不到
        System.out.println(result);
    }
}
