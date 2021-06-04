package com.example.populararticles.presentation.article.ui.articles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.Navigation.findNavController
import com.example.populararticles.R
import com.example.populararticles.base.compose.preference.Preferences
import com.example.populararticles.base.compose.theme.AppTheme
import com.example.populararticles.databinding.ActivityArticlesBinding
import com.example.populararticles.presentation.article.ui.compose.HomeBottomNavigation
import com.example.populararticles.presentation.article.ui.compose.HomeNavigation
import com.example.populararticles.utils.compose.shouldUseDarkColors
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

enum class FilterType(val value: String) {
    DAILY("1"),
    WEEKLY("7"),
    MONTHLY("30")

}

private var currentSelectedItem by mutableStateOf(HomeNavigation.Main)

@AndroidEntryPoint
class ArticlesActivity : AppCompatActivity() {
    @Inject
    lateinit var preferences: Preferences
    private lateinit var binding: ActivityArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)
       binding.homeBottomNavigation .setContent {
           CompositionLocalProvider(
               LocalWindowInsets provides ViewWindowInsetObserver(binding.homeBottomNavigation).start()
           ){
                AppTheme(useDarkColors = preferences.shouldUseDarkColors()) {
                    HomeBottomNavigation(
                        selectedNavigation = currentSelectedItem,
                        onNavigationSelected = { item ->
                            currentSelectedItem = item
                           navigatTo(item)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    private fun navigatTo(item: HomeNavigation) {

        when(item){
            HomeNavigation.Main ->TODO()
            HomeNavigation.Bookmark ->  findNavController(binding.homeNavHost).navigate(R.id.DetailsFragment)
            HomeNavigation.Profile -> TODO()
            HomeNavigation.Search -> TODO()
        }
    }


}