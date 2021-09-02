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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.populararticles.entities.Article
import com.example.populararticles.presentation.articles.screens.ArticleDetailsContent
import com.example.populararticles.presentation.articles.screens.ExploreContent
import com.google.gson.Gson
import java.io.Serializable


internal sealed class Screen(val route: String) {
    object Explore : Screen("explore")
    object Following : Screen("followingroot")
    object Watched : Screen("watchedroot")
    object Search : Screen("searchroot")
}

 sealed class LeafScreen(val route: String) {
    object Explore : LeafScreen("explore")
    object Account : LeafScreen("account")
    object Search : LeafScreen("search")

    object ArticleDetails : LeafScreen("articledetails") {
        fun createRoute(articleObject: String): String = "article/$articleObject"
    }


}

@ExperimentalFoundationApi
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
        addShowDetails(navController )
    }
}

@ExperimentalFoundationApi
private fun NavGraphBuilder.addExploreTopLevel(
    navController: NavController,
    openSettings: () -> Unit,
) {

    composable(Screen.Explore.route) {
        ExploreContent(navController = navController)
    }
/*    navigation(
        route = Screen.Explore.route,
        startDestination = LeafScreen.Explore.route
    ) {


     
    }*/
}

@Suppress("UNCHECKED_CAST")
fun <T> NavHostController.getArgument(name: String): T {
    return previousBackStackEntry?.arguments?.getSerializable(name) as? T
        ?: throw IllegalArgumentException()
}

fun NavHostController.putArgument(name: String, arg: Serializable?) {
    currentBackStackEntry?.arguments?.putSerializable(name, arg)
}
@ExperimentalFoundationApi
private fun NavGraphBuilder.addShowDetails(navController: NavController) {
    composable(
        route = LeafScreen.ArticleDetails.route/*,
        arguments = listOf(
            navArgument("articleId") { type = NavType.StringType }
        )*/
    ) { entry -> // Look up "name" in NavBackStackEntry's arguments
/*        val articleString = entry.arguments?.getString("articleId")
        val articleObject = Gson().fromJson(articleString, Article::class.java)*/
        ArticleDetailsContent(
            navController = navController
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



/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Explore) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                isRouteInDestinationChain(destination, Screen.Explore.route) -> {
                    selectedItem.value = Screen.Explore
                }
                isRouteInDestinationChain(destination, Screen.Watched.route) -> {
                    selectedItem.value = Screen.Watched
                }
                isRouteInDestinationChain(destination, Screen.Following.route) -> {
                    selectedItem.value = Screen.Following
                }
                isRouteInDestinationChain(destination, Screen.Search.route) -> {
                    selectedItem.value = Screen.Search
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

/**
 * Copied from similar function in NavigationUI.kt
 *
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-ui/src/main/java/androidx/navigation/ui/NavigationUI.kt
 */
private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

/**
 * Returns true if a destination with the given [route] is in the ancestor chain of [destination].
 */
private fun isRouteInDestinationChain(
    destination: NavDestination,
    route: String,
): Boolean {
    var currentDestination: NavDestination = destination
    while (currentDestination.route != route && currentDestination.parent != null) {
        currentDestination = currentDestination.parent!!
    }
    return currentDestination.route == route
}
