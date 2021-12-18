package com.tp.sharefile.network.api

import com.tp.sharefile.data.model.FileInfo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface FilesService {

    @Multipart
    @POST("/upload")
    fun upload(@Part file: MultipartBody.Part): Single<FileInfo>

    @GET("/download")
    @Streaming
    fun download(@Query("id") id: String): Observable<Response<ResponseBody>>

//    @GET
//    @Streaming
//    fun download(
//        @Url url: String = "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_30MB.mp4"
//    ): Observable<Response<ResponseBody>>

}