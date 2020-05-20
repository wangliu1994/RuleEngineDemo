package com.example.easyrule3xdemo.rule;

import com.example.easyrule3xdemo.entity.QueryParam;
import com.example.easyrule3xdemo.service.RuleDemoService;
import org.jeasy.rules.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/18
 * @desc @Rule可以标注name和description属性，每个rule的name要唯一，如果没有指定，则RuleProxy则默认取类名@Rule可以标注name和description属性，每个rule的name要唯一，如果没有指定，则RuleProxy则默认取类名
 */
@Rule(priority = 1)
public class AddRule1 {
    private Logger logger = LoggerFactory.getLogger(AddRule1.class);

    /**
     * 判断是否命中规则
     * -@Condition是条件判断，要求返回boolean值，表示是否满足条件
     */
    @Condition
    public boolean isAdd(@Fact("number") QueryParam queryParam) {
        return "+".equals(queryParam.getParamSign());
    }

    /**
     * 命中规则后执行动作
     * -@Action标注条件成立之后触发的方法
     */
    @Action
    public void doAction(@Fact("number") QueryParam queryParam, @Fact("service") RuleDemoService ruleDemoService) {
        Integer result = ruleDemoService.addParam(queryParam);
        logger.info("加法规则, result = {}",result);
    }
}

