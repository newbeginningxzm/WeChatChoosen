package com.bupt.paragon.wechatchoosen.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Paragon on 2016/5/27.
 */
public interface IPageBiz {
    @GET("query")
    Call<Response> getNews(@Query("pno") int page,@Query("ps") int pageCount,@Query("dtype") String dataType,@Query("key") String key);
}
