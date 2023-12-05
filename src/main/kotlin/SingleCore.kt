fun main() {
    val matrix = readMatrixFromFile("matrix.txt")

    println("Матрица:")
    for (row in matrix) {
        println(row.joinToString(separator = "\t"))
    }

    val time = System.currentTimeMillis()
    val determinant = calculateDeterminantSync(matrix)
    val singleThreadTimeResult = (System.currentTimeMillis() - time)
    println("Определитель матрицы: $determinant")
    println("Время выполнения в милисекундах: $singleThreadTimeResult")

}

fun calculateDeterminantSync(matrix: List<List<Int>>): Int {
    if (matrix.size != matrix[0].size) {
        throw IllegalArgumentException("Матрица должна быть квадратной")
    }

    val n = matrix.size

    if (n == 1) {
        return matrix[0][0]
    }

    var determinant = 0

    for (col in matrix.indices) {
//        val threadName = Thread.currentThread().name
//        println("Thread name for column $col: $threadName")
        val minor = getMinor(matrix, 0, col)
        val cofactor = matrix[0][col] * calculateDeterminantSync(minor)
        determinant += if (col % 2 == 0) cofactor else -cofactor
    }

    return determinant
}
