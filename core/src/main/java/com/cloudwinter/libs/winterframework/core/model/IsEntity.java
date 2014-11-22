package com.cloudwinter.libs.winterframework.core.model;

import java.io.Serializable;

/**
 * Base interface for the objectify @Entities in your application. <br />
 * <br />
 * Please extend all your entities from the IsModel interface. We strongly
 * recommend to use a String as the primary Id. <br />
 * <br />
 * If you need to use non-String id, you can use this interface
 *
 * @author Tomas de Priede
 * @since 11/20/14 4:55 PM
 * @version 1.0.0
 */
public interface IsEntity extends Serializable {
}
