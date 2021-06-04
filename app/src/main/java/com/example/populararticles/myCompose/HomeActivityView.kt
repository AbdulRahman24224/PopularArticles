

/*
@ExperimentalFocus
@Composable
fun HomeActivityContent( modifier: Modifier){

    val fabShape = CircleShape
    val scaffoldState = rememberScaffoldState()

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = mainBlue(),
            navigationIcon = { },
            title = { stringResource(id = R.string.label_sign_up) }*/
/*,
            actions = { Icon(asset = R.drawable.vc_arrow_right) }*//*

        )
        */
/*   { actionImage ->
            AppBarIcon(actionImage) {  do something here  }
        }*//*

    },
        bottomBar = {
            BottomAppBar(
                backgroundColor = mainBlue(),
                cutoutShape = fabShape
            ) {
                TabRow(selectedTabIndex = 0,
                        backgroundColor = transparent() ,
                                tabs = {
                            Tab(
                                selected = true,
                                onClick = { Log.d("clicked", "column1") },
                                text = { "fgdfgg" },
                                icon = { Icon(asset = vectorResource(id = R.drawable.vc_home)) }

                            )
                                    Tab(
                                        selected = true,
                                        onClick = { Log.d("clicked", "column1") },
                                        text = { "fgdfgg" },
                                        icon = { Icon(asset = vectorResource(id = R.drawable.vc_yalla_money)) }

                                    )
                                    Tab(
                                        selected = true,
                                        onClick = { Log.d("clicked", "column1") },
                                        text = { "fgdfgg" },
                                        icon = { Icon(asset = vectorResource(id = R.drawable.vc_social)) }

                                    )
                                    Tab(
                                        selected = true,
                                        onClick = { Log.d("clicked", "column1") },
                                        text = { "fgdfgg" },
                                        icon = { Icon(asset = vectorResource(id = R.drawable.ic_about)) }

                                    )

                        })
                    */
/*     TabRow(
                        items = tabTitles, selectedIndex = tab.ordinal
                    ) { index, text ->
                        Tab(
                            text = text,
                            selected = tab.ordinal == index
                        )
                        {
                            tab = Tabs.values()[index]
                        }
                    }
                    when (tab) {
                        Tabs.Tab1 -> // inflate the first tab
                            Tabs.Tab2 -> // inflate the second tab
                    }*//*

                    */
/*   Column(modifier = Modifier.clickable(onClick = {
                        Log.d("clicked", "column1")
                    })) {
                        Image(
                            asset = vectorResource(R.drawable.vc_language),
                            contentScale = ContentScale.Inside
                        )
                        Text(
                            text = stringResource(id = R.string.A),
                            modifier = Modifier.padding(4.dp)
                        )
                    }*//*



            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = white(),
                */
/*contentColor = colorResource(id = R.color.transparent),*//*


                shape = fabShape,
                onClick = {}
            ) {
                Icon(asset = vectorResource(id = R.drawable.vc_yalla_colored))
            }
        }, bodyContent = {
            //bodyContent()
        })
}
*/
