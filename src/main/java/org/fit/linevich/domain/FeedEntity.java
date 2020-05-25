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
@Table(name = "feeds")
public class FeedEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "feed_gen")
    @SequenceGenerator(name = "feed_gen", sequenceName = "feeds_id_seq", allocationSize = 1)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "type", nullable = false, length = -1)
    private String type;
    @Basic
    @Column(name = "stock", nullable = false)
    private Integer stock;
    @Basic
    @Column(name = "volume_independent_production", nullable = false)
    private Integer volumeIndependentProduction;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "feedId")
    private Collection<EvenDayRationEntity> evenDayRationsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "feedId")
    private Collection<OddDayRationEntity> oddDayRationsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "feedId")
    private Collection<ProvidersSpecializationEntity> providersSpecializationsById;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "feedId")
    private Collection<SupplyEntity> suppliesById;
}
