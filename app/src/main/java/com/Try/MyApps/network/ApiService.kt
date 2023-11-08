package com.Try.MyApps.network

import com.Try.MyApps.network.GithubUser
import com.Try.MyApps.model.ResponseUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //get list users with query
    @GET("users")
    fun getListUsers(@Query("page") page: String): Call<ResponseUser>
    @GET("users")
    suspend fun getUsers(): List<GithubUser>

    //get list user by id using path
    @GET("users/{id}")
    fun getUser(@Path("id") id: String): Call<ResponseUser>

    //post user using field x-www-form-urlencoded
    @FormUrlEncoded
    @POST("users")
    fun createUser(
        @Field("name") name: String,
        @Field("job") job: String
    ): Call<ResponseUser>

    //upload file using multipart
    @Multipart
    @PUT("api/uploadfile")
    fun updateUser(
        @Part("file") file: MultipartBody.Part,
        @PartMap data: Map<String, RequestBody>
    ): Call<ResponseUser>
}
