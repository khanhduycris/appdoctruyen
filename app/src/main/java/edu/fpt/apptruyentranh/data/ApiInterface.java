package edu.fpt.apptruyentranh.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("thongTinTruyenTranh")
    Call<List<TruyenTranh>> getAllTruyenTranh();
}
