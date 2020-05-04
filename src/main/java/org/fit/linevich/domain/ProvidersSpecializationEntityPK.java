package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class ProvidersSpecializationEntityPK implements Serializable {
    @Id
    @Column(name = "feed_id")
    private Integer feedId;
    @Id
    @Column(name = "provider_id")
    private Integer providerId;
}
