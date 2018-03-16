@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    val matrix = MatrixImpl<E>(height, width)
    for (i in 0 until height)
        for (j in 0 until width)
            matrix[i, j] = e
    return matrix
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int) : Matrix<E> {

    private val map = mutableMapOf<Cell, E>()

    override fun get(row: Int, column: Int): E  = get(Cell(row, column))

    override fun get(cell: Cell): E  = map[cell] ?: throw IndexOutOfBoundsException()

    override fun set(row: Int, column: Int, value: E) = set(Cell(row, column), value)

    override fun set(cell: Cell, value: E) { map[cell] = value }

    override fun equals(other: Any?) =
            other is MatrixImpl<*> && other.height == height && other.width == width
                    && map.all { it == other[it.key] }

    override fun toString(): String {
        val str = StringBuilder()
        str.append("[").appendln()
        for (i in 0 until height) {
            for (j in 0 until width)
                str.append(get(i, j)).append(", ")
            str.appendln()
        }
        str.append("]")
        return "$str"
    }

    override fun hashCode(): Int = map.hashCode()
}

