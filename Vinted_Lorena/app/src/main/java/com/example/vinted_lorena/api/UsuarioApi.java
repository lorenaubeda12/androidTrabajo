package com.example.vinted_lorena.api;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.Entity.service.Usuario;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UsuarioApi {
    //Ruta del controller del usuario
    String baseUrl = "api/usuario";
    String baseURL2="api/registro";
    //Llamada al servicio
    //Ruta del método de login
    @FormUrlEncoded
    @POST(baseUrl + "/login")
    Call<GenericResponse<Usuario>> login(@Field("email") String email, @Field("pass") String password);
    @POST(baseURL2)
    Call<GenericResponse<Usuario>> save(@Body Usuario u);
    @GET(baseUrl + "/vendedoresConMasProductos")
    Call<GenericResponse<List<Usuario>>> listarVendedoresConMasProductos();

}
