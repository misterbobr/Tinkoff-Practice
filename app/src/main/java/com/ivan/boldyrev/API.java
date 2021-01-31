package com.ivan.boldyrev;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    @GET("{tab}/{page}?json=true")
    Call<Result> getPost(
            @Path("tab") String tab, @Path("page") int page
    );
}
