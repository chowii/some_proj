package com.soho.sohoapp.dev.network

import android.util.Log
import com.soho.sohoapp.dev.network.SohoError
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by chowii on 25/7/17.
 */

class StatusCodeInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        val statusCode = response.code()
        if (statusCode < 200 || statusCode > 299 || response.body() == null) {
            Log.d("TAG", "interceptStatusCode: " + statusCode)
            handleErrorResponse(response)
        }

        return response
    }

    @Throws(HttpStatusException::class)
    private fun handleErrorResponse(response: Response?) {
        val statusCode = response!!.code()
        if (statusCode == 401) {
            throw HttpStatusException("Your session has expired, please relogin.", HttpErrorType.ReloginRequired)
        } else if (statusCode == 419) {
            throw HttpStatusException("Pin re-entry required to continue.", HttpErrorType.PasswordReEnterRequired)
        } else if (statusCode == 480) {
            throw HttpStatusException("Pin has expired, please request a new one.", HttpErrorType.PasswordResetRequired)
        } else if (response == null) {
            throw HttpStatusException(SohoError().error, HttpErrorType.General)
        }
        val error = SohoError.fromResponse(response)
        throw HttpStatusException(error.getMessage(), HttpErrorType.General)
    }
}