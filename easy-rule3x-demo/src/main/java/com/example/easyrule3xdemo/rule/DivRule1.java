package com.example.easyrule3xdemo.rule;
;
import com.example.easyrule3xdemo.entity.QueryParam;
import com.example.easyrule3xdemo.service.RuleDemoService;
import org.jeasy.rules.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/18
 * @desc
 */
@Rule
public class DivRule1 {
    private Logger logger = LoggerFactory.getLogger(DivRule1.class);

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

