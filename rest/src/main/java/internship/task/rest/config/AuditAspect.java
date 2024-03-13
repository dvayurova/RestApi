package internship.task.rest.config;

import internship.task.rest.models.Audit;
import internship.task.rest.repository.AuditRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AuditRepository auditRepository;

    @Around("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        Audit audit = new Audit();
        audit.setUsername(currentPrincipalName);
        audit.setMethod(joinPoint.getSignature().toString());
        audit.setParams(Arrays.toString(joinPoint.getArgs()));
        audit.setExecutionTime(executionTime);

        auditRepository.save(audit);

        return proceed;
    }
}

