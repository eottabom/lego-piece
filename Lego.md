
### why new line in spring check style
---

파일 끝에 개행이 없는 경우에는  
CheckStyle 을 돌리거나, github 에 올리면 경고가 뜨는데,  
파일 끝에 개행해줘야 하는 이유는 POSIX 명세가 그렇기 때문이다.  

https://pubs.opengroup.org/onlinepubs/007904875/basedefs/xbd_chap03.html

Definitions - 3.392 Text File : A file that contains characters organized into one or more lines. The lines do not contain NUL characters and none can exceed {LINE_MAX} bytes in length, including the Although IEEE Std 1003.1-2001 does not distinguish between text files and binary files (See the ISO C Standard), many utilities only produce predictable or meaningful output when operating on text files. The standard utilities that have such restrictions always specify “text files”in their STDIN or INPUT FILES sections.
Definitions - 3.205 Line : A sequence of zero or more non- s plus a terminating .

행의 끝(terminating)은 개행(EOL, end-of-line)
텍스트 파일은 행의 집합이며 행은 반드시 개행으로 끝난다.

POSIX(Portable operating system interface)  
운영체제 간의 호환성을 유지하기 위해 1980년대에 IEEE 에서 개발한 표준이다.
소프트웨어가 POSIX 표준을 충족한다면 다른 POSIX 호환 운영 체제와도 호환되어야 하는 것과 같다.

POSIX 표준은 1988년에 출시된 반면에 IEEE Std 1003.1-2017 은 2017에 출시되었다.
POSIX 표준 개발에는 여러가지 이유가 있는데 그 중에서도
응용프로그램 개발과 이식성을 쉽게 하기 위해 만들어졌기 때문에 UNIX 뿐만 아니라 다른 Non_UNIX 시스템에서도 사용할 수 있도록 만들었다.
이 표준은 어플리케이션이나 운영체제의 개발을 정의하지 않고 단지 어플리케이션과 운영체제의 계약을 설명한다.
POSIX 표준은 C언어로 작성되었지만 모든 언어와 함께 사용할 수 있다.
POSIX 는 성능 저하 없이 이식성을 달성할 수 있도록 설계 되었고, 개발자의 시간과 비용을 절약할 수 있다. 
이식성을 달성할 수 없는 경우 모든 시스템에 대해서 코드를 작성해야하는데 이는 시간과 비용이 많이 드는 프로세스이다.
간단히 말해서 POSIX 는 전 세계 개발자가 어플리케이션을 POSIX 표준 기능과 호환 되도록 만들기 위해 따르는 일련의 규칙 및 지침이다.
이는 어플리케이션이 다른 운영 체제에서 실행될 수 있는 이유다.

https://medium.com/@cloud.devops.enthusiast/posix-59d0ee68b498
https://www.baeldung.com/linux/posix



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
JSON 및 XML 기반 웹 서비시를 테스트 하는데 사용할 수 있다.  
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
* SOAP(Simple Object Access Protocol) API 테스트를 면시적으로 지원하지 않음

간단한 예시 

https://www.jadeglobal.com/blog/complete-guide-api-testing-using-rest-assured-tips-techniques-and-best-practices  
https://github.com/rest-assured/rest-assured/wiki/Usage


### java buildpack memory calculator
https://github.com/cloudfoundry/java-buildpack-memory-calculator
