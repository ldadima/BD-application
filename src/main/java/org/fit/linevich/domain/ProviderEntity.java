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
import java.sql.Date;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "providers")
public class ProviderEntity {
    @Id
    @GeneratedValue(generator = "provider_gen")
    @SequenceGenerator(name = "provider_gen", sequenceName = "providers_prov_id_seq", allocationSize = 1)
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
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "providerId")
    private Collection<ProvidersSpecializationEntity> providersSpecializationsByProvId;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "providerId")
    private Collection<SupplyEntity> suppliesByProvId;
}
