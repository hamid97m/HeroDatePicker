package com.hamid97m.herodatepicker.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import com.hamid97m.herodatepicker.utils.HeroDatePickerUtil
import com.hamid97m.herodatepicker.utils.ShamsiDatePickerUtill
import com.hamid97m.herodatepicker.utils.ShamsiHeroDatePicker
import java.util.Date

@Composable
fun HeroDatePicker(
    modifier: Modifier = Modifier,
    selectableYearRange: Iterable<Int>? = null,
    isSelectFromFutureEnable: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current
) {
    val currentDate = Date(System.currentTimeMillis())
    val currentDateShamsi = ShamsiDatePickerUtill()
    currentDateShamsi.gregorianToPersian(currentDate)
    val heroDatePickerUtil: HeroDatePickerUtil by lazy {
        with(currentDateShamsi) {
            val maxMonth = if (isSelectFromFutureEnable) 12 else month
            val maxDay = if (isSelectFromFutureEnable) 31 else day
            val maxYear = when {
                selectableYearRange != null -> selectableYearRange.last()
                isSelectFromFutureEnable -> year + 100
                else -> year
            }
            ShamsiHeroDatePicker(
                maxYear,
                maxMonth,
                maxDay,
                selectableYearRange
            )
        }
    }

    val yearValue = remember { mutableStateOf(currentDateShamsi.year) }
    val monthValue = remember { mutableStateOf(currentDateShamsi.month) }
    val dayValue = remember { mutableStateOf(currentDateShamsi.day) }

    Column(modifier = modifier) {
        val context = LocalContext.current
        Row(modifier = Modifier.fillMaxWidth()) {
            NumberPicker(
                modifier = Modifier.weight(0.25f),
                textStyle = textStyle,
                range = heroDatePickerUtil.getYearRange(),
                value = yearValue.value,
                onValueChange = { yearValue.value = it }
            )

            NumberPicker(
                modifier = Modifier.weight(0.5f),
                textStyle = textStyle,
                range = heroDatePickerUtil.getMonthRange(yearValue.value),
                value = monthValue.value,
                label = { value ->
                    heroDatePickerUtil.getMonthName(
                        value,
                        context
                    ) + " / " + value.toString()
                },
                onValueChange = { monthValue.value = it }
            )

            NumberPicker(
                modifier = Modifier.weight(0.25f),
                textStyle = textStyle,
                range = heroDatePickerUtil.getDayRange(
                    monthValue.value,
                    yearValue.value
                ),
                value = dayValue.value,
                onValueChange = { dayValue.value = it }
            )

        }

    }


}