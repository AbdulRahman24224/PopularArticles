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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun LazyListScope.spacerItem(height: Dp) {
    item {
        Spacer(Modifier.requiredHeight(height).fillParentMaxWidth())
    }
}


/**
 * Displays a 'fake' grid using [LazyColumn]'s DSL. It's fake in that we just we add individual
 * column items, with a inner fake row.
 */
/*fun <T : Any> LazyListScope.itemsInGrid(
    lazyPagingItems: LazyPagingItems<T>,
    columns: Int,
    contentPadding: PaddingValues = PaddingValues(),
    horizontalItemPadding: Dp = 0.dp,
    verticalItemPadding: Dp = 0.dp,
    itemContent: @Composable (T?) -> Unit
) {
    val rows = when {
        lazyPagingItems.itemCount % columns == 0 -> lazyPagingItems.itemCount / columns
        else -> (lazyPagingItems.itemCount / columns) + 1
    }

    for (row in 0 until rows) {
        if (row == 0) spacerItem(contentPadding.top)

        item {
            Row(
                Modifier.fillMaxWidth()
                    .padding(start = contentPadding.start, end = contentPadding.end)
            ) {
                for (column in 0 until columns) {
                    Box(modifier = Modifier.weight(1f)) {
                        val index = (row * columns) + column
                        if (index < lazyPagingItems.itemCount) {
                            itemContent(lazyPagingItems[index])
                        }
                    }
                    if (column < columns - 1) {
                        Spacer(modifier = Modifier.preferredWidth(horizontalItemPadding))
                    }
                }
            }
        }

        if (row < rows - 1) {
            spacerItem(verticalItemPadding)
        } else {
            spacerItem(contentPadding.bottom)
        }
    }
}*/

/**
 * Displays a 'fake' grid using [LazyColumn]'s DSL. It's fake in that we just we add individual
 * column items, with a inner fake row.
 */
fun <T> LazyListScope.itemsInGrid(
    items: List<T>,
    columns: Int,
    contentPadding: PaddingValues = PaddingValues(),
    horizontalItemPadding: Dp = 0.dp,
    verticalItemPadding: Dp = 0.dp,
    itemContent: @Composable (T) -> Unit
) {
    val rows = when {
        items.size % columns == 0 -> items.size / columns
        else -> (items.size / columns) + 1
    }

    for (row in 0 until rows) {
        if (row == 0) spacerItem(contentPadding.calculateTopPadding())

        item {
            Row(
                Modifier.fillMaxWidth()
                    .padding(start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = contentPadding.calculateEndPadding(LayoutDirection.Ltr))
            ) {
                for (column in 0 until columns) {
                    Box(modifier = Modifier.weight(1f)) {
                        val index = (row * columns) + column
                        if (index < items.size) {
                            itemContent(items[index])
                        }
                    }
                    if (column < columns - 1) {
                        Spacer(modifier = Modifier.requiredWidth(horizontalItemPadding))
                    }
                }
            }
        }

        if (row < rows - 1) {
            spacerItem(verticalItemPadding)
        } else {
            spacerItem(contentPadding.calculateBottomPadding())
        }
    }
}
