package com.im.ext.xchem.filedrop;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by timbo on 20/01/17.
 */
public class FileDropRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {

        from("timer:foo?period={{poll_interval}}")
                .log("I've been fired!");

        from("file:foo?antInclude=input/**/*.sdf&move=output&recursive=true&delay={{poll_interval}}")
                .log("File ${header.CamelFileName} received")
                .to("direct:process_rxn_smarts_filter");

        from("direct:process_rxn_smarts_filter")
                .log("Processing ...");
    }
}
