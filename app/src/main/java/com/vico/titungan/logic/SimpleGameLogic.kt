/*
 *     Dooz
 *     SimpleGameLogic.kt Created/Updated by Yamin Siahmargooei at 2022/9/5
 *     This file is part of Dooz.
 *     Copyright (C) 2022  Yamin Siahmargooei
 *
 *     Dooz is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Dooz is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Dooz.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.vico.titungan.logic

import com.vico.titungan.model.Player
import com.vico.titungan.model.TitunganCell
import com.vico.titungan.util.Utility.rotated
import io.github.yamin8000.dooz.game.logic.GameLogic

class SimpleGameLogic(
    private val gameCells: List<List<TitunganCell>>,
    private val gameSize: Int,
) : GameLogic() {

    override var winner: Player? = null
    override fun findWinner(): Player? {
        return null
    }

//    override fun findWinner(): Player? {
//        winner = findRowOrColumnWinner(gameCells)
//        if (winner != null) return winner
//
//        winner = findRowOrColumnWinner(gameCells.rotated())
//        if (winner != null) return winner
//
//        winner = findDiagonalWinner()
//        if (winner != null) return winner
//
//        return null
//    }

//    override fun isGameDrew(): Boolean {
//        winner = findWinner()
//        return winner == null && gameCells.all { row -> row.all { it.owner != null } }
//    }


}