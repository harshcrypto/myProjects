package org.hs.service;

import org.hs.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MixerService {
    @Autowired
    private Wallet wallet;

    public String getDepositAddress(String clientAddress) {
        return wallet.getDepositAddress(clientAddress);
    }
}
