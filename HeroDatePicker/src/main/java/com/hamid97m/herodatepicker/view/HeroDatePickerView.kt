package com.hamid97m.herodatepicker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.hamid97m.herodatepicker.R
import com.hamid97m.herodatepicker.enums.DateType
import com.hamid97m.herodatepicker.utils.HeroDatePickerUtill
import kotlinx.android.synthetic.main.hero_date_picker_view.view.*
import java.util.*

class HeroDatePickerView constructor(context: Context) :
    FrameLayout(context) {

    constructor(context: Context, attrs: AttributeSet?) : this(context) {
        init(context, attrs)
    }

    private lateinit var inflater: LayoutInflater
    private lateinit var binding: View

    var minYear: Int = 1300
        set(value) {
            field = value
            setDayOnView()
        }
    private var maxYear: Int = 1399
    private var maxMonth: Int = 12
    private var maxDay: Int = 31

    fun setMaxDate(year: Int, month: Int, day: Int) {
        maxYear = year
        maxMonth = month
        maxDay = day
        setDayOnView()
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = inflater.inflate(R.layout.hero_date_picker_view, this, true)

        val currentDate = Date(System.currentTimeMillis())
        val converter = HeroDatePickerUtill()
        converter.gregorianToPersian(currentDate)
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.HeroDatePickerView)
            val isBeforeNowMode =
                a.getBoolean(R.styleable.HeroDatePickerView_before_now_mode, false)
            if (isBeforeNowMode) {
                with(converter) {
                    setMaxDate(year, month, day)
                }
            } else {
                val year = a.getInteger(R.styleable.HeroDatePickerView_max_year, converter.year)
                val month = a.getInteger(R.styleable.HeroDatePickerView_max_month, converter.month)
                val day = a.getInteger(R.styleable.HeroDatePickerView_max_day, converter.day)
                setMaxDate(year, month, day)
            }

            minYear = a.getInteger(R.styleable.HeroDatePickerView_min_year, maxYear - 100)

            a.recycle()
        }
        setDayOnView()

    }


    val selectedDate: Date
        get() {
            val year = binding.yearPicker.value
            val month = binding.monthPicker.value
            val day = binding.dayPicker.value
            val dateConverter = HeroDatePickerUtill()
            dateConverter.persianToGregorian(year, month, day)
            val date = Calendar.getInstance()
            date.set(dateConverter.year, dateConverter.month - 1, dateConverter.day, 0, 0)
            return date.time
        }

    fun setDayOnView(date: Date) {
        setDayOnView(date.time)
    }

    fun setDayOnView() {
        val currentDate = Date(System.currentTimeMillis())
        val converter = HeroDatePickerUtill()
        converter.gregorianToPersian(currentDate)
        val finalDate = if (converter.year > maxYear) {
            converter.persianToGregorian(maxYear, 1, 1)
            val calender = Calendar.getInstance()
            calender.set(converter.year, converter.month - 1, converter.day)
            calender.time
        } else {
            currentDate
        }
        setDayOnView(finalDate)
    }

    private fun setDayOnView(time: Long) {
        val date = Date(time)
        val persianDate = HeroDatePickerUtill()
        persianDate.gregorianToPersian(date)
        binding.yearPicker.apply {
            minValue = minYear
            maxValue = maxYear
            value = persianDate.year
        }
        binding.monthPicker.apply {
            minValue = 1
            maxValue = 12
            value = persianDate.month
            setFormatter { monthName(it) + " / " + it.toString() }
        }
        binding.dayPicker.apply {
            minValue = 1
            maxValue = 31
            value = persianDate.day
        }

        binding.monthPicker.setOnValueChangedListener { _, _, newMonth ->
            binding.dayPicker.maxValue = getMaxDayForCurrentMonth(newMonth)
        }

        binding.yearPicker.setOnValueChangedListener { _, _, newYear ->
            binding.dayPicker.maxValue = getMaxDayForCurrentMonth(binding.monthPicker.value)
            binding.monthPicker.maxValue = getMaxMonthForCurrentYear(newYear)
        }

        binding.yearPicker.value = persianDate.year
    }

    private fun getMaxMonthForCurrentYear(year: Int): Int {
        return if (year == maxYear) {
            maxMonth
        } else {
            12
        }
    }

    private fun getMaxDayForCurrentMonth(month: Int): Int {
        val monthLength = getMonthLength(month)
        return if (binding.yearPicker.value == maxYear && month == maxMonth) {
            if (maxDay < monthLength) maxDay
            else
                monthLength
        } else {
            monthLength
        }
    }

    private fun getMonthLength(month: Int, type: DateType = DateType.SHAMSI) = when (month) {
        1, 2, 3, 4, 5, 6 -> {
            31
        }
        12 -> {
            if (HeroDatePickerUtill.isFarsiLeap(binding.yearPicker.value))
                30
            else
                29
        }
        else -> {
            30
        }
    }

    private fun monthName(monthNumber: Int, type: DateType = DateType.SHAMSI) = when (type) {
        DateType.SHAMSI -> {
            resources.getStringArray(R.array.shamsi_mouth)[monthNumber+1]
        }
        else -> ""
    }
}


