package org.hs.model;

import org.hs.service.dto.AddressInfo;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class Wallet {
    // Fresh Deposit address
    private LinkedList<String> freshDepositAddressList = new LinkedList<>();
    private ConcurrentHashMap<String, AddressInfo> checkfillDepositAddressList = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, AddressInfo> depositCompleteAddressList = new ConcurrentHashMap<>();
    //Mixer Addresses: for internal use only
    private LinkedList<String> freshMixerAddressList = new LinkedList<>();
    private LinkedList<String> filledMixerAddressList = new LinkedList<>();
    private LinkedList<String> freshGatewayAddressList = new LinkedList<>();
    private LinkedList<String> withdrawalCompleteAddress = new LinkedList<>();

    public LinkedList<String> getWithdrawalCompleteAddress() {
        return withdrawalCompleteAddress;
    }

    public Wallet() {
        //Static address generation. In v2.0 replenish these address list automatically
        for (int i = 0; i < 1000; i++) {
            freshDepositAddressList.push("4DepositAddress"+i);
        }

        for (int i = 0; i < 1000; i++) {
            freshMixerAddressList.push("4MixerAddress"+i);
        }

        for (int i = 0; i < 1000; i++) {
            freshGatewayAddressList.push("4GatewayAddress"+i);
        }
    }

    public ConcurrentHashMap<String, AddressInfo> getDepositCompleteAddressList() {
        return depositCompleteAddressList;
    }

    public ConcurrentHashMap<String, AddressInfo> getCheckfillDepositAddressList() {
        return checkfillDepositAddressList;
    }

    public  AddressInfo getUsedAddressInfo(String address) {
        return checkfillDepositAddressList.get(address);
    }

    public  String getDepositAddress(String clientAddress) {
        String depositAddress = freshDepositAddressList.pop();
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setClientAddresses(clientAddress);
        checkfillDepositAddressList.put(depositAddress,addressInfo);
        return depositAddress;
    }

    public void completeDeposit(String clientAddress,double balance) {
        AddressInfo addressInfo = checkfillDepositAddressList.get(clientAddress);
        addressInfo.setBalance(balance);
        depositCompleteAddressList.put(clientAddress, addressInfo);
        checkfillDepositAddressList.remove(clientAddress);
    }

    public int getRandom(int maximum, int minimum) {

        int result;
         do {
             result = ((int) (Math.random()*(maximum - minimum))) + minimum;
         } while (result%3==0);
        return result;
    }

    //Mixer address function

    public  LinkedList<String> getFreshMixerAddress(int totalAddress) {
        LinkedList<String> nFreshMixerAddresses = new LinkedList<>();
        for (int i = 0; i < totalAddress; i++) {
            nFreshMixerAddresses.push(freshMixerAddressList.pop());
        }
        return nFreshMixerAddresses;
    }

    public  void addFilledMixerAddress(String mixerAddress) {
        filledMixerAddressList.push(mixerAddress);
    }

    public LinkedList<String> getFilledMixerAddressList() {
        return filledMixerAddressList;
    }

    // gateway address
    public  String getGatewayAddress() {
        return freshGatewayAddressList.pop();
    }

    public static void main(String[] args ) {
        Wallet m = new Wallet();
        for (int i = 0; i < 1000; i++) {
            System.out.println(m.getRandom(5,1));
        }

    }
}
