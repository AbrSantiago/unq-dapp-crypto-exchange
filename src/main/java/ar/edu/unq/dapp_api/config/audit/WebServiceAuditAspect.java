package ar.edu.unq.dapp_api.config.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
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
    private final ObjectMapper objectMapper;

    public WebServiceAuditAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logWebServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // Obtener timestamp de la ejecución
        Instant start = Instant.now();

        // Obtener el usuario autenticado
        String user = getAuthenticatedUser();

        // Nombre del método y parámetros
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String params = "no parameters";

        // Intentar serializar los parámetros del método
        try {
            if (args != null && args.length > 0) {
                // Filtrar parámetros problemáticos como HttpServletRequest
                Object[] filteredArgs = Arrays.stream(args)
                        .filter(arg -> !(arg instanceof HttpServletRequest))
                        .toArray();

                // Serializar los parámetros filtrados
                params = objectMapper.writeValueAsString(filteredArgs);
            }
        } catch (JsonProcessingException e) {
            // Loguear error en caso de que no se pueda serializar, pero no interrumpir
            logger.warn("Error serializing parameters for method {}: {}", methodName, e.getMessage());
        }

        // Ejecutar el método
        Object result = joinPoint.proceed();

        // Calcular el tiempo de ejecución
        Instant end = Instant.now();
        long executionTime = Duration.between(start, end).toMillis();

        // Loguear la auditoría
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