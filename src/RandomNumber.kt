import kotlin.random.Random
import kotlin.random.nextInt

/**
 * 값이 한곳에 몰리진 않는지 테스트
 * sampling count : 10_000_000
 * 각 숫자가 나온 횟수와 평균값을 통해 값이 진짜 랜덤하게 나오는지 테스트
 *
 * 평균이 max_number / 2 에 가까울수록 랜덤하게 나왔다고 판단
 *
 * Exception
 * - NumberformatException : 테스트를 위한 코드이기 때문에 maxNumber가 Int형을 벗어나면 에러 발생
 */
fun test(maxNumber: String) {
    var sum = 0
    val array = IntArray(maxNumber.toInt()) { 0 }

    for (i in 1..10_000_000) {
        val random = get_random(maxNumber).toInt()

        array[random]++
        sum += random
    }

    println("각 숫자가 나온 횟수 / 빈도")
    array.forEachIndexed { index, i ->
        println("$index : $i / ${(i / 100_000f)}%")
    }

    println("\n평균값")
    println(sum.toFloat() / 10_000_000)
}

fun get_zero_or_one() = Random.nextInt(0..1)

fun main() {
//    test("12")
    println(get_random("12"))
}

/**
 * @get_random()
 * - 자료형에 상관없이 무한한 수를 받기 위해 Input 타입을 String 으로 지정
 * Step {
 *  1. max_number <= 2^k 를 만족하는 가장 작은 k를 구함
 *  2. k길이의 array 생성
 *  3. array의 각 index에 get_zero_or_one()을 이용해서 0 또는 1로 채움
 *  4. array의 각 값들을 나열해 2진수로 보고 10진수로 변환
 *  5. 변환된 10진수 < max_number를 만족할때까지 반복
 *}
 *
 * @calculateString()
 * - String을 Decimal하게 더하기 위해 만든 함수
 *
 * @binaryToDecimal()
 * - 2진수로 표현된 수를 10진수로 변환
 *
 * @isGreaterThan()
 * - Number format인 String을 문자비교가 아닌 수의 비교를 위해 만든 함수
 */
fun get_random(max_number: String): String {
    if (max_number == "0" || max_number == "1") return "0"

    fun calculateString(num1: String, num2: String): String {
        var i = num1.length - 1
        var j = num2.length - 1
        var carry = 0
        var result = ""
        while (i >= 0 || j >= 0 || carry != 0) {
            if (i >= 0) carry += (num1[i--] - '0')
            if (j >= 0) carry += (num2[j--] - '0')
            result += (carry % 10)
            carry /= 10
        }
        return result.reversed()
    }

    fun binaryToDecimal(binaryArray: IntArray): String {
        var decimalNum = "0"
        binaryArray.forEachIndexed { index, int ->
            if (int != 0) {
                var weight = "1"
                for (i in 0 until binaryArray.size - index - 1) {
                    weight = calculateString(weight, weight)
                }
                decimalNum = calculateString(decimalNum, weight)
            }
        }
        return decimalNum
    }

    fun isGreaterThan(source: String, target: String, isEqual: Boolean = false): Boolean {
        val sourceToCharArray = source.toCharArray()
        val targetToCharArray = target.toCharArray()

        return when {
            sourceToCharArray.size > targetToCharArray.size -> true
            sourceToCharArray.size < targetToCharArray.size -> false
            else -> {
                var result = isEqual
                sourceToCharArray.forEachIndexed { index, c ->
                    if (c.toInt() > targetToCharArray[index].toInt()) {
                        result = true
                        return@forEachIndexed
                    }
                    if (c.toInt() < targetToCharArray[index].toInt()) {
                        result = false
                        return@forEachIndexed
                    }
                }
                result
            }
        }
    }

    val binaryLength = max_number.let {
        var k = 0
        var pow = "1"
        while (isGreaterThan(it, pow, false)) {
            pow = calculateString(pow, pow)
            k++
        }
        k
    }

    var random = max_number

    while (isGreaterThan(random, max_number, true)) {
        random = binaryToDecimal(IntArray(binaryLength).apply {
            forEachIndexed { index, _ ->
                this[index] = get_zero_or_one()
            }
        })
    }

    return random
}