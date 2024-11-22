package ar.edu.unq.dapp_api.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Aspect
@Component
public class WebServiceAuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebServiceAuditAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logWebServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();

        // Start the timer
        Instant start = Instant.now();

        // Obtain method details
        String user = getAuthenticatedUser();
        String methodName = joinPoint.getSignature().getName();

        // Serialize the arguments to JSON
        String params = objectMapper.writeValueAsString(joinPoint.getArgs());

        // Proceed with the method execution
        Object result = joinPoint.proceed();

        // Measure execution time
        Instant end = Instant.now();
        long executionTime = Duration.between(start, end).toMillis();

        // Log the details
        String timestamp = java.time.format.DateTimeFormatter.ISO_INSTANT.format(start);
        logger.info("Timestamp: {}, User: {}, Method: {}, Parameters: {}, ExecutionTime: {} ms",
                timestamp, user, methodName, params, executionTime);

        return result;
    }


    private String getAuthenticatedUser() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "unknown"; // Si no se puede obtener el usuario, retornamos "unknown"
        }
    }
}