

/*

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.ui.tooling.preview.Preview
import com.surrus.bikeshare.R
import com.surrus.bikeshare.ui.home.main.HomeActivity
import com.surrus.bikeshare.ui.login.LoginActivity

import com.surrus.bikeshare.ui.login.poweredByComponent

import com.surrus.bikeshare.utils.Extentions.toMutableLiveData
import com.surrus.bikeshare.utils.compose.max_max
import com.surrus.bikeshare.utils.compose.text_bold
import com.surrus.common.data.SessionManager
import com.surrus.common.remote.models.Data
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    var visibility = false.toMutableLiveData()
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { SplashScreen(visibility) }
        window.decorView.post { visibility.postValue(true)
            Timer().schedule(3000) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }}

    }



    private fun updateNavigation(model: Data) {
        SessionManager.instance?.apply {
            empData = model
            isLogin = true
        }
           // Toast.makeText(this@LoginActivity, "${model}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
               finish()

    }

    }

@ExperimentalAnimationApi
@Composable
fun SplashScreen(visibility: MutableLiveData<Boolean>) {

    MaterialTheme {
        Surface(color = colorResource(id = R.color.Black)) {

            Stack(max_max) {

                    Image(
                        asset = imageResource(R.drawable.vc_yalla_background),
                        Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth
                    )



                ConstraintLayout(max_max) {
                    val (column, powered) = createRefs()
                    val guide8 = createGuidelineFromTop(0.8f)

                    val powered_modifier  = Modifier.constrainAs(powered){
                        bottom .linkTo(parent.bottom)
                        start .linkTo(parent.start)
                        end.linkTo(parent.end)

                    }

                    val yalla_modifier =   Modifier.constrainAs(column){
                                top.linkTo(parent.top)
                                bottom .linkTo(guide8)
                                centerHorizontallyTo(parent)
                            }



                    AnimatedVisibility(
                            modifier = yalla_modifier,
                            visible = visibility.observeAsState().value ?:false,
                            enter = slideIn(
                                    { fullSize -> IntOffset(100, 0) },
                                    tween(2000, easing = LinearOutSlowInEasing)
                            ), */
/*+fadeIn()*//*

                    ) {
                    Column(
                     modifier =  yalla_modifier.padding(0.dp,0.dp,0.dp,20.dp),
                       verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Image(asset = vectorResource(R.drawable.vc_yalla_logo_small)
                            , Modifier.preferredSize(200.dp)
                        )

                        Text(
                            modifier = Modifier.padding(40.dp ,15.dp ,10.dp , 10.dp),
                            text = stringResource(id = R.string.every_thing),
                            color = colorResource(id = R.color.dashboardBlue),
                            fontSize = TextUnit(25),
                            style = text_bold
                        )

                    }
                    }

                 */
/*   Button(
                            modifier = Modifier.fillMaxWidth().padding(8.dp).height(40.dp),
                            onClick = {visibility.value = visibility.value.not() },

                            backgroundColor = colorResource(id = R.color.dashboardBlue)
                    ) {
                        Text(
                                text = stringResource(id = R.string.Login),
                                color = colorResource(id = R.color.White),
                                style = text_bold
                        )
                    }*//*

                    poweredByComponent(powered_modifier.padding(0.dp,0.dp,0.dp,28.dp,))

                }







            }





        }
    }
}

@Preview
@Composable
fun previewLogin (){

}
*/

