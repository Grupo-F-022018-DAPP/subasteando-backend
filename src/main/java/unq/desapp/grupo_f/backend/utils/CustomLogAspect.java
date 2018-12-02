package unq.desapp.grupo_f.backend.utils;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomLogAspect {

	@Around("@annotation(CustomLogAnnotation)")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		final Logger logger = Logger.getLogger(joinPoint.getTarget().getClass());
		StringBuilder parametersBuilder = new StringBuilder();
		CodeSignature signature = (CodeSignature)joinPoint.getSignature();
		for(Integer i=0; i< signature.getParameterNames().length; i++) {
			parametersBuilder.append(signature.getParameterNames()[i] + ": " + joinPoint.getArgs()[i]);
			parametersBuilder.append(", ");
		}
		String parameters = parametersBuilder.toString();
		if(parameters.length() > 2) {
			parameters = parameters.substring(0, parameters.length() - 2);
		}
		
		logger.info(joinPoint.getSignature().toShortString() + "With: " + parameters);
		
		return joinPoint.proceed();
	}
}
