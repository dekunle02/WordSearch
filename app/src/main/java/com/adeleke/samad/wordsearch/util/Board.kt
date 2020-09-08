package com.adeleke.samad.wordsearch.util

import com.adeleke.samad.wordsearch.views.BoardLetterView
import java.lang.StringBuilder
import java.util.*

class Board private constructor(dimension: Int, wordsList: List<String>) {
    private val dim = dimension
    private val words = wordsList
    private val boardArr = Array(dim) { Array(dim) { "-" } }
    private val random = Random()
    private val correctCoordinates = mutableMapOf<String, Array<Array<Int>>>()

    companion object {
        private val defaultWords =
            listOf<String>("SWIFT", "KOTLIN", "OBJECTIVEC", "VARIABLE", "JAVA", "MOBILE", "ANDROID")
        private const val defaultDim = 10

        fun makeDefaultGameBoard(): Board {
            return Board(
                defaultDim,
                defaultWords
            )
        }

        fun makeWithDimension(dim: Int): Board {
            return Board(
                dim,
                defaultWords
            )
        }

        fun makeWithWordList(words: List<String>): Board {
            return Board(
                defaultDim,
                words
            )
        }

        fun makeWithDimensionAndWordList(dim: Int, words: List<String>): Board {
            return Board(dim, words)
        }
    }

    init {
        makeBoard()
    }

    fun getWords(): MutableList<String> {
        val mutableWords = mutableListOf<String>()
        mutableWords.addAll(words)
        return mutableWords
    }
    fun getCorrectCoordinates(): MutableMap<String, Array<Array<Int>>> {
        return correctCoordinates
    }
    fun getBoardArray(): Array<Array<String>> {
        return boardArr
    }
    fun getBoardDimension() = dim

    // Function that renders board to Console
    private fun renderBoard() {
        for (row in boardArr) {
            println(row.joinToString("  "))
        }
    }

    // Function that sometimes reverses the word if the random number generated is 0 or 1
    private fun randomizeWordOrder(word: String): String {
        return if (random.nextInt(2) == 0) word.reversed() else word
    }

    // Fills all empty board spaces with random Letters
    private fun fillEmptySpaces() {
        for (i in boardArr.indices) {
            for (j in boardArr[i].indices) {
                if (boardArr[i][j] == "-") {
                    boardArr[i][j] = ((random.nextInt(26) + 'A'.toInt())).toChar().toString()
                }
            }
        }
    }

    // Function receives the word and the coordinates to insert the letters
    private fun insertWord(word: String, coordinates: Array<Array<Int>>) {
        coordinates.forEachIndexed { index, coordinate ->
            val rowIndex = coordinate[0]
            val columnIndex = coordinate[1]
            boardArr[rowIndex][columnIndex] = word[index].toString()
        }
    }

    // Function determines if the word can be put into coordinates provided without overwriting the existing word
    private fun isCoordinateSafe(coordinates: Array<Array<Int>>, word: String): Boolean {
        val wordArray = word.toCharArray().map { it.toString() }.toTypedArray()
        val boardLetters = coordinates.map { coordinate ->
            val rowIndex = coordinate[0]
            val columnIndex = coordinate[1]
            boardArr[rowIndex][columnIndex]
        }
        return boardLetters.all { it == "-" || it == wordArray[boardLetters.indexOf(it)] }
    }


    private fun createHorizontalCoordinates(word: String): Array<Array<Int>> {
        val len = word.length
        val coordinates = Array(len) { Array(2) { 0 } }
        val rowIndex = random.nextInt(dim)
        val startingColumnIndex = if (len >= dim - 1) 0 else random.nextInt(dim - len)
        for (i in 0 until len) {
            coordinates[i][0] = rowIndex
            coordinates[i][1] = startingColumnIndex + i
        }
        return coordinates
    }

    private fun createVerticalCoordinates(word: String): Array<Array<Int>> {
        val len = word.length
        val coordinates = Array(len) { Array(2) { 0 } }
        val columnIndex = random.nextInt(dim)
        val startingRowIndex = if (len >= dim - 1) 0 else random.nextInt(dim - len)
        for (i in 0 until len) {
            coordinates[i][0] = startingRowIndex + i
            coordinates[i][1] = columnIndex
        }
        return coordinates
    }

    private fun createDiagonalCoordinates(word: String): Array<Array<Int>> {
        val len = word.length
        val coordinates = Array(len) { Array(2) { 0 } }
        val direction = arrayOf<String>("//", "\\").random()
        if (direction == "\\") {
            val startingRowIndex = if (len >= dim - 1) 0 else random.nextInt(dim - len)
            val startingColumnIndex = if (len >= dim - 1) 0 else random.nextInt(dim - len)
            for (i in 0 until len) {
                coordinates[i][0] = startingRowIndex + i
                coordinates[i][1] = startingColumnIndex + i
            }
        } else if (direction == "//") {
            val startingRowIndex = if (len >= dim - 1) dim - 1 else random.nextInt(dim - len) + len
            val startingColumnIndex = if (len >= dim - 1) dim - 1 else random.nextInt(dim - len) + len
            for (i in 0 until len) {
                coordinates[i][0] = startingRowIndex - i
                coordinates[i][1] = startingColumnIndex - i
            }
        }
        return coordinates
    }

    private fun makeBoard() {
        words.forEach { originalWord ->
            val word = randomizeWordOrder(originalWord)
            var shouldEnterLoop = true
            lateinit var coordinates: Array<Array<Int>>
            while (shouldEnterLoop) {
                val coordinateType = arrayOf("horizontal", "vertical", "diagonal").random()
                coordinates = when (coordinateType) {
                    "horizontal" -> createHorizontalCoordinates(word)
                    "vertical" -> createVerticalCoordinates(word)
                    else -> createDiagonalCoordinates(word)
                }
                shouldEnterLoop = !(isCoordinateSafe(coordinates, word))
            }
            insertWord(word, coordinates)
            correctCoordinates[originalWord] = coordinates
        }
        fillEmptySpaces()
    }

    fun getWordFromEndPoints(start: String, end: String): Pair<BoardLetterView.LineDirection, MutableList<IntArray>> {
        val x = intArrayOf(start.split(",")[0].toInt(), end.split(",")[0].toInt())
        val y = intArrayOf(start.split(",")[1].toInt(), end.split(",")[1].toInt())
        var direction = BoardLetterView.LineDirection.OFF


        val wordCoordinates = mutableListOf<IntArray>()

        val minX = minOf(x[0], x[1])
        val maxX = maxOf(x[0], x[1])
        val minY = minOf(y[0], y[1])
        val maxY = maxOf(y[0], y[1])

        when {
            x[0] == x[1] -> {
                direction = BoardLetterView.LineDirection.HOR
                for (i in minY..maxY) {
                    wordCoordinates.add(intArrayOf(x[0], i))
                }
            }
            y[0] == y[1] -> {
                direction = BoardLetterView.LineDirection.VERT
                for (i in minX..maxX) {
                    wordCoordinates.add(intArrayOf(i, y[0]))
                }
            }
            else -> {
                direction = if (x[0] > x[1]) BoardLetterView.LineDirection.LDIAG else BoardLetterView.LineDirection.RDIAG
                for (i in minX..maxY) {
                    wordCoordinates.add(intArrayOf(i, i))
                }
            }
        }
        println("wordcoordinatesize " + wordCoordinates.size)
        for (cordinate in wordCoordinates) {
            println(cordinate.contentToString())
        }
        return Pair(direction, wordCoordinates)
    }

//
//fun getWordFromEndPoints(start: String, end: String): MutableList<IntArray> {
//    val x = intArrayOf(start.split(",")[0].toInt(), end.split(",")[0].toInt())
//    val y = intArrayOf(start.split(",")[1].toInt(), end.split(",")[1].toInt())
//
//    val wordCoordinates = mutableListOf<IntArray>()
//
//    val minX = minOf(x[0], x[1])
//    val maxX = maxOf(x[0], x[1])
//    val minY = minOf(y[0], y[1])
//    val maxY = maxOf(y[0], y[1])
//
//    when {
//        x[0] == x[1] -> {
//            for (i in minY..maxY) {
//                wordCoordinates.add(intArrayOf(x[0], i))
//            }
//        }
//        y[0] == y[1] -> {
//            for (i in minX..maxX) {
//                wordCoordinates.add(intArrayOf(i, y[0]))
//            }
//        }
//        else -> {
//            for (i in minX..maxY) {
//                wordCoordinates.add(intArrayOf(i, i))
//            }
//        }
//    }
//
//    return wordCoordinates
//}

}

