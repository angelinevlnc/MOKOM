package com.proyekmokom.chastethrift.Network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part("tipe") tipe: RequestBody,
        @Part("title") title: RequestBody,
        @Part files: MultipartBody.Part
    ): Response<UploadResponse>

    @Multipart
    @POST("upload")
    fun uploadImageBasic(
        @Part("tipe") tipe: RequestBody,
        @Part("title") title: RequestBody,
        @Part files: MultipartBody.Part
    ): Call<UploadResponse>
}