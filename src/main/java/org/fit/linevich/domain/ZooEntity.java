package org.fit.linevich.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "zoos", schema = "public", catalog = "bd_zoo")
public class ZooEntity {
    private Integer id;
    private String name;
    private Collection<AnimalReceiptEntity> animalReceiptsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZooEntity that = (ZooEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "zoosByZooId")
    public Collection<AnimalReceiptEntity> getAnimalReceiptsById() {
        return animalReceiptsById;
    }

    public void setAnimalReceiptsById(Collection<AnimalReceiptEntity> animalReceiptsById) {
        this.animalReceiptsById = animalReceiptsById;
    }
}
