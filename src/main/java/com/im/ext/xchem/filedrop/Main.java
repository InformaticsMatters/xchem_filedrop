package com.im.ext.xchem.filedrop;

import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by timbo on 20/01/17.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {


        final DefaultCamelContext context = new DefaultCamelContext();
        PropertiesComponent pc = new PropertiesComponent();
        pc.setLocation("classpath:com/im/ext/xchem/filedrop/default.properties,file:application.properties");
        //pc.setLocation("classpath:com/im/ext/xchem/filedrop/default.properties,file:application.properties;optional=true");
        context.addComponent("properties", pc);

        context.addRoutes(new FileDropRouteBuilder());

        LOG.info("Starting CamelContext");
        context.start();

        // add shutdown hook that gracefully shuts down camel
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                LOG.info("Stopping CamelContext");
                try {
                    context.stop();
                } catch (Exception e) {
                    LOG.log(Level.SEVERE, "Failed to shutdown CamelContext", e);
                }
            }

        });

        // wait forever. Terminate using Ctrl-C.
        Thread.currentThread().join();
    }

}
