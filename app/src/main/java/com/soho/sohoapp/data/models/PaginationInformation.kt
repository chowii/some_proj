package com.soho.sohoapp.data.models

data class PaginationInformation(var page: Int? = null,
                                 var nextPage: Int? = null,
                                 var perPage: Int? = null,
                                 var totalPages: Int? = null,
                                 var totalItems: Int? = null,
                                 var offSet: Int? = null) {

    val hasNextPage: Boolean
        get() = nextPage != null

}