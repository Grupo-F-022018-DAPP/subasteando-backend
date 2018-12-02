package unq.desapp.grupo_f.backend;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import unq.desapp.grupo_f.backend.repositories.AuctionRepository;
import unq.desapp.grupo_f.backend.repositories.BidRepository;
import unq.desapp.grupo_f.backend.repositories.UserRepository;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProjectTest {

	@Autowired 
	private AuctionRepository auctionRepository;
	@Autowired 
	private UserRepository userRepository;
	@Autowired 
	private BidRepository bidRepository;
	private HashMap<Class<?>, CrudRepository<?,?>>repositories;
	
	@Before
	public void before() {
		repositories = new HashMap<Class<?>, CrudRepository<?, ?>>();
		repositories.put(AuctionRepository.class, auctionRepository);
		repositories.put(UserRepository.class,userRepository);
		repositories.put(BidRepository.class,bidRepository);
	}
	@Test
	public void testAllModelObjectsHaveBuildersClasses() {
		try {
			List<Class<?>> classes = getAllModelClasses();
			List<Class<?>> builders = getAllBuildersClassesForModels(classes);
			assertEquals(builders.size(), classes.size());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	@Transactional
	public void testAllModelObjectsAreSavedCorrectly() {
		try {
			List<Class<?>> models = getAllModelClasses();
			List<Class<?>> builders = getAllBuildersClassesForModels(models);
			Object instance;
			Method buildMethod;
			Object buildedObject;
			Method saveMethod;
			Method findMethod;
			Object foundObject;
			Method getIdMethod;
			Method optionalGetMethod = Optional.class.getMethod("get");
			
			for(Class<?> builder: builders) {
				
				System.out.println(builder.getSimpleName());
				
				
				instance = builder.newInstance();										//create instance of a builder
				buildMethod = builder.getDeclaredMethod("buildRandom");					//get its builder.buildRandom method
				buildedObject = buildMethod.invoke(instance);							//and invoke it in the instance
				String className = builder.getSimpleName().replaceAll("Builder", "");	
				Class<?> repo = getRepositoryForClass(className);
				
				Class<?>[] params = new Class<?>[1];
				params[0] = buildedObject.getClass();
				saveMethod = repo.getDeclaredMethod("save", params);
				
				saveMethod.invoke(getRepositoryInstance(repo), buildedObject);

				findMethod = repo.getDeclaredMethod("findById", Integer.class);
				getIdMethod = buildedObject.getClass().getMethod("getId");
				foundObject = optionalGetMethod.invoke(findMethod.invoke(getRepositoryInstance(repo), getIdMethod.invoke(buildedObject)));
				
				assertEquals(getIdMethod.invoke(foundObject), getIdMethod.invoke(buildedObject));
			}
			
		} catch (ClassNotFoundException 	| InstantiationException 	| IllegalAccessException 
				| NoSuchMethodException 	| SecurityException 		| IllegalArgumentException 
				| InvocationTargetException e) {
			
			e.printStackTrace();
			assert false;
		}
	}
	

	/* ******************************************
	 * 				Private methods				*
	 * ******************************************/
	

	
	private CrudRepository<?,?> getRepositoryInstance(Class<?> repositoryClass) {
		return this.repositories.get(repositoryClass);
	}
	private List<Class<?>> getClassesInPackage(String packageName, List<String> includeRegexPatterns, List<String> excludeRegexPatterns) throws ClassNotFoundException{
		List<Class<?>> classes = new ArrayList<>();
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		for(String pattern: includeRegexPatterns) {
			provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(pattern)));
		}
		for(String pattern: excludeRegexPatterns) {
			provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(pattern)));
		}

		// get matching classes defined in the package
		final Set<BeanDefinition> beans = provider.findCandidateComponents(packageName);
		// this is how you can load the class type from BeanDefinition instance
		for (BeanDefinition bean: beans) {
		    Class<?> clazz = Class.forName(bean.getBeanClassName());
		    classes.add(clazz);
		}
		return classes;
	}
	
	private List<Class<?>> getClassesInPackageWithProvider(ClassPathScanningCandidateComponentProvider provider, String packageName, List<String> includeRegexPatterns, List<String> excludeRegexPatterns) throws ClassNotFoundException{
		List<Class<?>> classes = new ArrayList<>();
		for(String pattern: includeRegexPatterns) {
			provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(pattern)));
		}
		for(String pattern: excludeRegexPatterns) {
			provider.addExcludeFilter(new RegexPatternTypeFilter(Pattern.compile(pattern)));
		}

		// get matching classes defined in the package
		final Set<BeanDefinition> beans = provider.findCandidateComponents(packageName);
		// this is how you can load the class type from BeanDefinition instance
		for (BeanDefinition bean: beans) {
		    Class<?> clazz = Class.forName(bean.getBeanClassName());
		    classes.add(clazz);
		}
		return classes;
	}
	
	private Class<?> getRepositoryForClass(String className) throws ClassNotFoundException{
		List<String> excludeRegexPatterns = new ArrayList<String>() ;
		List<String> includeRegexPatterns = new ArrayList<String>(Arrays.asList(".*"));
		List<Class<?>> 		possibleRepos = this.getClassesInPackageWithProvider(new ClassPathScanningCandidateComponentProviderForAbstracts(), 
																				"unq.desapp.grupo_f.backend.repositories", includeRegexPatterns, excludeRegexPatterns);
		Optional<Class<?>> repo = Optional.empty();
		for(Class<?> posRepo: possibleRepos) {
			posRepo.getSimpleName();
			List<String> repoWords = Arrays.asList(posRepo.getSimpleName().split("(?=\\p{Upper})"));
			List<String> classNameWords = Arrays.asList(className.split("(?=\\p{Upper})"));

			if(repoWords.contains(className) || classNameWords.stream().anyMatch(word -> repoWords.contains(word))) {
				System.out.println(repoWords);
				System.out.println(classNameWords);
				repo = Optional.of(posRepo);
			}
		}
		return repo.orElseThrow(() -> new ClassNotFoundException());
		
	}

	private List<Class<?>> getAllBuildersClassesForModels(List<Class<?>> classes) throws ClassNotFoundException {
		List<Class<?>> builders = new ArrayList<>();
		
		String builderClassName;
		for(Class<?> c: classes) {
			builderClassName = "unq.desapp.grupo_f.backend.model.builders." + c.getSimpleName() + "Builder";
			builders.add(Class.forName(builderClassName));
		}
		return builders;
	}

	private List<Class<?>> getAllModelClasses() throws ClassNotFoundException {
		List<String> include = new ArrayList<String>(Arrays.asList(".*"));
		List<String> exclude = new ArrayList<String>(Arrays.asList(	".*State.*",
																	".*Builder.*", 
																	".*Exception.*", 
																	".*Test.*",
																	".*RandomStrings.*"));
		
		List<Class<?>> classes = getClassesInPackage("unq.desapp.grupo_f.backend.model", include, exclude);
		return classes;
	}
	
}
