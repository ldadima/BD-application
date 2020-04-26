package org.fit.linevich.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "providers", schema = "public", catalog = "bd_zoo")
public class ProviderEntity {
    private Integer provId;
    private String name;
    private Date dateBegin;
    private Date dateEnd;
    private Collection<ProvidersSpecializationEntity> providersSpecializationsByProvId;
    private Collection<SupplyEntity> suppliesByProvId;

    @Id
    @Column(name = "prov_id", nullable = false)
    public int getProvId() {
        return provId;
    }

    public void setProvId(int provId) {
        this.provId = provId;
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
    @Column(name = "date_begin", nullable = false)
    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    @Basic
    @Column(name = "date_end", nullable = true)
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderEntity that = (ProviderEntity) o;
        return provId == that.provId &&
                Objects.equals(name, that.name) &&
                Objects.equals(dateBegin, that.dateBegin) &&
                Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(provId, name, dateBegin, dateEnd);
    }

    @OneToMany(mappedBy = "providersByProviderId")
    public Collection<ProvidersSpecializationEntity> getProvidersSpecializationsByProvId() {
        return providersSpecializationsByProvId;
    }

    public void setProvidersSpecializationsByProvId(
            Collection<ProvidersSpecializationEntity> providersSpecializationsByProvId) {
        this.providersSpecializationsByProvId = providersSpecializationsByProvId;
    }

    @OneToMany(mappedBy = "providersByProviderId")
    public Collection<SupplyEntity> getSuppliesByProvId() {
        return suppliesByProvId;
    }

    public void setSuppliesByProvId(Collection<SupplyEntity> suppliesByProvId) {
        this.suppliesByProvId = suppliesByProvId;
    }
}
