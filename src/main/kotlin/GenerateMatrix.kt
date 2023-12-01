import java.io.File

fun generateMatrixToFile(n: Int, filePath: String) {
    val matrix = Array(n) { Array(n) { (0..9).random() } }

    val file = File(filePath)
    file.bufferedWriter().use { writer ->
        for (row in matrix) {
            writer.write(row.joinToString(separator = "\t"))
            writer.newLine()
        }
    }

    println("Матрица размерности $n * $n успешно сохранена в файл: $filePath")
}

fun main() {
    val n = 13
    val filePath = "matrix.txt"

    generateMatrixToFile(n, filePath)
}
