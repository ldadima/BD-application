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
@Table(name = "feeds", schema = "public", catalog = "bd_zoo")
public class FeedEntity {
    private Integer id;
    private String name;
    private String type;
    private int stock;
    private int volumeIndependentProduction;
    private Collection<EvenDayRationEntity> evenDayRationsById;
    private Collection<OddDayRationEntity> oddDayRationsById;
    private Collection<ProvidersSpecializationEntity> providersSpecializationsById;
    private Collection<SupplyEntity> suppliesById;

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

    @Basic
    @Column(name = "type", nullable = false, length = -1)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "stock", nullable = false)
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "volume_independent_production", nullable = false)
    public int getVolumeIndependentProduction() {
        return volumeIndependentProduction;
    }

    public void setVolumeIndependentProduction(int volumeIndependentProduction) {
        this.volumeIndependentProduction = volumeIndependentProduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedEntity that = (FeedEntity) o;
        return id == that.id &&
                stock == that.stock &&
                volumeIndependentProduction == that.volumeIndependentProduction &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, stock, volumeIndependentProduction);
    }

    @OneToMany(mappedBy = "feedsByFeedId")
    public Collection<EvenDayRationEntity> getEvenDayRationsById() {
        return evenDayRationsById;
    }

    public void setEvenDayRationsById(Collection<EvenDayRationEntity> evenDayRationsById) {
        this.evenDayRationsById = evenDayRationsById;
    }

    @OneToMany(mappedBy = "feedsByFeedId")
    public Collection<OddDayRationEntity> getOddDayRationsById() {
        return oddDayRationsById;
    }

    public void setOddDayRationsById(Collection<OddDayRationEntity> oddDayRationsById) {
        this.oddDayRationsById = oddDayRationsById;
    }

    @OneToMany(mappedBy = "feedsByFeedId")
    public Collection<ProvidersSpecializationEntity> getProvidersSpecializationsById() {
        return providersSpecializationsById;
    }

    public void setProvidersSpecializationsById(
            Collection<ProvidersSpecializationEntity> providersSpecializationsById) {
        this.providersSpecializationsById = providersSpecializationsById;
    }

    @OneToMany(mappedBy = "feedsByFeedId")
    public Collection<SupplyEntity> getSuppliesById() {
        return suppliesById;
    }

    public void setSuppliesById(Collection<SupplyEntity> suppliesById) {
        this.suppliesById = suppliesById;
    }
}
