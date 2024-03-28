package com.sanshao.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.PlaceVdstate;
import com.sanshao.entity.UserAppointment;
import com.sanshao.service.PlaceVdstateService;
import com.sanshao.service.SysPlaceService;
import com.sanshao.service.UserAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 场地预约
 */
@RestController
@RequestMapping("/appointment")
public class UserAppointmentController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserAppointmentService userAppointmentService;
    @Autowired
    private PlaceVdstateService placeVdstateService;
    @Autowired
    private SysPlaceService sysPlaceService;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @PostMapping("order")
    public Result order(@RequestBody UserAppointment userAppointment){
        PlaceVdstate placeVdstate = placeVdstateService.getById(userAppointment.getPlaceStateId());
        char[] cs = placeVdstate.getVdstatest().toCharArray();
        if (cs[userAppointment.getOrderstart() - 8] == '2' || cs[userAppointment.getOrderstart() - 8] == '1'){
            return Result.error().message("预约失败");
        }
        cs[userAppointment.getOrderstart() - 8] = (char)(userAppointment.getRadio() + '0');
        placeVdstate.setVdstatest(new String(cs));
        boolean flag = placeVdstateService.updateById(placeVdstate);
        if (!flag){
            return Result.error().message("预约失败");
        }
        if (userAppointment.getRadio() == 2){
            UserAppointment appointment = userAppointmentService.getOne(new LambdaQueryWrapper<UserAppointment>().eq(UserAppointment::getUserid, userAppointment.getUserid())
                    .eq(UserAppointment::getOrderdate, userAppointment.getOrderdate()).eq(UserAppointment::getPlaceid, userAppointment.getPlaceid())
                    .eq(UserAppointment::getOrderstart, userAppointment.getOrderstart()).eq(UserAppointment::getOrderend, userAppointment.getOrderend()));
            userAppointment.setCreated(new Date());
            if (appointment == null){
                flag = userAppointmentService.save(userAppointment);
            }else{
                appointment.setStatus(0);
                flag = userAppointmentService.updateById(appointment);
            }
        }
        return flag ? Result.ok() : Result.error().message("预约失败");
    }

    @RequestMapping("getAppointmentOrderByUser/{userId}")
    public Result getAppointmentOrderByUser(@PathVariable("userId") Long userId){
        Page<UserAppointment> pageData = userAppointmentService.page(getPage(),new LambdaQueryWrapper<UserAppointment>().eq(UserAppointment::getUserid, userId).orderByDesc(UserAppointment::getCreated));
        for (int i = 0; i < pageData.getRecords().size(); i++) {
            UserAppointment appointment = pageData.getRecords().get(i);
            appointment.setPlaceName(sysPlaceService.getById(appointment.getPlaceid()).getPlace());
        }
        return Result.ok().data("pageData",pageData);
    }

    @PostMapping("cancel/{appointmentId}")
    public Result cancel(@PathVariable("appointmentId") Long appointmentId){
        UserAppointment appointment = userAppointmentService.getById(appointmentId);
        appointment.setStatus(1);
        PlaceVdstate vdstate = placeVdstateService.getOne(new LambdaQueryWrapper<PlaceVdstate>().eq(PlaceVdstate::getPlaceid, appointment.getPlaceid()).eq(PlaceVdstate::getVdstatedate, appointment.getOrderdate()));
        char[] cs = vdstate.getVdstatest().toCharArray();
        cs[appointment.getOrderstart() - 8] = '0';
        vdstate.setVdstatest(new String(cs));
        boolean flag = userAppointmentService.updateById(appointment) && placeVdstateService.updateById(vdstate);
        return flag ? Result.ok() : Result.error().message("取消预约失败");
    }
}

