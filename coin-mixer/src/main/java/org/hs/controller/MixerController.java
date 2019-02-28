package org.hs.controller;

import org.hs.service.MixerService;
import org.hs.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
@RestController
public class MixerController{
    @Autowired
    MixerService mixerService;
    private Logger logger = Logger.getLogger(MixerController.class);
    @RequestMapping(value = "/depositAddress/{clientAddress}", method = RequestMethod.GET)
    public String getDepositAddress(@PathVariable("clientAddress") String clientAddress) {
        logger.info("Got input addresses: "+clientAddress);
        String depositAddress = mixerService.getDepositAddress(clientAddress);
        return depositAddress;
    }

}
