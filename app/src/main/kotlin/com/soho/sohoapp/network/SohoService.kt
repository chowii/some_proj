package com.soho.sohoapp.network

import com.soho.sohoapp.dev.feature.User
import com.soho.sohoapp.feature.marketplace.model.SohoProperty
import com.soho.sohoapp.network.results.PropertyTypesResult
import com.soho.sohoapp.network.results.PropertyUserRolesResult
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * Created by chowii on 25/7/17.
 */
interface SohoService {

    @POST("users")
    fun register(@Body map: Map<String, String>): Observable<User>

    @POST("search/properties")
    fun searchProperties(@QueryMap map: java.util.Map<String, Object>): Observable<java.util.List<SohoProperty>>

    @GET("options/property_user_roles")
    fun getPropertyUserRoles(): Observable<List<PropertyUserRolesResult>>

    @GET("options/property_types")
    fun getPropertyTypes(): Observable<List<PropertyTypesResult>>

}


