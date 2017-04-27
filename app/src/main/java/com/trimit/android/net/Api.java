package com.trimit.android.net;

import com.trimit.android.model.EmailExistsResponce;
import com.trimit.android.model.Responce;
import com.trimit.android.model.ResponceBarberType;
import com.trimit.android.model.barber.BarberResponce;
import com.trimit.android.model.createuser.ResponceCreateUser;
import com.trimit.android.model.getuser.ResponceGetUser;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by artem on 4/19/17.
 */

interface Api {

    @GET("barber")
    Observable<BarberResponce> getBarbers(@Query("access_token") String token);

    @GET("barber/{barber_id}")
    Observable<BarberResponce> getBarber(@Path("barber_id") String barberId, @Query("access_token") String token);

    @POST("barber")
    Observable<BarberResponce> findBarbers(@Query("access_token") String token,  @Field("data")  String data);


    @GET("barber_type")
    Observable<ResponceBarberType> getBarberTypes(@Query("access_token") String token);

    @GET("user/{user_id}")
    Observable<ResponceGetUser> getUser(@Path("user_id") String userId, @Query("access_token") String token);

    @FormUrlEncoded
    @POST("user")
    Observable<ResponceCreateUser> createUser(@Query("access_token") String token, @Field("data")  String data);

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
