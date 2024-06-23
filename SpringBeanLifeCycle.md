  
### @PostConstruct / @PreDestory + Add spring bean life cycle
---
Spring Bean Life Cycle 흐름
1) 스프링 컨테이너 생성
2) 스프링 빈 생성
3) 의존성 주입
4) 초기화 콜백 : 빈 생성, 빈의 의존 관계 주입 완료 후 호출
5) 사용
6) 소멸전 콜백 : 빈 소멸 직전 호출
7) 스프링 종료

스프링 빈은 객체를 생성하고 의존관계 주입이 다 끝나야 필요한 데이터를 사용할 수 있는 준비가 완료된다.  
즉, 생성자가 호출되면 스프링 빈은 초기화 전이고 DI 가 이루어진 후에 스프링 빈이 초기화 된다.  
초기화 작업은 의존 관계 주입이 모두 완료되고 난 후에 호출되는데, 개발자가 의존 관계 주입이 완료된 시점을 알 수가 없다.  

Bean 생명 주기 관리
(빈 인스턴스화 및 DI)  
XML 파일 / 자바설정클래스 / 어노테이션에서 빈 정의 스캔 -> 빈 인스턴스 생성 -> 빈 프로퍼티에 의존성 주입 ->  
(스프링 인지 여부 검사)  
-> 빈이 BeanNameAware 인터페이스 구현 시 setBeanName() 호출 -> 빈이 BeanClassLoaderAware 인터페이스 구현시 setBeanClassLoader() 호출 -> 빈이 ApplicationContextAware 인터페이스 구현시 setApplicationContext() 호출 ->  
(빈 생성 생명 주기 콜백)  
-> @PostConstruct 어노테이션 적용 메서드 호출 -> 빈인 InitializingBean 인터페이스 구현시 afterProperteisSet() 호출 -> 빈이 init-method 정의 하면 지정한 메서드 호출 ->  
(빈 소멸 생명 주기 콜백)  
-> @PreDestory 어노테이션 적용 메서드 호출 -> 빈이 DispoableBean 인터페이스 구현시 destroy() 호출 -> 빈이 destroy-method 정의하면 지정한 메서드 호출 -> prototype 스코프 빈에서는 호출되지 않음  

Spring 은 빈 생명 주기 콜백을 지원하는데,  
인터페이스(InitializingBean, DisposableBean), Bean 설정 정보에 초기화,종료 메서드 지정,  @PostConstruct / @PreDestory 어노테이션 지원  

@PostConstruct / @PreDestory 이건 JSR-250 표준이라 spring 이 아닌 컨테이너에서도 동작한다.  
@PostConstruct - 초기화 콜백  
생성자가 호출되면, 의존 관계 주입이 끝나지 않았기 때문에 빈은 초기화 되지 않는다.  
@PostConstruct 를 이용해서 DI 가 이루어진 후에 초기화를 수행해서 객체의 값을 설정한 체로 호출할 수 있다!  
@PreDestory - 소멸전 콜백  
application context 에서 bean 을 제거하기 위해 사용된다.  

https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/postconstruct-and-predestroy-annotations.html
