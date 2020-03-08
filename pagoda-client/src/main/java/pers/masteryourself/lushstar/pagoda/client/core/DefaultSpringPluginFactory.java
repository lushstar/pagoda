package pers.masteryourself.lushstar.pagoda.client.core;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import pers.masteryourself.lushstar.pagoda.client.PluginFactory;
import pers.masteryourself.lushstar.pagoda.client.util.ResourcesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>description : DefaultSpringPluginFactory
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 22:10
 */
@Slf4j
public class DefaultSpringPluginFactory implements ApplicationContextAware, PluginFactory {

    private ApplicationContext applicationContext;

    /**
     * 存储所有的插件
     */
    private Map<Long, PluginChangeMetadata> pluginCache = new ConcurrentHashMap<>();

    private Map<Long, JarURLConnection> jarCache = new ConcurrentHashMap<>();

    /**
     * 本地插件位置
     */
    private static final String PLUGIN_PATH = System.getProperty("user.dir") + File.separator + "plugin";

    static {
        File file = new File(PLUGIN_PATH);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            log.info("创建 {} 目录结果 {}", PLUGIN_PATH, result ? "成功" : "失败");
        }
    }

    @Override
    public void install(PluginChangeMetadata pluginChangeMetadata) {
        // 1. 判断插件是否已经激活
        PluginChangeMetadata cachePlugin = pluginCache.get(pluginChangeMetadata.getId());
        if (cachePlugin != null) {
            log.warn("plugin {} has been active, cancel install operation", cachePlugin);
            return;
        }
        // 2. 判断插件是否存在，不存在则下载插件
        log.info("download plugin {} start", pluginChangeMetadata);
        this.download(pluginChangeMetadata);
        // 3. 添加到插件列表中
        // todo temp solution
        pluginChangeMetadata.setActive(false);
        pluginCache.putIfAbsent(pluginChangeMetadata.getId(), pluginChangeMetadata);
    }


    @Override
    public void uninstall(Long id) {
        // 1. 判断插件是否存在
        PluginChangeMetadata plugin = pluginCache.get(id);
        if (plugin == null) {
            log.warn("plugin id {} is empty, cancel uninstall operation", id);
            return;
        }
        // 2. 禁用插件
        this.disable(id);
        // 3. 释放 jar 包资源
        this.releaseJarResource(plugin.getId());
        // 4. 删除本地 plugin 文件
        this.deleteLocalPlugin(plugin);
        // 5. 清除插件 cache 缓存
        pluginCache.remove(id);
    }

    @Override
    public void active(Long id) {
        // 1. 判断插件是否存在
        PluginChangeMetadata plugin = pluginCache.get(id);
        if (plugin == null) {
            log.warn("plugin id {} is empty, cancel active operation", id);
            return;
        }
        // 2. 判断插件是否激活
        if (plugin.isActive()) {
            log.warn("plugin id {} has been active", id);
            return;
        }
        try {
            // 3. 装载 jar 包
            URLClassLoader classLoader = (URLClassLoader) this.getClass().getClassLoader();
            this.localJarResource(classLoader, plugin);
            // 4. 装载插件并实例化
            Advice advice = this.loadAdviceClass(plugin, classLoader);
            // 5. 装载至 spring proxy bean 中
            this.modifyAdvisedAdvice(plugin, advice);
            // 6. 设置激活状态
            plugin.setActive(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public void disable(Long id) {
        // 1. 判断插件是否存在
        PluginChangeMetadata plugin = pluginCache.get(id);
        if (plugin == null) {
            log.warn("plugin id {} is empty, cancel uninstall operation", id);
            return;
        }
        // 2. 判断插件是否禁用
        if (!plugin.isActive()) {
            log.warn("plugin id {} has been active", id);
            return;
        }
        // 3. 禁用插件
        this.modifyAdvisedAdvice(plugin, null);
        // 4. 设置激活状态
        plugin.setActive(false);
    }

    /**
     * 注入 applicationContext
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 装载插件并实例化
     *
     * @param pluginChangeMetadata
     * @param classLoader
     * @return
     * @throws Exception
     */
    private Advice loadAdviceClass(PluginChangeMetadata pluginChangeMetadata, URLClassLoader classLoader) throws Exception {
        // 获取插件类名
        Class<?> pluginClass = classLoader.loadClass(pluginChangeMetadata.getClassName());
        if (!Advice.class.isAssignableFrom(pluginClass)) {
            throw new RuntimeException("插件配置错误");
        }
        return (Advice) pluginClass.newInstance();
    }

    /**
     * 用 ClassLoader 加载资源文件
     *
     * @param pluginChangeMetadata
     * @return
     * @throws Exception
     */
    private void localJarResource(URLClassLoader classLoader, PluginChangeMetadata pluginChangeMetadata) throws Exception {
        // 1. 构建 jar file 协议
        URL targetUrl = new URL("jar:file:/" + pluginChangeMetadata.getLocalAddress() + "!/");
        // 2. 判断是否已经加载过 jar 包
        URL[] clUrls = classLoader.getURLs();
        boolean exist = false;
        for (URL temp : clUrls) {
            if (temp.equals(targetUrl)) {
                exist = true;
                break;
            }
        }
        // 3. 加载至 ClassLoader
        if (!exist) {
            // 通过反射调用 addURL 方法
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, targetUrl);
            // 4. 缓存 url 资源
            this.cacheUrlConnection(pluginChangeMetadata.getId(), targetUrl);
        } else {
            log.warn("pluginChangeMetadata {} has been load", pluginChangeMetadata.getLocalAddress());
        }
    }

    /**
     * 删除本地插件 jar 包
     *
     * @param plugin
     */
    private void deleteLocalPlugin(PluginChangeMetadata plugin) {
        String localAddress = plugin.getLocalAddress();
        File file = new File(localAddress);
        if (file.exists()) {
            boolean flag = file.delete();
            log.info("删除本地插件 {} {}", localAddress, flag ? "成功" : "失败");
        } else {
            log.info("文件不存在：{}", localAddress);
        }
    }

    /**
     * 下载插件
     *
     * @param pluginChangeMetadata
     */
    private void download(PluginChangeMetadata pluginChangeMetadata) {
        String address = pluginChangeMetadata.getAddress();
        FileInputStream inputStream = null;
        FileChannel readChannel = null;
        FileOutputStream outputStream = null;
        FileChannel writeChannel = null;
        try {
            inputStream = new FileInputStream(address);
            String name = address.substring(address.lastIndexOf(File.separator) + 1);
            readChannel = inputStream.getChannel();
            String localAddress = PLUGIN_PATH + File.separator + name;
            outputStream = new FileOutputStream(localAddress);
            writeChannel = outputStream.getChannel();
            writeChannel.transferFrom(readChannel, 0, readChannel.size());
            pluginChangeMetadata.setLocalAddress(localAddress);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            ResourcesUtils.close(inputStream);
            ResourcesUtils.close(readChannel);
            ResourcesUtils.close(outputStream);
            ResourcesUtils.close(writeChannel);
        }
    }

    /**
     * 修改 spring proxy bean 的 advice
     *
     * @param plugin
     * @param advice
     */
    private void modifyAdvisedAdvice(PluginChangeMetadata plugin, Advice advice) {
        for (String name : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(name);
            if (bean == this) {
                continue;
            }
            if (!(bean instanceof Advised)) {
                continue;
            }
            String className = plugin.getClassName();
            if (advice != null) {
                // 判断是否装载过
                Advice candidateAdvice = this.findAdvice(className, (Advised) bean);
                if (candidateAdvice != null) {
                    continue;
                }
                // 添加 advice，结束
                ((Advised) bean).addAdvice(advice);
                log.info("bean {}, add advice {}", ((Advised) bean).getTargetSource(), advice);
                continue;
            }
            // 删除，判断是否有这个 Advice
            Advice candidateAdvice = this.findAdvice(className, (Advised) bean);
            if (candidateAdvice != null) {
                // 删除
                ((Advised) bean).removeAdvice(candidateAdvice);
                log.info("bean {}, remove advice {}", ((Advised) bean).getTargetSource(), candidateAdvice);
            }
        }
    }

    /**
     * 查找某个 bean 的 Advice
     *
     * @param className
     * @param advised
     * @return
     */
    private Advice findAdvice(String className, Advised advised) {
        for (Advisor advisor : advised.getAdvisors()) {
            Advice candidateAdvice = advisor.getAdvice();
            if (candidateAdvice.getClass().getName().equals(className)) {
                return candidateAdvice;
            }
        }
        return null;
    }

    /**
     * 缓存 jar 资源
     *
     * @param id
     * @param url
     */
    private void cacheUrlConnection(Long id, URL url) {
        try {
            // 打开并缓存 url 连接
            URLConnection uc = url.openConnection();
            if (uc instanceof JarURLConnection) {
                uc.setUseCaches(true);
                jarCache.put(id, (JarURLConnection) uc);
            }
        } catch (Exception e) {
            System.err.println("Failed to cache plugin JAR url: " + url.toExternalForm());
        }
    }

    /**
     * 释放 jar 包资源
     *
     * @param id
     */
    private void releaseJarResource(Long id) {
        try {
            JarURLConnection jarurlconnection = jarCache.get(id);
            if (jarurlconnection == null) {
                log.warn("未找到插件 {} 对应的 jarurlconnection", pluginCache.get(id));
                return;
            }
            jarurlconnection.getJarFile().close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
