package com.hamid97m.herodatepicker.utils

import android.content.Context

/**
 * Created by Hamid Mahmoodi on 10/3/20.
 */
interface HeroDatePickerUtil {
    fun getMonthLength(month: Int,currentYear:Int):Int
    fun getMaxDayForCurrentMonth(currentMonth: Int,currentYear:Int):Int
    fun getMaxMonthForCurrentYear(currentYear: Int):Int
    fun getMonthName(month: Int,context:Context):String
}