package com.soho.sohoapp.network

import com.soho.sohoapp.data.dtos.*
import com.soho.sohoapp.network.results.PortfolioCategoryResult
import com.soho.sohoapp.network.results.PortfolioPropertyResult
import com.soho.sohoapp.network.results.PropertyTypesResult
import com.soho.sohoapp.network.results.PropertyUserRolesResult
import com.soho.sohoapp.utils.QueryHashMap
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by chowii on 25/7/17.
 */
interface SohoService {

    // MARK: - ================== User Related ==================

    @POST("users")
    fun register(@Body map: Map<String, String>): Observable<UserResult>

    @PUT("profile/update_password")
    fun updatePassword(@Body map: QueryHashMap): Observable<Unit>

    @POST("facebook")
    fun loginWithFb(@Query("access_token") fbToken: String): Observable<UserResult>

    @POST("google")
    fun loginWithGoogle(@Query("access_token") fbToken: String): Observable<UserResult>

    @POST("sessions")
    fun loginUser(@Body map: Map<String, String>): Observable<UserResult>

    @PUT("profile")
    fun updateUserProfile(@Body map: QueryHashMap): Observable<UserResult>

    @PUT("profile")
    fun updateUserProfileWithPhoto(@Body requestBody: RequestBody): Observable<UserResult>

    @GET("profile")
    fun getProfile(): Observable<UserResult>

    @GET("passwords/new")
    fun sendForgotPasswordRequest(@Query("email") email: String): Observable<Unit>

    // MARK: - ================== Property Related ==================

    @POST("search/properties")
    fun searchProperties(@Body map: QueryHashMap): Observable<Response<List<BasicPropertyResult>>>

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

    @POST("properties/{id}/property_listing/inspection_times")
    fun createInspectionTime(@Path("id") propertyId: Int, @Body map: QueryHashMap): Observable<InspectionTimeResult>

    @DELETE("properties/{property_id}/property_listing/inspection_times/{id}")
    fun deleteInspectionTime(@Path("property_id") propertyId: Int, @Path("id") inspectionTimeId: Int): Observable<InspectionTimeResult>

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
    fun retrieveVerificationList(): Observable<List<VerificationResult>>

    @PUT("verifications/licence")
    fun verifyPhotoId(@Body file: RequestBody): Observable<VerificationResult>

    @PUT("verifications/mobile")
    fun verifyPhoneNumber(@Body mapOf: HashMap<String, Any>): Observable<VerificationResult>

    @POST("verifications/mobile")
    fun verifyPin(@Body map: QueryHashMap): Observable<VerificationResult>

    @PUT("verifications/agent_licence")
    fun verifyAgentLicenseNumber(@Body map: HashMap<String, Any>): Observable<VerificationResult>

    @PUT("verifications/property")
    fun sendPropertyVerificationAttachments(@Body file: RequestBody): Observable<ResponseBody>
}


