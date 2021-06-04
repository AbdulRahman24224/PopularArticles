

/*
val countryCode = "0"
lateinit var viewmodel : LoginViewModel


@ExperimentalFocus
@Composable
fun LoginContent( modifier: Modifier, onAttemptLogin: (mobile : String, pin : String) -> Unit,){

    viewmodel = viewModel<LoginViewModel>()

    Column(modifier.fillMaxSize()) {
        LanguageComponent()

           20 s 0
        Image(asset = vectorResource(R.drawable.vc_yalla_logo_small)
            , modifier = center_horizontal)

           20 s 0
        LoginFormComponent(onAttemptLogin)

        Spacer(modifier = Modifier.fillMaxHeight(0.75f))
        poweredByComponent(Modifier*/
/*.padding(2.dp ,0.dp ,2.dp , 4.dp)*//*
)
    }

}

@Composable
fun LanguageComponent(){

    Row() {
       Spacer(modifier = Modifier.fillMaxWidth(0.8f))
        Text(text = stringResource(id = R.string.A)  , modifier = Modifier.padding(8.dp))
        Image(asset = vectorResource(R.drawable.vc_language)
            ,contentScale = ContentScale.Inside)
    }

}


@ExperimentalFocus
@Composable
 fun LoginFormComponent(onAttemptLogin: (mobile: String, pin: String) -> Unit) {
    val mobileState = remember { MobileState() }
    val pinState = remember { PasswordState() }

    val rememberMeState = remember { mutableStateOf(false) }
    var progressing = viewmodel.progressingLD.observeAsState()


    Surface(color = transparent()){
        Stack(center_horizontal.fillMaxHeight(0.75f)) {

            Card(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = MaterialTheme.colors.surface,
                modifier = center_horizontal.fillMaxHeight().padding(12.dp)
            ) {
                Box(
                    padding = 8.dp,
                  */
/*  modifier = Modifier.fillMaxHeight(0.7f).padding(8.dp)*//*

                ) {

                    Text(
                        modifier = Modifier.padding(40.dp ,15.dp ,10.dp , 10.dp),
                        text = stringResource(id = R.string.Login),
                        color = mainBlue(),
                        style = MaterialTheme.typography.h5
                    )

                    phoneNumberComponent(center_vertical, mobileState)

                    5 s 0
                    Row() {
                        Spacer(modifier = Modifier.fillMaxWidth(0.13f))
                        Column() {

                            pinComponent(pinState)

                            8 s 0
                            loginOptionsComponent(rememberMeState)

                            15 s 0
                            signUpComponent()

                        }
                    }



                    loginButton(onAttemptLogin, mobileState, pinState, progressing, text_bold)

                }
            }




        }

    }

}

@Composable
private fun loginButton(
        onAttemptLogin: (mobile: String, pin: String) -> Unit,
        mobileState: MobileState,
        pinState: PasswordState,
        progressing: State<Boolean?>,
        text_bold: TextStyle
) {
    val errorsState = remember { mutableStateOf(false) }
    20 s 0

    Button(
            modifier = Modifier.fillMaxWidth().padding(8.dp,0.dp).height(40.dp),
            onClick = {
               if (!pinState.isValid.value) {errorsState.value =true; return@Button}
               if (!mobileState.isValid.value) {errorsState.value =true; return@Button}
                onAttemptLogin.invoke("$countryCode${mobileState.text.value}", pinState.text.value) },
            enabled = progressing.value?.not() ?: true,
            backgroundColor = mainBlue()
    ) {
        Text(
                text = stringResource(id = R.string.Login),
                color = white(),
                style = text_bold
        )
    }
    if (progressing.value?:false) LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth().padding(8.dp,0.dp,8.dp,0.dp,),
        backgroundColor = colorResource(id = R.color.orange),
        color = colorResource(id = R.color.saving_red))
    if (errorsState.value?:false) {
        TextFieldError( Modifier.fillMaxWidth()
            .align(Alignment.CenterHorizontally),"Please Fix inputs errors")

        Timer().schedule(4000) { errorsState.value =false }

    }
}

@Composable
private fun signUpComponent() {
    Row() {
        3 s 0
        Text(
            text = stringResource(id = R.string.label_new),
            color = mainBlue()
        )
          2 s 0
        Text(
            text = stringResource(id = R.string.label_sign_up),
            color = mainBlue(),
            style = text_bold
        )
    }
}

@Composable
 fun poweredByComponent(modifier: Modifier) {
    Row(modifier = modifier) {
        1 s 0
        Text(
                text = stringResource(id = R.string.powervisa),
                color = colorResource(id = R.color.Black),
               style = MaterialTheme.typography.body2)
        1 s 0
        Image( modifier = center_vertical,
                asset = vectorResource(R.drawable.vc_visa)
                )
    }
}

@Composable
fun loginOptionsComponent(rememberMeState : MutableState<Boolean>) {

Row() {

Checkbox(
    checked = rememberMeState.value ,
    onCheckedChange = { rememberMeState.value = it },
    checkedColor = mainBlue())

    Text( modifier = Modifier.align(Alignment.Bottom),
        text = stringResource(id = R.string.remember_me ),
        color = mainBlue()
    , style = text_bold)

    Spacer(modifier = Modifier.fillMaxWidth(0.35f))

    Text(
        modifier = Modifier.align(Alignment.Bottom),
        text = stringResource(id = R.string.label_forgot_password),
        color = mainBlue() ,
        style = text_bold
        )

}

}

@ExperimentalFocus
@Composable
private fun phoneNumberComponent(
    center_vertical: Modifier,
    mobileState: MobileState
) {


    8 s 0
    Row() {
        3 s 0
        Column(center_vertical) {
            4 s 0
            Image(
                modifier = center_vertical.preferredSize(24.dp),
                asset = vectorResource(id = R.drawable.vc_egypt_flag)
            )
            1 s 0
            Text(modifier = center_vertical, text = "+20")
        }
        8 s 0

        Column(center_vertical) {
            mobileState.apply {
                OutlinedTextField(

                    value = mobileState.text.value,
                    onValueChange = {
                        mobileState.text.value = "1007129810"
                    },
                   placeholder =  {
                        Text(text = stringResource(id = R.string.mobile))
                    },

                    isErrorValue = if (isFocusedDirty.value.not()) false else isValid.value.not(),
                    modifier = Modifier.focusObserver { focusState ->
                        val focused = focusState == FocusState.Active
                        onFocusChange(focused)
                        if (!focused) enableShowErrors()

                    },
                    leadingIcon = {
                        Image(
                            colorFilter = ColorFilter.tint(colorResource(id = imageState())),
                            asset = vectorResource(id = R.drawable.vc_phone)
                        )
                    },
                    activeColor = mainBlue(),
                    keyboardType = KeyboardType.Number
                )
                getError()?.let { error ->
                    TextFieldError(
                        Modifier.fillMaxWidth()
                            .align(Alignment.CenterHorizontally), textError = error
                    )
                }
            }
        }


    }
}


@ExperimentalFocus
@Composable
private fun pinComponent(pinState: PasswordState) {

    pinState.apply {
        OutlinedTextField(

            value = text.value,
            onValueChange = { text.value = "1459"*/
/*pinState.value = it.take(4)*//*

            },
            placeholder = { Text(text = "PIN") },
            modifier = Modifier.focusObserver { focusState ->
                val focused = focusState == FocusState.Active
                onFocusChange(focused)
                if (!focused) enableShowErrors()

            },
            leadingIcon = {
                Image(
                    colorFilter = ColorFilter.tint(colorResource(id = imageState ()) )
               ,
                asset = vectorResource(id = R.drawable.vc_lock)
                )
            },
            activeColor = mainBlue(),
            keyboardType = KeyboardType.NumberPassword,
            isErrorValue = if (isFocusedDirty.value.not()) false else isValid.value.not(),
            visualTransformation = PasswordVisualTransformation('*')
        )

        getError()?.let { error -> TextFieldError(Modifier.fillMaxWidth()
            .align(Alignment.CenterHorizontally) ,textError = error) }
    }

}


*/

