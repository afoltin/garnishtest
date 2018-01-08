package org.garnishtest.demo.rest_complex.web.infrastructure.logging;

import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class Jul4Slf4jBridgeInstaller implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent event) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Override
    public void contextDestroyed(final ServletContextEvent event) {}
}
