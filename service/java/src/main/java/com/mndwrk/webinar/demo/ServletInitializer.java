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

package com.mndwrk.webinar.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        application.sources(WebinarDemoApplication.class);

        return application;
    }

}
