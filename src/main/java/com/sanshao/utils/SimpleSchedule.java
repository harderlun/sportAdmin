package com.sanshao.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanshao.entity.PlaceVdstate;
import com.sanshao.entity.SysPlace;
import com.sanshao.service.PlaceVdstateService;
import com.sanshao.service.SysPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SimpleSchedule {

    @Autowired
    private PlaceVdstateService placeVdstateService;
    @Autowired
    private SysPlaceService sysPlaceService;

//    @Scheduled(cron = "0 17 17 * * ?")
    @Scheduled(cron = "0,30 * * * * ?")
    private void process(){
        System.out.println("执行了定时器");
        List<SysPlace> placeList = sysPlaceService.list(new LambdaQueryWrapper<SysPlace>().eq(SysPlace::getStatus, 0));
        for (int i = 0; i < placeList.size(); i++) {
            SysPlace place = placeList.get(i);
            List<PlaceVdstate> placeVdstateList = placeVdstateService.list(new LambdaQueryWrapper<PlaceVdstate>().eq(PlaceVdstate::getPlaceid, place.getId()).ge(PlaceVdstate::getVdstatedate, new Date()));
            for (int j = 0; j < 3 - placeVdstateList.size(); j++) {
                Date now = new Date();
                Date date = new Date(now.getTime() + (1000 * 60 * 60 * 24) * (3 - j));
                PlaceVdstate placeVdstate = new PlaceVdstate();
                placeVdstate.setPlaceid(place.getId());
                placeVdstate.setVdstatedate(date);
                placeVdstateService.save(placeVdstate);
            }
        }
    }
}
