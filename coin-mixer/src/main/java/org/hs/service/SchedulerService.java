package org.hs.service;

import org.apache.log4j.Logger;
import org.hs.model.Mixer;
import org.hs.model.Wallet;
import org.hs.service.dto.AddressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class SchedulerService {
    @Autowired
    private MyRestClient myRestClient;
    @Autowired
    private Wallet wallet;
    @Autowired
    private Mixer mixer;
    private Logger logger = Logger.getLogger(SchedulerService.class);
    @Scheduled(cron = "0 * * * * *")
    public void moveCoinsToHouse() {
        //1. poll deposit address
        ConcurrentHashMap<String, AddressInfo> depositAddressList = wallet.getCheckfillDepositAddressList();
        logger.info("Total address to poll:"+depositAddressList.size());
        for (String address : depositAddressList.keySet()) {
            logger.info("Polling Address: "+address + " " + depositAddressList.get(address));
            AddressInfo addressInfo= myRestClient.getAddressInfo(address);
            if(addressInfo.getBalance()>0) {
                logger.info("Found non-zero balance for Address: "+address+" moving to house address.");
                //2. Move coins to house address
                myRestClient.sendCoinToHouse(address,addressInfo.getBalance());
                wallet.completeDeposit(address,addressInfo.getBalance());
            }
        }
        mixer.mixCoins();
        mixer.doClientWithdrawal();
    }




}
