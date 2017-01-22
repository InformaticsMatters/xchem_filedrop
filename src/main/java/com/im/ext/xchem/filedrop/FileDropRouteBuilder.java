package com.im.ext.xchem.filedrop;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecBinding;

/**
 * Created by timbo on 20/01/17.
 */
public class FileDropRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {

//        from("timer:foo?period={{poll_interval}}")
//                .log("I've been fired!");

        from("file:input?antInclude=**/*.sdf&recursive=true&delay={{poll_interval}}")
                .log("Path ${header.CamelFileParent} received")
                .to("direct:process_rxn_smarts_filter");

//        from("box://poll-events/poll?userName={{box_username}}&userPassword={{box_userpassword}}&clientId={{box_client_id}}&clientSecret={{box_client_secret}}&streamPosition=-1&streamType=all&limit=100")
//            .log("Event: $body");

        /* This copies the input file to the output directory and runs the analysis */
        from("direct:process_rxn_smarts_filter")
                .log("Processing ${header.CamelFileName} ...")
                .to("file:output/input")
                .setHeader(ExecBinding.EXEC_COMMAND_WORKING_DIR, simple("output/${header.CamelFileParent}"))
                .to("exec://{{filter_cmd}}")
                .log("Execution complete");

        /* This listens for result files and will send them to Box.*/
//        from("file:output/input?antInclude=**/*.sdf,**/*.sdf.gz&recursive=true&delay={{poll_interval}}")
//                .log("Result file ${header.CamelFileName}")
//                // TODO send to Box
//        ;

    }
}
