package com.example.populararticles.presentation.articles

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.populararticles.R
import com.example.populararticles.base.AppNavigation
import com.example.populararticles.base.Screen
import com.example.populararticles.base.compose.preference.Preferences
import com.example.populararticles.base.compose.theme.AppTheme
import com.example.populararticles.presentation.articles.screens.MainAppBar
import com.example.populararticles.utils.compose.shouldUseDarkColors
import com.google.accompanist.insets.navigationBarsHeight
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

enum class FilterType(val value: String) {
    DAILY("1"),
    WEEKLY("7"),
    MONTHLY("30")

}

internal enum class HomeNavigation {
    Main,
    Bookmark,
    Profile,
    Search,
}

private var currentSelectedItem by mutableStateOf(HomeNavigation.Main)

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var preferences: Preferences
    lateinit var navController : NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
           CompositionLocalProvider(
             // LocalWindowInsets provides ViewWindowInsetObserver(this).start()
           ){
                AppTheme(useDarkColors = preferences.shouldUseDarkColors()) {

                     navController = rememberNavController()
                    val backstackEntry = navController.currentBackStackEntryAsState()
                  ///  val currentScreen = AppNavigaton.fromRoute(backstackEntry.value?.destination?.route)

                    Scaffold (
                        topBar = {
                            MainAppBar(
                                loggedIn = false /*state.authState == TraktAuthState.LOGGED_IN*/,
                                refreshing = false /*state.refreshing*/,
                                onRefreshActionClick = { /*actioner(RefreshAction) */},
                                onUserActionClick = { /*actioner(OpenUserDetails)*/ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                  //  .onSizeChanged { app = it.height }
                            )
                        } ,
                        bottomBar =
                        {
                            HomeBottomNavigation(
                                selectedNavigation = currentSelectedItem,
                                onNavigationSelected = { item ->
                                    currentSelectedItem = item
                                    navigatTo(item)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                            ) {

                        AppNavigation( navController ,{} )
                    }
                }
            }
        }
    }

    private fun navigatTo(item: HomeNavigation) {

        when(item){
            HomeNavigation.Main ->TODO()
            HomeNavigation.Bookmark ->  navController.navigate(Screen.Explore.route)
            HomeNavigation.Profile -> TODO()
            HomeNavigation.Search -> TODO()
        }
    }


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
                    icon = { Icon(Icons.Filled.VerifiedUser ,"") },
                    label = { Text(stringResource(R.string.profile)) },
                    selected = selectedNavigation == HomeNavigation.Profile,
                    onClick = { onNavigationSelected(HomeNavigation.Profile) },
                )

                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Search ,"") },
                    label = { Text(stringResource(R.string.search)) },
                    selected = selectedNavigation == HomeNavigation.Search,
                    onClick = { onNavigationSelected(HomeNavigation.Search) },
                )




            }

            Spacer(modifier = Modifier.navigationBarsHeight())
        }
    }
}
