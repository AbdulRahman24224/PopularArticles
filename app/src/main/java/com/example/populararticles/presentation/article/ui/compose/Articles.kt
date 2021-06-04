package com.example.populararticles.presentation.article.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    actioner: (MainFragmentAction) -> Unit
) {

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
                        onItemClick = { article ->  actioner(OpenShowDetails(article)) },
                        onMoreClick = { actioner(SwitchThemeAction) }
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