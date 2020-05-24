package com.landmark.app.model.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class AreaCodeCount {

    @Id
    @Column(name = "area_code")
    private int areaCode;

    private String name;

    private int level;

}
