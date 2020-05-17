package org.fit.linevich.views;

import lombok.Data;

@Data
public class FeedNotNeedQuery {
    private String name;
    private String type;
    private int consumption;
    private int produce;
}
