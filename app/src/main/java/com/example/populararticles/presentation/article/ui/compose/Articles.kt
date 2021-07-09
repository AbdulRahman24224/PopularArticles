package com.example.populararticles.presentation.article.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.example.populararticles.R
import com.example.populararticles.entities.Article
import com.example.populararticles.presentation.article.ArticleData
import com.example.populararticles.presentation.article.ui.articles.MainFragmentAction
import com.example.populararticles.presentation.article.ui.articles.OpenShowDetails
import com.example.populararticles.presentation.article.ui.articles.SwitchThemeAction
import com.example.populararticles.utils.compose.Carousel
import com.example.populararticles.utils.compose.components.ArticleCard
import com.example.populararticles.utils.compose.components.AutoSizedCircularProgressIndicator
import com.example.populararticles.utils.compose.rememberMutableState
import com.example.populararticles.utils.compose.sH
import com.example.populararticles.utils.compose.spacerItem
import com.google.accompanist.insets.statusBarsPadding


@Composable
fun Main(
    state: ArticleData,
    actioner: (MainFragmentAction , NavController ) -> Unit ,
    navController: NavController
) {

    /*
    *
    *
    *
    * val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val currentSelectedItem by navController.currentScreenAsState()

            HomeBottomNavigation(
                selectedNavigation = currentSelectedItem,
                onNavigationSelected = { selected ->
                    navController.navigate(selected.route) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(findStartDestination(navController.graph).id) {
                            saveState = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            AppNavigation(
                navController = navController,
                onOpenSettings = onOpenSettings
            )
        }
    }*/

    Scaffold(
        bottomBar = {
            val currentSelectedItem by navController.currentScreenAsState()

            HomeBottomNavigation(
                selectedNavigation = currentSelectedItem,
                onNavigationSelected = { selected ->
                    navController.navigate(selected.route) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(findStartDestination(navController.graph).id) {
                            saveState = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            AppNavigation(
                navController = navController,
                onOpenSettings = onOpenSettings
            )
        }
    }

    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            var appBarHeight by rememberMutableState { 0 }

            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    val height = with(LocalDensity.current) { appBarHeight.toDp() }
                    Spacer(Modifier.requiredHeight(height))
                }

                spacerItem(16.dp)

                item {
                    CarouselWithHeader(
                        items = state.articles,
                        title = stringResource(R.string.popular_articles),
                        refreshing = state.isLoading,
                        onItemClick = { article ->  actioner(OpenShowDetails(article) , navController) },
                        onMoreClick = { actioner(SwitchThemeAction , navController) }
                    )
                }

            }

            MainAppBar(
                loggedIn = false /*state.authState == TraktAuthState.LOGGED_IN*/,
                refreshing = false /*state.refreshing*/,
                onRefreshActionClick = { /*actioner(RefreshAction) */},
                onUserActionClick = { /*actioner(OpenUserDetails)*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { appBarHeight = it.height }
            )
        }
    }
}

@Composable
private fun  CarouselWithHeader(
    items: List<Article>,
    title: String,
    refreshing: Boolean,
    onItemClick: (Article) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        if (refreshing || items.isNotEmpty()) {
          16.sH(x = 0)

            Header(
                title = title,
                loading = refreshing,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = onMoreClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colors.secondary
                    ),
                    modifier = Modifier.alignBy(FirstBaseline)
                ) {
                    Text(text = stringResource(R.string.More))
                }
            }
        }
        if (items.isNotEmpty()) {
            ArticleCarousel(
                items = items,
                onItemClick = onItemClick,
                modifier = Modifier
                    .requiredHeight(350.dp)
                    .fillMaxWidth()
            )
        }

    }
}
@Composable
private fun  ArticleCarousel(
    items: List<Article>,
    onItemClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    Carousel(
        items = items,
        contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
        itemSpacing = 4.dp,
        modifier = modifier
    ) { item, padding ->
        ArticleCard(
            article = item,
            onClick = { onItemClick(item) },
            modifier = Modifier
                .padding(padding)
                .fillParentMaxHeight()
                .aspectRatio(2 / 3f)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Header(
    title: String,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    content: @Composable RowScope.() -> Unit = {}
) {
    Row(modifier) {
        Spacer(Modifier.requiredWidth(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
        )

        Spacer(Modifier.weight(1f))

        AnimatedVisibility(visible = loading) {
            AutoSizedCircularProgressIndicator(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(8.dp).requiredSize(16.dp)
            )
        }

        content()

        Spacer(Modifier.requiredWidth(16.dp))
    }
}

private const val TranslucentAppBarAlpha = 0.93f

@Composable
private fun MainAppBar(
    loggedIn: Boolean,/*
    user: TraktUser?,*/
    refreshing: Boolean,
    onRefreshActionClick: () -> Unit,
    onUserActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.surface.copy(alpha = TranslucentAppBarAlpha),
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 4.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .requiredHeight(56.dp)
                .padding(start = 16.dp, end = 4.dp)
        ) {
            Text(
                text = stringResource(R.string.main_articles),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(Modifier.weight(1f))

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                IconButton(
                    onClick = onRefreshActionClick,
                    enabled = !refreshing,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    if (refreshing) {
                        AutoSizedCircularProgressIndicator(Modifier.requiredSize(20.dp))
                    } else {
                        Icon(Icons.Default.Refresh , "")
                    }
                }

// should have same logic for logged in  users or not
             /*   IconButton(
                    onClick = onUserActionClick,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    when {
                        loggedIn && user?.avatarUrl != null -> {
                            CoilImage(
                                data = user.avatarUrl!!,
                                modifier = Modifier.preferredSize(32.dp).clip(CircleShape)
                            )
                        }
                        loggedIn -> IconResource(R.drawable.ic_person)
                        else -> IconResource(R.drawable.ic_person_outline)
                    }
                }*/
            }
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
    val selectedItem = remember { mutableStateOf<Screen>(BlendMode.Screen.Discover) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                isRouteInDestinationChain(destination, BlendMode.Screen.Discover.route) -> {
                    selectedItem.value = BlendMode.Screen.Discover
                }
                isRouteInDestinationChain(destination, BlendMode.Screen.Watched.route) -> {
                    selectedItem.value = BlendMode.Screen.Watched
                }
                isRouteInDestinationChain(destination, BlendMode.Screen.Following.route) -> {
                    selectedItem.value = BlendMode.Screen.Following
                }
                isRouteInDestinationChain(destination, BlendMode.Screen.Search.route) -> {
                    selectedItem.value = BlendMode.Screen.Search
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