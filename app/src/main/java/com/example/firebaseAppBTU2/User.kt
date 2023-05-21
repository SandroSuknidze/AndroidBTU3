package com.example.firebaseAppBTU2

import android.provider.ContactsContract.CommonDataKinds.Email

data class User(
    val email : String ?= null,
    val name : String ?= null,
    val surname : String ?= null,
    val id : String ?= null,
    val uid : String ?= null,
    val link : String ?= null,
    val username: String ?= null)

data class School(
    val students : List<User>
)
