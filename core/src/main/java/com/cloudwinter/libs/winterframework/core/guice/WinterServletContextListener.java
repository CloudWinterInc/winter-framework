package com.cloudwinter.libs.winterframework.core.guice;

import static com.google.common.base.Preconditions.checkArgument;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.common.base.Strings;
import com.google.inject.Guice;

/**
 * Main listener to start the application. This should be called in the web.xml
 * passing as context parameter the StartModule class defined in your
 * application. <br />
 * <br />
 * Example:<br />
 * <listener> <br />
 * <listener-class><br />
 * com.cloudwinter.libs.winterframework.core.guice.WinterServletContextListener
 * </listener-class> <br />
 * </listener><br />
 * <br />
 * <context-param><br />
 * <param-name><br />
 * winter-start-module<br />
 * </param-name><br />
 * <param-value> <br />
 * com.cloudwinter.web.init.StartModule<br />
 * </param-value><br />
 * </context-param>
 *
 * @author Tomas de Priede
 * @since 11/20/14 14:30 PM
 * @version 1.0.0
 */
public class WinterServletContextListener implements ServletContextListener {

    protected static final String MODULES_ATTRIBUTE = "winter-start-module";
    protected static final String INJECTOR_ATTRIBUTE = "guice-injector";
    public static final String EMPTY_MODULE_ERROR = "Empty winter start module with name. Please add a winter-start-module context param.";
    public static final String FAIL_TO_LOAD_MODULE_ERROR = "Fail to load winter start module with name: ";

    /**
     * This method is called when AppEngine kill our instance. <br />
     * We will remove our injector from the current instance.
     * 
     * @param event
     *            injected context event from the application
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.removeAttribute(INJECTOR_ATTRIBUTE);
    }

    /**
     * This method is called when AppEngine starts our instance. <br />
     * We will load the winter-start-module from the web.xml and will configure
     * the main modules of your app. <br />
     * There can be only one start module per app.
     *
     * @param event
     *            injected context event from the application
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String moduleName = context.getInitParameter(MODULES_ATTRIBUTE);
        //
        checkArgument(!Strings.isNullOrEmpty(moduleName), EMPTY_MODULE_ERROR);
        try {
            WinterStartModule module = (WinterStartModule) Class.forName(
                    moduleName.trim()).newInstance();
            context.setAttribute(INJECTOR_ATTRIBUTE,
                    Guice.createInjector(module));
        } catch (Exception e) {
            // TODO convert this to a custom initilization exception
            throw new IllegalArgumentException(FAIL_TO_LOAD_MODULE_ERROR
                    + moduleName, e);
        }
    }
}
