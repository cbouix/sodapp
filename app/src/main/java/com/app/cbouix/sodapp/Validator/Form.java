package com.app.cbouix.sodapp.Validator;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Form {

    private List<AbstractValidate> mValidates =  new ArrayList<AbstractValidate>();

    /**
     * Metodo para agregar las validaciones del formulario
     * @param validate
     *  {@link AbstractValidate} Validate to add
     */
    public void addValidates (AbstractValidate validate){
        mValidates.add(validate);
    }

    /**
     * Metodo para remover las validaciones del formulario
     * @param validate
     *   {@link AbstractValidate} validación a remover
     * @return valida que fue retirado del formulario
     */
    public boolean removeValidates(AbstractValidate validate){
        if (mValidates != null && !mValidates.isEmpty()){
            mValidates.remove(validate);
        }
        return false;
    }

    /**
     * llama a la validación del formulario
     * si el error fue encontrado, lo mostrara en el campo correspondiente
     * @return verdadero si el formulario es valido, sino, falso
     */
    public boolean validate(){
        boolean formValid = true;
        for (AbstractValidate validate : mValidates){
            formValid = formValid & validate.isValid();
        }
        return formValid;
    }

    /**
     * cierra el error surgido de el TextView
     * solo llama al source.serError(null)
     * @param sourceTextView
     */
    public void closeError(TextView sourceTextView) {
        for (AbstractValidate av : mValidates) {
            Validate v = (Validate) av;
            if (v.getmSource().equals(sourceTextView))
                v.getmSource().setError(null);
        }
    }

    /**
     * cierra todas las ventanas emergentes creadas por el validator
     */
    public void closeAllErrors() {
        for (AbstractValidate av : mValidates) {
            Validate v = (Validate) av;
            v.getmSource().setError(null);
        }
    }
}
