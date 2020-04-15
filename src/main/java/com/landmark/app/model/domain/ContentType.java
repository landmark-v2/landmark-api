package com.landmark.app.model.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CONTENT_TYPE")
public class ContentType {

    @Id
    @Column(name = "content_type_id")
    private int contentTypeId;

    private String name;

}
