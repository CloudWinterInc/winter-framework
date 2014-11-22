package com.cloudwinter.libs.winterframework.core.guice;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;

import com.cloudwinter.libs.winterframework.core.guice.path.PathBuilder;
import com.cloudwinter.libs.winterframework.core.model.IsEntity;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyFilter;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;

/**
 * Configure all the modules of the application when AppEngine loads the
 * instance.
 * 
 * @author Tomas de Priede
 * @since 11/20/14 2:27 PM
 * @version 1.0.0
 */
public abstract class WinterStartModule extends
        ExternallyConfigurableServletModule {

    protected static final String NO_MODULES_FOUND = "No modules were loaded. Please override the WinterStartModule.getMainModules()";
    /**
     * Used to get the URL to register from a HttpServlet's class annotations ;
     */
    private PathBuilder pathBuilder = new PathBuilder();
    /**
     * A list that will be filled by the extended class with the main
     * application modules.
     */
    private List<WinterModule> mainModules = new ArrayList<>();
    /**
     * The factory class that register the entities in the application.
     */
    private ObjectifyFactory ofyFactory = ObjectifyService.factory();

    @Override
    protected final void configureServlets() {
        configureMainModules(mainModules);
        checkArgument(!Iterables.isEmpty(mainModules), NO_MODULES_FOUND);
        //
        for (WinterModule module : mainModules) {
            configureModule(module);
        }
        //
        filter("/*").through(AsyncCacheFilter.class);
        filter("/*").through(ObjectifyFilter.class);
        bind(ObjectifyFilter.class).in(Singleton.class);
    }

    /**
     * First configure the module itself, loading their child content to that
     * module, and configuring the ofy classes and http classes on it. <br />
     * <br />
     * Then configure the module registering on the objectify factory the
     * module's entities and registering on the URLS the HttpServlets for the
     * application use.
     * 
     * @param module
     *            one application module to be configured
     */
    protected void configureModule(WinterModule module) {
        checkNotNull(module);
        module.configure();
        //
        for (Class<? extends IsEntity> entityClazz : module.getOfyClasses()) {
            ofyFactory.register(entityClazz);
        }
        for (Class<? extends HttpServlet> entityClazz : module
                .getServletClasses()) {
            // URL config
            String path = pathBuilder.buildPathFor(entityClazz);
            serve(path, entityClazz);
        }
    }

    /**
     * Configure the main modules adding them to the list.
     * 
     * @param mainModules
     *            an empty non null list where you should add your main app
     *            modules
     */
    protected abstract void configureMainModules(List<WinterModule> mainModules);

    /**
     * Useful for testing purposes. To be able to mock it
     * 
     * @param pathBuilder
     *            a mocked instance of path builder
     */
    void setPathBuilder(PathBuilder pathBuilder) {
        this.pathBuilder = pathBuilder;
    }

    /**
     * Useful for testing purposes. To be able to mock it
     * 
     * @param ofyFactory
     *            a mocked instance of the ofyFactory
     */
    void setOfyFactory(ObjectifyFactory ofyFactory) {
        this.ofyFactory = ofyFactory;
    }

    /**
     * Useful for testing purposes. To be able to mock it
     * 
     * @param mainModules
     *            the list of main modules
     */
    void setMainModules(List<WinterModule> mainModules) {
        this.mainModules = mainModules;
    }
}
