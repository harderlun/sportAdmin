package com.yu.controller.sys;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.common.lang.Result;
import com.yu.entity.SysNotice;
import com.yu.service.SysNoticeService;
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
 * 信息通知
 */
@RestController
@RequestMapping("/sys/notice")
public class SysNoticeController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysNoticeService sysNoticeService;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:notice:list')")
    public Result list(String title){
        Page<SysNotice> pageData = sysNoticeService.page(getPage(),new LambdaQueryWrapper<SysNotice>().like(StrUtil.isNotBlank(title),SysNotice::getTitle,title).orderByDesc(SysNotice::getCreated));
        return Result.ok().data("pageData",pageData);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:notice:save')")
    public Result save(@Validated @RequestBody SysNotice sysNotice){
        sysNotice.setCreated(new Date());
        boolean flag = sysNoticeService.save(sysNotice);
        return flag ? Result.ok() : Result.error();
    }

    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:notice:list')")
    public Result info(@PathVariable("id") Long id){
        SysNotice notice = sysNoticeService.getById(id);
        return Result.ok().data("info",notice);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Result update(@Validated @RequestBody SysNotice sysNotice){
        sysNotice.setUpdated(new Date());
        boolean flag = sysNoticeService.updateById(sysNotice);
        return flag ? Result.ok() : Result.error();
    }

    @PostMapping("delete")
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    @Transactional
    public Result delete(@RequestBody Long[] noticeIds){
        boolean flag = sysNoticeService.removeByIds(Arrays.asList(noticeIds));
        return flag ? Result.ok() : Result.error();
    }
}

