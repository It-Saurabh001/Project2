package com.saurabh.project2.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saurabh.project2.data.model.SaleItem

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromSaleItemList(value: List<SaleItem>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSaleItemList(value: String?): List<SaleItem>? {
        val listType = object : TypeToken<List<SaleItem>>() {}.type
        return gson.fromJson(value, listType)
    }
}