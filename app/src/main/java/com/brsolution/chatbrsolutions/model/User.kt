package com.brsolution.chatbrsolutions.model

data class User(
    var id: String,
    var name: String,
    var email: String,
    var password: String,
    var photo: String = "",
)
