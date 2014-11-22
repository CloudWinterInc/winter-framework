package com.cloudwinter.libs.winterframework.core.guice;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.cloudwinter.libs.winterframework.core.guice.dummy.DummyTestWinterStartModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;

/**
 * User: Tomas de Priede Date: 11/20/14 Time: 2:09 PM
 */
public class WinterServletContextListenerTest {

    private WinterServletContextListener contextListener;

    @Before
    public void setUp() throws Exception {
        contextListener = new WinterServletContextListener();
    }

    @After
    public void tearDown() throws Exception {
        contextListener = null;
    }

    @Test
    public void testContextDestroyed() throws Exception {
        ServletContextEvent contextEvent = mock(ServletContextEvent.class);
        ServletContext context = mock(ServletContext.class);
        when(contextEvent.getServletContext()).thenReturn(context);
        //
        contextListener.contextDestroyed(contextEvent);
        //
        verify(context).removeAttribute(
                eq(WinterServletContextListener.INJECTOR_ATTRIBUTE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void contextInitialized_withNull() throws Exception {
        ServletContextEvent contextEvent = mock(ServletContextEvent.class);
        ServletContext context = mock(ServletContext.class);
        when(
                context.getAttribute(WinterServletContextListener.MODULES_ATTRIBUTE))
                .thenReturn(null);
        when(contextEvent.getServletContext()).thenReturn(context);
        //
        contextListener.contextInitialized(contextEvent);
    }

    @Test(expected = IllegalArgumentException.class)
    public void contextInitialized_notModuleClass() throws Exception {
        ServletContextEvent contextEvent = mock(ServletContextEvent.class);
        ServletContext context = mock(ServletContext.class);
        when(
                context.getInitParameter(WinterServletContextListener.MODULES_ATTRIBUTE))
                .thenReturn(String.class.getName());
        when(contextEvent.getServletContext()).thenReturn(context);
        //
        contextListener.contextInitialized(contextEvent);
    }

    @Test
    public void contextInitialized_startModuleClass() throws Exception {
        ServletContextEvent contextEvent = mock(ServletContextEvent.class);
        ServletContext context = mock(ServletContext.class);
        when(
                context.getInitParameter(WinterServletContextListener.MODULES_ATTRIBUTE))
                .thenReturn(DummyTestWinterStartModule.class.getName());
        when(contextEvent.getServletContext()).thenReturn(context);
        //
        contextListener.contextInitialized(contextEvent);
        //
        verify(context).setAttribute(
                eq(WinterServletContextListener.INJECTOR_ATTRIBUTE),
                (Injector) anyObject());
    }
}
