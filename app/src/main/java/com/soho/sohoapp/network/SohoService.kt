package com.soho.sohoapp.network

import com.soho.sohoapp.data.dtos.BasicPropertyResult
import com.soho.sohoapp.data.dtos.PropertyListingResult
import com.soho.sohoapp.data.dtos.PropertyResult
import com.soho.sohoapp.data.dtos.UserResult
import com.soho.sohoapp.feature.home.more.model.AccountVerification
import com.soho.sohoapp.feature.marketplaceview.feature.filterview.fitlermodel.FilterCheckboxItem
import com.soho.sohoapp.network.results.PortfolioCategoryResult
import com.soho.sohoapp.network.results.PortfolioPropertyResult
import com.soho.sohoapp.network.results.PropertyTypesResult
import com.soho.sohoapp.network.results.PropertyUserRolesResult
import com.soho.sohoapp.utils.QueryHashMap
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by chowii on 25/7/17.
 */
interface SohoService {

    // MARK: - ================== User Related ==================

    @POST("users")
    fun register(@Body map: Map<String, String>): Observable<UserResult>

    @POST("facebook")
    fun loginWithFb(@Query("access_token") fbToken: String): Observable<UserResult>

    @POST("google")
    fun loginWithGoogle(@Query("access_token") fbToken: String): Observable<UserResult>

    @POST("sessions")
    fun loginUser(@Body map: Map<String, String>): Observable<UserResult>

    @PUT("profile")
    fun updateUserProfile(@Body map: Map<String, String>): Observable<UserResult>

    @GET("profile")
    fun getProfile(): Observable<UserResult>

    // MARK: - ================== Property Related ==================

    @POST("search/properties")
    fun searchProperties(@Body map: java.util.Map<String, Object>): Observable<java.util.List<BasicPropertyResult>>

    @POST("properties")
    fun createProperty(@QueryMap map: QueryHashMap): Observable<PropertyResult>

    @PUT("properties/{id}/")
    fun sendPropertyPhoto(@Path("id") id: Long, @Body file: RequestBody): Observable<ResponseBody>

    @GET("properties/{id}/")
    fun getProperty(@Path("id") id: Long): Observable<PropertyResult>

    @PUT("properties/{id}/")
    fun updateProperty(@Path("id") id: Long, @Body map: QueryHashMap): Observable<PropertyResult>

    @PUT("properties/{id}/property_listing")
    fun updatePropertyListing(@Path("id") propertyId: Long, @Body map: QueryHashMap): Observable<PropertyListingResult>

    @GET("options/property_user_roles")
    fun getPropertyUserRoles(): Observable<List<PropertyUserRolesResult>>

    @GET("options/property_types")
    fun getPropertyTypes(): Observable<List<PropertyTypesResult>>

    // MARK: - ================== Portfolio Related ==================

    @GET("portfolios")
    fun getPortfolios(@Query("portfolio_type") portfolioType: String,
                      @Query("user_id") userId: Int): Observable<List<PortfolioPropertyResult>>

    @GET("portfolios/owned")
    fun getOwnedPortfolios(): Observable<List<PortfolioCategoryResult>>

    @GET("portfolios/managed")
    fun getManagedPortfolios(): Observable<List<PortfolioCategoryResult>>

    // MARK: - ================== Verification Related ==================

    @GET("verifications")
    fun retrieveVerificationList(): Observable<List<AccountVerification>>

    @PUT("verifications/mobile")
    fun verifyPhoneNumber(@Body mapOf: HashMap<String, Any>): Observable<AccountVerification>

    @PUT("verifications/agent_licence")
    fun verifyAgentLicenseNumber(@Body map: HashMap<String, Any>): Observable<AccountVerification>
}


