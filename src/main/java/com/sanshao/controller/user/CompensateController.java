package com.sanshao.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.SysCompensate;
import com.sanshao.service.SysCompensateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 器材赔偿
 */
@RestController
@RequestMapping("/compensate")
public class CompensateController {

    @Autowired
    private SysCompensateService sysCompensateService;

    @GetMapping("getCompensateNum/{userId}")
    public Result getCompensateNum(@PathVariable("userId") Long userId){
        List<SysCompensate> compensateList = sysCompensateService.list(new LambdaQueryWrapper<SysCompensate>().eq(SysCompensate::getStatus, 0).eq(SysCompensate::getUserid, userId));
        int num = compensateList.size();
        return Result.ok().data("compensateNum",num).data("compensateList",compensateList);
    }

    @PostMapping("pay/{id}")
    public Result pay(@PathVariable("id") Long id){
        SysCompensate compensate = sysCompensateService.getById(id);
        compensate.setStatus(1);
        boolean flag = sysCompensateService.updateById(compensate);
        return flag ? Result.ok() : Result.error().message("支付失败");
    }
}
