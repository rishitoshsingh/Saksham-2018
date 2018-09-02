package com.bdcoe.saksham.POJOs.News

import com.google.gson.annotations.SerializedName


class List {

    @SerializedName("classified_id")
    var classifiedId: String? = null
    @SerializedName("desc")
    var desc: String? = null
    @SerializedName("image1")
    var image1: String? = null
    @SerializedName("image2")
    var image2: String? = null
    @SerializedName("sport")
    var sport: String? = null
    @SerializedName("teams")
    var teams: String? = null
    @SerializedName("timestamp")
    var timestamp: String? = null

}
