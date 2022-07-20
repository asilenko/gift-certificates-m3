package com.epam.esm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * WebApplicationInitializer to register a DispatcherServlet and use Java-based Spring configuration.
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * @see AbstractAnnotationConfigDispatcherServletInitializer#onStartup(ServletContext)
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("spring.profiles.active", "prod");
        super.onStartup(servletContext);
    }

    /**
     * @see AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{};
    }

    /**
     * @see AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ControllerConfiguration.class};
    }

    /**
     * @see AbstractAnnotationConfigDispatcherServletInitializer#getServletMappings()
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
