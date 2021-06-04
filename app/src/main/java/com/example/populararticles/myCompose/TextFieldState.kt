


/*const val Invalid = R.color.dark_red
const val Focused = R.color.dashboardBlue
const val NotFocused = R.color.textDarkGrey*/
/*
open class TextFieldState(
    private val validator: (String ) ->  MutableState<Boolean> = { mutableStateOf(false) },
    private val errorFor: (String) -> String = { "" }
) {
    var text = mutableStateOf("")
    // was the TextField ever focused
    val isFocusedDirty by lazy { mutableStateOf(false) }
    var isFocused= mutableStateOf(false)
    private var displayErrors= mutableStateOf(false)

    open val isValid: MutableState<Boolean>
        get() =  validator(text.value)

    fun onFocusChange(focused: Boolean) {
        isFocused.value = focused
        if (focused) isFocusedDirty.value = true
    }

    fun enableShowErrors() {
        // only show errors if the text was at least once focused
        if (isFocusedDirty.value) {
            displayErrors.value = true
        }
    }

    fun showErrors() = isValid.value.not() && displayErrors.value

    fun imageState() : Int {
        if (isFocusedDirty.value.not()) return NotFocused
       if (isValid.value.not()) return Invalid
        else if (isFocused.value) return Focused
        else return NotFocused

    }


    open fun getError(): String? {
        return if (showErrors()) {
            errorFor(text.value)
        } else {
            null
        }
    }
}*/
