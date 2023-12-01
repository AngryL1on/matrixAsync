import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.File

fun readMatrixFromFile(filePath: String): List<List<Int>> {
    val matrix = mutableListOf<List<Int>>()

    BufferedReader(File(filePath).bufferedReader()).use { reader ->
        reader.forEachLine { line ->
            val row = line.split("\\s+".toRegex()).map { it.toInt() }
            matrix.add(row)
        }
    }

    return matrix
}

suspend fun calculateDeterminant(matrix: List<List<Int>>): Int = coroutineScope {
    if (matrix.size != matrix[0].size) {
        throw IllegalArgumentException("Матрица должна быть квадратной")
    }

    val n = matrix.size

    if (n == 1) {
        return@coroutineScope matrix[0][0]
    }

    val result = async(Dispatchers.Default) {
        var determinant = 0

        for (col in matrix.indices) {
            val minor = getMinor(matrix, 0, col)
            val cofactor = matrix[0][col] * calculateDeterminant(minor)
            determinant += if (col % 2 == 0) cofactor else -cofactor
        }

        determinant
    }

    result.await()
}

fun getMinor(matrix: List<List<Int>>, rowToRemove: Int, colToRemove: Int): List<List<Int>> {
    return matrix.filterIndexed { i, _ -> i != rowToRemove }
        .map { it.filterIndexed { j, _ -> j != colToRemove } }
}

fun main() = runBlocking {
    val matrix = readMatrixFromFile("matrix.txt")

    println("Матрица:")
    for (row in matrix) {
        println(row.joinToString(separator = "\t"))
    }

    val timeAsync = System.currentTimeMillis()
    val determinant = calculateDeterminant(matrix)
    val asyncTimeResult = (System.currentTimeMillis() - timeAsync)
    println("Определитель матрицы: $determinant")
    println("Время выполнения в милисекундах: $asyncTimeResult")
}
