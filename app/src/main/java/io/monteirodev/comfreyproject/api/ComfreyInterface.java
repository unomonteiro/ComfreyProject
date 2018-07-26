package io.monteirodev.comfreyproject.api;

import io.monteirodev.comfreyproject.data.ComfreyData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ComfreyInterface {
    @GET("/api/jsonBlob/c4a143b1-852f-11e8-9013-cd715956ac11")
    Call<ComfreyData> getData();
}
