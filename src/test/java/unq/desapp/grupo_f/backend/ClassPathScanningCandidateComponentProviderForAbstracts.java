package unq.desapp.grupo_f.backend;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.AnnotationMetadata;

public class ClassPathScanningCandidateComponentProviderForAbstracts  extends ClassPathScanningCandidateComponentProvider{
	
	/**
	 * Determine whether the given bean definition qualifies as candidate.
	 * <p>The default implementation checks whether the class is not an interface
	 * and not dependent on an enclosing class.
	 * <p>Can be overridden in subclasses.
	 * @param beanDefinition the bean definition to check
	 * @return whether the bean definition qualifies as a candidate component
	 */
	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		AnnotationMetadata metadata = beanDefinition.getMetadata();
		return metadata.isAbstract();
	}

}
