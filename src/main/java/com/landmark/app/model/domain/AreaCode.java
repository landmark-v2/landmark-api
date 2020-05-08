package com.landmark.app.model.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "AREA_CODE")
public class AreaCode {

    @Id
    private int code;

    private String name;

    @Data
    public static class AreaCodeCount {

        @Id
        @Column(name = "area_code")
        private int areaCode;

        private int cnt;
    }

}
