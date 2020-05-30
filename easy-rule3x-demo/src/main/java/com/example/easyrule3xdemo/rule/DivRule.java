package com.example.easyrule3xdemo.rule;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.service.RuleDemoService;
import org.jeasy.rules.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/18
 * @desc
 */
@Rule
public class DivRule {
    private Logger logger = LoggerFactory.getLogger(DivRule.class);

    @Condition
    public boolean isAdd(@Fact("number") QueryParam queryParam) {
        return "/".equals(queryParam.getParamSign());
    }

    @Action
    public void doAction(@Fact("number") QueryParam queryParam, @Fact("service") RuleDemoService ruleDemoService) {
        Integer result = ruleDemoService.divParam(queryParam);
        logger.info("除法规则, result = {}",result);
    }

    @Priority
    public int getPriority() {
        return 4;
    }
}

