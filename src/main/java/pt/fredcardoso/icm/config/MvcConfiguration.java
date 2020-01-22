package pt.fredcardoso.icm.config;

import java.util.Locale;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import pt.fredcardoso.icm.dao.BidDAO;
import pt.fredcardoso.icm.dao.BidDAOImpl;
import pt.fredcardoso.icm.dao.MultimediaDAO;
import pt.fredcardoso.icm.dao.MultimediaDAOImpl;
import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.dao.ProductDAOImpl;
import pt.fredcardoso.icm.dao.SoldDAO;
import pt.fredcardoso.icm.dao.SoldDAOImpl;
import pt.fredcardoso.icm.dao.UserDAO;
import pt.fredcardoso.icm.dao.UserDAOImpl;
import pt.fredcardoso.icm.handlerInterceptors.AuthHandler;
import pt.fredcardoso.icm.services.BidService;
import pt.fredcardoso.icm.services.BidServiceImpl;
import pt.fredcardoso.icm.services.MultimediaService;
import pt.fredcardoso.icm.services.MultimediaServiceImpl;
import pt.fredcardoso.icm.services.ProductService;
import pt.fredcardoso.icm.services.ProductServiceImpl;
import pt.fredcardoso.icm.services.SoldService;
import pt.fredcardoso.icm.services.SoldServiceImpl;
import pt.fredcardoso.icm.services.UserService;
import pt.fredcardoso.icm.services.UserServiceImpl;
import pt.fredcardoso.icm.utils.ArrayUtil;

@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {

	private ApplicationContext applicationContext;
	private Locale locale = new Locale("pt", "PT");

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Bean
	public MappedInterceptor myInterceptor()
	{
	    return new MappedInterceptor(null, new AuthHandler());
	}

	@Bean
	public ViewResolver htmlViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
		resolver.setContentType("text/html");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(ArrayUtil.array("*.html"));
		return resolver;
	}

	@Bean
	public DataSource getDataSource() {
		DataSource dataSource = new DataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://home.fredcardoso.pt//icm");
		dataSource.setUsername("sa");
		dataSource.setPassword("pass");

		return dataSource;
	}

	@Bean
	public ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addDialect(new LayoutDialect(new GroupingStrategy()));
		engine.setTemplateResolver(templateResolver);
		return engine;
	}

	private ITemplateResolver htmlTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}
	
	@Bean
	public MessageSource messageSource() {
	    final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

	    messageSource.setBasenames("/WEB-INF/messages/auth", "/WEB-INF/messages/error", "/WEB-INF/messages/validation");
	    messageSource.setDefaultEncoding("UTF-8");
	    messageSource.setFallbackToSystemLocale(false);
	    messageSource.setCacheSeconds(0);

	    return messageSource;
	}
	
    @Bean(name="localeResolver")
    public LocaleResolver localeResolver(){
        SessionLocaleResolver  resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(locale);
        return resolver;
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDAO getUserDAO() {
		return new UserDAOImpl(getDataSource());
	}

	@Bean
	public UserService getUserService() {
		return new UserServiceImpl();
	}
	
	@Bean
	public ProductDAO getProductDAO() {
		return new ProductDAOImpl(getDataSource());
	}
	
	@Bean
	public ProductService getProductService() {
		return new ProductServiceImpl();
	}
	
	@Bean
	public MultimediaDAO getMultimediaDAO() {
		return new MultimediaDAOImpl(getDataSource());
	}

	@Bean
	public MultimediaService getMultimediaService() {
		return new MultimediaServiceImpl();
	}
	
	@Bean
	public BidDAO getBidDAO() {
		return new BidDAOImpl(getDataSource());
	}

	@Bean
	public BidService getBidService() {
		return new BidServiceImpl();
	}
	
	@Bean
	public SoldDAO getSoldDAO() {
		return new SoldDAOImpl(getDataSource());
	}
	
	@Bean
	public SoldService getSoldService() {
		return new SoldServiceImpl();
	}
}
