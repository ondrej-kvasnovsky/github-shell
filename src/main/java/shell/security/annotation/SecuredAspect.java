package shell.security.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import shell.security.NotAuthorizedException;
import shell.security.session.SessionHolder;

/**
 * Makes sure a session is present. If a session is not present,
 * {@link shell.security.NotAuthorizedException} exception is thrown.
 */
@Aspect
@Component
public class SecuredAspect {

    private final SessionHolder sessionHolder;

    public SecuredAspect(SessionHolder sessionHolder) {
        this.sessionHolder = sessionHolder;
    }

    @Around("@annotation(Secured)")
    public Object secured(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!sessionHolder.getCurrentClientSession().isPresent()) {
            throw new NotAuthorizedException("First you need to login. Use: login <your login> <your password>");
        }
        return joinPoint.proceed();
    }
}
