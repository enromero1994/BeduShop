package Retrofit

import DataClass.LoginResponse
import retrofit2.Call
import DataClass.User
import retrofit2.http.*


interface AuthApi {
    @FormUrlEncoded
    @POST("register")
    fun loginUser(@Field("email") first: String, @Field("password") last: String?): Call<LoginResponse>


    @GET("users/{users}")
    fun getUser(@Path("users") userId: Int): Call<User>


}

