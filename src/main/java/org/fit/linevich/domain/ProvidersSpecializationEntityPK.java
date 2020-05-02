package org.fit.linevich.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class ProvidersSpecializationEntityPK implements Serializable {
    @Id
    @Column(name = "feed_id")
    private int feedId;
    @Id
    @Column(name = "provider_id")
    private int providerId;
}
