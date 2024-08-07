package com.vico.titungan

import com.vico.titungan.model.TitunganCell
import org.junit.Test

import org.junit.Assert.*
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        print(getRandomNumber(3, arrayOf("x")))
        print(getRandomNumber(3, arrayOf("/")))
        print(getRandomNumber(3, arrayOf("x", "+")))
        print(getRandomNumber(3, arrayOf("x", "-")))
        print(getRandomNumber(3, arrayOf("/", "-")))
        print(getRandomNumber(3, arrayOf("/", "+")))
        print(getRandomNumber(3, arrayOf("x", "-", "+")))
        print(getRandomNumber(3, arrayOf("x", "/","-")))


    }
}

fun getRandomNumber(jumlahCells : Int = 0, listOperators: Array<String>, min : Int = 11, max : Int = 100) : String{
    var angkaRandom  = ""
    var angkaRandomTemp = 0

    if(listOperators.size <= 2 && (!listOperators.contains("+") && !listOperators.contains("-"))) {
        angkaRandom = "\n\nNeed Prime\n"
    } else {
        angkaRandom = "\n\nDoesn't Need Prime\n"
    }

    return angkaRandom
}

fun isPrime(num: Int): Boolean {
    if (num < 2) return false
    for (i in 2 until num) {
        if (num % i == 0) {
            return false
        }
    }
    return true
}

//fun randomNumber(jumlahCells : Int = 0) {
//    val cells = listOf(
//        listOf(
//            TitunganCell(0, 0, 1, isActive =false),
//            TitunganCell(0, 1, 2, isActive = true)
//        ),
//        listOf(
//            TitunganCell(1, 0, 3, isActive =false),
//            TitunganCell(1, 1, 4, isActive =false)
//        )
//    )
//
//    // Menggunakan `flatMap` dan `find` untuk mendapatkan sel pertama yang aktif
//    println("Sebelum diubah: ${cells[0][1].isActive}")
//    cells.flatMap { it }.find { it.isActive }?.isActive = false
//    println("Sebelum diubah: ${cells[0][1].isActive}")
//}

fun checkList() {
     val columns = mutableListOf<List<TitunganCell>>()
//    for (x in 0 until 3) {
//        val row = mutableListOf<TitunganCell>()
//        for (y in 0 until 3)
//            row.add(TitunganCell(x, y))
//        columns.add(row)
//    }

    print(columns)
}
