package com.example.adegadevinho;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface VinhoApi {

    @GET("vinhos")
    Call<List<Vinho>> getVinhos();

    @POST("vinhos")
    Call<Vinho> addVinho(@Body Vinho vinho);

    @GET("vinhos/{id}")
    Call<Vinho> getVinho(@Path("id") Long id);

    @PUT("vinhos/{id}")
    Call<Vinho> updateVinho(@Path("id") Long id, @Body Vinho vinho);

    @DELETE("vinhos/{id}")
    Call<Void> deleteVinho(@Path("id") Long id);
}
