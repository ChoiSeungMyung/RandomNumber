# **RandomNumber**
### 0과 1을 이용해 0..(max_number - 1) 사이의 랜덤함수 만들기
<br>

## **Step**
- 무한한 수를 받기 위해 Input타입을 String으로 지정합니다.

<br>

### 1. max_number <= 2^k 를 만족하는 가장 작은 k를 구합니다.
### 2. k 길이의 IntArray를 생성합니다.
### 3. 2에서 생성된 IntArray(이하 array)의 각 [index]를 get_zero_or_one()을 이용해서 0 또는 1로 채웁니다.
### 4. array를 나열했을때를 2진수로 보고 10진수로 변홥합니다.
### 5. 4에서 변환된 10진수 < max_number를 만족할때 까지 3과 4를 반복합니다.

<br>

---

## **Test Code**
- 프로젝트 안에 fun test() 함수를 통해 테스트함수를 구현했습니다.
- 10,000,000번의 샘플링을 통해 목표를 확인합니다.
### **Test목표**
1. 값이 특정구간에 몰리진 않는지 확인
1. 랜덤 값들이 합이 max_number / 2에 가까운지 확인(1과 같은 이유)
1. 각 숫자가 나온 횟수와 평균값을 통해 정말 랜덤하게 나오는지 확인

### **Test code**
``` kotlin
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
```

### **결과**
- input: "12" <br>
출력값을 통해 테스트 목표를 만족하는 결과가 나옴을 확인할수 있습니다.

```
각 숫자가 나온 횟수 / 빈도
0 : 833676 / 8.33676%
1 : 833985 / 8.33985%
2 : 832870 / 8.3287%
3 : 832618 / 8.32618%
4 : 833869 / 8.33869%
5 : 832662 / 8.32662%
6 : 834442 / 8.34442%
7 : 832763 / 8.32763%
8 : 832585 / 8.32585%
9 : 833100 / 8.331%
10 : 834079 / 8.34079%
11 : 833351 / 8.33351%

평균값
5.499859
```



