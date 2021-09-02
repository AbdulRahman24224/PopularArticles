package com.surrus.bikeshare.utils.compose


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


val screenMargin  = Modifier.padding(8.dp)
/*val center_horizontal = Modifier.alignm(Alignment.CenterHorizontally)
val center_vertical = Modifier.align(Alignment.CenterVertically)*/
val text_bold = TextStyle(fontWeight = FontWeight.Bold)
val max_max = Modifier.fillMaxHeight().fillMaxWidth()
val unselectedScale = 0.85f

val roundSurfaceMod =Modifier
.padding(start = 8.dp, end = 8.dp, top = 8.dp)
.requiredHeight(60.dp)
.requiredWidth(60.dp)
.clip(CircleShape)
.border(
shape = CircleShape,
border = BorderStroke(
3.dp,
color = Color.LightGray
)
)


