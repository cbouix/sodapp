package com.app.cbouix.sodapp.Validator.Validators;

import android.content.Context;
import android.text.TextUtils;

import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.Validator.AbstractValidator;

public class ObligatoryFieldValidator extends AbstractValidator {

public static final int DEFAULT_ERROR_MESSAGE_RESOURCE = R.string.msj_campo_obligatorio;

    public ObligatoryFieldValidator(Context c){
        super(c,DEFAULT_ERROR_MESSAGE_RESOURCE);
    }

    public ObligatoryFieldValidator(Context c, int errorMessage) {
        super(c, errorMessage);
    }


    @Override
    public boolean isValid(String text){
        return !TextUtils.isEmpty(text);
    }

}
