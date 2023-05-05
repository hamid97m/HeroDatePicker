package com.hamid97m.herodatepickersample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.hamid97m.herodatepicker.model.SelectedDate
import com.hamid97m.herodatepicker.view.HeroDatePicker

class MainActivity : AppCompatActivity() {
    private val myCustomFont = FontFamily(
        Font(R.font.iran_sans_regular),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedDateState by remember { mutableStateOf(SelectedDate(0, 0, 0)) }
            Column {
                HeroDatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontFamily = myCustomFont)
                ) { selectedDate ->
                    /*
                    selectedDate.year
                    selectedDate.month
                    selectedDate.day
                    */
                    selectedDateState = selectedDate
                }
                Text(
                    style = TextStyle(fontFamily = myCustomFont),
                    text = "${selectedDateState.year}/${selectedDateState.month}/${selectedDateState.day}"
                )
            }

        }
    }
}