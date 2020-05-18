package com.example.easyruledemo.rule;

import org.easyrules.annotation.Action;
import org.easyrules.core.CompositeRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/18
 * @desc 这是一个组合规则，它由AddRule和SubRule组成
 */
public class AddSubRule extends CompositeRule {
    private Logger logger = LoggerFactory.getLogger(AddSubRule.class);

    public AddSubRule(Object... rules) {
        for (Object rule : rules) {
            addRule(rule);
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Action
    public void doAction() {
        logger.info("加减法规则");
    }
}
