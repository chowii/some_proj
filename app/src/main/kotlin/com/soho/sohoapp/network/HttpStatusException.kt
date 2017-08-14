package com.soho.sohoapp.network

import java.io.IOException

/**
 * Created by chowii on 25/7/17.
 */

class HttpStatusException(var error: String, var errorType: HttpErrorType) : IOException()
