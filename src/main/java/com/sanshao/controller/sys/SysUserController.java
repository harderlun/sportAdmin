package com.sanshao.controller.sys;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanshao.common.lang.Const;
import com.sanshao.common.lang.Result;
import com.sanshao.common.vo.PassVo;
import com.sanshao.entity.SysRole;
import com.sanshao.entity.SysUser;
import com.sanshao.entity.SysUserRole;
import com.sanshao.service.SysRoleService;
import com.sanshao.service.SysUserRoleService;
import com.sanshao.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result list(String username){
        Page<SysUser> pageData = sysUserService.page(getPage(),new LambdaQueryWrapper<SysUser>().like(StrUtil.isNotBlank(username),SysUser::getUsername,username));
        pageData.getRecords().forEach(u -> {
            u.setSysRoles(sysRoleService.listRolesByUserId(u.getId()));
        });
        return Result.ok().data("pageData",pageData);
    }

    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result info(@PathVariable("id") Long id){
        SysUser user = sysUserService.getById(id);
        Assert.notNull(user,"找不到该管理员");
        List<SysRole> roleList = sysRoleService.listRolesByUserId(id);
        user.setSysRoles(roleList);
        return Result.ok().data("info",user);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result save(@Validated @RequestBody SysUser sysUser){
        sysUser.setCreated(new Date());
        String password = passwordEncoder.encode(Const.DEFAULT_PASSWORD);
        sysUser.setPassword(password);
        sysUser.setAvatar(Const.DEFAULT_AVATAR);
        boolean flag = sysUserService.save(sysUser);
        return flag ? Result.ok() : Result.error();
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result update(@Validated @RequestBody SysUser sysUser){
        sysUser.setUpdated(new Date());
        boolean flag = sysUserService.updateById(sysUser);
        return flag ? Result.ok() : Result.error();
    }

    @Transactional
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result delete(@RequestBody Long[] ids){
        sysUserService.removeByIds(Arrays.asList(ids));
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId,ids));
        return Result.ok();
    }

    @Transactional
    @PostMapping("role/{userId}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public Result rolePerm(@PathVariable("userId") Long userId,@RequestBody Long[] roleIds){
        List<SysUserRole> userRoles = new ArrayList<>();
        Arrays.stream(roleIds).forEach(r -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(r);
            sysUserRole.setUserId(userId);
            userRoles.add(sysUserRole);
        });
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,userId));
        sysUserRoleService.saveBatch(userRoles);
        sysUserService.clearUserAuthorityInfo(userId);
        return Result.ok();
    }

    @PostMapping("repass")
    @PreAuthorize("hasAuthority('sys:user:repass')")
    public Result repass(@RequestBody Long userId){
        SysUser user = sysUserService.getById(userId);
        user.setPassword(passwordEncoder.encode(Const.DEFAULT_PASSWORD));
        user.setUpdated(new Date());
        sysUserService.updateById(user);
        return Result.ok();
    }

    @PostMapping("updatePass")
    public Result updatePass(@Validated @RequestBody PassVo passVo, Principal principal){
        SysUser user = sysUserService.getByUsername(principal.getName());
        boolean matches = passwordEncoder.matches(passVo.getCurrentPass(), user.getPassword());
        if (!matches){
            return Result.error().message("旧密码不正确");
        }
        user.setPassword(passwordEncoder.encode(passVo.getCurrentPass()));
        user.setUpdated(new Date());
        sysUserService.updateById(user);
        return Result.ok();
    }
}

