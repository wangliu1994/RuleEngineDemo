package com.example.easyrule2xdemo.rule;

import com.example.easyrule2xdemo.entity.QueryParam;
import com.example.easyrule2xdemo.service.RuleDemoService;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/18
 * @desc
 */
@Rule
public class MulRule {
    private Logger logger = LoggerFactory.getLogger(MulRule.class);

    private RuleDemoService ruleDemoService;

    private QueryParam queryParam;


    @Condition
    public boolean isAdd() {
        return "*".equals(queryParam.getParamSign());
    }

    @Action
    public void doAction() {
        Integer result = ruleDemoService.mulParam(queryParam);
        logger.info("乘法规则, result = {}",result);
    }

    public void setParam(QueryParam queryParam, RuleDemoService ruleDemoService) {
        this.queryParam = queryParam;
        this.ruleDemoService = ruleDemoService;
    }

    @Priority
    public int getPriority() {
        return 3;
    }
}

