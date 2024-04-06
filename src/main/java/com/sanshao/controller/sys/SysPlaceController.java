package com.sanshao.controller.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.SysPlace;
import com.sanshao.service.SysPlaceService;
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
 * 场地信息管理 Controller
 */
@RestController
@RequestMapping("/sys/place")
public class SysPlaceController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysPlaceService sysPlaceService;

    /**
     * 获取分页场地信息列表
     *
     * @param placeName 场地名称，模糊查询条件
     * @return Result 包含分页数据的结果
     */
    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:place:list')")
    public Result list(String placeName) {
        // 使用 ServletRequestUtils 从请求中获取分页参数
        Page<SysPlace> pageData = sysPlaceService.page(
                getPage(),
                new LambdaQueryWrapper<SysPlace>().like(StrUtil.isNotBlank(placeName), SysPlace::getPlace, placeName)
        );
        return Result.ok().data("pageData", pageData);
    }

    /**
     * 保存场地信息
     *
     * @param sysPlace 待保存的场地信息
     * @return Result 表示操作结果的返回值
     */
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:place:save')")
    public Result save(@Validated @RequestBody SysPlace sysPlace) {
        // 设置创建时间
        sysPlace.setCreated(new Date());
        boolean flag = sysPlaceService.save(sysPlace);
        return flag ? Result.ok() : Result.error();
    }

    /**
     * 获取指定场地信息详情
     *
     * @param id 场地信息的唯一标识符
     * @return Result 包含场地信息详情的结果
     */
    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('sys:place:list')")
    public Result info(@PathVariable("id") Long id) {
        SysPlace place = sysPlaceService.getById(id);
        return Result.ok().data("info", place);
    }

    /**
     * 更新场地信息
     *
     * @param sysPlace 待更新的场地信息
     * @return Result 表示操作结果的返回值
     */
    @PostMapping("update")
    @PreAuthorize("hasAuthority('sys:place:update')")
    public Result update(@Validated @RequestBody SysPlace sysPlace) {
        // 设置更新时间
        sysPlace.setUpdated(new Date());
        boolean flag = sysPlaceService.updateById(sysPlace);
        return flag ? Result.ok() : Result.error();
    }

    /**
     * 删除场地信息
     *
     * @param placeIds 待删除的场地信息的标识符数组
     * @return Result 表示操作结果的返回值
     */
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('sys:place:delete')")
    @Transactional
    public Result delete(@RequestBody Long[] placeIds) {
        boolean flag = sysPlaceService.removeByIds(Arrays.asList(placeIds));
        return flag ? Result.ok() : Result.error();
    }

    /**
     * 获取分页对象，从请求中获取当前页码和每页数量
     *
     * @return Page 分页对象
     */
    private Page getPage() {
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 10);
        return new Page(current, size);
    }
}
