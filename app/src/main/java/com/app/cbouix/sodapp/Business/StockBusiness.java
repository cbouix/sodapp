package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.StockRepartidor;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Services.UtilsServices;
import com.app.cbouix.sodapp.Utils.DateTimeUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bouix on 28/3/2018.
 */

public class StockBusiness {
    private static final String GET_STOCK = "getStockRepartidor/%s,%s";

    public static ArrayList<StockRepartidor> getStockByRepartidor(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + GET_STOCK,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""),
                DateTimeUtil.getDateNowString());

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String visitasStr= gson.fromJson(reader, String.class);
        StockRepartidor[] stock = new Gson().fromJson(visitasStr, StockRepartidor[].class);

        return new ArrayList<StockRepartidor>(Arrays.asList(stock));
    }
}
