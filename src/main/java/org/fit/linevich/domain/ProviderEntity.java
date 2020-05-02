package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Collection;

@Data
@Entity
@Table(name = "providers", schema = "public", catalog = "bd_zoo")
public class ProviderEntity {
    @Id
    @GeneratedValue(generator = "provider_gen")
    @SequenceGenerator(name = "provider_gen", sequenceName = "provider_prov_id_seq", allocationSize = 1)
    @Column(name = "prov_id")
    private Integer provId;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "date_begin", nullable = false)
    private Date dateBegin;
    @Basic
    @Column(name = "date_end", nullable = true)
    private Date dateEnd;
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "providerId")
    private Collection<ProvidersSpecializationEntity> providersSpecializationsByProvId;
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "providerId")
    private Collection<SupplyEntity> suppliesByProvId;

    public Integer getProvId() {
        return provId;
    }
}
