fun main() {
    fun part1(input: List<String>): Int = input
        .asSequence()
        .map { it.chunked(it.length / 2) }
        .map { Pair(it[0].toSet(), it[1].toSet()) }
        .map { it.first.intersect(it.second) }
        .map { it.first() }
        .sumOf { if (it.isUpperCase()) it.code - 38 else it.code - 96 }

    fun part2(input: List<String>): Int = input
        .asSequence()
        .chunked(3)
        .map { it.map { rs -> rs.toSet() } }
        .map { it[0].intersect(it[1]).intersect(it[2]) }
        .map { it.first() }
        .sumOf { if (it.isUpperCase()) it.code - 38 else it.code - 96 }

    val testInput = readInputLines("Day03_test")
    val input = readInputLines("Day03")

    check(part1(testInput) == 157)
    println(part1(input))

    check(part2(testInput) == 70)
    println(part2(input))
}
