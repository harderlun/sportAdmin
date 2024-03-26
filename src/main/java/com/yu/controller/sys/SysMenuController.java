package com.yu.controller.sys;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yu.common.lang.Result;
import com.yu.common.vo.SysMenuVo;
import com.yu.entity.SysMenu;
import com.yu.entity.SysRoleMenu;
import com.yu.entity.SysUser;
import com.yu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * 器材租用
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 获取当前用户的菜单和权限信息
     * @param principal
     * @return
     */
    @GetMapping("nav")
    public Result nav(Principal principal){
        SysUser user = sysUserService.getByUsername(principal.getName());
        //获取权限信息
        String authorityInfo = sysUserService.getUserAuthorityInfo(user.getId());
        String[] authorities = StringUtils.tokenizeToStringArray(authorityInfo, ",");
        //获取导航栏信息
        List<SysMenuVo> nav = sysMenuService.getCurrentUserNav();
        return Result.ok().data("authorities",authorities).data("nav",nav);
    }

    /**
     * 查询菜单列表
     * @return
     */
    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result list(){
        List<SysMenu> menus = sysMenuService.tree();
        return Result.ok().data("menuList",menus);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result update(@Validated @RequestBody SysMenu sysMenu){
        sysMenu.setUpdated(new Date());
        boolean flag = sysMenuService.updateById(sysMenu);
        //清楚所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId(sysMenu.getId());
        return flag ? Result.ok() : Result.error();
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result save(@Validated @RequestBody SysMenu sysMenu){
        sysMenu.setCreated(new Date());
        boolean flag = sysMenuService.save(sysMenu);
        return flag ? Result.ok() : Result.error();
    }

    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result info(@PathVariable(name = "id") Long id){
        return Result.ok().data("menuInfo",sysMenuService.getById(id));
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result delete(@PathVariable("id") Long id){
        int count = sysMenuService.count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
        if (count > 0){
            return Result.error().message("请删除子菜单");
        }
        //清楚所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId(id);
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getMenuId,id));
        boolean flag = sysMenuService.removeById(id);
        return flag ? Result.ok() : Result.error();
    }
}

