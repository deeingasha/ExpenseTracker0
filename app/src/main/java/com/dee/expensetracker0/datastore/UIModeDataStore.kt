package com.dee.expensetracker0.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class PrefsDataStore(context: Context,fileName:String){
    internal val dataStore: DataStore<Preferences> = context.createDataStore(fileName)
}

class UIModeDataStore (context: Context): PrefsDataStore(
    context,
    PREF_FILE_UI_MODE
),UIModeImpl{
    companion object{
        private const val PREF_FILE_UI_MODE = "ui_mode_preference"
        private val UI_MODE_KEY = PreferencesKey<Boolean>("ui_mode")
    }
//used to get data from dataStore
    override val uiMode: Flow<Boolean>
        get() = dataStore.data.map {  preferences->
            val uiMode = preferences[UI_MODE_KEY]?:false
            uiMode
        }
//used to save to dataStore
    override suspend fun saveToDatastore(isNightMode: Boolean) {
        dataStore.edit { preferences->
            preferences[UI_MODE_KEY] = isNightMode
        }
    }

}
interface UIModeImpl{
    val uiMode : Flow<Boolean>
    suspend fun saveToDatastore(isNightMode:Boolean)
}