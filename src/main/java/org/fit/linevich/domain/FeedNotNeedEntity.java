package org.fit.linevich.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@Data
@SqlResultSetMapping(
        name = "feedQueryMapper",
        classes =
        @ConstructorResult(targetClass = FeedNotNeedEntity.class,
                columns = {@ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "type", type = String.class),
                        @ColumnResult(name = "consumption", type = Integer.class),
                        @ColumnResult(name = "produce", type = Integer.class)
                }
        )
)
@AllArgsConstructor
@NoArgsConstructor
public class FeedNotNeedEntity {
    @Id
    private String name;
    private String type;
    private int consumption;
    private int produce;
}
