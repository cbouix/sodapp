package com.app.cbouix.sodapp.Validator;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Validate extends AbstractValidate {

    private List<AbstractValidator> mValidator = new ArrayList<>();
    private TextView mSourceView;

    public Validate(TextView sourceView){
        mSourceView = sourceView;
    }

    /**
     * añade un nuevo validator para campos adjuntos
     * @param validator {@link AbstractValidator} : validator a adjuntar
     */
    public void addValidator(AbstractValidator validator){
        mValidator.add(validator);
    }

    @Override
    public boolean isValid() {
        for (AbstractValidator validator : mValidator)
        try {
            if (!validator.isValid(mSourceView.getText().toString())){
                mSourceView.setError(validator.getMessage());
                return false;
            }
        } catch (ValidatorExeption validatorExeption) {
            validatorExeption.printStackTrace();
            mSourceView.setError(validatorExeption.getMessage());
            return false;
        }
        mSourceView.setError(null);
        return true;
    }


    public TextView getmSource(){
        return mSourceView;
    }
}
