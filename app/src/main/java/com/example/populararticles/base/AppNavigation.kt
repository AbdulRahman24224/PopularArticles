/*
 * Copyright 2021 Google LLC
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

package com.example.populararticles.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navigation
import com.example.populararticles.presentation.article.ui.compose.ArticleDetailsScreen


internal sealed class Screen(val route: String) {
    object Explore : Screen("exploreroot")
    object Following : Screen("followingroot")
    object Watched : Screen("watchedroot")
    object Search : Screen("searchroot")
}

private sealed class LeafScreen(val route: String) {
    object Explore : LeafScreen("explore")
    object Account : LeafScreen("account")
    object Search : LeafScreen("search")

    object ArticleDetails : LeafScreen("article/{articleId}") {
        fun createRoute(articleId: Long): String = "article/$articleId"
    }


}

@Composable
internal fun AppNavigation(
    navController: NavHostController,
    onOpenSettings: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Explore.route ,
        /*modifier = Modifier.then()*/

    ) {
        addExploreTopLevel(navController, onOpenSettings)

    }
}

private fun NavGraphBuilder.addExploreTopLevel(
    navController: NavController,
    openSettings: () -> Unit,
) {
    navigation(
        route = Screen.Explore.route,
        startDestination = LeafScreen.Explore.route
    ) {
      
        addShowDetails(navController)
     
    }
}


private fun NavGraphBuilder.addShowDetails(navController: NavController) {
    composable(
        route = LeafScreen.ArticleDetails.route,
        arguments = listOf(
            navArgument("articleId") { type = NavType.LongType }
        )
    ) {
        ArticleDetailsScreen(
            navigateUp = {
                navController.popBackStack()
            },
            openShowDetails = { articleId ->
                navController.navigate(LeafScreen.ArticleDetails.createRoute(articleId))
            }
        )
    }
}


private fun NavGraphBuilder.addAccount(
    navController: NavController,
    onOpenSettings: () -> Unit,
) {
    composable(LeafScreen.Account.route) {
        // This should really be a dialog, but we're waiting on:
        // https://issuetracker.google.com/179608120
        Dialog(
            onDismissRequest = { navController.popBackStack() }
        ) {
           // AccountUi(navController, onOpenSettings)
        }
    }
}
