package com.sanshao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanshao.entity.SysMenu;
import com.sanshao.entity.SysRole;
import com.sanshao.entity.SysUser;
import com.sanshao.mapper.SysUserMapper;
import com.sanshao.service.SysMenuService;
import com.sanshao.service.SysRoleService;
import com.sanshao.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanshao.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        String authority = "";
        if (redisUtil.hasKey("GrantedAuthority:" + userId)){
            authority = (String) redisUtil.get("GrantedAuthority:" + userId);
            return authority;
        }
        List<SysRole> roles = sysRoleService.list(new LambdaQueryWrapper<SysRole>()
                .inSql(SysRole::getId, "select role_id from sys_user_role where user_id = " + userId));
        if (roles.size() > 0){
            authority = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(",")).concat(",");
        }
        List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
        if (menuIds.size() > 0){
            List<SysMenu> menus = sysMenuService.listByIds(menuIds);
            String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
            authority = authority.concat(menuPerms);
        }
        redisUtil.set("GrantedAuthority:" + userId,authority,60 * 60);
        return authority;
    }

    //清除redis中的缓存的权限
    @Override
    public void clearUserAuthorityInfo(Long userId) {
        redisUtil.del("GrantedAuthority:" + userId);
    }

    //根据角色id清除redis中的角色缓存
    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        List<SysUser> userList = this.list(new LambdaQueryWrapper<SysUser>()
                .inSql(SysUser::getId, "select user_id from sys_user_role where role_id = " + roleId));
        userList.forEach(u -> {
            this.clearUserAuthorityInfo(u.getId());
        });
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<SysUser> userList = sysUserMapper.listByMenuId(menuId);
        System.out.println("userList" + userList.size());
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i) == null) userList.remove(i);
        }
        userList.forEach(u -> {
            this.clearUserAuthorityInfo(u.getId());
        });
    }

    @Override
    public List<Long> getNavMenuIds(Long userId) {
        return baseMapper.getNavMenuIds(userId);
    }
}
