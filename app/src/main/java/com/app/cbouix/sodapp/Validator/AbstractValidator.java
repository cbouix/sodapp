package com.app.cbouix.sodapp.Validator;

import android.content.Context;

import java.text.ParseException;

public abstract class AbstractValidator {

    private Context mContext;
    private int mErrorMessageRes;
    private String mErrorMessageString;


    public AbstractValidator(Context c, int errorMessageRes) {
        this.mContext = c;
        this.mErrorMessageRes = errorMessageRes;
        this.mErrorMessageString = mContext.getString(mErrorMessageRes);
    }

    public AbstractValidator(Context c, String ErrorMessageString){
        this.mContext = c;
        this.mErrorMessageString = ErrorMessageString;
    }

    /**
     *Verifica si el valor pasado en el parametro es valido o no
     *
     * @param value : valor a validar
     * @return boolean : verdad si es valido, falso de lo contrario
     * @throws ValidatorExeption
     */
    public abstract boolean isValid (String value)
            throws ValidatorExeption, ValidatorExeption;



    // GETTER AND SETTER
    /**
     * se utiliza para recuperar el mensaje de error correspondiente al validator
     * @return
     */
    public String getMessage(){
        return mErrorMessageString;
    }

    /**
     * establecer el context del validator
     * @param c
     */
    public void setContext(Context c){
        this.mContext = c;
    }

    /**
     * Obtener el context del TextView/s
     * @return
     */
    public Context getmContext(){
        return mContext;
    }

}
