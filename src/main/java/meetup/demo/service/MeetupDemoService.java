/*
 * ====================================
 * Copyright (C) 2020  Commsignia Ltd
 * http://commsignia.com
 * All rights reserved
 * ------------------------------------
 * Date: Oct 6, 2020
 * Author: Timót Tarjáni <timot.tarjani@commsignia.com>
 * Project: MessageLake
 * ====================================
 */

package meetup.demo.service;

import meetup.demo.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MeetupDemoService{



    public void handleMessage(final Event event) {
        log.info("Consumed event {}", event);
    }


}
