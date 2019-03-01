package org.hs.model;

import org.apache.log4j.Logger;
import org.hs.service.MyRestClient;
import org.hs.service.dto.AddressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class Mixer {

    @Autowired
    private Wallet wallet;
    @Autowired
    private MyRestClient myRestClient;
    @Value("${myapp.house-address}")
    private String houseAddress;
    @Value("${myapp.mixer.speed}")
    private int mixerSpeed;
    private Logger logger = Logger.getLogger(Mixer.class);
    public void mixCoins() {
        // M1. Take coins from deposit complete and move to mixer address
        ConcurrentHashMap<String, AddressInfo> depositCompleteAddressList= wallet.getDepositCompleteAddressList();
        logger.info("Total address to mix:"+depositCompleteAddressList.size());
        for (String address : depositCompleteAddressList.keySet()) {
            AddressInfo addressInfo = depositCompleteAddressList.get(address);
            moveToMixerAddress(address,addressInfo);
            stageTwoMixer();
        }
    }

    /**
     * Move the coin based on a mixer speed
     * @param address
     * @param addressInfo
     */
    private void moveToMixerAddress(String address, AddressInfo addressInfo) {
        double balance = addressInfo.getBalance();
        int evenRandomNum = wallet.getRandom(5,1)*2*mixerSpeed;
        logger.info("Random Number for this run: "+evenRandomNum);
        //M2. Get fresh mixer address and fill move coins evenly
        LinkedList<String> freshMixerAddress = wallet.getFreshMixerAddress(evenRandomNum);
        for (String mixerAddress:freshMixerAddress) {
            myRestClient.sendCoin(houseAddress,mixerAddress,balance/evenRandomNum);
            wallet.addFilledMixerAddress(mixerAddress);
        }
    }
    private void stageTwoMixer() {
        //for future: M3. Mix the coins again, to make the algo more complex

    }

    /**
     * Initiate the withdrawal to the clients addresses
     */
    public void doClientWithdrawal()
    {
        ConcurrentHashMap<String, AddressInfo> depositCompleteAddressList= wallet.getDepositCompleteAddressList();
        logger.info("Withdrawal for clients:"+depositCompleteAddressList.size());
        // Iterate over the clients deposit address
        for (String address : depositCompleteAddressList.keySet()) {
            AddressInfo addressInfo = depositCompleteAddressList.get(address);
            String[] withdrawalAddresses = parseAddresses(addressInfo.getClientAddresses());
            ArrayList mixerAddresses = getMixerAddressForWithdrawl(addressInfo.getBalance());
            //Withdraw from xier address to client's address
            withdraw(withdrawalAddresses,mixerAddresses,addressInfo.getBalance());
            wallet.getWithdrawalCompleteAddress().push(address);
            wallet.getDepositCompleteAddressList().remove(address);
        }
    }

    /**
     * Moves coin from mixer addresses to client's address
     * @param withdrawalAddresses
     * @param mixerAddresses
     * @param amount
     */
    private void withdraw( String[] withdrawalAddresses,ArrayList<String> mixerAddresses,double amount) {
        double amountToTransfer= amount;
        String gatewayAddress=wallet.getGatewayAddress();
        logger.info("Mixer address to withdraw from:"+mixerAddresses.size());
        for (String mixerAddress:mixerAddresses) {
            logger.info("Mixer address:"+mixerAddress);
            AddressInfo mixerAddressInfo = myRestClient.getAddressInfo(mixerAddress);
            if(mixerAddressInfo.getBalance()>0)
            {
                if(amountToTransfer>mixerAddressInfo.getBalance()) {
                    myRestClient.sendCoin(mixerAddress,gatewayAddress,mixerAddressInfo.getBalance());
                    amountToTransfer -= mixerAddressInfo.getBalance();
                } else {
                    myRestClient.sendCoin(mixerAddress,gatewayAddress,amountToTransfer);
                    amountToTransfer-=amountToTransfer;
                    wallet.addFilledMixerAddress(mixerAddress);
                }
            }

        }
        AddressInfo gatewayAddressInfo = myRestClient.getAddressInfo(gatewayAddress);
        logger.info("Gateway balance:"+gatewayAddressInfo.getBalance());
        //Move to client's withdrawal address
        for (int i = 0; i < withdrawalAddresses.length; i++) {
            myRestClient.sendCoin(gatewayAddress,withdrawalAddresses[i],amount/withdrawalAddresses.length);
        }
    }

    /**
     * Get the eligible mixer address for withdrawal
     * @param amount
     * @return
     */
    private ArrayList<String> getMixerAddressForWithdrawl( double amount) {
        double transferAmount = amount;
        ArrayList withdrawMixerAddresses = new ArrayList<String>();
        while (transferAmount>0){
            String mixerAddress = wallet.getFilledMixerAddressList().getFirst();
            AddressInfo mixerAddressInfo = myRestClient.getAddressInfo(mixerAddress);
            withdrawMixerAddresses.add(mixerAddress);
            wallet.getFilledMixerAddressList().pop();
            transferAmount -= mixerAddressInfo.getBalance();
        }
        return withdrawMixerAddresses;
    }

    private String[] parseAddresses(String addresses) {
        return addresses.split(",");
    }

}
