package com.warrior.util.common;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/***
 * 类文件扫描
 *
 */

@Log4j
public class ClassPathScanHandler {

    //是否排除内部类 true->是 false->否
    @Setter @Getter
    private boolean excludeInner = true;

    //过滤规则适用情况 true->搜索符合规则的 false->排除符合规则的
    @Setter @Getter
    private boolean checkInOrEx = true;

    //过滤规则列表 null或者空，即全部符合不过滤
    @Setter @Getter
    private List<String> classFilters = null;

    public ClassPathScanHandler(){}

    public ClassPathScanHandler(boolean excludeInner, boolean checkInOrEx, List<String> classFilters) {
        this.excludeInner = excludeInner;
        this.checkInOrEx = checkInOrEx;
        this.classFilters = classFilters;
    }

    /**
     * 扫描包
     * @param basePackage 基础包
     * @param recursive   是否递归搜索子包
     * @return
     */
    public Set<Class<?>> getPackageAllClasses(String basePackage,boolean recursive){
        Set<Class<?>> classes = Sets.newLinkedHashSet();
        String packageName = basePackage;
        if (packageName.endsWith(".")){
            packageName = packageName.substring(0,packageName.length()-1);
        }
        String package2Path = packageName.replace(".","/");
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(package2Path);
            URL url;
            String protocol,filePath;
            while(dirs.hasMoreElements()){
                url = dirs.nextElement();
                protocol = url.getProtocol();
                if (StringUtils.equals("file",protocol)){
                    filePath = URLDecoder.decode(url.getFile(),"UTF-8");
                    doScanPackageClassesByFile(classes,packageName,filePath,recursive);
                } else if(StringUtils.equals("jar",protocol)){
                    doScanPackageClassesByJar(packageName,url,recursive,classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 以文件的方式扫描包下的所有Class文件
     * @param classes
     * @param packageName
     * @param packagePath
     * @param recursive
     */
    private void doScanPackageClassesByFile(Set<Class<?>> classes,String packageName,String packagePath,boolean recursive){
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()){
            return;
        }
        File [] dirfiles = dir.listFiles((File file) -> {
            if(file.isDirectory()){
                return recursive;
            }
            String fileName = file.getName();
            if (excludeInner && fileName.indexOf('$') != -1){
                return false;
            }
            return filterClassName(fileName);
        });
        Arrays.stream(dirfiles).forEach(item ->{
            if(item.isDirectory()){
                doScanPackageClassesByFile(classes,packageName+"."+item.getName(),item.getAbsolutePath(),recursive);
            } else {
                String className = item.getName().substring(0,item.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName+"."+className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 以jar的方式扫描包下的所有Class文件
     * @param basePackage
     * @param url
     * @param recursive
     * @param classes
     */
    private void doScanPackageClassesByJar(String basePackage,URL url,boolean recursive,Set<Class<?>> classes){
        String packageName = basePackage;
        String package2Path = packageName.replace('.', '/');
        JarFile jar;

        try {
            jar = ((JarURLConnection)url.openConnection()).getJarFile();
            Enumeration<JarEntry> entries = jar.entries();

            JarEntry entry;
            String name,classSimpleName,className;
            while (entries.hasMoreElements()){
                entry = entries.nextElement();
                name = entry.getName();
                if(!name.startsWith(package2Path) || entry.isDirectory()){
                    continue;
                }
                if(!recursive && name.lastIndexOf('/') != package2Path.length()){
                    continue;
                }
                if (this.excludeInner && name.indexOf('$') != -1){
                    continue;
                }
                classSimpleName = name.substring(name.lastIndexOf('/')+1);
                if(this.filterClassName(classSimpleName)){
                    className = name.replace('/','.');
                    className = className.substring(0,className.length()-6);
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(className));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 根据过滤规则判断类名
     * @param fileName
     * @return
     */
    private boolean filterClassName(String fileName){
        if (!fileName.endsWith(".class")){
            return false;
        }
        if(null == this.classFilters || this.classFilters.isEmpty()){
            return true;
        }
        String tmpName = fileName.substring(0,fileName.length() - 6);
        boolean flag = false;

        for (String item : classFilters) {
            String tmpreg = "^"+item.replace("*",".*")+"$";
            Pattern p = Pattern.compile(tmpreg);
            if(p.matcher(tmpName).find()){
                flag = true;
                break;
            }
        }
        return (checkInOrEx && flag) || (!checkInOrEx && !flag);
    }
}