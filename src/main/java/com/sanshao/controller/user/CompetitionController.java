package com.sanshao.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.*;
import com.sanshao.service.SysCompetitionService;
import com.sanshao.service.SysUserService;
import com.sanshao.service.UserApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 赛事展示
 */
@RestController
@RequestMapping("/competition")
public class CompetitionController {

    @Autowired
    private SysCompetitionService sysCompetitionService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserApplyService userApplyService;

    @GetMapping("getCompetitionList/{id}")
    public Result getCompetitionList(@PathVariable("id") Long id){
        List<SysCompetition> competitionList = sysCompetitionService.list(new LambdaQueryWrapper<SysCompetition>().orderByDesc(SysCompetition::getCreated).eq(SysCompetition::getStatus, 0));
        for (int i = 0; i < competitionList.size(); i++) {
            SysCompetition competition = competitionList.get(i);
            UserApply apply = userApplyService.getOne(new LambdaQueryWrapper<UserApply>().eq(UserApply::getCompetitionid, competition.getId()).eq(UserApply::getUserid, id));
            if (apply != null){
                competition.setApplyStatus(apply.getStatus());
            }else{
                competition.setApplyStatus(1);
            }
            competition.setUsername(sysUserService.getById(competition.getUserid()).getUsername());
        }
        return competitionList.size() > 0 ? Result.ok().data("competitionList",competitionList) : Result.error();
    }

    @GetMapping("getCompetitionInfo/{id}/{userId}")
    public Result getCompetitionInfo(@PathVariable("id") Long id,@PathVariable("userId") Long userId){
        SysCompetition competitionInfo = sysCompetitionService.getById(id);
        UserApply apply = userApplyService.getOne(new LambdaQueryWrapper<UserApply>().eq(UserApply::getCompetitionid,id).eq(UserApply::getUserid, userId));
        if (apply != null){
            competitionInfo.setApplyStatus(apply.getStatus());
        }else{
            competitionInfo.setApplyStatus(1);
        }
        competitionInfo.setUsername(sysUserService.getById(competitionInfo.getUserid()).getUsername());
        return Result.ok().data("competitionInfo",competitionInfo);
    }

    @GetMapping("getApplyPeopleList/{id}")
    public Result getApplyPeopleList(@PathVariable("id") Long id){
        List<UserApply> userApplyList = userApplyService.list(new LambdaQueryWrapper<UserApply>().eq(UserApply::getCompetitionid, id).eq(UserApply::getStatus, 0));
        for (int i = 0; i < userApplyList.size(); i++) {
            UserApply userApply = userApplyList.get(i);
            SysUser applyUser = sysUserService.getById(userApply.getUserid());
            userApply.setUsername(applyUser.getUsername());
            userApply.setUserAvatar(applyUser.getAvatar());
        }
        return Result.ok().data("userApplyList",userApplyList);
    }


}
