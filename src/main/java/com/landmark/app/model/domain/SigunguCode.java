package com.landmark.app.model.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SIGUNGU_CODE")
@IdClass(SigunguCodePK.class)
public class SigunguCode {

    @Id
    private int code;

    private String name;

    @Id
    @Column(name = "area_code")
    private int areaCode;

}
