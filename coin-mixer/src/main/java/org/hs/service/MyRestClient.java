package org.hs.service;

import org.apache.log4j.Logger;
import org.hs.service.dto.AddressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MyRestClient {
    @Autowired
    private RestTemplate restTemplate;
    private Logger logger = Logger.getLogger(MyRestClient.class);
    @Value("${myapp.url}")
    private String ROOT_URI;
    @Value("${myapp.house-address}")
    private String houseAddress;

    public AddressInfo getAddressInfo(String address) {
        String url = ROOT_URI + "/addresses/"+address;
        logger.info("url: "+url);
        ResponseEntity<AddressInfo> response = restTemplate.getForEntity(url, AddressInfo.class);
        logger.info("Balance: "+response.getBody().getBalance());
        return response.getBody();
    }

    public String sendCoinToHouse(String address, Double amount) {
        String url = ROOT_URI + "/transactions";
        logger.info("url: "+url);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("fromAddress", address);
        map.add("toAddress", houseAddress);
        map.add("amount", amount.toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );

        logger.info("Balance: "+response.getBody());
        return response.getBody();
    }

    public String sendCoin(String fromAddress,String toAddress, Double amount) {
        String url = ROOT_URI + "/transactions";



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("fromAddress", fromAddress);
        map.add("toAddress", toAddress);
        map.add("amount", amount.toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        logger.info("url: "+url+" fromAddress: "+fromAddress+" toAddress: "+toAddress+" amount: "+amount);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );

        logger.info("Balance: "+response.getBody());
        return response.getBody();
    }

}
