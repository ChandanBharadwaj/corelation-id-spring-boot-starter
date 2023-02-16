# corelation-id-spring-boot-starter
 
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} - %X{CORRELATION} - [%thread] %-5level %logger{36} - %msg%n
%d{yyyy-MM-dd HH:mm:ss} ||Application_Name=${spring.application.name}||GUID = %X{X-B3-TraceId:-} [%thread] %-5level %logger{36} - %msg%n
