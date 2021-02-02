package com.ejercicio2.apis.controllers;

import com.ejercicio2.apis.exceptions.InvalidAmount;
import com.ejercicio2.apis.exceptions.InvalidInstallments;
import com.ejercicio2.apis.exceptions.InvalidPaymentMethod;
import com.ejercicio2.apis.exceptions.Validations;
import com.ejercicio2.apis.services.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins ={"http://localhost:3000","*"}  )
@RequestMapping("spring")
public class MercadoController {

    @Autowired
    MercadoService mercadoService;

    @GetMapping("/cuotas")
    @ResponseBody
    public  Object getCuotas(@RequestParam("public_key") String public_key ,
                             @RequestParam("payment_method_id") String paymentMethodId,
                             @RequestParam("issuer_id") String issuer,
                             @RequestParam("amount") Float amount,
                             @RequestParam("bin") Integer bin){
        return mercadoService.getCuota(public_key,paymentMethodId,issuer,amount,bin);
    }
    @GetMapping("/process_payment")
    @ResponseBody
    public Object payProduct(@RequestParam("token") String token ,
                             @RequestParam("transaction_amount") String transaction_amount,
                             @RequestParam("installments") String installments,
                             @RequestParam("payment_method_id") String payment_method_id,
                             @RequestParam("email") String email){

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


        return mercadoService.getStatusPayment(token,transaction_amount,installments,payment_method_id,email);


    }

}
