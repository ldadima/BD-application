package org.fit.linevich.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ProvidersSpecializationEntityPK implements Serializable {
    private int feedId;
    private int providerId;

    @Column(name = "feed_id", nullable = false)
    @Id
    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    @Column(name = "provider_id", nullable = false)
    @Id
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
        ProvidersSpecializationEntityPK that = (ProvidersSpecializationEntityPK) o;
        return feedId == that.feedId &&
                providerId == that.providerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedId, providerId);
    }
}
