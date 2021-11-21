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

package meetup.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class MeetupDemoApplication {

    private static final Logger log = LogManager.getLogger(MeetupDemoApplication.class);

    public static void main(final String[] args) {

        final SpringApplication application = new SpringApplication(MeetupDemoApplication.class);

        application.addListeners(new ApplicationPidFileWriter());

        application.run(args);

    }

}
