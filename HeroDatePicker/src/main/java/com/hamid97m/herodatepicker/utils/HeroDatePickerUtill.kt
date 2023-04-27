package com.hamid97m.herodatepicker.utils

import android.content.Context

/**
 * Created by Hamid Mahmoodi on 10/3/20.
 */
internal interface HeroDatePickerUtil {
    fun getMonthLength(month: Int, currentYear: Int): Int
    fun getMaxDayForCurrentMonth(
        currentMonth: Int,
        currentYear: Int
    ): Int

    fun getMaxMonthForCurrentYear(currentYear: Int): Int
    fun getMonthName(month: Int, context: Context): String

    fun getYearRange(): Iterable<Int>
    fun getMonthRange(
        currentYear: Int
    ): Iterable<Int>

    fun getDayRange(
        currentMonth: Int,
        currentYear: Int
    ): Iterable<Int>
}