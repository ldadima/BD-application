package org.fit.linevich.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "supply", schema = "public", catalog = "bd_zoo")
public class SupplyEntity {
    private Integer id;
    private int providerId;
    private int feedId;
    private BigDecimal price;
    private int feedAmount;
    private Date dateSupply;
    private ProviderEntity providersByProviderId;
    private FeedEntity feedsByFeedId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "provider_id", nullable = false)
    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    @Basic
    @Column(name = "feed_id", nullable = false)
    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "feed_amount", nullable = false)
    public int getFeedAmount() {
        return feedAmount;
    }

    public void setFeedAmount(int feedAmount) {
        this.feedAmount = feedAmount;
    }

    @Basic
    @Column(name = "date_supply", nullable = false)
    public Date getDateSupply() {
        return dateSupply;
    }

    public void setDateSupply(Date dateSupply) {
        this.dateSupply = dateSupply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplyEntity that = (SupplyEntity) o;
        return id == that.id &&
                providerId == that.providerId &&
                feedId == that.feedId &&
                feedAmount == that.feedAmount &&
                Objects.equals(price, that.price) &&
                Objects.equals(dateSupply, that.dateSupply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, providerId, feedId, price, feedAmount, dateSupply);
    }

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "prov_id", nullable = false, insertable = false, updatable = false)
    public ProviderEntity getProvidersByProviderId() {
        return providersByProviderId;
    }

    public void setProvidersByProviderId(ProviderEntity providersByProviderId) {
        this.providersByProviderId = providersByProviderId;
    }

    @ManyToOne
    @JoinColumn(name = "feed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public FeedEntity getFeedsByFeedId() {
        return feedsByFeedId;
    }

    public void setFeedsByFeedId(FeedEntity feedsByFeedId) {
        this.feedsByFeedId = feedsByFeedId;
    }
}
