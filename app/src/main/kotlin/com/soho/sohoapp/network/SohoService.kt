package com.soho.sohoapp.network

import com.soho.sohoapp.data.PropertyDetail
import com.soho.sohoapp.data.SohoProperty
import com.soho.sohoapp.feature.User
import com.soho.sohoapp.feature.home.more.model.AccountVerification
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.FilterCheckboxItem
import com.soho.sohoapp.network.results.*
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
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

    @GET("options/property_types")
    fun getPropertyTypesForFilter(): Observable<List<FilterCheckboxItem>>

    @PUT("properties/{id}/")
    fun sendPropertyPhoto(@Path("id") id: Long, @Body file: RequestBody): Observable<ResponseBody>

    @GET("properties/{id}/")
    fun getProperty(@Path("id") id: Long): Observable<PropertyResult>

    @GET("verifications")
    fun retrieveVerificationList(): Observable<List<AccountVerification>>

    @GET("properties/{id}")
    fun getPropertyById(@Path("id") id: Int): Observable<PropertyDetail>

    @PUT("verifications/mobile")
    fun verifyPhoneNumber(@Body mapOf: HashMap<String, Any>): Observable<AccountVerification>

    @PUT("verifications/agent_licence")
    fun verifyAgentLicenseNumber(@Body map: HashMap<String, Any>): Observable<AccountVerification>
}


