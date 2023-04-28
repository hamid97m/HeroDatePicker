package com.hamid97m.herodatepicker.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import com.hamid97m.herodatepicker.model.SelectedDate
import com.hamid97m.herodatepicker.utils.HeroDatePickerUtil
import com.hamid97m.herodatepicker.utils.ShamsiDatePickerUtill
import com.hamid97m.herodatepicker.utils.ShamsiHeroDatePicker
import java.util.Date

/**
 * @param selectableYearRange determines the range of years that is selectable.
 * @param isSelectFromFutureEnable if false, the max date that would be selected is now.
 */
@Composable
fun HeroDatePicker(
    modifier: Modifier = Modifier,
    selectableYearRange: Iterable<Int>? = null,
    isSelectFromFutureEnable: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    onSelectedDateChange: (SelectedDate) -> Unit
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
    LaunchedEffect(Unit) {
        onSelectedDateChange(
            SelectedDate(
                currentDateShamsi.year,
                currentDateShamsi.month,
                currentDateShamsi.day
            )
        )
    }
    var selectedYear by remember { mutableStateOf(currentDateShamsi.year) }
    var selectedMonth by remember { mutableStateOf(currentDateShamsi.month) }
    var selectedDay by remember { mutableStateOf(currentDateShamsi.day) }



    Column(modifier = modifier) {
        val context = LocalContext.current
        Row(modifier = Modifier.fillMaxWidth()) {
            NumberPicker(
                modifier = Modifier.weight(0.25f),
                textStyle = textStyle,
                range = heroDatePickerUtil.getYearRange(),
                value = selectedYear,
                onValueChange = {
                    selectedYear = it
                    onSelectedDateChange(SelectedDate(selectedYear, selectedMonth, selectedDay))
                }
            )

            NumberPicker(
                modifier = Modifier.weight(0.5f),
                textStyle = textStyle,
                range = heroDatePickerUtil.getMonthRange(selectedYear),
                value = selectedMonth,
                label = { value ->
                    heroDatePickerUtil.getMonthName(value, context) + " / " + value.toString()
                },
                onValueChange = {
                    selectedMonth = it
                    onSelectedDateChange(SelectedDate(selectedYear, selectedMonth, selectedDay))
                }
            )

            NumberPicker(
                modifier = Modifier.weight(0.25f),
                textStyle = textStyle,
                range = heroDatePickerUtil.getDayRange(selectedMonth, selectedYear),
                value = selectedDay,
                onValueChange = {
                    selectedDay = it
                    onSelectedDateChange(SelectedDate(selectedYear, selectedMonth, selectedDay))
                }
            )

        }

    }


}