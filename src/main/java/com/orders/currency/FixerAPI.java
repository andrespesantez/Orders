package com.orders.currency;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orders.enums.MonedaEnum;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FixerAPI {

    public static final String URL_BASE = "http://data.fixer.io/api/latest";
    public static final String API_KEY = "API_KEY";
    public static final String BASE = MonedaEnum.USD.getDesc();

    Double valor = 0.0;

    public Double requestAPI(MonedaEnum moneda) throws Exception{
        StringBuilder _URL = new StringBuilder();
        _URL.append(URL_BASE);
        _URL.append("?");
        _URL.append("access_key=").append(API_KEY).append("&");
        //_URL.append("base=").append(BASE).append("&"); // solo accesible con plan
        _URL.append("symbols=").append(MonedaEnum.USD.getDesc());
        _URL.append(",").append(MonedaEnum.EUR.getDesc());
        _URL.append(",").append(MonedaEnum.CHF.getDesc());
        _URL.append(",").append(MonedaEnum.GBP.getDesc());
        URL url = new URL(_URL.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.flush();
        out.close();

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
        if(jsonObject.get("success").getAsBoolean()) {
            jsonObject.get("base").getAsString();
            this.valor =  jsonObject.get("rates").getAsJsonObject().get(moneda.getDesc()).getAsDouble();
        } else {
            this.valor = null;
        }

        return valor;
    }



    public static void main(String args []) throws Exception {
        FixerAPI api = new FixerAPI();
        api.requestAPI(MonedaEnum.USD);
    }
}
