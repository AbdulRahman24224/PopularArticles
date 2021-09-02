/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.populararticles.utils.compose

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


/*@Composable
fun IconResource(
    @DrawableRes resourceId: Int,
    modifier: Modifier = Modifier,
    tint: Color = foregroundColor()
) {
    val deferredResource = painterResource(resourceId)
    deferredResource. { vector ->
        Icon(imageVector = vector,"" ,modifier = modifier, tint = tint)
    }
}

inline fun <T> DeferredResource<T>.onLoadRun(block: (T) -> Unit) {
    val res = resource.resource
    if (res != null) {
        block(res)
    }
}*/

/**
 * Returns the [AmbientContentColor] with the current [AmbientContentAlpha].
 */
@Composable
inline fun foregroundColor(): Color = LocalContentColor.current.copy(LocalContentAlpha.current)

