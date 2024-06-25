### Effectively final vs final

Java 8 에 도입된 기능 중 하나는 `Effectively final` 이다.  
이를 통해 변수, 필드 및 매개 변수에 대해 final 을 쓰지 않고 final 처럼 효과적으로 처리하고 사용할 수 있다.  

JLS 4.12.4 에서는 컴파일 타임 오류 없이 유효한 프로그램의 매개변수나 지역 변수에 final 를 제거하면 effectively final 이 된다고 명시하고 있다.  
https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.12

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
  
반면에 final 을 제거하면 effectively final 로 간주하지만, 연결에만 사용되기 때문에 컴파일러는 이를 최적화하지 않는다.

사실, `final` keyword 를 사용하면 얻을 수 있는 성능 상의 이점은 매우 인기 있는 논쟁이다.  
어디에 적용하느냐에 따라 `final` 은 다른 목적과 성능에 미치는 영향을 가질 수 있다.  

지역변수  
final 이 지역 변수에 적용될 때 값은 정확히 한 번 할당 되어야 한다.  
로컬 변수에 `final` 키워드를 사용하면 성능이 향상 될 수 있다. 
(JMH)  

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

Benchmark                                   Mode  Cnt  Score   Error  Units  
BenchmarkRunner.compilerOptimizationString  avgt    5  0.339 ± 0.006  ns/op  
BenchmarkRunner.finalStrings                avgt    5  0.340 ± 0.004  ns/op  
BenchmarkRunner.nonFinalStrings             avgt    5  4.404 ± 0.188  ns/op  


javac XXX.java  
javap -v -p -s XXX.class  

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

바이트 코드를 보면, finalStrings 메서드는 이미 최적화된 결과를 반환하는 것을 볼 수 있다.  
하지만, 지역 변수 `final` 키워드를 추가하는 것이 바이트 코드 최적화에 큰 영향을 주지 않는데,  
  
그 이유는 여러가지가 있을 것 같은데, 핵심은 객체 내부의 상태까지 불변을 보장 하지 않는다.  
final 키워드를 사용해서 최적할 수 있는 여러 기회를 제공하기는 하지만, 모든 경우에 해당되지 않는다는 것이다.  

예시)  
 
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
finalObject 은 final 로 선언되었기 때문에 생성자에서 한 번 초기화 된 후에 다시 할당 될 수 없다.  
이것은 컴파일러에세 객체 finalObject 가 초기화 이후에는 불변하다는 것을 알려주고, 컴파일러는 최적화를 수행할 수 있다.

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

위의 예제에서 numbers 는 final 로 선언되었지만 list 자체는 불변이 아니다.  
final 키워드는 객체의 참조가 변경되지 않음을 보장하지만,  
**객체의 내부 상태까지 불변을 보장하지 않는다.**
  
https://www.baeldung.com/java-final-performance



### 리팩토링

### optional (https://dzone.com/articles/using-optional-correctly-is-not-optional)

### k6 예제

### istio 트래픽 flow

### envoy

### java list (Arrays.asList vs List.of vs Collections.singletonList )

### testcontainer 

### 이펙티브 자바

### RestAssured

RestAssured 는 REST API 를 테스트하고 검증하는 오픈 소스 Java 라이브러리이다.  
JSON 및 XML 기반 웹 서비스를 테스트 하는데 사용할 수 있다.  
RestAssured 는 테스트 케이스 작성을 위해 Junit 및 TestNG 프레임워크와 통합 될 수 있다.
  
RestAssured 의 이점
1. HTTP 연결을 설정하고, 요청보내고, 응답을 수신/구문 분석하는데 필요한 많은 코드를 작성할 필요 없다.
   * 상태코드, 응답 시간 등을 빠르게 확인하기 위한 메서드가 내장되어 있음
   * JSON 또는 XML 응답 구문 분석을 위한 JSONPath, XMLPath 지원
   * 응답은 JSON 또는 XML 로 제공되므로 구문 분석 및 유효성 검사가 쉽다.
2. Give/When/Then 테스트 표기법을 지원하여 직관적이다.
   * BDD 접근 방식을 따르고 코드를 읽기 쉽게 given(), when(), then() 키워드 지원
3. RestAssured 는 Java 라이브러리이므로 지속적 통합/전달 설정이 매우 쉽다.
   * Junit TestNG 와 같은 테스트 프레임워크와 쉽게 통합

RestAssured 단점
* SOAP(Simple Object Access Protocol) API 테스트를 명시적으로 지원하지 않음

간단한 예시 

결론  
RestAssured 는 RESTful API 에 대한 강력하고 유지 관리 가능한 테스트를 생성하도록 설계된  
매우 효과적이고 사용자 친화적인 오픈 소스 Java 라이브러리이다.  
API 테스트 케이스 코딩 및 유지 관리에 대한 BDD 스타일 접근 방식을 제공하므로 테스터가 더 쉽게 이해하고 구현할 수 있다.  
테스트 사례 생성 프로세스를 단순화하고 결과가 정확하고 신뢰할 수 있도록 보장하는 다양한 어설션과 내장된 메서드를 제공한다.  

https://www.jadeglobal.com/blog/complete-guide-api-testing-using-rest-assured-tips-techniques-and-best-practices  
https://github.com/rest-assured/rest-assured/wiki/Usage


### java buildpack memory calculator
https://github.com/cloudfoundry/java-buildpack-memory-calculator
