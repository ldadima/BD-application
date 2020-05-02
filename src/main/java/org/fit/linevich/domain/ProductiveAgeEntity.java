package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "productive_age", schema = "public", catalog = "bd_zoo")
public class ProductiveAgeEntity {
    @Id
    @Column(name = "kind", nullable = false, length = -1)
    private String kind;
    @Basic
    @Column(name = "age", nullable = false)
    private int age;
}
