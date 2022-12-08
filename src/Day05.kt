data class Step(val move: Int, val from: Int, val to: Int)


fun main() {

    fun parseLines(lines: List<String>): Pair<MutableMap<Int, String>, List<Step>> {
        val dock = lines
            .takeWhile { line -> !line.startsWith(" 1") }
            .map { line ->
                (1..line.length)
                    .step(4)
                    .withIndex()
                    .map { (num, ind) -> Pair(num + 1, line[ind]) }
                    .filter { it.second != ' ' }
            }
            .reversed()
            .flatten()
            .groupBy({ it.first }, { it.second })
            .mapValues { tmp -> tmp.value.joinToString("") }
            .toMutableMap()

        val steps = lines
            .filter { it.startsWith("move") }
            .map { line -> line.split("\\s".toRegex()) }
            .map { Step(move = it[1].toInt(), from = it[3].toInt(), to = it[5].toInt()) }

        return Pair(dock, steps)
    }

    fun part(dock: MutableMap<Int, String>, steps: List<Step>, reverse: Boolean): String {
        steps.forEach { step ->
            dock[step.to] = dock[step.to] + (dock[step.from]
                ?.takeLast(step.move)
                ?.let { str -> if (reverse) str.reversed() else str })
            dock[step.from] = dock[step.from]?.dropLast(step.move) ?: ""
        }
        return dock
            .entries
            .sortedBy { it.key }
            .map { it.value.last() }
            .joinToString("")
    }

    val (exampleDock, exampleSteps) = parseLines(readInputLines("Day05_test"))
    val (dock, steps) = parseLines(readInputLines("Day05"))

    check(part(exampleDock.toMutableMap(), exampleSteps, reverse = true) == "CMZ")
    println("Solution for part 1: ${part(dock.toMutableMap(), steps, reverse = true)}")

    check(part(exampleDock.toMutableMap(), exampleSteps, reverse = false) == "MCD")
    println("Solution for part 2: ${part(dock.toMutableMap(), steps, reverse = false)}")
}