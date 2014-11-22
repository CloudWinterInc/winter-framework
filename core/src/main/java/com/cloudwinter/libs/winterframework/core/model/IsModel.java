package com.cloudwinter.libs.winterframework.core.model;

import java.io.Serializable;

/**
 * Base interface for the models in your application. <br />
 * <br />
 * Please extend all your models from this interface. We strongly recommend to
 * use a String as the primary Id
 * 
 * @author Tomas de Priede
 * @since 11/19/14 10:40 PM
 * @version 1.0.0
 */
public interface IsModel extends IsEntity {

    /**
     *
     *
     * @return the unique id associated with the entity
     */
    public String getId();

    /**
     * Caution! This method should only be used when necessary. Avoid modifying
     * the id programatically whenever possible since this introduces the
     * potential for id mismatches, which can lead to other problems.
     *
     * @param id
     *            the unique id associated with the entity.
     */
    public void setId(String id);
}
