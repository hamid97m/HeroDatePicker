package com.hamid97m.herodatepicker.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamid97m.herodatepicker.model.SelectedDate
import com.hamid97m.herodatepicker.sample.theme.HeroDatePickerTheme
import com.hamid97m.herodatepicker.view.HeroDatePicker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeroDatePickerTheme {
                Surface {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedDateState by remember {
        mutableStateOf(SelectedDate(0, 0, 0))
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        HeroDatePicker { selectedDate ->
            selectedDateState = selectedDate
        }

        val (year, month, day) = selectedDateState
        Text(
            text = "$year/$month/$day",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HeroDatePickerTheme {
        MainScreen()
    }
}
