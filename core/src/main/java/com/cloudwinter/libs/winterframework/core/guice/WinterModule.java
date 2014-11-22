package com.cloudwinter.libs.winterframework.core.guice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServlet;

import com.cloudwinter.libs.winterframework.core.model.IsEntity;
import com.google.common.collect.Iterables;
import com.google.inject.AbstractModule;

/**
 * A custom guice module. Contains entity classes to be registered in Objectify,
 * Servlets to be registered by guice and also submodules that would receive
 * their classes to register them too.
 * 
 * @author Tomas de Priede
 * @since 11/20/14 4:12 PM
 * @version 1.0.0
 */
public abstract class WinterModule extends AbstractModule {

    private List<Class<? extends IsEntity>> ofyClasses = new ArrayList<>();
    private List<Class<? extends HttpServlet>> servletClasses = new ArrayList<>();
    private List<WinterModule> childModules = new ArrayList<>();

    /**
     * Add to this list the entities to be registered in Objectify.
     * 
     * @param ofyClasses
     *            list with entities to be registed.
     *
     */
    protected abstract void configureOfyClasses(List<Class<? extends IsEntity>> ofyClasses);

    /**
     * Add to that list the Servlets to be registered.
     * 
     * @param servletClasses
     *            list with servlets to be registered by guice.
     * 
     */
    protected abstract void configureServletClasses(List<Class<? extends HttpServlet>> servletClasses);

    /**
     * Add to that list the submodules to the current one
     * 
     * @param childModules
     *            list where submodules could be added
     */
    protected abstract void configureChildModules(List<WinterModule> childModules);

    /**
     * Iterate through the submodules and add all their ofy and servlet classes
     * to the current one. This will allow to configure guice submodules in the
     * app.
     */
    protected void joinChildModules() {
        for (WinterModule child : childModules) {
            // Join child modules
            child.joinChildModules();
            // Configure these child modules
            child.configure();
            // Add child classes to this module classes
            Iterables.addAll((Collection) ofyClasses, child.getOfyClasses());
            Iterables.addAll((Collection) servletClasses, child.getServletClasses());
        }
    }

    /**
     * Add the child modules, entities and servlet classes from the extended
     * class to this abstract one. Then call the join child modules to iterate
     * over them and add submodules classes to this one.
     */
    public void configure() {
        configureChildModules(childModules);
        configureOfyClasses(ofyClasses);
        configureServletClasses(servletClasses);
        //
        joinChildModules();
        //
    }

    /**
     * Return the current list of entity classes to be registered by objectify.
     * 
     * @return
     */
    public List<Class<? extends IsEntity>> getOfyClasses() {
        return ofyClasses;
    }

    /**
     * Return the current list of servlet classes to be registered by guice.
     * 
     * @return
     */
    public List<Class<? extends HttpServlet>> getServletClasses() {
        return servletClasses;
    }

    /**
     * Return the current submodules to this one, to be registered in the app.
     * 
     * @return
     */
    public List<WinterModule> getChildModules() {
        return childModules;
    }
}
