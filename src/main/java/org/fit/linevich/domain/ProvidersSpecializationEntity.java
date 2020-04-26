package org.fit.linevich.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "providers_specialization", schema = "public", catalog = "bd_zoo")
@IdClass(ProvidersSpecializationEntityPK.class)
public class ProvidersSpecializationEntity {
    private int feedId;
    private int providerId;
    private FeedEntity feedsByFeedId;
    private ProviderEntity providersByProviderId;

    @Id
    @Column(name = "feed_id", nullable = false)
    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    @Id
    @Column(name = "provider_id", nullable = false)
    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvidersSpecializationEntity that = (ProvidersSpecializationEntity) o;
        return feedId == that.feedId &&
                providerId == that.providerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedId, providerId);
    }

    @ManyToOne
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public FeedEntity getFeedsByFeedId() {
        return feedsByFeedId;
    }

    public void setFeedsByFeedId(FeedEntity feedsByFeedId) {
        this.feedsByFeedId = feedsByFeedId;
    }

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "prov_id", nullable = false, insertable = false, updatable = false)
    public ProviderEntity getProvidersByProviderId() {
        return providersByProviderId;
    }

    public void setProvidersByProviderId(ProviderEntity providersByProviderId) {
        this.providersByProviderId = providersByProviderId;
    }
}
