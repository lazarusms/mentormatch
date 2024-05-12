package com.example.mentormatch

import androidx.annotation.DrawableRes
import com.example.mentormatch.R

data class MatchProfile(
    val name: String,
    @DrawableRes val drawableResId: Int,
    val faculdade: Faculdade,
)

val profiles = listOf(
    MatchProfile("TESTE Bachman", R.drawable.erlich, Faculdade.FIAP),
    MatchProfile("Richard Hendricks", R.drawable.richard, Faculdade.UNICID),
    MatchProfile("Laurie Bream", R.drawable.laurie, Faculdade.UNICID),
    MatchProfile("Russ Hanneman", R.drawable.russ, Faculdade.UNICID),
    MatchProfile("Dinesh Chugtai", R.drawable.dinesh, Faculdade.UNICID),
    MatchProfile("Monica Hall", R.drawable.monica, Faculdade.UNICID),
    MatchProfile("Bertram Gilfoyle", R.drawable.gilfoyle, Faculdade.UNICID),

    MatchProfile("Peter Gregory", R.drawable.peter, Faculdade.UNICID),
    MatchProfile("Jared Dunn", R.drawable.jared, Faculdade.UNICID),
    MatchProfile("Nelson Bighetti", R.drawable.big_head, Faculdade.UNICID),
    MatchProfile("Gavin Belson", R.drawable.gavin, Faculdade.UNICID),
    MatchProfile("Jian Yang", R.drawable.jian, Faculdade.UNICID),
    MatchProfile("Jack Barker", R.drawable.barker, Faculdade.UNICID),
)

enum class Faculdade { FIAP,UNICID, USP, UNINOVE, TESTE }