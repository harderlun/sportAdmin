package com.yu.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.common.lang.Result;
import com.yu.entity.SysCompetition;
import com.yu.entity.UserApply;
import com.yu.service.SysCompetitionService;
import com.yu.service.UserApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 赛事报名
 */
@RestController
@RequestMapping("/user/apply")
public class UserApplyController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserApplyService userApplyService;
    @Autowired
    private SysCompetitionService sysCompetitionService;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @PostMapping("save")
    public Result save(@RequestBody UserApply userApply){
        UserApply apply = userApplyService.getOne(new LambdaQueryWrapper<UserApply>().eq(UserApply::getCompetitionid, userApply.getCompetitionid()).eq(UserApply::getUserid, userApply.getUserid()));
        boolean flag = false;
        if (apply != null){
            apply.setCreated(new Date());
            apply.setStatus(0);
            flag = userApplyService.updateById(apply);
        }else{
            userApply.setCreated(new Date());
            flag = userApplyService.save(userApply);
        }
        return flag ? Result.ok() : Result.error().message("报名失败！！！");
    }

    @PostMapping("cancel/{id}/{userId}")
    public Result cancel(@PathVariable("id") Long id,@PathVariable("userId") Long userId){
        UserApply userApply = userApplyService.getOne(new LambdaQueryWrapper<UserApply>().eq(UserApply::getCompetitionid, id).eq(UserApply::getUserid, userId));
        userApply.setStatus(1);
        boolean flag = userApplyService.updateById(userApply);
        return flag ? Result.ok() : Result.error().message("取消失败！！！");
    }

    @GetMapping("getApplyOrderByUser/{userId}")
    public Result getCompetitionOrderByUser(@PathVariable("userId") Long userId){
        Page<UserApply> pageData = userApplyService.page(getPage(),new LambdaQueryWrapper<UserApply>().eq(UserApply::getUserid, userId).orderByDesc(UserApply::getCreated));
        for (int i = 0; i < pageData.getRecords().size(); i++) {
            UserApply apply = pageData.getRecords().get(i);
            SysCompetition competition = sysCompetitionService.getById(apply.getCompetitionid());
            apply.setCompetitionStartTime(competition.getStarttime());
            apply.setCompetitionName(competition.getName());
        }
        return Result.ok().data("pageData",pageData);
    }
}

