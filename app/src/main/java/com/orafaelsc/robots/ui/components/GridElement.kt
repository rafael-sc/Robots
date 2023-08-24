package com.orafaelsc.robots.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GridElement() {
    Box(modifier = Modifier.padding(8.dp)) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFDEDEDE), shape = RoundedCornerShape(28.dp)),

        )
    }
}

@Preview(showBackground = true)
@Composable
fun GridElementPreview() {
    GridElement()
}
