package com.yu.service;

import com.yu.common.vo.SysMenuVo;
import com.yu.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuVo> getCurrentUserNav();

    List<SysMenu> tree();

}
