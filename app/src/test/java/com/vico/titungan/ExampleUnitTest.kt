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

        var random = randomNumber(3)
        print(random)
        print(random.get(1))
    }
}

fun randomNumber(jumlahCells : Int = 0) : MutableList<Int>{
    var angkaRandom  = mutableListOf<Int>()

    for (i in 1..jumlahCells * jumlahCells) {
        angkaRandom.add(Random.nextInt(0, 100))
    }
    return angkaRandom
}

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
