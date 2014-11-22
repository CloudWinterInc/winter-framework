package com.cloudwinter.libs.winterframework.core.guice;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cloudwinter.libs.winterframework.core.guice.dummy.DummyTestEntity;
import com.cloudwinter.libs.winterframework.core.guice.dummy.DummyTestModel;
import com.cloudwinter.libs.winterframework.core.guice.dummy.DummyTestWinterStartModule;
import com.cloudwinter.libs.winterframework.core.guice.path.PathBuilder;
import com.cloudwinter.libs.winterframework.core.model.IsEntity;
import com.google.common.collect.Lists;
import com.google.inject.Binder;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.googlecode.objectify.ObjectifyFactory;

/**
 * @author Tomas de Priede
 * @since 11/21/14 10:10 PM
 * @version 1.0.0
 */
public class WinterStartModuleTest {

    private WinterStartModule module;
    private Binder binder;
    @Mock
    private ObjectifyFactory ofyFactory;
    @Mock
    private PathBuilder pathBuilder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        module = new DummyTestWinterStartModule();
        //
        binder = mock(Binder.class);
        when(binder.bind((Class) anyObject())).thenReturn(
                mock(AnnotatedBindingBuilder.class));
        //
        module.setOfyFactory(ofyFactory);
        module.setPathBuilder(pathBuilder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConfigureServlets_noModules() throws Exception {
        // No modules should throw IAE
        module.configureServlets();
    }

    @Test
    public void testConfigureServlets_withModules() throws Exception {
        WinterModule winterModule = mock(WinterModule.class);
        // Ofy classes
        List<Class<? extends IsEntity>> ofyClasses = Lists.newArrayList(
                DummyTestEntity.class, DummyTestModel.class);
        when(winterModule.getOfyClasses()).thenReturn(ofyClasses);
        // Servlet classes
        List servletClasses = Lists.newArrayList(HttpServlet.class);
        when(winterModule.getServletClasses()).thenReturn(servletClasses);
        when(pathBuilder.buildPathFor(eq(HttpServlet.class))).thenReturn(
                "/path");
        //
        List winterModuleList = Lists.newArrayList(winterModule);
        module.setMainModules(winterModuleList);
        //
        // Execute the class
        module.configure(binder);
        //
        verify(winterModule).configure();
        //
        verify(ofyFactory).register(eq(DummyTestEntity.class));
        verify(ofyFactory).register(eq(DummyTestModel.class));
    }
}
