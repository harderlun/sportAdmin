package com.yu.controller.sys;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.common.lang.Result;
import com.yu.entity.SysEquipment;
import com.yu.entity.SysUser;
import com.yu.entity.UserBorrow;
import com.yu.entity.UserRepairs;
import com.yu.server.WebSocketServer;
import com.yu.service.SysEquipmentService;
import com.yu.service.SysUserService;
import com.yu.service.UserBorrowService;
import com.yu.service.UserRepairsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 器材租用
 */
@RestController
@RequestMapping("/sys/borrow")
public class SysBorrowController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserBorrowService userBorrowService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysEquipmentService sysEquipmentService;
    @Autowired
    private UserRepairsService userRepairsService;
    @Autowired
    private WebSocketServer webSocketServer;


    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:borrow:list')")
    public Result list(String username){
        Page<UserBorrow> pageData = null;
        if (StrUtil.isNotBlank(username)){
            List<SysUser> userList = sysUserService.list(new LambdaQueryWrapper<SysUser>().like(SysUser::getUsername, username));
            for (int i = 0; i < userList.size(); i++) {
                SysUser user = userList.get(i);
                if (i == 0){
                    pageData = userBorrowService.page(getPage(),new LambdaQueryWrapper<UserBorrow>().eq(UserBorrow::getUserid,user.getId()).orderByDesc(UserBorrow::getCreated));
                }else{
                    List<UserBorrow> borrowList = userBorrowService.list(new LambdaQueryWrapper<UserBorrow>().eq(UserBorrow::getUserid, user.getId()).orderByDesc(UserBorrow::getCreated));
                    pageData.getRecords().addAll(borrowList);
                    pageData.setTotal(pageData.getTotal() + borrowList.size());
                }
            }
        }else{
            pageData = userBorrowService.page(getPage(),new LambdaQueryWrapper<UserBorrow>().orderByDesc(UserBorrow::getCreated));
        }
        for (int i = 0; i < pageData.getRecords().size(); i++) {
            UserBorrow borrow = pageData.getRecords().get(i);
            borrow.setUsername(sysUserService.getById(borrow.getUserid()).getUsername());
            borrow.setEquipmentName(sysEquipmentService.getById(borrow.getEquipmentid()).getName());
            UserRepairs repairs = userRepairsService.getOne(new LambdaQueryWrapper<UserRepairs>().orderByDesc(UserRepairs::getCreated).eq(UserRepairs::getBorrowid, borrow.getId()).last("limit 1"));
            if (repairs != null){
                borrow.setRepairsStatus(repairs.getStatus());
            }
        }
        return Result.ok().data("pageData",pageData);
    }

    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:borrow:approve')")
    public Result info(@PathVariable("id") Long id){
        UserBorrow borrow = userBorrowService.getById(id);
        return Result.ok().data("info",borrow);
    }

    @PostMapping("passOrNotPass")
    @PreAuthorize("hasAuthority('sys:borrow:list')")
    public Result passOrNotPass(@Validated @RequestBody UserBorrow userBorrow){
        if(userBorrow.getStatus() == 2){
            SysEquipment equipment = sysEquipmentService.getById(userBorrow.getEquipmentid());
            equipment.setSurplus(equipment.getSurplus() + userBorrow.getNumber());
            sysEquipmentService.save(equipment);
        }
        boolean flag = userBorrowService.updateById(userBorrow);
        return flag ? Result.ok() : Result.error();
    }

    @GetMapping("getBorrowNum")
    @PreAuthorize("hasAuthority('sys:borrow:list')")
    public Result getBorrowNum(){
        List<UserBorrow> borrowNoticeList = userBorrowService.list(new LambdaQueryWrapper<UserBorrow>().eq(UserBorrow::getStatus, 0));
        int num = borrowNoticeList.size();
        for (int i = 0; i < borrowNoticeList.size(); i++) {
            borrowNoticeList.get(i).setUsername(sysUserService.getById(borrowNoticeList.get(i).getUserid()).getUsername());
        }
        return Result.ok().data("borrowNum",num).data("borrowNoticeList",borrowNoticeList);
    }

    @PostMapping("back/{id}")
    @PreAuthorize("hasAuthority('sys:borrow:approve')")
    public Result back(@PathVariable("id") Long id) throws IOException {
        UserBorrow borrow = userBorrowService.getById(id);
        borrow.setStatus(4);
        SysEquipment equipment = sysEquipmentService.getById(borrow.getEquipmentid());
        equipment.setSurplus(equipment.getSurplus() + borrow.getNumber());
        boolean flag = userBorrowService.updateById(borrow) && sysEquipmentService.updateById(equipment);
        return flag ? Result.ok() : Result.error();
    }

    @PostMapping("repair/{id}")
    @PreAuthorize("hasAuthority('sys:borrow:approve')")
    public Result repair(@PathVariable("id") Long id){
        UserRepairs repairs = userRepairsService.getOne(new LambdaQueryWrapper<UserRepairs>().orderByDesc(UserRepairs::getCreated).eq(UserRepairs::getBorrowid,id).last("limit 1"));
        repairs.setStatus(1);
        boolean flag = userRepairsService.updateById(repairs);
        return flag ? Result.ok() : Result.error();
    }
}

