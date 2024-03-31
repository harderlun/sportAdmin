package com.sanshao.controller.sys;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.Equipment;
import com.sanshao.service.SysEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * 器材信息以及操作
 */
@RestController
@RequestMapping("/sys/equipment")
public class SysEquipmentController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysEquipmentService sysEquipmentService;

    private Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        int size = ServletRequestUtils.getIntParameter(request,"size",10);
        return new Page(current,size);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:equipment:list')")
    public Result list(String name){
        Page<Equipment> pageData = sysEquipmentService.page(getPage(),new LambdaQueryWrapper<Equipment>().like(StrUtil.isNotBlank(name), Equipment::getName,name));
        return Result.ok().data("pageData",pageData);
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('sys:equipment:add')")
    public Result add(@Validated @RequestBody Equipment equipment){
        equipment.setCreated(new Date());
        equipment.setSurplus(equipment.getTotal());
        boolean flag = sysEquipmentService.save(equipment);
        return flag ? Result.ok() : Result.error();
    }



    @PostMapping("update")
    @PreAuthorize("hasAuthority('sys:equipment:update')")
    public Result update(@Validated @RequestBody Equipment equipment){
        boolean flag = sysEquipmentService.updateById(equipment);
        return flag ? Result.ok() : Result.error();
    }

    @PostMapping("delete")
    @PreAuthorize("hasAuthority('sys:equipment:delete')")
    @Transactional
    public Result delete(@RequestBody Long[] equipmentIds){
        boolean flag = sysEquipmentService.removeByIds(Arrays.asList(equipmentIds));
        return flag ? Result.ok() : Result.error();
    }



    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:equipment:list')")
    public Result info(@PathVariable("id") Long id){
        Equipment equipment = sysEquipmentService.getById(id);
        return Result.ok().data("info",equipment);
    }



}

