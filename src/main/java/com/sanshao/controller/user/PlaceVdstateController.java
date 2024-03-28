package com.sanshao.controller.user;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.PlaceDateState;
import com.sanshao.entity.PlaceVdstate;
import com.sanshao.entity.SysPlace;
import com.sanshao.service.PlaceVdstateService;
import com.sanshao.service.SysPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 场地时间段状态
 */
@RestController
@RequestMapping("/place/vdstate")
public class PlaceVdstateController {

    @Autowired
    private PlaceVdstateService placeVdstateService;
    @Autowired
    private SysPlaceService sysPlaceService;

    @GetMapping("getPlaceState")
    public Result getPlaceState(){
        List<PlaceVdstate> vdstateList = placeVdstateService.list(new LambdaQueryWrapper<PlaceVdstate>().ge(PlaceVdstate::getVdstatedate, new Date()).orderByAsc(PlaceVdstate::getVdstatedate));
        List<PlaceDateState> placeDateStateList = new ArrayList<>();
        PlaceDateState placeDateState = new PlaceDateState();
        long pre = -1;
        long idx = 0;
        for (int i = 0; i < vdstateList.size(); i++) {
            PlaceVdstate placeVdstate = vdstateList.get(i);
            SysPlace place = sysPlaceService.getById(placeVdstate.getPlaceid());
            placeVdstate.setPlaceName(place.getPlace());
            if (i == 0){
                placeDateState.setId(idx);
                placeDateState.setDate(placeVdstate.getVdstatedate());
            }else{
                if (placeVdstate.getVdstatedate().getTime() != pre){
                    placeDateStateList.add(placeDateState);
                    placeDateState = new PlaceDateState();
                    idx++;
                    placeDateState.setId(idx);
                    placeDateState.setDate(placeVdstate.getVdstatedate());
                }
            }
            placeDateState.getVdstateList().add(placeVdstate);
            pre = placeVdstate.getVdstatedate().getTime();
        }
        placeDateStateList.add(placeDateState);
        return Result.ok().data("placeDateStateList",placeDateStateList);
    }

}

