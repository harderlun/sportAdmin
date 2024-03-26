package com.yu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yu.entity.SysRole;
import com.yu.mapper.SysRoleMapper;
import com.yu.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> listRolesByUserId(Long id) {
        return this.list(new LambdaQueryWrapper<SysRole>()
                .inSql(SysRole::getId,"select role_id from sys_user_role where user_id = " + id));
    }
}
