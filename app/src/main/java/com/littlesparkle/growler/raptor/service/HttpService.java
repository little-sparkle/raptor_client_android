package com.littlesparkle.growler.raptor.service;


import com.littlesparkle.growler.raptor.entity.PositionEntity;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;


/**
 * Created by dell on 2016/7/10.
 */
public interface HttpService {


    //上传位置
    @POST("/location/report")
    @FormUrlEncoded
    Observable reportLocation(@Field("user_id") int id, @Field("token") String token,
                              @Field("latitude") double latitude, @Field("longitude") double longitude);

    //检测注册手机号码
    @POST("/user/mobile_check")
    @FormUrlEncoded
    Observable mobileCheck(@Field("mobile") String mobile);

    //请求验证码
    @POST("/user/signup_send_sms")
    @FormUrlEncoded
    Observable sendSms(@Field("mobile") String mobile);

    //登录
    @POST("/user/signin")
    @FormUrlEncoded
    Observable login(@Field("mobile") String mobile, @Field("password") String password);

    //登出
    @POST
    @FormUrlEncoded
    Observable logout(@Field("user_id") int id, @Field("token") String token);


}
