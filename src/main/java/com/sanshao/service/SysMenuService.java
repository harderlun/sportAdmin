package com.sanshao.service;

import com.sanshao.common.vo.SysMenuVo;
import com.sanshao.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuVo> getCurrentUserNav();

    List<SysMenu> tree();

}
