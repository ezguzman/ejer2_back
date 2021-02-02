package com.ejercicio2.apis.services.impl;

import com.ejercicio2.apis.services.MercadoService;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Identification;
import com.mercadopago.resources.datastructures.payment.Payer;
import com.mercadopago.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MercadoServiceImp implements MercadoService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object getCuota(String publicKey, String paymentMethodId, String issuer, Float amount, Integer bin) {

       //validar parametros aca
        Map <String, String> map = new HashMap<>();
        StringBuilder paramsBuilder= new StringBuilder();
        paramsBuilder.append("?public_key=" +publicKey);
        paramsBuilder.append("&payment_method_id="+paymentMethodId);
        paramsBuilder.append("&issuer_id="+issuer);
        paramsBuilder.append("&amount="+amount.toString());
        paramsBuilder.append("&bin="+bin.toString());
        Object cuotas = restTemplate.getForObject("https://api.mercadopago.com/v1/payment_methods/installments" + paramsBuilder.toString(),Object.class,map);
        return  cuotas;
    }

    @Override
    public String getStatusPayment(String token, String transaction_amount, String installments, String payment_method_id, String email) {

        try {
            MercadoPago.SDK.setAccessToken("TEST-8212990517475895-012818-562b4dc423a7f4cd0ed29efc42b11ec6-701057800");
        } catch (MPConfException e) {
            e.printStackTrace();
        }
        Payment payment = new Payment();
        payment.setTransactionAmount(Float.valueOf(transaction_amount))
                .setToken(token)
                .setDescription("pago de prueba")
                .setInstallments(Integer.valueOf(installments))
                .setPaymentMethodId(payment_method_id);

        Identification identification = new Identification();
        identification.setType("DNI")
                .setNumber("33547862");
        Payer payer = new Payer();
        payer.setFirstName("APRO");
        payer.setLastName("APRO");

        payer.setEmail("test_user_92856259@testuser.com")
                .setIdentification(identification);

        payment.setPayer(payer);

        try {
            payment.save();
        } catch (MPException e) {
            e.printStackTrace();
        }
        if(payment.getStatus()== null){
            System.out.println(payment.getLastApiResponse().getStringResponse());
            return payment.getLastApiResponse().getStringResponse();

        }
        else{
            System.out.println(payment.getStatus());
            return payment.getStatus().toString();
        }

    }

}
