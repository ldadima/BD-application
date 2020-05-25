package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProvidersSpecializationEntityPK implements Serializable {
    @Column(name = "feed_id")
    private Integer feedId;
    @Column(name = "provider_id")
    private Integer providerId;
}
