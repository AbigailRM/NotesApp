package com.moviledev.notesapp.generalFiles.models

data class User(
    val firstName: String? = null,
    val lastName: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val sex: String? = null,
    val country: String? = null,
    val address: String? = null,
    val password: String? = null,
    val forPassword: String? = null,
    val uid: String? = null
)