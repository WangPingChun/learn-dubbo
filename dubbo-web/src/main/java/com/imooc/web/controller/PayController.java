package com.imooc.web.controller;

import com.imooc.common.utils.ImoocJsonResult;
import com.imooc.web.service.ClusterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订购商品.
 *
 * @author : chris
 * 2018-06-30
 */
@Controller
public class PayController {
    private final ClusterService clusterService;

    @Autowired
    public PayController(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @ResponseBody
    @GetMapping("/buy")
    public ImoocJsonResult docGetLogin(String itemId) {
        if (StringUtils.isNotBlank(itemId)) {
            clusterService.displayBuy(itemId);
        } else {
            return ImoocJsonResult.errorMsg("商品id不能为空");
        }
        return ImoocJsonResult.ok();
    }
}
