//import java.io.File
//import kotlin.math.pow
//
//fun main() {
//    val matrix = readMatrixFromFile("matrix.txt")
//
//    println("Исходная матрица:")
//    for (row in matrix) {
//        println(row.joinToString("\t"))
//    }
//
//    val time = System.currentTimeMillis()
//    val determinant = calculateDeterminant(matrix)
//    val singleThreadTimeResult = (System.currentTimeMillis() - time)
//    println("Определитель матрицы: $determinant")
//    println("Время выполнения в милисекундах: $singleThreadTimeResult")
//
//}
//
//fun readMatrixFromFile(filePath: String): Array<IntArray> {
//    val matrix = mutableListOf<IntArray>()
//    File(filePath).forEachLine { line ->
//        val row = line.split("\t").map { it.toInt() }.toIntArray()
//        matrix.add(row)
//    }
//    return matrix.toTypedArray()
//}
//
//fun calculateDeterminant(matrix: Array<IntArray>): Int {
//    if (matrix.size != matrix[0].size) {
//        throw IllegalArgumentException("Матрица должна быть квадратной")
//    }
//
//    if (matrix.size == 1) {
//        return matrix[0][0]
//    }
//
//    var determinant = 0
//    for (i in matrix.indices) {
//        determinant += matrix[0][i] * cofactor(matrix, 0, i)
//    }
//
//    return determinant
//}
//
//fun cofactor(matrix: Array<IntArray>, row: Int, col: Int): Int {
//    val minorMatrix = matrix.filterIndexed { rowIndex, _ -> rowIndex != row }
//        .map { it.filterIndexed { colIndex, _ -> colIndex != col }.toIntArray() }
//        .toTypedArray()
//
//    val sign = (-1.0).pow((row + col).toDouble()).toInt()
//
//    return sign * calculateDeterminant(minorMatrix)
//}
