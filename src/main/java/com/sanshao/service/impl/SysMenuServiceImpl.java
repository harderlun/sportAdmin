package com.sanshao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanshao.common.vo.SysMenuVo;
import com.sanshao.entity.SysMenu;
import com.sanshao.entity.SysUser;
import com.sanshao.mapper.SysMenuMapper;
import com.sanshao.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanshao.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<SysMenuVo> getCurrentUserNav() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserService.getByUsername(username);
        System.out.println(user.getId());
        List<Long> menuIds = sysUserService.getNavMenuIds(user.getId());
        List<SysMenu> menus = this.listByIds(menuIds);
        List<SysMenu> menuTree = buildTreeMenu(menus);
        return convert(menuTree);
    }

    @Override
    public List<SysMenu> tree() {
        List<SysMenu> sysMenus = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getOrderNum));
        return buildTreeMenu(sysMenus);
    }

    private List<SysMenuVo> convert(List<SysMenu> menuTree) {
        List<SysMenuVo> menuVos = new ArrayList<>();
        menuTree.forEach(m -> {
            SysMenuVo vo = new SysMenuVo();
            vo.setId(m.getId());
            vo.setName(m.getPerms());
            vo.setTitle(m.getName());
            vo.setComponent(m.getComponent());
            vo.setPath(m.getPath());
            vo.setIcon(m.getIcon());
            if (m.getChildren().size() > 0){
                vo.setChildren(convert(m.getChildren()));
            }
            menuVos.add(vo);
        });
        return menuVos;
    }

    private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {
        List<SysMenu> finalMenus = new ArrayList<>();
        for (SysMenu menu : menus) {
            for (SysMenu e : menus) {
                if (menu.getId() == e.getParentId()){
                    menu.getChildren().add(e);
                }
            }
            if (menu.getParentId() == 0L) finalMenus.add(menu);
        }
        return finalMenus;
    }
}
