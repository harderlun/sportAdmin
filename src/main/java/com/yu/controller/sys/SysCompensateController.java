package com.yu.controller.sys;
import com.yu.common.lang.Result;
import com.yu.entity.SysCompensate;
import com.yu.entity.UserBorrow;
import com.yu.server.WebSocketServer;
import com.yu.service.SysCompensateService;
import com.yu.service.UserBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

/**
 * 器材赔偿
 */
@RestController
@RequestMapping("/sys/compensate")
public class SysCompensateController {

    @Autowired
    private SysCompensateService sysCompensateService;
    @Autowired
    private UserBorrowService userBorrowService;
    @Autowired
    private WebSocketServer webSocketServer;

    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:borrow:approve')")
    public Result save(@RequestBody SysCompensate sysCompensate) throws IOException {
        long id = sysCompensateService.count();
        sysCompensate.setId(id + 1);
        sysCompensate.setCreated(new Date());
        sysCompensate.setStatus(0);
        UserBorrow borrow = userBorrowService.getById(sysCompensate.getBorrowid());
        borrow.setStatus(4);
        webSocketServer.sendInfo("您需要支付一笔器材赔偿，请尽快缴纳，否则无法租用器材", borrow.getUserid());
        boolean update = userBorrowService.updateById(borrow);
        boolean flag = sysCompensateService.save(sysCompensate);
        return flag && update ? Result.ok() : Result.error();
    }
}

