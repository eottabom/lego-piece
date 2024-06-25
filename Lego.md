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

Benchmark                              Mode  Cnt  Score   Error  Units
BenchmarkRunner.concatFinalStrings     avgt  200  2,976 ± 0,035  ns/op
BenchmarkRunner.concatNonFinalStrings  avgt  200  7,375 ± 0,119  ns/op  

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
