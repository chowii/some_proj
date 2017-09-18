package com.soho.sohoapp.data.dtos

import com.google.gson.annotations.SerializedName

/**
 * Created by Jovan on 16/9/17.
 */

class InspectionTimeResult {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("start_time")
    var startTime: String? = null

    @SerializedName("end_time")
    var endTime: String? = null

}