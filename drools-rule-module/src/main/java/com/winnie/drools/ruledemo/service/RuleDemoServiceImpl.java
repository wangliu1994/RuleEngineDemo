package com.winnie.drools.ruledemo.service;

import com.winnie.common.entity.QueryParam;
import com.winnie.common.service.RuleDemoService;
import org.springframework.stereotype.Service;

/**
 * @author : winnie [wangliu023@qq.com]
 * @date : 2020/5/6
 * @desc
 */
@Service
public class RuleDemoServiceImpl implements RuleDemoService {
    @Override
    public Integer addParam(QueryParam param) {
        return param.getParam1() + param.getParam2();
    }

    @Override
    public Integer subParam(QueryParam param) {
        return param.getParam1() - param.getParam2();
    }

    @Override
    public Integer mulParam(QueryParam param) {
        return param.getParam1() * param.getParam2();
    }

    @Override
    public Integer divParam(QueryParam param) {
        return param.getParam1() / param.getParam2();
    }
}
