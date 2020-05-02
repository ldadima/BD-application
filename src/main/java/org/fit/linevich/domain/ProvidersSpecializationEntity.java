package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "providers_specialization", schema = "public", catalog = "bd_zoo")
@IdClass(ProvidersSpecializationEntityPK.class)
public class ProvidersSpecializationEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private FeedEntity feedId;
    @Id
    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "prov_id", nullable = false, insertable = false, updatable = false)
    private ProviderEntity providerId;
}
