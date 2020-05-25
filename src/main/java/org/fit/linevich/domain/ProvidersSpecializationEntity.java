package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "providers_specialization")
public class ProvidersSpecializationEntity {
    @EmbeddedId
    private ProvidersSpecializationEntityPK providersSpecializationEntityPK;

    @MapsId("feedId")
    @ManyToOne
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private FeedEntity feedId;
    @MapsId("providerId")
    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "prov_id", nullable = false, insertable = false, updatable = false)
    private ProviderEntity providerId;
}
