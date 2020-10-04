package com.hamid97m.herodatepicker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.hamid97m.herodatepicker.R
import com.hamid97m.herodatepicker.utils.HeroDatePickerUtil
import com.hamid97m.herodatepicker.utils.ShamsiDatePickerUtill
import com.hamid97m.herodatepicker.utils.ShamsiHeroDatePicker
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
        val converter =
            ShamsiDatePickerUtill()
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
            val dateConverter =
                ShamsiDatePickerUtill()
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
        val converter = ShamsiDatePickerUtill()
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
        val persianDate = ShamsiDatePickerUtill()
        persianDate.gregorianToPersian(date)
        val heroDatePickerUtil: HeroDatePickerUtil = ShamsiHeroDatePicker(maxYear, maxMonth, maxDay)
        binding.yearPicker.apply {
            minValue = minYear
            maxValue = maxYear
            value = persianDate.year
        }
        binding.monthPicker.apply {
            minValue = 1
            maxValue = 12
            value = persianDate.month
            setFormatter { heroDatePickerUtil.getMonthName(it, context) + " / " + it.toString() }
        }
        binding.dayPicker.apply {
            minValue = 1
            maxValue = 31
            value = persianDate.day
        }

        binding.monthPicker.setOnValueChangedListener { _, _, newMonth ->
            binding.dayPicker.maxValue =
                heroDatePickerUtil.getMaxDayForCurrentMonth(newMonth, binding.yearPicker.value)
        }

        binding.yearPicker.setOnValueChangedListener { _, _, newYear ->
            binding.dayPicker.maxValue = heroDatePickerUtil.getMaxDayForCurrentMonth(
                binding.monthPicker.value,
                binding.yearPicker.value
            )
            binding.monthPicker.maxValue = heroDatePickerUtil.getMaxMonthForCurrentYear(newYear)
        }

        binding.yearPicker.value = persianDate.year
    }


}


