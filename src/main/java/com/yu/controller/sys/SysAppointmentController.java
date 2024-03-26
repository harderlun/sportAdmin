package com.yu.controller.sys;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.common.lang.Result;
import com.yu.entity.SysUser;
import com.yu.entity.UserAppointment;
import com.yu.service.SysPlaceService;
import com.yu.service.SysUserService;
import com.yu.service.UserAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 场地预约
 */
@RestController
@RequestMapping("/sys/appointment")
public class SysAppointmentController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserAppointmentService userAppointmentService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPlaceService sysPlaceService;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:appointment:list')")
    public Result list(String username){
        Page<UserAppointment> pageData = null;
        if (StrUtil.isNotBlank(username)){
            List<SysUser> userList = sysUserService.list(new LambdaQueryWrapper<SysUser>().like(SysUser::getUsername, username));
            for (int i = 0; i < userList.size(); i++) {
                SysUser user = userList.get(i);
                if (i == 0){
                    pageData = userAppointmentService.page(getPage(),new LambdaQueryWrapper<UserAppointment>().eq(UserAppointment::getUserid,user.getId()));
                }else{
                    List<UserAppointment> appointmentList = userAppointmentService.list(new LambdaQueryWrapper<UserAppointment>().eq(UserAppointment::getUserid, user.getId()));
                    pageData.getRecords().addAll(appointmentList);
                    pageData.setTotal(pageData.getTotal() + appointmentList.size());
                }
            }
        }else{
            pageData = userAppointmentService.page(getPage());
        }
        for (int i = 0; i < pageData.getRecords().size(); i++) {
            UserAppointment userAppointment = pageData.getRecords().get(i);
            userAppointment.setUsername(sysUserService.getById(userAppointment.getUserid()).getUsername());
            userAppointment.setPlaceName(sysPlaceService.getById(userAppointment.getPlaceid()).getPlace());
        }
        return Result.ok().data("pageData",pageData);
    }
}
