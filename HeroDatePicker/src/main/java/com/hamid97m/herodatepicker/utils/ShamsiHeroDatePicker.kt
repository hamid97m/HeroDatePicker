package com.hamid97m.herodatepicker.utils

import android.content.Context
import com.hamid97m.herodatepicker.R

/**
 * Created by Hamid Mahmoodi on 10/3/20.
 */
class ShamsiHeroDatePicker(private val maxYear: Int, private val maxMonth: Int, private val maxDay: Int) : HeroDatePickerUtil {
    override fun getMonthLength(month: Int, currentYear: Int) = when (month) {
        1, 2, 3, 4, 5, 6 -> {
            31
        }
        12 -> {
            if (ShamsiDatePickerUtill.isFarsiLeap(currentYear))
                30
            else
                29
        }
        else -> {
            30
        }
    }

    override fun getMaxDayForCurrentMonth(
        currentMonth: Int,
        currentYear: Int
    ): Int {
        val monthLength = getMonthLength(currentMonth, currentYear)
        return if (currentYear == maxYear && currentMonth == maxMonth) {
            if (maxDay < monthLength) maxDay
            else
                monthLength
        } else {
            monthLength
        }
    }

    override fun getMaxMonthForCurrentYear(currentYear: Int) =
        if (currentYear == maxYear) {
            maxMonth
        } else {
            12
        }

    override fun getMonthName(month: Int, context: Context) =
        context.resources.getStringArray(R.array.shamsi_mouth)[month + 1]
}