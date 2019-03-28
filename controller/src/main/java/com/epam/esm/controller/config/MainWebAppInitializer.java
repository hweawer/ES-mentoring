package com.epam.esm.controller.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class MainWebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(final ServletContext sc) {

        AnnotationConfigWebApplicationContext root =
                new AnnotationConfigWebApplicationContext();
        root.scan("com.epam.esm");
        sc.addListener(new ContextLoaderListener(root));

        DispatcherServlet dispatcherServlet = new DispatcherServlet(new GenericWebApplicationContext());
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic appServlet =
                sc.addServlet("mvc", dispatcherServlet);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }
}