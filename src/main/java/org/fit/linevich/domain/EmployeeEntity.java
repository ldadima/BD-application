package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fit.linevich.converters_for_db.EmployeeCategoryConverter;
import org.fit.linevich.converters_for_db.GenderConverter;
import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.model.Gender;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(generator = "employees_gen")
    @SequenceGenerator(name = "employees_gen", sequenceName = "employees_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "surname", nullable = false, length = -1)
    private String surname;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "patronymic", nullable = false, length = -1)
    private String patronymic;
    @Basic
    @Convert(converter = EmployeeCategoryConverter.class)
    @Column(name = "category", nullable = false)
    private EmployeeCategory category;
    @Basic
    @Column(name = "duration_work", nullable = false)
    private Integer durationWork;
    @Basic
    @Column(name = "age", nullable = false)
    private Integer age;
    @Basic
    @Column(name = "gender", nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender;
    @Basic
    @Column(name = "salary", nullable = false, precision = 0)
    private BigDecimal salary;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "employerId")
    private Collection<AccessAnimalsEntity> accessAnimalsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "employerId")
    private Collection<ResponsibleAnimalsEntity> responsibleAnimalsById;
}
