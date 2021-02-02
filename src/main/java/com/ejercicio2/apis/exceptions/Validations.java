package com.ejercicio2.apis.exceptions;

public class Validations {
    public Boolean paymentMethodIsValid(String methodId){
        if(isNumeric(methodId))
        {
            return false;
        }
        return true;
    }

    public Boolean installmentsIsValid(String installments)
    {
        if(isNumeric(installments))
        {
            return true;
        }
        return false;
    }

    public  Boolean amountIsValid(String transaction_amount){
        if(isNumeric(transaction_amount))
        {
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
