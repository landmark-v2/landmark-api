package com.landmark.app.model.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CAT2")
public class Cat2 {

    @Id
    private String code;

    private String name;

}
