package com.hamid97m.herodatepicker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.hamid97m.herodatepicker.R
import com.hamid97m.herodatepicker.utils.HeroDatePickerUtill
import kotlinx.android.synthetic.main.hero_date_picker_view.view.*
import java.util.*
import java.util.zip.Inflater

class HeroDatePickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val binding = inflater.inflate(R.layout.hero_date_picker_view, null);

    var maxYear: Int = 1399
        set(value) {
            field = value
            setDayOnView()
        }
    var minYear: Int = 1300
        set(value) {
            field = value
            setDayOnView()
        }


//    constructor(context: Context) : super(context) {
//        init(context, null)
//    }
//
//    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
//        init(context, attrs)
//    }
//
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    ) {
//        init(context, attrs)
//    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val currentDate = Date(System.currentTimeMillis())
        val converter = HeroDatePickerUtill()
        converter.gregorianToPersian(currentDate)
        if (attrs != null) {
//            val a = context.obtainStyledAttributes(attrs, R.styleable.DayPickerView)
//            maxYear = a.getInteger(R.styleable.DayPickerView_max_year, converter.year)
//            minYear = a.getInteger(R.styleable.DayPickerView_min_year, maxYear - 100)
//            title_group.isVisible = a.getBoolean(R.styleable.DayPickerView_header_visibility, true)
//            a.recycle()
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

    fun setDayOnView(time: Long) {
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

        binding.monthPicker.setOnValueChangedListener { _, _, _ ->
            binding.dayPicker.maxValue = getMonthLength(binding.monthPicker.value)
        }
        binding.yearPicker.setOnValueChangedListener { _, _, _ ->
            if (binding.monthPicker.value == 12) {
                binding.dayPicker.maxValue = getMonthLength(binding.monthPicker.value)
            }
        }

    }

    private fun getMonthLength(month: Int) = when (month) {
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

    private fun monthName(monthNumber: Int) = when (monthNumber) {
        1 -> "فروردین"
        2 -> "اردیبهشت"
        3 -> "خرداد"
        4 -> "تیر"
        5 -> "مرداد"
        6 -> "شهریور"
        7 -> "مهر"
        8 -> "آبان"
        9 -> "آذر"
        10 -> "دی"
        11 -> "بهمن"
        12 -> "اسفند"

        else -> ""
    }
}


