package com.app.cbouix.sodapp.Validator;

public class ValidatorExeption extends Exception{

    public ValidatorExeption(){
        super();
    }

    public ValidatorExeption(String detailMennsage, Throwable throwable){
        super(detailMennsage,throwable);
    }

    public  ValidatorExeption (String detailMennsage){
        super(detailMennsage);
    }

    public ValidatorExeption(Throwable throwable){
        super(throwable);
    }
}
