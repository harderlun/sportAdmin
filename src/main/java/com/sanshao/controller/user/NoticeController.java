package com.sanshao.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.SysNotice;
import com.sanshao.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 通知展示
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

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
    public Result list(){
        Page<SysNotice> pageData = sysNoticeService.page(getPage(),new LambdaQueryWrapper<SysNotice>().orderByDesc(SysNotice::getCreated).eq(SysNotice::getStatus,0));
        return Result.ok().data("pageData",pageData);
    }

    @GetMapping("info/{id}")
    public Result info(@PathVariable("id") Long id){
        SysNotice notice = sysNoticeService.getById(id);
        return Result.ok().data("info",notice);
    }
}
