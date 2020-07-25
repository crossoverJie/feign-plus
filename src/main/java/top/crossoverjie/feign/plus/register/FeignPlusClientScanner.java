package top.crossoverjie.feign.plus.register;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import top.crossoverjie.feign.plus.factory.FeignPlusBeanFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020/7/25 02:44
 * @since JDK 11
 */
public class FeignPlusClientScanner extends ClassPathBeanDefinitionScanner {
    public FeignPlusClientScanner(BeanDefinitionRegistry registry) {
        super(registry, true);
        registerFilters() ;
    }

    public void registerFilters() {
        // include all interfaces
        addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });


        // exclude package-info.java
        addExcludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                String className = metadataReader.getClassMetadata().getClassName();
                return className.endsWith("package-info");
            }
        });
    }

    /**
     * @param basePackages
     * @return
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            logger.warn("No feign plus client is found in package '" + Arrays.toString(basePackages) + "'.");
        }

        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            MergedAnnotation<FeignPlusClient> feignPlus = ((ScannedGenericBeanDefinition) definition).getMetadata().getAnnotations().get(FeignPlusClient.class);
            MergedAnnotation<RequestMapping> requestMapping = ((ScannedGenericBeanDefinition) definition).getMetadata().getAnnotations().get(RequestMapping.class);


            String beanClassName = definition.getBeanClassName();
            definition.setBeanClass(FeignPlusBeanFactory.class);

            definition.getPropertyValues().add("proxyInterface", beanClassName);
            definition.getPropertyValues().add("url", buildUrl(feignPlus, requestMapping));


        }

        return beanDefinitions;
    }

    private String buildUrl( MergedAnnotation<FeignPlusClient> feignPlus, MergedAnnotation<RequestMapping> requestMapping){
        String url = feignPlus.getString("url") ;

        if (requestMapping.isPresent()){
            String[] value = (String[]) requestMapping.getValue("value").get();
            url += value[0] ;

        }

        return url ;

    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }
}
