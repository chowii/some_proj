package com.soho.sohoapp.network

import com.soho.sohoapp.data.SohoProperty
import com.soho.sohoapp.feature.User
import com.soho.sohoapp.network.results.PortfolioCategoryResult
import com.soho.sohoapp.network.results.PortfolioPropertyResult
import com.soho.sohoapp.network.results.PropertyTypesResult
import com.soho.sohoapp.network.results.PropertyUserRolesResult
import io.reactivex.Observable
import retrofit2.http.*

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

    @POST("sessions")
    fun loginUser(@Body map: Map<String, String>): Observable<User>

    @PUT("profile")
    fun updateUserProfile(@Body map: Map<String, String>): Observable<User>

    @POST("properties")
    fun createProperty(@QueryMap map: java.util.Map<String, Object>): Observable<SohoProperty>

    @GET("portfolios/owned")
    fun getOwnedPortfolios(): Observable<List<PortfolioCategoryResult>>

    @GET("portfolios/managed")
    fun getManagedPortfolios(): Observable<List<PortfolioCategoryResult>>

    @GET("portfolios")
    fun getPortfolios(@Query("portfolio_type") portfolioType: String,
                      @Query("user_id") userId: Int): Observable<List<PortfolioPropertyResult>>

}


