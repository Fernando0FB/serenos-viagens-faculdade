package com.example.serenosviagens.database.api.endpoint;

import com.example.serenosviagens.database.Models.Resposta;
import com.example.serenosviagens.database.Models.Resultado;
import com.example.serenosviagens.database.Models.ViagemModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ResultadoEndPoint {

    @GET("/api/listar/viagem")
    Call<ArrayList<ViagemModel>> getAllViagens();

    @GET("/api/listar/viagem")
    Call<ArrayList<ViagemModel>> getViagem();

    @POST("/api/cadastro/viagem")
    Call<Resposta> postResultado(@Body Resultado viagem);

}
