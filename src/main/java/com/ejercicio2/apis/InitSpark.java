package com.ejercicio2.apis;

import com.ejercicio2.apis.controllers.SparkController;
import com.ejercicio2.apis.exceptions.InvalidAmount;
import com.ejercicio2.apis.exceptions.InvalidInstallments;
import com.ejercicio2.apis.exceptions.InvalidPaymentMethod;
import com.ejercicio2.apis.exceptions.Validations;
import com.google.gson.Gson;
import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class InitSpark implements SparkApplication {
    @Override
    public void init() {


        get("/spark/cuotas/:public_key/:payment_method_id/:issuer_id/:amount/:bin",(req, res)->{
            res.type("application/json");
            Validations validations = new Validations();
            if(!validations.paymentMethodIsValid(req.params(":payment_method_id").toString()))
            {
                throw new InvalidPaymentMethod("payment_method_id  no es valido:" + req.params(":payment_method_id").toString());
            }

            if(!validations.amountIsValid(req.params(":amount").toString()))
            {
                throw new InvalidAmount("el monto no es valido :" + req.params(":amount").toString());
            }

            SparkController sparkController = new SparkController();
            Object objeto = sparkController.getCuotas(
                    req.params(":public_key").toString(),
                    req.params(":payment_method_id").toString(),
                    req.params(":issuer_id").toString(),
                    Float.parseFloat(req.params(":amount").toString()),
                    Integer.parseInt(req.params(":bin"))
            );
            return new Gson().toJson(objeto);
        });

        get("/spark/pay/:token/:transaction_amount/:installments/:payment_method_id/:email",(req, res)->{
            res.type("application/json");

            SparkController sparkController = new SparkController();

            String statusPayment = sparkController.payProduct(req.params(":token").toString(),
                    req.params(":transaction_amount").toString(),
                    req.params(":installments").toString(),
                    req.params(":payment_method_id").toString(),
                    req.params(":email").toString());

            return new Gson().toJson(statusPayment);

        });


    }
}
