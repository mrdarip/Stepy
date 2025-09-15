package com.mrdarip.stepy.ui.screens.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun EditScreen(
) {

    Column {
        TextField(
            value = "",
            onValueChange = { },
            label = { Text("TaskName") },
            placeholder = { Text("Name") }
        )


        Row {
            Button(onClick = { /*TODO*/ }) {
                Text("Exit")
            }

            Button(onClick = { /*TODO*/ }) {
                Text("Save")
            }
        }
    }
}