package com.yu.controller.user;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.common.lang.Result;
import com.yu.entity.SysEquipment;
import com.yu.server.WebSocketServer;
import com.yu.service.SysEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 器材展示
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysEquipmentService sysEquipmentService;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @GetMapping("list")
    public Result list(String competitionName) {
        Page<SysEquipment> pageData = sysEquipmentService.page(getPage(),new LambdaQueryWrapper<SysEquipment>().eq(SysEquipment::getStatus,0).like(StrUtil.isNotBlank(competitionName), SysEquipment::getName,competitionName));
        return Result.ok().data("pageData",pageData);
    }
}
