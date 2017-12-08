package com.warrior.schedule.spring;

import com.google.common.collect.Sets;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

@Log4j
public class PackscanUtil {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    public static Set<String> findPackageClass(String scanPackages) {
        if (StringUtils.isBlank(scanPackages)) {
            return Collections.EMPTY_SET;
        }
        Set<String> clazzSet = Sets.newHashSet();
        try {
            Set<String> packages = checkPackage(scanPackages);
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            String packageSearchPath, clazz;
            Resource[] resources;
            for (String basePackage : packages) {
                if (StringUtils.isBlank(basePackage)) {
                    continue;
                }
                packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" +
                        DEFAULT_RESOURCE_PATTERN;
                resources = resourcePatternResolver.getResources(packageSearchPath);
                for (Resource resource : resources) {
                    clazz = loadClassName(metadataReaderFactory, resource);
                    if (StringUtils.isNotEmpty(clazz)) {
                        clazzSet.add(clazz);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clazzSet;
    }

    private static String loadClassName(MetadataReaderFactory metadataReaderFactory, Resource resource) throws IOException {
        if (resource.isReadable()) {
            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
            if (metadataReader != null && metadataReader.getClassMetadata().getInterfaceNames() != null
                    && metadataReader.getClassMetadata().getInterfaceNames().length > 0
                    && StringUtils.equals(metadataReader.getClassMetadata().getInterfaceNames()[0], "org.quartz.Job")
                    && metadataReader.getAnnotationMetadata().hasAnnotation("com.warrior.schedule.task.Job")) {
                return metadataReader.getClassMetadata().getClassName();
            }
        }
        return null;
    }

    private static Set<String> checkPackage(String scanPackages) {
        if (StringUtils.isBlank(scanPackages)) {
            return Collections.EMPTY_SET;
        }
        Set<String> packages = Sets.newHashSet();
        Collections.addAll(packages, scanPackages.split(","));
        boolean needAdd;
        for (String pInArr : packages.toArray(new String[packages.size()])) {
            if (StringUtils.isBlank(pInArr) || pInArr.equals(".") || pInArr.startsWith(".")) {
                continue;
            }
            if (pInArr.endsWith(".")) {
                pInArr = pInArr.substring(0, pInArr.length() - 1);
            }
            needAdd = true;
            for(String pack : packages){
                if (pInArr.startsWith(pack + ".")) {
                    needAdd = false;
                } else if (pack.startsWith(pInArr + ".")) {
                    packages.remove(pack);
                }
                if (needAdd) {
                    packages.add(pInArr);
                }
            }
        }
        return packages;
    }
}