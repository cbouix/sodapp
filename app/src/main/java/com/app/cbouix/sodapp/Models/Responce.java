package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 10/05/2017.
 */

public class Responce {
    @SerializedName("Id")
    private int id;
    @SerializedName("Responce")
    private String error;
    @SerializedName("Error")
    private String msj;

    public int getId() {
        return id;
    }

    public String getError() {
        if(this.error == null)
            return this.msj;
        else
            return this.error;
    }
    public String getMsj() {
        return msj;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean hasError(){
        return this.id == 0 || String.valueOf(this.id) == null;
    }
}
