import java.io.File

class SearchEngine(/*file: String*/) {
    private val fileInput = File("src${File.separator}names.txt").readLines()
    private val individualFileElements = fileInput.joinToString().replace(",","").split(" ")
    private val mappedSearchResults = mutableMapOf<String, List<Int>>()

    private fun easySearch () {
        for (person in individualFileElements) {
            for (people in fileInput) {
                if (person in people) {
                    if(mappedSearchResults.containsKey(person.uppercase())) {
                        mappedSearchResults[person.uppercase()] = mappedSearchResults[person.uppercase()]!! + listOf(fileInput.indexOf(people))
                    } else mappedSearchResults[person.uppercase()] = listOf(fileInput.indexOf(people))
                }
            }
        }
    }

    fun menu() {
        while (true) {
            println("""
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
        """.trimIndent())
            when(readln()) {
                "1" -> {
                    println("\nSelect a matching strategy: ALL, ANY, NONE")
                    if (mappedSearchResults.isEmpty()) {
                        easySearch()
                    }
                    when(readLine()) {
                        "ALL" -> all()
                        "ANY" -> any()
                        "NONE" -> none()
                    }
                }
                "2" -> printListOfPeople()
                "0" -> { println("\nBye!"); break }
                else -> println("\nIncorrect option! Try again.")
            }
        }
    }

    private fun printListOfPeople() {
        println("\n=== List of people ===")
        for (people in fileInput) {
            println(people)
        }
        println()
    }

    fun inputCheck(): MutableList<Int> {
        val numbers = mutableListOf<Int>()
        println("\nEnter a name or email to search all matching people.")
        val input = readln().uppercase().split(" ")
        for(person in input) {
            if(mappedSearchResults[person] != null) {
                for(people in mappedSearchResults[person]!!) {
                    numbers.add(people)
                }
            }
        }
        return numbers
    }

    fun resultPrint(list: MutableList<Int>) {
        if(list.isEmpty()) {
            println("No matching people found.")
        } else {
            if(list.size == 1) {
                println("1 person found:")
                println(fileInput[list[0]])
            } else{
                println("${list.distinct().size} persons found:")
                for (all in list.distinct()) {
                    println(fileInput[all])
                }
            }
        }
        println()
    }

    fun all() {
        var tally = 0
        val numbers = mutableListOf<Int>()
        println("\nEnter a name or email to search all matching people.")
        val input = readln().uppercase().split(" ")
        if(mappedSearchResults[input[0]] != null) {
            for(person in mappedSearchResults[input[0]]?.distinct()!!) {
                for (all in input) {
                    if (all in fileInput[person].uppercase()) {
                        tally++
                    }
                    if (input.size == tally) {
                        numbers.add(person)
                    }
                }
            }
        }
        resultPrint(numbers)
    }

    fun any() {
        val inputChecker = inputCheck()
        resultPrint(inputChecker)
    }

    fun none() {
        val inputChecker = inputCheck()
        if(inputChecker.isEmpty()) {
            println("No matching people found.")
        } else {
            if(inputChecker.size == 1) {
                println("1 person found:")
                println(fileInput[inputChecker[0]])
            } else{
                println("${fileInput.size - inputChecker.distinct().size} persons found:")
                for (all in fileInput.indices) {
                    if(all !in inputChecker){
                        println(fileInput[all])
                    }
                }
            }
        }
        println()
    }
}

fun main(args: Array<String>) {
    val searchEngine = SearchEngine()
    searchEngine.menu()

}

