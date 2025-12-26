package com.pab.tixid.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.pab.tixid.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        private val USER_ID = intPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_PHONE = stringPreferencesKey("user_phone")
        private val USER_ROLE = stringPreferencesKey("user_role")
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = user.id
            prefs[USER_NAME] = user.name
            prefs[USER_EMAIL] = user.email
            prefs[USER_PHONE] = user.phone ?: ""
            prefs[USER_ROLE] = user.role ?: "user"
            prefs[IS_LOGGED_IN] = true
        }
    }

    val userFlow: Flow<User?> = context.dataStore.data.map { prefs ->
        if (prefs[IS_LOGGED_IN] == true) {
            User(
                id = prefs[USER_ID] ?: 0,
                name = prefs[USER_NAME] ?: "",
                email = prefs[USER_EMAIL] ?: "",
                phone = prefs[USER_PHONE],
                role = prefs[USER_ROLE],
                created_at = null
            )
        } else null
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_LOGGED_IN] ?: false
    }

    val isAdmin: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[USER_ROLE] == "admin"
    }
}

