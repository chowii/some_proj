package com.soho.sohoapp.extensions

import com.soho.sohoapp.data.models.PaginationInformation
import com.soho.sohoapp.utils.toPaginationInformation
import retrofit2.Response

/**
 * Created by Jovan on 5/10/17.
 */

fun< T:Any> Response<T>.getPaginationInformation(): PaginationInformation =
        this.headers().toPaginationInformation()


