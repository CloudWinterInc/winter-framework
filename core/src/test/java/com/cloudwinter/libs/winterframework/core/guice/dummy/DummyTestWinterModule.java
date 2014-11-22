package com.cloudwinter.libs.winterframework.core.guice.dummy;

import java.util.List;

import javax.servlet.http.HttpServlet;

import com.cloudwinter.libs.winterframework.core.guice.WinterModule;
import com.cloudwinter.libs.winterframework.core.model.IsEntity;

/**
 * @author Tomas de Priede
 * @since 11/21/14 11:22 PM
 * @version 1.0.0
 */
public class DummyTestWinterModule extends WinterModule {

    @Override
    protected void configureOfyClasses(List<Class<? extends IsEntity>> ofyClasses) {
    }

    @Override
    protected void configureServletClasses(List<Class<? extends HttpServlet>> servletClasses) {
    }

    @Override
    protected void configureChildModules(List<WinterModule> childModules) {
    }
}
