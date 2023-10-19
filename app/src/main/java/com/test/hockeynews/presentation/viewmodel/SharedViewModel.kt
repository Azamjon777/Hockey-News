package com.test.hockeynews.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.hockeynews.R
import com.test.hockeynews.presentation.models.TableUserModel
import com.test.hockeynews.presentation.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel(private val application: Application) : AndroidViewModel(application) {
    private val userTableListLiveData = MutableLiveData<List<TableUserModel>>()
    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    private val _score = MutableLiveData(sharedPreferences.getInt("score", 0))
    val scoreGold: LiveData<Int> get() = _score

    private val _soundLevel = MutableLiveData(sharedPreferences.getInt("sound_level", 50))

    init {
        userTableListLiveData.value = myTableUser()
        _score.value = sharedPreferences.getInt("score", 0)

        _score.observeForever { newScore ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    sharedPreferences.edit()
                        .putInt("score", newScore)
                        .apply()
                }
            }
        }
        _soundLevel.observeForever { newSoundLevel ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    sharedPreferences.edit()
                        .putInt("sound_level", newSoundLevel)
                        .apply()
                }
            }
        }
    }

    fun setSoundLevel(newSoundLevel: Int) {
        _soundLevel.value = newSoundLevel

        sharedPreferences.edit()
            .putInt("sound_level", newSoundLevel)
            .apply()
    }

    fun updateScore(newScore: Int) {
        _score.value = newScore
    }

    fun getUserListLiveData(): LiveData<List<UserModel>> {
        return userList
    }

    fun getTableListLiveData(): LiveData<List<TableUserModel>> {
        return userTableListLiveData
    }

    fun enableAllUsers() {
        val currentList = userList.value ?: emptyList() // Получаем текущий список пользователей
        val updatedList = currentList.map { user ->
            user.isEnabled.value = true // Меняем значение isEnabled на true
            user // Возвращаем обновленный объект UserModel
        }

        // Обновляем userList вызвав postValue
        userList.postValue(updatedList)
    }

    private val userList = MutableLiveData<List<UserModel>>(
        mutableListOf(
            UserModel(
                1,
                "ESP",
                "GER",
                4,
                3,
                application.getString(R.string.esp),
                application.getString(R.string.ger),
                MutableLiveData(true)
            ),
            UserModel(
                2,
                "BEL",
                "NED",
                2,
                4,
                application.getString(R.string.bel),
                application.getString(R.string.ned),
                MutableLiveData(true)
            ),
            UserModel(
                3,
                "BEL",
                "ESP",
                5,
                1,
                application.getString(R.string.bel),
                application.getString(R.string.esp),
                MutableLiveData(true)
            ),
            UserModel(
                4,
                "GER",
                "ESP",
                1,
                3,
                application.getString(R.string.bel),
                application.getString(R.string.esp),
                MutableLiveData(true)
            ),
            UserModel(
                5,
                "BEL",
                "NED",
                1,
                6,
                application.getString(R.string.bel),
                application.getString(R.string.ned),
                MutableLiveData(true)
            ),
            UserModel(
                6,
                "BEL",
                "ESP",
                7,
                2,
                application.getString(R.string.bel),
                application.getString(R.string.esp),
                MutableLiveData(true)
            ),
            UserModel(
                7,
                "NED",
                "NZL",
                4,
                1,
                application.getString(R.string.ned),
                application.getString(R.string.nzl),
                MutableLiveData(true)
            ),
            UserModel(
                8,
                "USA",
                "GER",
                3,
                4,
                application.getString(R.string.usa),
                application.getString(R.string.ger),
                MutableLiveData(false)
            ),
            UserModel(
                9,
                "CAN",
                "LAT",
                3,
                4,
                application.getString(R.string.can),
                application.getString(R.string.lat),
                MutableLiveData(false)
            ),
            UserModel(
                10,
                "GER",
                "CAN",
                2,
                5,
                application.getString(R.string.ger),
                application.getString(R.string.can),
                MutableLiveData(false)
            )
        )
    )

    private fun myTableUser(): List<TableUserModel> {
        return listOf(
            TableUserModel(1, "Canada", +22, 24),
            TableUserModel(2, "Germany", +11, 17),
            TableUserModel(3, "Latvia", +5, 18),
            TableUserModel(4, "USA", +27, 25),
            TableUserModel(5, "Switzerland", +17, 19),
            TableUserModel(6, "Sweden", +17, 18),
            TableUserModel(7, "Finland", +10, 16),
            TableUserModel(8, "Czechia", +3, 13),
            TableUserModel(9, "Slovakia", 0, 11),
            TableUserModel(10, "Denmark", -7, 8),
            TableUserModel(11, "Kazakhstan", -17, 7),
            TableUserModel(12, "France", -21, 4),
            TableUserModel(13, "Norway", -8, 6),
            TableUserModel(14, "Austria", -16, 3),
            TableUserModel(15, "Hungary", -25, 3),
            TableUserModel(16, "Slovenia", -18, 0)
        )
    }
}
