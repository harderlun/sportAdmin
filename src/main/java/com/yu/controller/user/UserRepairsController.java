package com.yu.controller.user;
import com.yu.common.lang.Result;
import com.yu.entity.UserRepairs;
import com.yu.service.UserRepairsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 器材修理
 */
@RestController
@RequestMapping("/user/repairs")
public class UserRepairsController {

    @Autowired
    private UserRepairsService userRepairsService;

    @PostMapping("save")
    public Result save(@RequestBody UserRepairs userRepairs){
        userRepairs.setCreated(new Date());
        boolean flag = userRepairsService.save(userRepairs);
        return flag ? Result.ok() : Result.error();
    }
}

