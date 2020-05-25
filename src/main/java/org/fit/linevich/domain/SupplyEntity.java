package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supply")
public class SupplyEntity {
    @Id
    @GeneratedValue(generator = "supply_gen")
    @SequenceGenerator(name = "supply_gen", sequenceName = "supply_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Basic
    @Column(name = "feed_amount", nullable = false)
    private Integer feedAmount;
    @Basic
    @Column(name = "date_supply", nullable = false)
    private Date dateSupply;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", referencedColumnName = "prov_id", nullable = false, insertable = false, updatable = false)
    private ProviderEntity providerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private FeedEntity feedId;
}
