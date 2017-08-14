package com.soho.sohoapp.network

import com.soho.sohoapp.dev.feature.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by chowii on 25/7/17.
 */
interface SohoService {

    @POST("users")
    fun register(@Body map: Map<String, String>): Observable<User>

}