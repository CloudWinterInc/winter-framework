package com.cloudwinter.libs.winterframework.core.guice.path;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

/**
 * @author Tomas de Priede
 * @version 1.0.0
 * @since 11/20/14 7:26 PM
 */
public class PathBuilder {

    private static final String BASE_PATH_NOT_FOUND = "The class: %s is not annotated with @BasePath.";
    private static final String RELATIVE_PATH_NOT_FOUND = "The class: %s is not annotated with @RelativePath.";

    /**
     * Builds the URL that will be used to register the current servlet class.
     *
     * @param servletClass
     * @return the URL as String
     */
    public List<String> buildPathFor(Class<? extends HttpServlet> servletClass) {
        checkNotNull(servletClass);
        //
        String className = servletClass.getName();
        BasePath basePath = checkNotNull(servletClass.getAnnotation(BasePath.class),
                String.format(BASE_PATH_NOT_FOUND, className));
        RelativePath relativePath = checkNotNull(servletClass.getAnnotation(RelativePath.class),
                String.format(RELATIVE_PATH_NOT_FOUND, className));
        //
        List<String> paths = new ArrayList<>();
        for (String path : relativePath.paths()) {
            paths.add(normalizeSlashes(basePath.path() + "/" + path));
        }
        return paths;
    }

    /**
     * This is a very simple (and not 100% perfect) to remove the double
     * slashes.
     *
     * @param input
     *            the String url with slashes
     * @return the String normalized
     */
    protected String normalizeSlashes(String input) {
        checkNotNull(input);
        return input.replaceAll("//", "/");
    }
}
