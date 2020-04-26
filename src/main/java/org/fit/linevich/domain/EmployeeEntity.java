package org.fit.linevich.domain;

import org.fit.linevich.model.EmployeeCategory;
import org.fit.linevich.model.Gender;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "employees", schema = "public", catalog = "bd_zoo")
public class EmployeeEntity {
    private Integer id;
    private String surname;
    private String name;
    private String patronymic;
    private EmployeeCategory category;
    private int durationWork;
    private int age;
    private Gender gender;
    private BigDecimal salary;
    private Collection<AccessAnimalsEntity> accessAnimalsById;
    private Collection<ResponsibleAnimalsEntity> responsibleAnimalsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = -1)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "patronymic", nullable = false, length = -1)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Basic
    @Column(name = "category", nullable = false)
    public String getCategory() {
        return category.getCategory();
    }

    public void setCategory(String category) {
        this.category = EmployeeCategory.findByName(category);
    }

    @Basic
    @Column(name = "duration_work", nullable = false)
    public int getDurationWork() {
        return durationWork;
    }

    public void setDurationWork(int durationWork) {
        this.durationWork = durationWork;
    }

    @Basic
    @Column(name = "age", nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Basic
    @Column(name = "gender", nullable = false)
    public String getGender() {
        return gender.getGender();
    }

    public void setGender(String gender) {
        this.gender = Gender.findByName(gender);
    }

    @Basic
    @Column(name = "salary", nullable = false, precision = 0)
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return id == that.id &&
                durationWork == that.durationWork &&
                age == that.age &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(name, that.name) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(category, that.category) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, patronymic, category, durationWork, age, gender, salary);
    }

    @OneToMany(mappedBy = "employeesByEmployerId")
    public Collection<AccessAnimalsEntity> getAccessAnimalsById() {
        return accessAnimalsById;
    }

    public void setAccessAnimalsById(Collection<AccessAnimalsEntity> accessAnimalsById) {
        this.accessAnimalsById = accessAnimalsById;
    }

    @OneToMany(mappedBy = "employeesByEmployerId")
    public Collection<ResponsibleAnimalsEntity> getResponsibleAnimalsById() {
        return responsibleAnimalsById;
    }

    public void setResponsibleAnimalsById(
            Collection<ResponsibleAnimalsEntity> responsibleAnimalsById) {
        this.responsibleAnimalsById = responsibleAnimalsById;
    }
}
