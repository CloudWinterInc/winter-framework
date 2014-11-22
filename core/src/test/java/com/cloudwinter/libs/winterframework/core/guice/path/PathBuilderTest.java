package com.cloudwinter.libs.winterframework.core.guice.path;

import static org.junit.Assert.assertNull;

import javax.servlet.http.HttpServlet;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Tomas de Priede
 * @since 11/20/14 11:36 PM
 * @version 1.0.0
 */
public class PathBuilderTest {

    private PathBuilder pathBuilder;

    @Before
    public void setUp() throws Exception {
        pathBuilder = new PathBuilder();
    }

    @Test
    public void testBuildPathFor() throws Exception {
        // TODO complete the real method and test case
        assertNull(pathBuilder.buildPathFor(HttpServlet.class));
    }
}
