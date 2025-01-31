package ali.hrhera.base.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("quiz_game_user_preferences")

class AppDataStore(context: Context) {
    val dataStore = context.dataStore

    suspend fun <T> save(key: String, value: T) {
        when (value) {
            is String -> {
                dataStore.edit { preferences ->
                    preferences[stringPreferencesKey(key)] = value as String
                }
            }

            is Boolean -> {
                dataStore.edit { preferences ->
                    preferences[booleanPreferencesKey(key)] = value as Boolean
                }
            }

            is Int -> {
                dataStore.edit { preferences ->
                    preferences[intPreferencesKey(key)] = value as Int
                }
            }

            is Float -> {
                dataStore.edit { preferences ->
                    preferences[floatPreferencesKey(key)] = value as Float
                }
            }

            is Double -> {
                dataStore.edit { preferences ->
                    preferences[doublePreferencesKey(key)] = value as Double
                }
            }

            else -> {
                dataStore.edit { preferences ->
                    val json = Gson().toJson(mapOf(key to value))
                    preferences[stringPreferencesKey(key)] = json.toString()
                }
            }
        }
    }

    suspend fun <T> saveCipher(key: String, value: T) {
        dataStore.edit { preferences ->
            val json = Gson().toJson(mapOf(key to value))
            json.encrypt()
            preferences[stringPreferencesKey(key)] = json.toString()
        }
    }

    fun <T> getCiphered(key: String, default: T): Flow<T> {
        return dataStore.data.map { preferences ->
            val json = preferences[stringPreferencesKey(key)] ?: ""
            val value = json.takeIf { it.isNotBlank() }?.decrypt()

            if (value != null) {
                return@map Gson().fromJson(value, default!!::class.java)
            }
            return@map default
        }
    }

    inline fun <reified T> get(key: String, default: T): Flow<T> {
        return dataStore.data.map { preferences ->
            when (default) {
                is String -> {
                    return@map (preferences[stringPreferencesKey(key)] as T?) ?: default
                }

                is Boolean -> {
                    val result = preferences[booleanPreferencesKey(key)] as T? ?: default
                    return@map result
                }

                is Int -> {
                    val result = (preferences[intPreferencesKey(key)] as T?) ?: default
                    return@map result
                }

                is Float -> {
                    val result = (preferences[floatPreferencesKey(key)] as T?) ?: default

                    return@map result
                }

                is Double -> {
                    val result = (preferences[doublePreferencesKey(key)] as T?) ?: default
                    return@map result
                }

                else -> {
                    val json = preferences[stringPreferencesKey(key)] ?: ""
                    val value = json.takeIf { it.isNotBlank() }?.let {
                        val type = object : TypeToken<Map<String, T?>>() {}.type
                        Gson().fromJson<Map<String, T?>>(it, type)
                    }
                    return@map value?.get(key) ?: default
                }
            }
        }
    }

}