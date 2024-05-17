package com.example.mentormatch

import androidx.annotation.DrawableRes
import com.example.mentormatch.R

data class MatchProfile(
    val name: String,
    val bio: String,
    @DrawableRes val drawableResId: Int,
    val university: University,
    val field: Field,
    val hobbie: Hobbie,
    val city: City,
    val available: Available,
    val assignment: Assignment
)

val profiles = listOf(
    MatchProfile("Taylor Swift", "Formada em ADS pela PUC, programo em Java e meu hobbie é comer. Estou disponível.", R.drawable.taylorswift, University.PUC, Field.TECNOLOGIA, Hobbie.COMER, City.COTIA, Available.SIM, Assignment.MENTOR),
    MatchProfile("Charlie Harper", "Estudante de matemática na FIAP, adoro ler. Atualmente, não estou disponível.", R.drawable.charlie, University.FIAP, Field.TECNOLOGIA, Hobbie.PROGRAMAR, City.OSASCO, Available.SIM, Assignment.APRENDIZ),
    MatchProfile("Sheldon Cooper", "Estudante de design na FIAP, adoro viajar. Estou disponível.", R.drawable.sheldon, University.FIAP, Field.DESIGN, Hobbie.VIAJAR, City.SAOPAULO, Available.SIM, Assignment.APRENDIZ),
    MatchProfile("Howard Wolowitz", "Formado em economia pela PUC, meu hobbie é programar. Estou disponível.", R.drawable.howard, University.PUC, Field.ECONOMIA, Hobbie.PROGRAMAR, City.COTIA, Available.SIM, Assignment.MENTOR),
    MatchProfile("Dinesh Chugtai", "Estudante de design na FIAP, adoro programar. Estou disponível.", R.drawable.dinesh, University.FIAP, Field.DESIGN, Hobbie.PROGRAMAR, City.SAOPAULO, Available.SIM, Assignment.APRENDIZ),
    MatchProfile("Alan Harper", "Formado em tecnologia na FIAP, adoro ler. Atualmente, não estou disponível.", R.drawable.alan, University.FIAP, Field.TECNOLOGIA, Hobbie.LER, City.SAOPAULO, Available.NAO, Assignment.MENTOR),
    MatchProfile("Bertram Gilfoyle", "Formado em matemática na USP, adoro comer. Estou disponível.", R.drawable.gilfoyle, University.USP, Field.MATEMATICA, Hobbie.COMER, City.COTIA, Available.SIM, Assignment.APRENDIZ),
    MatchProfile("Peter Gregory", "Estudante de matemática na USP, adoro viajar. Atualmente, não estou disponível.", R.drawable.peter, University.USP, Field.MATEMATICA, Hobbie.VIAJAR, City.OSASCO, Available.NAO, Assignment.MENTOR),
    MatchProfile("Jake Harper", "Formado em economia pela PUC, adoro viajar. Estou disponível.", R.drawable.jake, University.PUC, Field.ECONOMIA, Hobbie.VIAJAR, City.COTIA, Available.SIM, Assignment.APRENDIZ),
    MatchProfile("Nelson Bighetti", "Estudante de design na PUC, adoro programar. Estou disponível.", R.drawable.big_head, University.PUC, Field.DESIGN, Hobbie.PROGRAMAR, City.SAOPAULO, Available.SIM, Assignment.APRENDIZ),
    MatchProfile("Gavin Belson", "Estudante de tecnologia na USP, adoro ler. Estou disponível.", R.drawable.gavin, University.USP, Field.TECNOLOGIA, Hobbie.LER, City.SAOPAULO, Available.SIM, Assignment.MENTOR),
    MatchProfile("Jian Yang", "Formado em matemática na PUC, adoro comer. Atualmente, não estou disponível.", R.drawable.jian, University.PUC, Field.MATEMATICA, Hobbie.COMER, City.COTIA, Available.NAO, Assignment.MENTOR),
    MatchProfile("Jack Barker", "Estudante de design na FIAP, adoro viajar. Estou disponível.", R.drawable.barker, University.FIAP, Field.DESIGN, Hobbie.VIAJAR, City.SAOPAULO, Available.SIM, Assignment.APRENDIZ),
    MatchProfile("Bertram Gilfoyle", "Estudante de tecnologia na FIAP, adoro comer. Estou disponível.", R.drawable.gilfoyle, University.FIAP, Field.TECNOLOGIA, Hobbie.COMER, City.COTIA, Available.SIM, Assignment.MENTOR),
    MatchProfile("Peter Gregory", "Formado em tecnologia na USP, adoro viajar. Estou disponível.", R.drawable.peter, University.USP, Field.TECNOLOGIA, Hobbie.VIAJAR, City.OSASCO, Available.SIM, Assignment.MENTOR),
    MatchProfile("Jared Dunn", "Estudante de tecnologia na FIAP, adoro programar. Estou disponível.", R.drawable.jared, University.FIAP, Field.TECNOLOGIA, Hobbie.PROGRAMAR, City.SAOPAULO, Available.SIM, Assignment.MENTOR),
    MatchProfile("Nelson Bighetti", "Formado em design na PUC, adoro programar. Estou disponível.", R.drawable.big_head, University.PUC, Field.DESIGN, Hobbie.PROGRAMAR, City.COTIA, Available.SIM, Assignment.APRENDIZ),
    MatchProfile("Gavin Belson", "Formado em tecnologia na USP, adoro ler. Estou disponível.", R.drawable.gavin, University.USP, Field.TECNOLOGIA, Hobbie.LER, City.SAOPAULO, Available.SIM, Assignment.MENTOR),
    MatchProfile("Jian Yang", "Formado em matemática na FIAP, adoro comer. Atualmente, não estou disponível.", R.drawable.jian, University.FIAP, Field.MATEMATICA, Hobbie.COMER, City.COTIA, Available.NAO, Assignment.MENTOR),
    MatchProfile("Jack Barker", "Estudante de design na FIAP, adoro viajar. Estou disponível.", R.drawable.barker, University.FIAP, Field.DESIGN, Hobbie.VIAJAR, City.SAOPAULO, Available.SIM, Assignment.APRENDIZ),
)
