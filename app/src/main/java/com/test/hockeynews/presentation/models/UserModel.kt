package com.test.hockeynews.presentation.models

import androidx.lifecycle.MutableLiveData

class UserModel(
    val id: Int,
    val nameOfTeam1: String,
    val nameOfTeam2: String,
    val scoreOfTeam1: Int,
    val scoreOfTeam2: Int,
    val flagOfTeam1: String,
    val flagOfTeam2: String,
    var isEnabled: MutableLiveData<Boolean>
)