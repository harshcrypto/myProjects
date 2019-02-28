package org.hs.service.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AddressInfo {

    private Double balance;
    private String clientAddresses;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getClientAddresses() {
        return clientAddresses;
    }

    public void setClientAddresses(String clientAddresses) {
        this.clientAddresses = clientAddresses;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
