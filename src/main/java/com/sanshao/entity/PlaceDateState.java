package com.sanshao.entity;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<PlaceVdstate> getVdstateList() {
        return vdstateList;
    }

    public void setVdstateList(List<PlaceVdstate> vdstateList) {
        this.vdstateList = vdstateList;
    }
}
