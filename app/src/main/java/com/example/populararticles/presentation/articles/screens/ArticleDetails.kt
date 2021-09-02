package com.example.populararticles.presentation.articles.screens

import android.annotation.SuppressLint
import android.text.Html
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.populararticles.R
import com.example.populararticles.base.compose.theme.surfaceGradient
import com.example.populararticles.entities.Article
import com.example.populararticles.presentation.articles.viewmodel.*
import com.example.populararticles.utils.compose.components.AutoSizedCircularProgressIndicator
import com.example.populararticles.utils.compose.sH
import com.example.populararticles.utils.compose.sW
import com.example.populararticles.utils.extentions.horizontalGradientBackground
import com.google.accompanist.coil.rememberCoilPainter
import grayBlack



@ExperimentalFoundationApi
@Composable
fun ArticleDetailsContent(
    viewModel : ArticleDetailsViewmodel = hiltViewModel(),
                   navController : NavController
){
    val state  = viewModel.liveData.observeAsState(ArticleDetails())
   // val viewState by rememberSaveable{ state }

    val article = navController.previousBackStackEntry
        ?.arguments?.getSerializable("article") as Article?

    if (state.value.article==null && article!=null) LaunchedEffect(key1 = Unit, block = {
        viewModel.submitAction(ArticleDetailsIntents.InitializeCurrentArticle(article))
    })
    ArticleDetailsScreen(state.value ){ navController.popBackStack()}
}

@ExperimentalFoundationApi
@Composable
fun ArticleDetailsScreen(details: ArticleDetails?, isDarkTheme : Boolean =false  , onBack: () -> Unit) {

    val surfaceGradient = surfaceGradient(isDarkTheme)
    Scaffold(
        /*      bottomBar = { ArticleBottomBar(onBack) },
        floatingActionButton = { ArticleFloatingActionButton() },*/
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .horizontalGradientBackground(surfaceGradient)) {
            val scrollState = rememberScrollState(0)

         details?.article?.apply {

                 ArticleTopSection(this@apply, scrollState, onBack)
                 LazyColumn(
                     modifier = Modifier
                         .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        /* .verticalScroll(scrollState , true)*/
                 ) {

                    item {
                        460.sH(0)
                        StatisticsSection(this@apply)
                        /*  FavSection()
                    NewsSection(Article)*/
                        50.sH(x = 0)
                    }

                 }


            }

        }
    }
}

@SuppressLint("NewApi")
@Composable
fun ArticleTopSection(article: Article, scrollState: ScrollState, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .requiredHeight(450.dp)
            .alpha(animateFloatAsState((1 - scrollState.value / 150 +0.2f).coerceIn(0f, 1f)).value)
    ) {

        article?.apply {

                Text(modifier = Modifier.padding( 20.dp),text = title, style = typography.h6)

            media?.apply {
                val media = if (isNotEmpty()) get(0) else null
                  val url =   media?.mediaMetadata?.get(2)?.url?:""

                Image(
                   painter = rememberCoilPainter(request = url)
                   ,
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )

                Text(
                    modifier = Modifier
                        .padding(8.dp, 16.dp)
                        .align(Alignment.CenterHorizontally) ,
                    text =  media?.caption?:"No Caption",
                    style = typography.caption,
                    color = grayBlack()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_user_list),
                    "",
                    modifier = Modifier
                        .requiredSize(32.dp)
                        .clip(CircleShape)
                )
                10.sW(0)

                Text(
                    text = article.byline ?: "No Caption",
                    style = typography.subtitle1,
                    color = grayBlack()
                )
            }
            }
        }
    }


@SuppressLint("NewApi")
@Composable
fun StatisticsSection(article: Article) {
    val valueModifier = Modifier.padding(bottom = 16.dp, top = 4.dp)

    val html = Html.fromHtml(article.details , HtmlCompat.FROM_HTML_MODE_LEGACY)
    Card(
        modifier = Modifier.padding(vertical = 8.dp).fillMaxSize(),
        elevation = 8.dp,
        shape = RoundedCornerShape(4.dp)
    ) {

        if (html.toString().isBlank())
        AutoSizedCircularProgressIndicator(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(30.dp)
                .requiredSize(30.dp)
        )
        else
        Text(
            text =  html.toString(),
            modifier = Modifier.padding(8.dp),
            // font sizes should be extracted into styles values
            style = typography.body1.merge(androidx.compose.ui.text.TextStyle(fontSize = 10.sp))
        )
    }
}

/*@Composable
fun ArticleBottomBar(onBack: () -> Unit) {
    BottomAppBar(
        cutoutShape = CircleShape
    ) {
        IconButton(onClick = { onBack }) {
            Icon(imageVector = Icons.Default.ArrowBack)
        }
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Default.MoreVert)
        }
    }
}

@Composable
fun ArticleFloatingActionButton() {
    var pressed by remember { mutableStateOf(false) }
    ExtendedFloatingActionButton(
        icon = { Icon(imageVector = Icons.Default.Add) },
        text = { Text(text = "Trade") },
        onClick = { pressed = !pressed },
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.width(animateAsState(if (pressed) 200.dp else 120.dp).value)
    )
}*/



/*@Composable
fun FavSection() {
    val viewModel: ArticleDetailViewModel = viewModel()
    val favArticles by viewModel.favArticleLiveData.observeAsState(emptyList())
    if (favArticles.isNotEmpty()) {
        Text(
            text = "Favorite Articles",
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
            style = typography.h5
        )
        LazyRow {
            items(
                items = favArticles,
                itemContent = { FavoriteArticleCard(Article = it) })
        }
    }
}*/

