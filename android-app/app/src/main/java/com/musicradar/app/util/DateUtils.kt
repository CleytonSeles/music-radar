package com.musicradar.app.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Classe utilitária para manipulação de datas no aplicativo
 */
object DateUtils {

    private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val displayDateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    /**
     * Converte uma string de data da API para o formato de visualização
     */
    fun formatApiDateForDisplay(apiDate: String?): String {
        if (apiDate.isNullOrEmpty()) return ""

        return try {
            val date = apiDateFormat.parse(apiDate)
            date?.let { displayDateFormat.format(it) } ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * Converte uma string de data da API para o formato de visualização com hora
     */
    fun formatApiDateTimeForDisplay(apiDate: String?): String {
        if (apiDate.isNullOrEmpty()) return ""

        return try {
            val date = apiDateFormat.parse(apiDate)
            date?.let { displayDateTimeFormat.format(it) } ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * Retorna a data atual no formato da API
     */
    fun getCurrentDateForApi(): String {
        val calendar = Calendar.getInstance()
        return apiDateFormat.format(calendar.time)
    }
}
