package com.demo.components.elasticsearch.v2;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dewu.de
 * @date 2024-06-07 4:29 下午
 */
@Getter
@Setter
public class MetaIndex<T extends BaseIndex> {

    private String index;
    private T doc;

}
