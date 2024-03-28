package com.sanshao.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.SysCompensate;
import com.sanshao.entity.SysEquipment;
import com.sanshao.entity.UserBorrow;
import com.sanshao.entity.UserRepairs;
import com.sanshao.server.WebSocketServer;
import com.sanshao.service.SysCompensateService;
import com.sanshao.service.SysEquipmentService;
import com.sanshao.service.UserBorrowService;
import com.sanshao.service.UserRepairsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * 器材租用
 */
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserBorrowService userBorrowService;
    @Autowired
    private SysEquipmentService sysEquipmentService;
    @Autowired
    private SysCompensateService sysCompensateService;
    @Autowired
    private UserRepairsService userRepairsService;
    @Autowired
    private WebSocketServer webSocketServer;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    //用户租用器材
    @PostMapping("save")
    public Result save(@Validated @RequestBody UserBorrow userBorrow) throws IOException {
        int count = sysCompensateService.count(new LambdaQueryWrapper<SysCompensate>().eq(SysCompensate::getUserid, userBorrow.getUserid()).eq(SysCompensate::getStatus,0));
        if (count > 0){
            return Result.error().message("您在失信名单中，无法租借器材");
        }
        userBorrow.setCreated(new Date());
        SysEquipment equipment = sysEquipmentService.getById(userBorrow.getEquipmentid());
        boolean update = false;
        if (equipment.getSurplus() >= userBorrow.getNumber()){
            userBorrow.setTotalmoney(equipment.getMoney() * (userBorrow.getEndtime().getTime() - userBorrow.getStarttime().getTime()) / (1000 *60 * 60 *24));
            equipment.setSurplus(equipment.getSurplus() - userBorrow.getNumber());
            update = sysEquipmentService.updateById(equipment);
        }else{
            return Result.error().message("租用器材不足");
        }
        boolean flag = userBorrowService.save(userBorrow);
        webSocketServer.sendBorrowNotice("一条器材申请，等待您的审批！！！ ");
        return flag && update ? Result.ok() : Result.error().message("抱歉，租用失败");
    }

    @GetMapping("getBorrowOrderByUser/{userId}")
    public Result getBorrowOrderByUser(@PathVariable("userId") Long userId){
        Page<UserBorrow> pageData = userBorrowService.page(getPage(),new LambdaQueryWrapper<UserBorrow>().eq(UserBorrow::getUserid, userId).orderByDesc(UserBorrow::getCreated));
        for (int i = 0; i < pageData.getRecords().size(); i++) {
            UserBorrow borrow = pageData.getRecords().get(i);
            borrow.setEquipmentName(sysEquipmentService.getById(borrow.getEquipmentid()).getName());
            UserRepairs repairs = userRepairsService.getOne(new LambdaQueryWrapper<UserRepairs>().orderByDesc(UserRepairs::getCreated).eq(UserRepairs::getBorrowid, borrow.getId()).last("limit 1"));
            if (repairs != null){
                borrow.setRepairsStatus(repairs.getStatus());
            }
        }
        return Result.ok().data("pageData",pageData);
    }

    @GetMapping("getBackInfo/{id}")
    public Result getBackInfo(@PathVariable("id") Long id){
        UserBorrow backInfo = userBorrowService.getById(id);
        backInfo.setEquipmentName(sysEquipmentService.getById(backInfo.getEquipmentid()).getName());
        return Result.ok().data("backInfo",backInfo);
    }

    @PostMapping("back")
    public Result back(@RequestBody UserBorrow userBorrow){
        userBorrow.setStatus(3);
        boolean flag = userBorrowService.updateById(userBorrow);
        return flag ? Result.ok() : Result.error();
    }
}
