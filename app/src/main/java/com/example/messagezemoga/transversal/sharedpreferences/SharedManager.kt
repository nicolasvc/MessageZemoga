package com.example.messagezemoga.transversal.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import com.example.messagezemoga.di.application.MyApp
import com.google.gson.Gson

class SharedManager {

    //region Propiedades
    private val app_setting_files = "APP_SETTINGS"
    private val gson = Gson()
    private val preferenciasDispositivo : SharedPreferences = getSharedPreference()
    //endregion

    //region Companion
    companion object {
        fun obtenerInstancia(): SharedManager = SharedManager()
    }
    //endregion


    //region Metodos propios
    private fun getSharedPreference(): SharedPreferences =
        MyApp.applicationContext().getSharedPreferences(app_setting_files, Context.MODE_PRIVATE)


    fun almacenar(@NonNull llave: String?, @NonNull objeto: Any?) {
        llave!!.trim { it <= ' ' }
        if (llave == "") throw IllegalArgumentException("No se puede pasar una llave vacia")
        if (objeto == null) throw IllegalArgumentException("No se puede pasar un objeto vacio")
        preferenciasDispositivo.edit()
            .putString(llave, gson.toJson(objeto))
            .apply()
    }

    fun limpiar() {
        preferenciasDispositivo.edit().clear().apply()
    }


    fun <T> obtener(@NonNull llave: String?, tipoClase: Class<T>?): T? {
        if (llave == "") throw IllegalArgumentException("No se puede pasar una llave vacia")
        if (llave == null) throw IllegalArgumentException("No se puede pasar  una llave nula")
        val json = preferenciasDispositivo.getString(llave, null)
        return if (json == null) {
            null
        } else {
            try {
                gson.fromJson(json, tipoClase)
            } catch (ex: Exception) {
                throw RuntimeException("No es posible obtener la clase ")
            }
        }
    }
    //endregion


}