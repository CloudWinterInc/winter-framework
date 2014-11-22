package com.cloudwinter.libs.winterframework.core.guice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServlet;

import org.junit.Before;
import org.junit.Test;

import com.cloudwinter.libs.winterframework.core.guice.dummy.DummyTestEntity;
import com.cloudwinter.libs.winterframework.core.guice.dummy.DummyTestModel;
import com.cloudwinter.libs.winterframework.core.guice.dummy.DummyTestWinterModule;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;

/**
 * @author Tomas de Priede
 * @since 11/21/14 11:21 PM
 * @version 1.0.0
 */
public class WinterModuleTest {

    private WinterModule winterModule;

    @Before
    public void setUp() throws Exception {
        winterModule = new DummyTestWinterModule();
    }

    @Test
    public void testJoinChildModules_noModules() throws Exception {
        // no Modules
        winterModule.joinChildModules();
        //
        assertTrue(winterModule.getOfyClasses().isEmpty());
        assertTrue(winterModule.getServletClasses().isEmpty());
    }

    @Test
    public void testJoinChildModules_withModules() throws Exception {
        WinterModule module1 = mock(WinterModule.class);
        List ofyClasses = Lists.newArrayList(DummyTestEntity.class);
        when(module1.getOfyClasses()).thenReturn(ofyClasses);
        //
        WinterModule module2 = mock(WinterModule.class);
        ofyClasses = Lists.newArrayList(DummyTestModel.class);
        when(module2.getOfyClasses()).thenReturn(ofyClasses);
        List servletClasses = Lists.newArrayList(HttpServlet.class);
        when(module2.getServletClasses()).thenReturn(servletClasses);
        //
        winterModule.getChildModules().add(module1);
        winterModule.getChildModules().add(module2);
        //
        winterModule.joinChildModules();
        //
        assertFalse(winterModule.getOfyClasses().isEmpty());
        assertFalse(winterModule.getServletClasses().isEmpty());
        //
        assertTrue(winterModule.getOfyClasses().contains(DummyTestEntity.class));
        assertTrue(winterModule.getOfyClasses().contains(DummyTestModel.class));
        assertTrue(winterModule.getServletClasses().contains(HttpServlet.class));
    }
}
