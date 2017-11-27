package com.warrior.web.config;

import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSConfigurationException;
import com.warrior.common.cache.PushCache;
import com.warrior.common.spring.CustomApplicationListener;
import com.warrior.common.spring.CustomDateConverter;
import com.warrior.common.spring.GlobalExceptionHandler;
import com.warrior.common.web.WarriorSession;
import com.warrior.web.filter.CorsSpringFilter;
import com.warrior.web.interceptor.SpringInterceptor;
import com.warrior.web.shiro.ShiroConfig;
import net.sf.ehcache.CacheManager;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

/***
 * @author rookie
 * spring mvc java配置[对应相应的xml配置]
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ImportResource(value = {"classpath:spring-mybatis.xml"})
@Import(value = {SwaggerConfig.class,ShiroConfig.class})
@ComponentScan(basePackages = {"com.warrior"})
public class WebConfig extends WebMvcConfigurerAdapter{

    @Bean("exceptionHandler")
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".jsp");
        viewResolver.setPrefix("/WEB-INF/");
        registry.viewResolver(viewResolver);
//        registry.viewResolver(freeMarkerViewResolver());
    }

    @Bean("viewResolver")
    public FreeMarkerViewResolver freeMarkerViewResolver(){
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setViewClass(FreeMarkerView.class);
        viewResolver.setSuffix(".html");
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setExposeRequestAttributes(true);
        viewResolver.setExposeSessionAttributes(true);
        viewResolver.setExposeSpringMacroHelpers(true);
        viewResolver.setRequestContextAttribute("request");
        return viewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/");
        configurer.setDefaultEncoding("UTF-8");
        Map<String,Object> setting = new HashMap<>();
        setting.put("template_update_delay",10);
        setting.put("locale","zh_CN");
        setting.put("datetime_format","yyyy-MM-dd HH:mm:ss");
        setting.put("date_format","yyyy-MM-dd");
        configurer.setFreemarkerVariables(setting);
        return configurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/dist/**").addResourceLocations("/dist/");
//        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("/swagger-ui.html");
//        registry.addResourceHandler("/webjars/springfox-swagger-ui/**").addResourceLocations("/webjars/springfox-swagger-ui/");
    }

    @Bean
    public FormattingConversionServiceFactoryBean conversionService(){
        FormattingConversionServiceFactoryBean factoryBean = new FormattingConversionServiceFactoryBean();
        Set<Converter> set = new LinkedHashSet<>();
        set.add(new CustomDateConverter());
        factoryBean.setConverters(set);
        return factoryBean;
    }

    /**
     * 自定义spring文件上传编码
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }

    //解析json返回数据
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList(converter.getSupportedMediaTypes());
        converter.setSupportedMediaTypes(mediaTypes);
        mediaTypes.addAll(Arrays.asList(new MediaType[]{MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.TEXT_XML}));
        converters.add(converter);
    }

    @Bean
    public CustomApplicationListener customApplicationListener(){
        return new CustomApplicationListener();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SpringInterceptor());
    }

    @Bean("corsFilter")
    public CorsSpringFilter corsSpringFilter(){
        CorsSpringFilter corsFilter = null;
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("cors.properties"));
            CORSConfiguration configuration = new CORSConfiguration(properties);
            corsFilter = new CorsSpringFilter(configuration);
        } catch (CORSConfigurationException e) {
            e.printStackTrace();
        } catch (ServletException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return corsFilter;
    }

    @Bean
    public CacheManager cacheManager(){
        CacheManager manager = CacheManager.create(this.getClass().getClassLoader().getResourceAsStream("ehcache.xml"));
        return manager;
    }

    @Bean
    public PushCache pushCache(){
        return new PushCache(cacheManager());
    }

    @Bean
    public WarriorSession warriorSession(){
        return new WarriorSession(cacheManager());
    }
}