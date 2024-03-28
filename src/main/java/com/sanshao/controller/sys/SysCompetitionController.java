package com.sanshao.controller.sys;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.SysCompetition;
import com.sanshao.service.SysCompetitionService;
import com.sanshao.service.SysUserService;
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
 * 赛事信息
 */
@RestController
@RequestMapping("/sys/competition")
public class SysCompetitionController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysCompetitionService sysCompetitionService;
    @Autowired
    private SysUserService sysUserService;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:competition:list')")
    public Result list(String competitionName){
        Page<SysCompetition> pageData = sysCompetitionService.page(getPage(),new LambdaQueryWrapper<SysCompetition>().like(StrUtil.isNotBlank(competitionName),SysCompetition::getName,competitionName).orderByDesc(SysCompetition::getCreated));
        for (int i = 0; i < pageData.getRecords().size(); i++) {
            SysCompetition competition = pageData.getRecords().get(i);
            competition.setUsername(sysUserService.getById(competition.getUserid()).getUsername());
        }
        return Result.ok().data("pageData",pageData);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:competition:save')")
    public Result save(@Validated @RequestBody SysCompetition sysCompetition){
        sysCompetition.setCreated(new Date());
        sysCompetition.setId((long) (sysCompetitionService.count() + 1));
        boolean flag = sysCompetitionService.save(sysCompetition);
        return flag ? Result.ok() : Result.error();
    }

    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:competition:list')")
    public Result info(@PathVariable("id") Long id){
        SysCompetition competition = sysCompetitionService.getById(id);
        return Result.ok().data("info",competition);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('sys:competition:update')")
    public Result update(@Validated @RequestBody SysCompetition sysCompetition){
        boolean flag = sysCompetitionService.updateById(sysCompetition);
        return flag ? Result.ok() : Result.error();
    }

    @PostMapping("delete")
    @PreAuthorize("hasAuthority('sys:competition:delete')")
    @Transactional
    public Result delete(@RequestBody Long[] competitionIds){
        boolean flag = sysCompetitionService.removeByIds(Arrays.asList(competitionIds));
        return flag ? Result.ok() : Result.error();
    }
}

