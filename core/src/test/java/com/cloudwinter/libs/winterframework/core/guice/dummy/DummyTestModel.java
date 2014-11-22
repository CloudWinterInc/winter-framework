package com.cloudwinter.libs.winterframework.core.guice.dummy;

import com.cloudwinter.libs.winterframework.core.model.IsModel;

/**
 * @author Tomas de Priede
 * @since 11/21/14 10:53 PM
 * @version 1.0.0
 */
public class DummyTestModel implements IsModel {

    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
