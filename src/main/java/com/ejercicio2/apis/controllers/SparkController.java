package com.ejercicio2.apis.controllers;

import com.ejercicio2.apis.exceptions.InvalidAmount;
import com.ejercicio2.apis.exceptions.InvalidInstallments;
import com.ejercicio2.apis.exceptions.InvalidPaymentMethod;
import com.ejercicio2.apis.exceptions.Validations;
import com.ejercicio2.apis.services.MercadoService;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Identification;
import com.mercadopago.resources.datastructures.payment.Payer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class SparkController {

    RestTemplate restTemplate = new RestTemplate();

    public  Object getCuotas(String publicKey ,String paymentMethodId, String issuer, Float amount, Integer bin){

        Map<String, String> map = new HashMap<>();
        StringBuilder paramsBuilder= new StringBuilder();
        paramsBuilder.append("?public_key=" +publicKey);
        paramsBuilder.append("&payment_method_id="+paymentMethodId);
        paramsBuilder.append("&issuer_id="+issuer);
        paramsBuilder.append("&amount="+amount.toString());
        paramsBuilder.append("&bin="+bin.toString());
        Object cuotas = restTemplate.getForObject("https://api.mercadopago.com/v1/payment_methods/installments" + paramsBuilder.toString(),Object.class,map);
        return  cuotas;
    }

    public String payProduct( String token ,
                              String transaction_amount,
                              String installments,
                              String payment_method_id,
                              String email){

        Validations validations = new Validations();
        if(!validations.paymentMethodIsValid(payment_method_id))
        {
            throw new InvalidPaymentMethod("payment_method_id  no es valido:" + payment_method_id);
        }

        if(!validations.amountIsValid(transaction_amount))
        {
            throw new InvalidAmount("el monto no es valido :" + transaction_amount);
        }
        if(!validations.amountIsValid(installments))
        {
            throw new InvalidInstallments("el numero de cuota es invalido: " + installments);
        }

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
