package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productive_age")
public class ProductiveAgeEntity {
    @Id
    @Column(name = "kind", nullable = false, length = -1)
    private String kind;
    @Basic
    @Column(name = "age", nullable = false)
    private Integer age;
}
