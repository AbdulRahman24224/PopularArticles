

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/*class PasswordState : TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)
class MobileState : TextFieldState(validator = ::isMobileValid, errorFor = ::mobileValidationError)*/


/*class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val
        get() = passwordAndConfirmationValid(passwordState.text.value, text.value)

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}*/

private fun isPasswordValid(password: String ): MutableState<Boolean> {
    val valid = password.length == 4 && password.isNotBlank()
    return mutableStateOf( valid)
}
private fun isMobileValid(mobile: String ): MutableState<Boolean> {
    val valid = mobile.length == 10 && mobile.isNotBlank()
    return mutableStateOf( valid)
}

private fun passwordValidationError(password: String): String {
    return "Invalid PIN Length"
}
private fun mobileValidationError(password: String): String {
    return "Invalid Phone Number Length"
}

private fun passwordConfirmationError(): String {
    return "Passwords don't match"
}