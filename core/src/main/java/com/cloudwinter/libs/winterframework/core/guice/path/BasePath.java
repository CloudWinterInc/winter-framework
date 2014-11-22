package com.cloudwinter.libs.winterframework.core.guice.path;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * User: Tomas de Priede Date: 11/21/14 Time: 11:54 PM
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BasePath {

    String path();
}
