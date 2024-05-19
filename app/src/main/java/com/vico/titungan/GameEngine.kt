package com.vico.titungan

object GameEngine {

    var container : MutableMap<Int, Char> = mutableMapOf()

    fun setContainer(order : Int, shape : Char) = container.put(order, shape)

    fun gameCheck() : GameDesc {
        var listTemp = mutableListOf<Char>()
        var position = mutableListOf<Int>()

        if(container.size > 4)  {

            var i = 1 //i for iterator

            //cek mendatar
            do {
                for (j in 0..2) {
                    if(container.containsKey(i + j)) {
                        listTemp.add(container.getOrDefault(i + j, ' '))
                        position.add(i + j)
                    } else break
                }

                if((listTemp.size == 3 && position.size == 3)
                    && (listTemp.all { it == 'O'} || listTemp.all { it == 'X' }))
                    return GameDesc(1,position.toIntArray(),listTemp.get(0))

                listTemp.clear()
                position.clear()
                i += 3

            }while(i < 9)

            //cek menurun
            i = 1

            do {
                for (j in 0..7 step 3) {
                    if(container.containsKey(i + j)) {
                        listTemp.add(container.getOrDefault(i + j, ' '))
                        position.add(i + j)
                    } else break
                }

                if((listTemp.size == 3 && position.size == 3)
                    && (listTemp.all { it == 'O'} || listTemp.all { it == 'X' }))
                    return GameDesc(2,position.toIntArray(),listTemp.get(0))

                listTemp.clear()
                position.clear()
                i++

            }while(i < 9)

            //cek diagonal
            i = 1

            do {
                if(i == 1) {
                    for (j in 0..9 step 4) {
                        if(container.containsKey(i + j)) {
                            listTemp.add(container.getOrDefault(i + j, ' '))
                            position.add(i + j)
                        } else break
                    }
                } else {
                    for (j in 1..5 step 2) {
                        if(container.containsKey(i + j)) {
                            listTemp.add(container.getOrDefault(i + j, ' '))
                            position.add(i + j)
                        } else break
                    }
                }


                if((listTemp.size == 3 && position.size == 3)
                    && (listTemp.all { it == 'O'} || listTemp.all { it == 'X' }))
                    return GameDesc(3,position.toIntArray(),listTemp.get(0))

                listTemp.clear()
                position.clear()
                i++

            }while(i < 3)

        }

        return GameDesc(0,null,null)
    }

    fun clearContainer() = container.clear()

}