package com.yu.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yu.common.lang.Result;
import com.yu.entity.SysNotice;
import com.yu.entity.SysPlace;
import com.yu.service.SysPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 场地信息
 */
@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private SysPlaceService sysPlaceService;

    @GetMapping("list")
    public Result list(){
        List<SysPlace> placeList = sysPlaceService.list(new LambdaQueryWrapper<SysPlace>().eq(SysPlace::getStatus,0));
        return Result.ok().data("placeList",placeList);
    }
}
