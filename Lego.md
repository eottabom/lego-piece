
graceful shundown 
- about
- termination signals
  - SIGTERAM : 프로그램 종료를 유발하는데 사용되는 일반 신호
  - SIGINT : 사용자가 INTR (e.g. `Ctrl + c`) 입력하면 보내지는 신호
  - SIGQUIT : SIGINT 와 비슷하게 QUIT (e.g. `Ctrl + \`) 입력하면 보내지는 신호, 에러 시그널을 보낸 것처럼 core dump 수행
  - SIGKILL : 즉시 프로그램 종료, 어플리케이션에서 핸들링하거나 무시할 수 없음
  - SIGHUP : 사용자의 터미널이 Disconnected 되었을 때 알려주기 위함
    - https://www.gnu.org/software/libc/manual/html_node/Termination-Signals.html
- Spring graceful shutdown
  - 4개의 embedded web servers 모두 지원 (Jetty, Reactor Netty, Tomcat and Undertow)
  - Application context 를 닫는 과정의 일부로 발생해서 SmartLifecycle bean 을 중지하는 가장 초기 단계에서 수행된다.
    - SmartLifecycle 은 Lifecycle, Phased 를 구현한 인터페이스
    - 종료 시점에 isRunning 을 체크해서 stop 을 호출 해주고 (Lifecycle 과 동일)
    - 자동 실행으로 기동 시점에 start() 메서드를 호출
      - Lifecycle 은 void start() : 빈이 시작될 때, void stop() : 빈이 중지 될 때, boolean isRunning() : 빈이 현재 실행 중인지 여부
        - spring context 가 초기화 되거나 종료될 때 특정 동작을 수행 할 때
      -  SmartLifecycle 은 빈의 생명 주기를 세밀하게 제어
        -  int getPhase() : 빈 시작되거나 종료될 때 순서 결졍, 낮은 숫자일 수록 높은 우선순위
        -  boolean isAutoStartup() : context 가 시작될 때 자동으로 빈이 시작될지 결정
        -  void stop(Runnalbe callback) : 비동기적으로 빈 중지, 중지 이후에 콜백 호출
        -  복잡한 초기화 / 종료 시퀀스 관리할 때 사용
        -  SmartLifecycle 빈은 인스턴스화되고 의존성 주입이 완료된 이후에 start() 메서드를 통해 초기화 -> isAutoStartup true 인 경우 context 가 시작될 때 자동 호출
      -  spring graceful shutdown 은 `spring.lifecycle.timeout-per-shutdown-phase` 에 설정된 시간 동안 대기하는데, 이 시간내에 모든 SmartLifecycle 빈의 stop(Runnable callback) 이 호출되고, 비동기 작업이 완료될 때까지 application 은 종료 되지 않는다.
    - SmartLifecycle bean 만 phases 에 들어가고, start() 를 호출해줌
  - 이 처리 중지는 기존 요청은 완료 할 수 있고, 새 요청은 허용되지 않는 유예 기간을 제공하는 타임아웃을 사용한다.
  - Jetty, Reactor Netty, Tomcat 은 Network Later 에서 요청을 받지 않는다. (Tomcat 은 9.0.33 이상 필요)
  - Undertow 는 요청을 받지만, 503 에러를 응답
  - https://docs.spring.io/spring-boot/reference/web/graceful-shutdown.html
  - https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/beans.html#beans-factory-lifecycle-combined-effects
- k8s graceful shutdown
    1) Pod is set to the “Terminating” State and removed from the endpoints list of all Services
       - Termination 상태가 된 Pod 는 Service 의 로드밸런싱 대상에서 제외 -> 해당 Pod 로 트래픽이 전달되지 않음
    2) preStop Hook is executed
    3) SIGTERM signal is sent to the pod
       - Pod 안에 어플리케이션이 SIGTERM 신호를 받을 때 Graceful shutdown 을 지원하지 않으면 preStop 설정을 해야함  
    4) Kubernetes waits for a grace period
       - terminationGracePeriodSeconds (default: 30s) 기다림
       - preStop hook / SIGTERM 신호 실행과 병렬
       - kubernetes 는 완료될 때까지 기다리지 않음
       - 이 기간이 끝나면 다음 단계로 넘어감
       - GracePeriod 기간 동안 Pod 가 모두 종료되면 GracePeriod 을 모두 기다리지 않고 종료
       - preStop 의 작업을 기다리지 않는다 -> GracePeriod 가 지나면 preStop 설정된 작업이 진행중이더라도 강제 종료
    5) SIGKILL signal is sent to pod, and the pod is removed
       - SIGKILL 신호가 Pod 로 전송되고 Pod 제거
       - GracePeriod 이 후에도 컨테이너가 계속 실행 중이다 = SIGKILL 에 의해 강제 제거 and 종료
  - https://cloud.google.com/blog/products/containers-kubernetes/kubernetes-best-practices-terminating-with-grace?hl=en
- test : client -> server (shudown: immediate and graceful, timeout-per-shudown-phase: )
- spring.lifecycle.timeout-per-shutdown-phase + preStop sleep time < terminationGracePeriodSeconds
- 참고
  - https://www.thoughtworks.com/insights/blog/cloud/shutdown-services-kubernetes
  - https://spring.io/guides/topicals/spring-on-kubernetes
  
@PostConstruct / @PreDestory + Add spring bean life cycle

why new line in spring check style

리팩토링

optional (https://dzone.com/articles/using-optional-correctly-is-not-optional)

k6 예제

istio 트래픽 flow

envoy

java list (Arrays.asList vs List.of vs Collections.singletonList )

testcontainer 
