package com.example.suitmediatest.model

data class ResponseReqres(
    val per_page: Int? = null,
    val total: Int? = null,
    val data: ArrayList<DataItem>? = null,
    val page: Int? = null,
    val total_pages: Int? = null,
    val support: Support? = null
)

data class DataItem(
    val last_name: String? = null,
    val id: Int? = null,
    val avatar: String? = null,
    val first_name: String? = null,
    val email: String? = null
)

data class Support(
    val text: String? = null,
    val url: String? = null
)
