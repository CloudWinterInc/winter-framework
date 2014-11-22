package com.cloudwinter.libs.winterframework.core.guice;

import javax.servlet.http.HttpServlet;

import com.google.inject.servlet.ServletModule;

/**
 * Handle the registration of a HttpServlet with a custom URL path
 *
 * @author Tomas de Priede
 * @since 11/20/14 4:27 PM
 * @version 1.0.0
 */
public abstract class ExternallyConfigurableServletModule extends ServletModule {

    /**
     * Register an url with a singleton HttpServlet class
     * 
     * @param path
     *            the url path to hit the servlet
     * @param servlet
     *            the servlet class that will be registered
     */
    public void serve(String path, Class<? extends HttpServlet> servlet) {
        serve(path).with(servlet);
    }
}
