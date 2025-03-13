package com.example.serenosviagens.database.api;

import com.example.serenosviagens.database.Models.Resposta;
import com.example.serenosviagens.database.Models.Resultado;
import com.example.serenosviagens.database.Models.ViagemModel;
import com.example.serenosviagens.database.api.endpoint.ResultadoEndPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static final String URL_ROOT = System.getenv("URL_ROOT_SERENOS_VIAGENS");

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void getResultado(final Callback<ArrayList<ViagemModel>> callback) {
        ResultadoEndPoint endPoint = retrofit.create(ResultadoEndPoint.class);
        Call<ArrayList<ViagemModel>> call = endPoint.getAllViagens();
        call.enqueue(callback);
    }

    public static void postResultado(Resultado resultado, final Callback<Resposta> callback) {
        ResultadoEndPoint endPoint = retrofit.create(ResultadoEndPoint.class);
        Call<Resposta> call = endPoint.postResultado(resultado);
        call.enqueue(callback);
    }
}