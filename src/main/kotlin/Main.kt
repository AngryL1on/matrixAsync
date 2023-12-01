import kotlinx.coroutines.*
import java.io.File

fun readMatrixFromFile(filePath: String): List<List<Int>> {
    val matrix = mutableListOf<List<Int>>()
    File(filePath).forEachLine { line ->
        val row = line.split("\t").map { it.toInt() }
        matrix.add(row)
    }
    return matrix
}

fun calculateDeterminantSync(matrix: List<List<Int>>): Int {
    if (matrix.size != matrix[0].size) {
        throw IllegalArgumentException("Матрица должна быть квадратной")
    }
    return calculateDeterminant(matrix)
}

@OptIn(DelicateCoroutinesApi::class)
fun calculateDeterminantAsync(matrix: List<List<Int>>): Deferred<Int> = GlobalScope.async {
    if (matrix.size != matrix[0].size) {
        throw IllegalArgumentException("Матрица должна быть квадратной")
    }
    calculateDeterminant(matrix)
}

private fun calculateDeterminant(matrix: List<List<Int>>): Int {
    return if (matrix.size == 1) {
        matrix[0][0]
    } else {
        var determinant = 0
        for (i in matrix.indices) {
            val sign = if (i % 2 == 0) 1 else -1
            determinant += sign * matrix[0][i] * calculateDeterminant(getMinor(matrix, 0, i))
        }
        determinant
    }
}

private fun getMinor(matrix: List<List<Int>>, row: Int, col: Int): List<List<Int>> {
    return matrix
        .filterIndexed { i, _ -> i != row }
        .map { it.filterIndexed { j, _ -> j != col } }
}

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val matrix = readMatrixFromFile("matrix.txt")

    // Синхронный расчет определителя
    val time = System.currentTimeMillis()
    val determinantSync = calculateDeterminantSync(matrix)
    val singleThreadTimeResult = (System.currentTimeMillis() - time)
    println("Определитель (синхронный): $determinantSync")
    println("Время выполнения в милисекундах: $singleThreadTimeResult")

    // Асинхронный расчет определителя с использованием корутин
    val timeAsync = System.currentTimeMillis()
    val job = GlobalScope.launch {
        val determinantAsync = calculateDeterminantAsync(matrix).await()
        println("Определитель (асинхронный): $determinantAsync")
    }
    val asyncTimeResult = (System.currentTimeMillis() - timeAsync)
    println("Время выполнения в милисекундах: $asyncTimeResult")

    // Ждем завершения асинхронной задачи
    runBlocking {
        job.join()
    }
}
