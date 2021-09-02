package com.example.populararticles.entities



sealed class Either<out L, out R> {
    data class Left<out T>(val value: T) : Either<T, Nothing>()
    data class Right<out T>(val value: T) : Either<Nothing, T>()
}
inline fun <L, R> Either<L, R>.rightOr(default: (L) -> R): R = when (this) {
    is Either.Left<L> -> default(this.value)
    is Either.Right<R> -> this.value
}
inline fun <L, R> Either<L, R>.leftOr(default: (R) -> L): L = when (this) {
    is Either.Left<L> -> this.value
    is Either.Right<R> -> default(this.value)
}