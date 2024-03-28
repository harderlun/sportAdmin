package com.sanshao.controller.sys;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.SysPlace;
import com.sanshao.service.SysPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * 场地信息
 */
@RestController
@RequestMapping("/sys/place")
public class SysPlaceController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysPlaceService sysPlaceService;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:place:list')")
    public Result list(String placeName){
        Page<SysPlace> pageData = sysPlaceService.page(getPage(),new LambdaQueryWrapper<SysPlace>().like(StrUtil.isNotBlank(placeName),SysPlace::getPlace,placeName));
        return Result.ok().data("pageData",pageData);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:place:save')")
    public Result save(@Validated @RequestBody SysPlace sysPlace){
        sysPlace.setCreated(new Date());
        boolean flag = sysPlaceService.save(sysPlace);
        return flag ? Result.ok() : Result.error();
    }

    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:place:list')")
    public Result info(@PathVariable("id") Long id){
        SysPlace place = sysPlaceService.getById(id);
        return Result.ok().data("info",place);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('sys:place:update')")
    public Result update(@Validated @RequestBody SysPlace sysPlace){
        sysPlace.setUpdated(new Date());
        boolean flag = sysPlaceService.updateById(sysPlace);
        return flag ? Result.ok() : Result.error();
    }

    @PostMapping("delete")
    @PreAuthorize("hasAuthority('sys:place:delete')")
    @Transactional
    public Result delete(@RequestBody Long[] placeIds){
        boolean flag = sysPlaceService.removeByIds(Arrays.asList(placeIds));
        return flag ? Result.ok() : Result.error();
    }
}

