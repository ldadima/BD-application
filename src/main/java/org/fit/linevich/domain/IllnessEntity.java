package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "illnesses")
public class IllnessEntity {
    @Id
    @GeneratedValue(generator = "illness_gen")
    @SequenceGenerator(name = "illness_gen", sequenceName = "illnesses_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "illnessId")
    private Collection<IllnessAnimalsEntity> illnessAnimalsById;
}
