package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.Models.Articulo;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Services.UtilsServices;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CBouix on 21/04/2017.
 */

public class ArticuloBusiness {

    private static final String ARTICULOS = "getArticulos/%s,%s";

    public static ArrayList<Articulo> getArticulos(Context context, int clienteId, String clienteCod) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + ARTICULOS, clienteId, clienteCod);
        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String articulosStr= gson.fromJson(reader, String.class);

        Articulo[] articulos = new Gson().fromJson(articulosStr, Articulo[].class);

        return new ArrayList<Articulo>(Arrays.asList(articulos));
    }
}
