package com.cloudwinter.libs.winterframework.core.guice.path;

import static org.junit.Assert.*;

import java.util.List;

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

    @Test(expected = NullPointerException.class)
    public void testBuildPathFor_null() throws Exception {
        pathBuilder.buildPathFor(null);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildPathFor_nonBasePath() throws Exception {
        pathBuilder.buildPathFor(NonBaseServlet.class);
    }

    @Test(expected = NullPointerException.class)
    public void testBuildPathFor_nonRelativePath() throws Exception {
        pathBuilder.buildPathFor(NonRelativeServlet.class);
    }

    @Test
    public void testBuildPathFor_valid() throws Exception {
        List<String> result = pathBuilder.buildPathFor(ValidServlet.class);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("/base/"));
        assertTrue(result.contains("/base/*"));
    }

    @Test(expected = NullPointerException.class)
    public void testNormalize_null() throws Exception {
        pathBuilder.normalizeSlashes(null);
    }

    @Test
    public void testNormalize() throws Exception {
        String result = pathBuilder.normalizeSlashes("/url//path");
        assertEquals("/url/path", result);
    }

    public class NonBaseServlet extends HttpServlet {
    }

    @BasePath(path = "/base/")
    public class NonRelativeServlet extends HttpServlet {
    }

    @BasePath(path = "/base/")
    @RelativePath(paths = { "", "*"})
    public class ValidServlet extends HttpServlet {
    }
}
