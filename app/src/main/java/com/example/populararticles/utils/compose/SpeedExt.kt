package com.example.populararticles.utils.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
infix fun Int.sP (x:Int )= Spacer(modifier = Modifier.padding(this.dp))
@Composable
infix fun Int.sH (x:Int )= Spacer(modifier = Modifier.requiredHeight(this.dp))
@Composable
infix fun Int.sW (x:Int )= Spacer(modifier = Modifier.requiredWidth(this.dp))



