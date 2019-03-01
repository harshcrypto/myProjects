package org.hs.service;

import org.hs.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepositService {
    @Autowired
    private Wallet wallet;

    /**
     * gets the deposit address from wallet
     * @param clientAddress
     * @return
     */
    public String getDepositAddress(String clientAddress) {
        return wallet.getDepositAddress(clientAddress);
    }
}
