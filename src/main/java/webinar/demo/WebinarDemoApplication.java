/*
 * ====================================
 * Copyright (C) 2020  Commsignia Ltd
 * http://commsignia.com
 * All rights reserved
 * ------------------------------------
 * Date: Oct 2, 2020
 * Author: Timót Tarjáni <timot.tarjani@commsignia.com>
 * Project: MessageLake
 * ====================================
 */

package webinar.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class WebinarDemoApplication {

    private static final Logger log = LogManager.getLogger(WebinarDemoApplication.class);

    public static void main(final String[] args) {

        final SpringApplication application = new SpringApplication(WebinarDemoApplication.class);

        application.addListeners(new ApplicationPidFileWriter());

        application.run(args);

    }

}
