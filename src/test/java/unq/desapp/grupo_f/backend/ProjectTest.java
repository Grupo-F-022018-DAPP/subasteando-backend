package unq.desapp.grupo_f.backend;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;



public class ProjectTest {

	
	@Test
	public void testAllModelObjectsHaveBuildersClasses() {
		try {
			List<Class<?>> classes = getClassesInPackage("unq.desapp.grupo_f.backend.model");
			List<Class<?>> builders = new ArrayList<>();
			
			String builderClassName;
			for(Class<?> c: classes) {
				builderClassName = "unq.desapp.grupo_f.backend.model.builders." + c.getSimpleName() + "Builder";
				builders.add(Class.forName(builderClassName));
			}
			assertEquals(builders.size(), classes.size());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void testAllModelObjectsAreSavedCorrectly() {
		
	}
	
	private List<Class<?>> getClassesInPackage(String packageName) throws ClassNotFoundException{
		List<Class<?>> classes = new ArrayList<>();
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		// add include filters which matches all the classes (or use your own)
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
		// add exclude filters
		provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*State.*")));
		provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*Builder.*")));
		provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*Exception.*")));
		provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*Test.*")));
		// get matching classes defined in the package
		final Set<BeanDefinition> beans = provider.findCandidateComponents(packageName);
		// this is how you can load the class type from BeanDefinition instance
		for (BeanDefinition bean: beans) {
		    Class<?> clazz = Class.forName(bean.getBeanClassName());
		    classes.add(clazz);
		}
		return classes;
	}
	
}
