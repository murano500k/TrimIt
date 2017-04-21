package com.trimit.android.utils.net;

import com.trimit.android.model.EmailExistsResponce;
import com.trimit.android.model.Responce;
import com.trimit.android.model.ResponceUserCreate;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by artem on 4/19/17.
 */

public interface Api {

    @FormUrlEncoded
    @POST("user")
    Observable<ResponceUserCreate> createUser(@Query("access_token") String token, @Field("data")  String data);

    @FormUrlEncoded
    @POST("account/email_exists")
    Observable<EmailExistsResponce> checkEmailRx(@Query("access_token") String token, @Field("data") String data);


    @FormUrlEncoded
    @POST("account/reset_password")
    Observable<Responce> forgotPassword(@Query("access_token") String token, @Field("data") String data);

    @FormUrlEncoded
    @POST("login")
    Observable<Responce> login(@Query("access_token") String token, @Field("data") String data);

}
