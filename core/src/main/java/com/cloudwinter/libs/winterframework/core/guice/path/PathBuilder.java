package com.cloudwinter.libs.winterframework.core.guice.path;

import javax.servlet.http.HttpServlet;

/**
 * @author Tomas de Priede
 * @since 11/20/14 7:26 PM
 * @version 1.0.0
 */
public class PathBuilder {

    /**
     * Builds the URL that will be used to register the current servlet class.
     * 
     * @param servletClass
     * @return the URL as String
     */
    public String buildPathFor(Class<? extends HttpServlet> servletClass) {
        return null;
    }
}
