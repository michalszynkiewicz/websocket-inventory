package com.mszynkiewicz.storage;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;

import java.net.URL;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/4/15
 * Time: 2:29 PM
 */
public class StorageServer {

    private Server server;

    private void configure() {
        HttpConnectionFactory http = new HttpConnectionFactory();
        ServerConnector httpConnector = new ServerConnector(server, http);
        httpConnector.setPort(8406);

        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/");


        setUpContextLoaderListener(handler);
        setUpJerseyServlet(handler);
        setUpDispatcherServlet(handler);
        setUpStaticResources(handler);

        server.setHandler(handler);

        server.addConnector(httpConnector);
    }

    private void setUpDispatcherServlet(ServletContextHandler handler) {
        ServletHolder servletHolder = new ServletHolder(new DispatcherServlet());
        handler.addServlet(servletHolder, "/websocket/*");
        servletHolder.setInitParameter("contextConfigLocation", "");

    }

    private void setUpStaticResources(ServletContextHandler handler) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("webapp");

        @SuppressWarnings("ConstantConditions")
        String webDir = resource.toExternalForm();

        handler.setResourceBase(webDir);

        handler.addServlet(new ServletHolder(new DefaultServlet()), "/*");
    }

    private void setUpJerseyServlet(ServletContextHandler handler) {
        ServletHolder servletHolder = new ServletHolder();
        servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "com.mszynkiewicz.storage.api");
        servletHolder.setInitParameter("com.sun.jersey.config.provider.packages", "com.fasterxml.jackson.jaxrs.json");
        servletHolder.setServlet(new SpringServlet());

        handler.addServlet(servletHolder, "/rest/*");
    }

    private void setUpContextLoaderListener(ServletContextHandler handler) {
        handler.addEventListener(new ContextLoaderListener());
        handler.setInitParameter("contextConfigLocation", "classpath:/applicationContext.xml");
    }

    private void create() {
        QueuedThreadPool threadPool = new QueuedThreadPool(512);

        server = new Server(threadPool);
    }

    private void start() throws Exception {
        server.start();
        server.join();
    }


    public static void main(String... args) throws Exception {
        StorageServer storageServer = new StorageServer();
        storageServer.create();
        storageServer.configure();
        storageServer.start();
    }
}
