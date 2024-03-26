package com.yu.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PlaceDateState implements Serializable {

    private Long id;
    private Date date;
    private List<PlaceVdstate> vdstateList = new ArrayList<>();
}
