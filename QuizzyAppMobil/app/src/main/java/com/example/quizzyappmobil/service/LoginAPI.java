package com.example.quizzyappmobil.service;

import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPI {
    private static LoginService mAPI = null;

    public static synchronized LoginService getAPI() {
        if (mAPI == null) {
            OkHttpClient client = getUnsafeOkHttpClient();

            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/") // Usa 10.0.2.2 para emuladores
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .client(client)
                    .build();

            mAPI = retrofit.create(LoginService.class);
        }
        return mAPI;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier((hostname, session) -> true);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);

        return builder.build();
    }
}
