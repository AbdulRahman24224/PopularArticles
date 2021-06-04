/*
lateinit var viewmodel :HomeViewModel
@Composable
fun HomeContent(){
     viewmodel = viewModel<HomeViewModel>()

    ScrollableColumn(Modifier.fillMaxSize()) {

        categoryTitleComponent(R.string.Yalla_Service ,{ Log.d("SErvice" ,"clicked")})
        categoriesList()

        categoryTitleComponent(R.string.home_yalla_bills ,{ Log.d("Bills" ,"clicked")})
        billsList()

        categoryTitleComponent(R.string.Yalla_Offers ,{ Log.d("Offers" ,"clicked")})
        val items = listOf(Offer() , Offer() , Offer())
        val selectedPage = remember { mutableStateOf(0) }
        offersPager(items , selectedPage = selectedPage)
        pagerIndicator(items, selectedPage)

        categoryTitleComponent(R.string.yalla_news ,{ Log.d("news" ,"clicked")})
        newsList()

    }
}

@Composable
private fun billsList() {
    Keyboard.Row(horizontalArrangement = Arrangement.SpaceBetween) {
        for (item in 0..4) roundCategoryItem(text = "Category 1")
    }
    2 s 0
    Keyboard.Row(horizontalArrangement = Arrangement.SpaceBetween) {
        for (item in 0..4) roundCategoryItem(text = "Category 2")
    }
}

@Composable
private fun pagerIndicator(
    items: List<Offer>,
    selectedPage: MutableState<Int>
) {
    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
        items.forEachIndexed { index, _ ->
            CarouselDot(
                selected = index == selectedPage.value,
                mainBlue(),
                Icons.Filled.CheckCircle
            )
        }
    }
}

@Composable
fun categoryTitleComponent(stringId :Int , onclick : () -> Unit){

    Row() {

        Surface(modifier = center_vertical.height(20.dp).width(7.dp) ,color = mainBlue()){}
        4 s 0
        Text(
            color = mainBlue(),

            style = text_bold.merge(
                TextStyle(fontSize = 17.sp)),
            text = stringResource(id = stringId),
            modifier = Modifier.padding(8.dp)
        )

        Row(Modifier.weight(1f).align(Alignment.CenterVertically)
            .clickable(onClick = { onclick.invoke() }) ,horizontalArrangement = Arrangement.End) {
        Text(text = stringResource(id = R.string.View_all)  , modifier = Modifier.padding(4.dp))
        Image(modifier = center_vertical,asset = vectorResource(R.drawable.vc_arrow_right)
            ,contentScale = ContentScale.Inside)
        }
        4 s 0
    }

}

@Composable
fun categoriesList(){

    LazyRowFor(items = viewmodel.categoryList.observeAsState().value ?: mutableListOf(),
        modifier = Modifier,
        itemContent = { item ->

            item.apply {
                roundedCard(content = {
                    
                   NetworkImage(url = "https://grey.paysky.io:7006/CMS2/$CategoryImageActive")

                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = CategoryName,
                        color = mainBlue(),
                        style = TextStyle(fontSize = 17.sp)
                    )
                })
            }
        })

}

@Composable
fun newsList(){

    LazyRowFor(items = viewmodel.newsList.observeAsState().value ?: mutableListOf(),
        modifier = Modifier,
        itemContent = { item ->
            item.apply {
                roundCategoryItem(websiteName , websiteImage)
            }
        })

}


@Composable
fun offersPager( items: List<Offer>,
            pagerState: PagerState = run {
                val clock = AnimationClockAmbient.current
                remember(clock) { PagerState(clock) }
            }
                 , modifier: Modifier = Modifier.preferredHeight(150.dp)
                 , selectedPage : MutableState<Int>
) {
    pagerState.maxPage = (items.size - 1).coerceAtLeast(0)

    Pager(
        state = pagerState,
        modifier = modifier
    ) {
        selectedPage.value = page
        offerCarouselItem(
            imgUrl ="",
            modifier = Modifier.padding(4.dp)
                .scalePagerItems(unselectedScale = unselectedScale)
        )
    }
}


@Composable
private fun offerCarouselItem(
    imgUrl: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    ) {

            Image(
                asset = imageResource(R.drawable.shop_1)*/
/*,
                Modifier.fillMaxSize()*//*
,
                contentScale = ContentScale.Fit
            )
        */
/* CoilImage(
             data = imgUrl?:"",
             contentScale = ContentScale.Crop,
             loading = { *//*
*/
/* TODO do something better here *//*
*/
/* },
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                )*//*



   }
}

*/


/*@Composable
private fun roundCategoryItem(
    text: String? = null,
    imgUrl: String?=null
) {
    Surface(color = transparent()){
        Stack() {
            Column(modifier = center_horizontal.padding(6.dp ,4.dp)) {


                Card(
                    shape = CircleShape,
                    border = BorderStroke(1.dp, colorResource(id = R.color.lightest_grey)),

                    modifier = center_horizontal.preferredHeight(70.dp).preferredWidth(70.dp).padding(6.dp ,6.dp)
                ) {
                    Box( modifier = Modifier.padding(0.dp ,2.dp,0.dp,0.dp)) {

                        val imageModifier = Modifier.preferredHeight(50.dp).preferredWidth(50.dp).padding(8.dp)
                        if (imgUrl == null) Image(
                            asset = vectorResource(R.drawable.vc_gesture),
                            modifier = imageModifier,
                            contentScale = ContentScale.Fit
                        )

                        else NetworkImage(imgUrl?:"" ,contentScale = ContentScale.Inside , modifier = imageModifier )

                    }
                }

                Text(text = text ?:"" ,
                    modifier = center_horizontal
                    ,style = TextStyle (fontSize = 14.sp))
            }
        }
    }
}*/
