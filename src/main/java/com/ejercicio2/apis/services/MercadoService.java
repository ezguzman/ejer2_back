package com.ejercicio2.apis.services;

public interface MercadoService {

    public Object getCuota(String publicKey, String paymentMethodId, String issuer, Float amount, Integer bin);

    public  String getStatusPayment(String token,String transaction_amount,String installments,String payment_method_id,String email);
}
