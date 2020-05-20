package com.example.easyruledemo.rule;

import com.example.easyruledemo.entity.QueryParam;
import com.example.easyruledemo.service.RuleDemoService;
import org.jeasy.rules.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/18
 * @desc
 */
@Rule
public class SubRule1 {
    private Logger logger = LoggerFactory.getLogger(SubRule1.class);

    @Condition
    public boolean isAdd(@Fact("number") QueryParam queryParam) {
        return "-".equals(queryParam.getParamSign());
    }

    @Action
    public void doAction(@Fact("number") QueryParam queryParam, @Fact("service") RuleDemoService ruleDemoService) {
        Integer result = ruleDemoService.subParam(queryParam);
        logger.info("减法规则, result = {}",result);
    }


    @Priority
    public int getPriority() {
        return 2;
    }
}

