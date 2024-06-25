### Effectively final and final

--- 

Java 8 에 도입된 기능 중 하나는 `Effectively final` 이다.  
이를 통해 변수, 필드 및 매개 변수에 대해 `final` 을 쓰지 않고 `final` 처럼 효과적으로 처리하고 사용할 수 있다.

JLS 4.12.4 에서는 컴파일 타임 오류 없이 유효한 프로그램의 매개변수나 지역 변수에 `final` 를 제거하면 `Effectively final` 이 된다고 명시하고 있다.  
https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.12

> Certain variables that are not declared final are instead considered effectively final

하지만, Java 컴파일러는 `Effectively final` 변수에 대한 정적 코드 최적화를 수행하지 않는다.

`final` 로 선언된 문자열을 연결하는 코드가 있다면,

```
public static void main(String[] args) {
    final String hello = "hello";
    final String world = "world";
    String test = hello + " " + world;
    System.out.println(test);
}
```

컴파일러는 기본 메서드에서 실행된 코드를 다음과 같이 변경한다.

```
public static void main(String[] var0) {
    String var1 = "hello world";
    System.out.println(var1);
}
```

반면에 final 을 제거하면 `Effectively final` 로 간주하지만,  
연결에만 사용되기 때문에 컴파일러는 이를 최적화하지 않는다.

사실, `final` keyword 를 사용하면 얻을 수 있는 성능 상의 이점은 매우 인기 있는 논쟁이다.  
어디에 적용하느냐에 따라 `final` 은 다른 목적과 성능에 미치는 영향을 가질 수 있다.

`final` 이 지역 변수에 적용될 때 값은 정확히 한 번 할당 되어야 한다.  
로컬 변수에 `final` 키워드를 사용하면 성능이 향상 될 수 있다.

예를 들어, 문자열을 연결하는 코드를 JMH(Java Microbenchmark Harness) 로 벤치마킹하면

```
@Benchmark
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public static String concatNonFinalStrings() {
    String x = "x";
    String y = "y";
    return x + y;
}


@Benchmark
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public static String concatFinalStrings() {
    final String x = "x";
    final String y = "y";
    return x + y;
}  
```

아래와 같은 결과를 볼 수 있다.

Benchmark                                   Mode  Cnt  Score   Error  Units  
BenchmarkRunner.compilerOptimizationString  avgt    5  0.339 ± 0.006  ns/op  
BenchmarkRunner.finalStrings                avgt    5  0.340 ± 0.004  ns/op  
BenchmarkRunner.nonFinalStrings             avgt    5  4.404 ± 0.188  ns/op


해당 메서드들을 컴파일해서 `javap` 커맨드로 바이트코드를 확인해보면,
```
javac XXX.java  
javap -v -p -s XXX.class
```  

```
public class lego.benchmark.finalkeyword.Test
  minor version: 0
  major version: 61
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #17                         // lego/benchmark/finalkeyword/Test
  super_class: #2                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 4, attributes: 3
Constant pool:
   #1 = Methodref          #2.#3          // java/lang/Object."<init>":()V
   #2 = Class              #4             // java/lang/Object
   #3 = NameAndType        #5:#6          // "<init>":()V
   #4 = Utf8               java/lang/Object
   #5 = Utf8               <init>
   #6 = Utf8               ()V
   #7 = String             #8             // x
   #8 = Utf8               x
   #9 = String             #10            // y
  #10 = Utf8               y
  #11 = InvokeDynamic      #0:#12         // #0:makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
  #12 = NameAndType        #13:#14        // makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
  #13 = Utf8               makeConcatWithConstants
  #14 = Utf8               (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
  #15 = String             #16            // xy
  #16 = Utf8               xy
  #17 = Class              #18            // lego/benchmark/finalkeyword/Test
  #18 = Utf8               lego/benchmark/finalkeyword/Test
  #19 = Utf8               Code
  #20 = Utf8               LineNumberTable
  #21 = Utf8               nonFinalStrings
  #22 = Utf8               ()Ljava/lang/String;
  #23 = Utf8               compilerOptimizationString
  #24 = Utf8               finalStrings
  #25 = Utf8               SourceFile
  #26 = Utf8               Test.java
  #27 = Utf8               BootstrapMethods
  #28 = MethodHandle       6:#29          // REF_invokeStatic java/lang/invoke/StringConcatFactory.makeConcatWithConstants:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[L
java/lang/Object;)Ljava/lang/invoke/CallSite;
  #29 = Methodref          #30.#31        // java/lang/invoke/StringConcatFactory.makeConcatWithConstants:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;
)Ljava/lang/invoke/CallSite;
  #30 = Class              #32            // java/lang/invoke/StringConcatFactory
  #31 = NameAndType        #13:#33        // makeConcatWithConstants:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;
  #32 = Utf8               java/lang/invoke/StringConcatFactory
  #33 = Utf8               (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;
  #34 = String             #35            // \u0001\u0001
  #35 = Utf8               \u0001\u0001
  #36 = Utf8               InnerClasses
  #37 = Class              #38            // java/lang/invoke/MethodHandles$Lookup
  #38 = Utf8               java/lang/invoke/MethodHandles$Lookup
  #39 = Class              #40            // java/lang/invoke/MethodHandles
  #40 = Utf8               java/lang/invoke/MethodHandles
  #41 = Utf8               Lookup
{
  public lego.benchmark.finalkeyword.Test();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0

  public static java.lang.String nonFinalStrings();
    descriptor: ()Ljava/lang/String;
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=2, args_size=0
         0: ldc           #7                  // String x, Constant Pool 에서 인덱스 #7 에 해당하는 문자열 "x" 를 스택에 넣는다.
         2: astore_0                          // 스택의 값을 로컬 변수 0에 저장한다.
         3: ldc           #9                  // String y, Constant Pool 인덱스 #9 에 해당하는 문자열 "y" 를 스택에 넣는다.
         5: astore_1                          // 스택의 값을 로컬 변수 1에 저장한다.
         6: aload_0                           // 로컬 변수 0의 값을 스택에 로드한다.
         7: aload_1                           // 로컬 변수 1의 값을 스택에 로드한다.
         8: invokedynamic #11,  0             // InvokeDynamic #0:makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, 두 문자열을 연결하는 InvokeDynamic 호출을 수행한다.
        13: areturn                           // 연결된 문자열을 반환한다.
      LineNumberTable:
        line 6: 0
        line 7: 3
        line 8: 6

  public static java.lang.String compilerOptimizationString();
    descriptor: ()Ljava/lang/String;
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=1, args_size=0
         0: ldc           #15                 // String xy, Constant Pool 에서 인덱스 #15 에 해당하는 문자열 "xy" 를 스택에 넣는다.
         2: areturn                           // 문자열 "xy" 를 반환한다.
      LineNumberTable:
        line 12: 0

  public static java.lang.String finalStrings();
    descriptor: ()Ljava/lang/String;
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=0, args_size=0
         0: ldc           #15                 // String xy, Constant Pool 에서 인덱스 #15 에 해당하는 문자열 "xy" 를 스택에 넣는다.
         2: areturn                           // 문자열 "xy" 를 반환한다.
      LineNumberTable:
        line 19: 0
}

```

finalStrings 메서드는 **이미 최적화된 결과를 반환**하는 것을 볼 수 있다.  
하지만, 지역 변수 `final` 키워드를 추가하는 것이 바이트 코드 최적화에 큰 영향을 주지 않는다.

그 이유는 여러가지가 있는데, 그 중의 핵심은 **객체 내부의 상태까지 불변을 보장 하지 않는다** 라는 점이다.  
`final` 키워드를 사용해서 최적할 수 있는 여러 기회를 제공하기는 하지만, 모든 경우에 해당되지 않는다는 것이다.

```
public class Example {
    
    private final SomeObject finalObject;

    public Example() {
        finalObject = new SomeObject();
    }

    public SomeObject getFinalObject() {
        return finalObject;
    }
}
```

위의 코드에서 finalObject 은 `final` 로 선언되었기 때문에 생성자에서 한 번 초기화 된 후에 다시 할당 될 수 없는 상태가 된다.  
이것은 컴파일러에서 객체 finalObject 가 초기화 이후에는 불변하다는 것을 알려주고,
컴파일러는 최적화를 수행한다는 것이다.

그러나, 이러한 최적화는 객체나 배열에 포함되거나 객체의 상태가 변경되는 경우 제한 될 수 있다.

```
public class Example {

    private final List<Integer> numbers = new ArrayList<>();

    public Example() {
        numbers.add(1);
        numbers.add(2);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
```

위의 코드에서 numbers 는 final 로 선언되었지만 list 자체는 불변이 아니다.  
final 키워드는 객체의 참조가 변경되지 않음을 보장하지만, **객체의 내부 상태까지 불변을 보장하지 않는다.**

https://www.baeldung.com/java-final-performance


그렇다면, final 은 언제 써야 하는게 좋을까??

### Best practices for using final keyword in Java

---

https://sg.wantedly.com/companies/bebit/post_articles/366756 에 대한 정리 내용

`final` keyword 는 다양하게 사용할 수 있다.
+ final class
    - 이 클래스는 상속 할 수 없다.
```
final class SomeClass {
}
```

+ final method
    - 이 메서드는 하위 클래스에서 재정의할 수 없다.
```
class SomeClass {
   public final void someMethod() {
   }
}
```
+ final method parameters
    - 이 매개변수는 메서드안에서 다시 할당 할 수 없다.
```
class SomeClass {
   public final void someMethod(final int someArgument) {
   }
}
```
+ final fields
    - 이 필드는 객체 생성 중에 설정되고 다시 할당 할 수 없다.
```
class SomeClass {
   final int someField = 42;
}
```
+ final local variables
    - 이 지역 변수는 한 번 할당 후에 다시 할당 할 수 없다.
```
class SomeClass {
   public void someMethod() {
      final int someVariable = getVar();
   }
}
```

#### Best practices for final in method parameters

--- 

```
// with finals
class SomeClass {
   public void someMethod(final int arg1, final int arg2, final int arg3) {
   }
}
```

이 코드는 안정성보다는, 어수선해보이고 가독성에 미치는 부정적인 영향이 크다는 내용이 많다.

```
// without finals
class SomeClass {
   public void someMethod(int arg1, int arg2, int arg3) {
   }
}
```

대부분의 개발자는 매개변수가 재할당 되지 않도록 하는 것이 가장 좋다는 점에서는 동의한다.  
하지만, 매개 변수의 최종 동작이 기본 값이 아닌 경우가 많다.

-> 따라서 전체 프로젝트에서 사용되지 않는다면, 굳이 매개변수에 final 을 사용할 필요가 없다.


#### Best practices for final in class fields

---

클래스 필드를 다시 할당 해야 하는 경우는 거의 없다.  
final 이 필드의 객체를 불변으로 만들지는 않지만, 최소한 참조는 보호된다는 점을 이해하는 것이 중요하다.  
즉, 객체의 불변성에 대해 별도로 작업해야 하지만 첫 단계는 필드를 final 로 만드는 것이다.

-> 타당한 이유로 필드를 다시 할당 해야 한다고 확신하지 않는 한 클래스의 모든 필드에 final 을 사용해라.

#### Best practices for final in local variables

---

지역 변수에 final 을 사용하는 것은 Java 커뮤니티에서 가장 의견이 불일치한다.  
일반적으로 재할당이 불가능한 메서드 매개변수와는 달리 지역 변수는 재할당이 가능한 경우가 많다.  
따라서, final 은 코드에서 개발자의 의도를 보여줄 수 있는 유용한 도구이다.

**Simulating expressions**  
함수형 패러타임을 채택한 언어에서는 표현식을 사용해서 변수에 값을 할당 할 수 있는데,
Java 에서는 그런 구문이 없지만 final 은 그러한 표현식에 가까워지는데 도움이 될 수 있다.
```
public void someMethod() {
   final String result;
   if (someValue == 200) {
      result = "SUCCESS";
   } else {
      result = "FAIL";
   }
   ...
}
```
이 경우에는 결과는 확실히 할당되고, if-else 표현식으로 결과가 출력되며 다시 할당되지 않는다.  
키워드 하나만 추가하면 제공할 수 있는 정보가 많다.

**Long methods**  
메서드가 길면 분할하는 것이고 이는 일반적으로 맞는 말이다.  
실제로 분할하고 싶지 않은 흐름의 메서드가 있을 수 있다.  
분할을 하게 되면 가독성이 떨어지고 순수하게 이름이 지정된 함수에 흐름이 숨겨질 수도 있다.

그런 경우 한 번 할당되고 그 시점부터 상수로 간주되는 지역 변수가 무엇인지, 메서드 실행 전발에 걸쳐서 변경될 변수가 무엇인지
알려주는게 훨씬 중요해진다.

-> 지역 변수에 대한 final 사용에 대해 공식적인 규칙을 만드는 것은 매우 어렵다.  
하지만 위에서 언급된 예제는 final 을 언제 사용해야 하는지에 대한 아이디어를 제공할 수 있다.
