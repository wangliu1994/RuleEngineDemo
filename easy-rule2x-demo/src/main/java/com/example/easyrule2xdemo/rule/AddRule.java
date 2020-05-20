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
 * @desc @Rule可以标注name和description属性，每个rule的name要唯一，如果没有指定，则RuleProxy则默认取类名@Rule可以标注name和description属性，每个rule的name要唯一，如果没有指定，则RuleProxy则默认取类名
 */
@Rule
public class AddRule {
    private Logger logger = LoggerFactory.getLogger(AddRule.class);

    private RuleDemoService ruleDemoService;

    private QueryParam queryParam;

    /**
     * 判断是否命中规则
     * -@Condition是条件判断，要求返回boolean值，表示是否满足条件
     */
    @Condition
    public boolean isAdd() {
        return "+".equals(queryParam.getParamSign());
    }

    /**
     * 命中规则后执行动作
     * -@Action标注条件成立之后触发的方法
     */
    @Action
    public void doAction() {
        Integer result = ruleDemoService.addParam(queryParam);
        logger.info("加法规则, result = {}",result);
    }


    public void setParam(QueryParam queryParam, RuleDemoService ruleDemoService) {
        this.queryParam = queryParam;
        this.ruleDemoService = ruleDemoService;
    }

    /**
     * 优先级 0/1/2/3.....  越小优先级越高
     * -@Priority标注该rule的优先级，默认是Integer.MAX_VALUE - 1，值越小越优先
     */
    @Priority
    public int getPriority() {
        return 1;
    }
}

