import kotlin.random.Random
import kotlinx.coroutines.*
import kotlinx.coroutines.delay
import kotlin.system.measureTimeMillis

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
suspend fun main() = coroutineScope {

    val time = measureTimeMillis {
        val list1 = getRandomList('[', 10)
        val list2 = getRandomList('p', 10)

        val one =  async(start = CoroutineStart.LAZY) { unpack(list1) }
        val two = async(start = CoroutineStart.LAZY) { unpack(list2) }
        println("Общее количество элементов = ${one.await() + two.await()}")

        val pair = concatenate(list1,list2)
        for (i in pair.second){
            print("\'$i\' ")
        }
    }
    println()
    println("Затрачено времени: $time мс")
}

fun getRandomList(value: Int, size: Int): List<Int>{
    val random = Random
    val list = (1..size).map { random.nextInt(0, 100) }
    return list
}

fun getRandomList(value: Char, size: Int): List<Char>{
    val random = Random
    val list = (1..size).map { random.nextInt(48, 126).toChar().lowercaseChar() }
    return list
}

suspend fun <T> unpack(list: List<T>): Int{
    var count = 0
    for (i in list){
        count++
        delay(1000L)
        println("$count - элемент: \'$i\'")
    }
    return count
}

fun <T> concatenate(vararg lists: List<T>): Pair<Int, MutableList<T>> {
    val concatList: MutableList<T> = mutableListOf()
    for (i in lists){
        concatList.addAll(i)
    }
    return Pair(concatList.size, concatList)
}