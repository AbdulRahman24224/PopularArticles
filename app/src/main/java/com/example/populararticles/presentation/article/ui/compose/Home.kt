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

package com.example.populararticles.presentation.article.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.populararticles.R
import com.google.accompanist.insets.navigationBarsHeight

internal enum class HomeNavigation {
    Main,
    Bookmark,
    Profile,
    Search,
}


@Composable
internal fun HomeBottomNavigation(
    selectedNavigation: HomeNavigation,
    onNavigationSelected: (HomeNavigation) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = contentColorFor(MaterialTheme.colors.surface),
        elevation = 8.dp,
        modifier = modifier
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth().requiredHeight(56.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BottomNavigationItem(
                    icon = {  Icon(Icons.Filled.Menu,"") },
                    label = { Text(stringResource(R.string.explore)) },
                    selected = selectedNavigation == HomeNavigation.Main,
                    onClick = { onNavigationSelected(HomeNavigation.Main) },
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.FavoriteBorder ,"") },
                    label = { Text(stringResource(R.string.bookMarks)) },
                    selected = selectedNavigation == HomeNavigation.Bookmark,
                    onClick = { onNavigationSelected(HomeNavigation.Bookmark) },
                )

                BottomNavigationItem(
                    icon = {Icon(Icons.Filled.VerifiedUser ,"") },
                    label = { Text(stringResource(R.string.profile)) },
                    selected = selectedNavigation == HomeNavigation.Profile,
                    onClick = { onNavigationSelected(HomeNavigation.Profile) },
                )

                BottomNavigationItem(
                    icon = {Icon(Icons.Filled.Search ,"") },
                    label = { Text(stringResource(R.string.search)) },
                    selected = selectedNavigation == HomeNavigation.Search,
                    onClick = { onNavigationSelected(HomeNavigation.Search) },
                )




            }

            Spacer(modifier = Modifier.navigationBarsHeight())
        }
    }
}
