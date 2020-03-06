package pers.masteryourself.lushstar.pagoda.client;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import pers.masteryourself.lushstar.pagoda.client.po.Plugin;

import java.io.*;
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
    private Map<Long, Plugin> pluginCache = new ConcurrentHashMap<>();

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
    public void install(Plugin plugin) {
        // 1. 判断插件是否已经激活
        Plugin cachePlugin = pluginCache.get(plugin.getId());
        if (cachePlugin != null) {
            log.debug("plugin {} has been active, cancel install operation", cachePlugin);
            return;
        }
        // 2. 判断插件是否存在，不存在则下载插件
        log.info("download plugin {} start", plugin);
        this.download(plugin);
        // 3. 添加到插件列表中
        pluginCache.putIfAbsent(plugin.getId(), plugin);
    }


    @Override
    public void uninstall(Long id) {
        // 1. 判断插件是否存在
        Plugin plugin = pluginCache.get(id);
        if (plugin == null) {
            log.debug("plugin id {} is empty, cancel uninstall operation", id);
            return;
        }
        // 2. 禁用插件
        this.disable(id);
        // 3. 释放 jar 包资源
        this.releaseJarResource(plugin.getId());
        // 4. 删除本地 plugin 文件
        this.deleteLocalPlugin(plugin);
    }

    @Override
    public void active(Long id) {
        // 1. 判断插件是否存在
        Plugin plugin = pluginCache.get(id);
        if (plugin == null) {
            log.debug("plugin id {} is empty, cancel active operation", id);
            return;
        }
        try {
            // 2. 装载 jar 包
            URLClassLoader classLoader = (URLClassLoader) this.getClass().getClassLoader();
            this.localJarResource(classLoader, plugin);
            // 3. 装载插件并实例化
            Advice advice = this.loadAdviceClass(plugin, classLoader);
            // 4. 装载至 spring proxy bean 中
            this.modifyAdvisedAdvice(plugin, advice);
            // 5. 设置激活状态
            plugin.setActive(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public void disable(Long id) {
        // 1. 判断插件是否存在
        Plugin plugin = pluginCache.get(id);
        if (plugin == null) {
            log.debug("plugin id {} is empty, cancel uninstall operation", id);
            return;
        }
        // 2. 禁用插件
        this.modifyAdvisedAdvice(plugin, null);
        // 3. 设置激活状态
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
     * @param plugin
     * @param classLoader
     * @return
     * @throws Exception
     */
    private Advice loadAdviceClass(Plugin plugin, URLClassLoader classLoader) throws Exception {
        // 获取插件类名
        Class<?> pluginClass = classLoader.loadClass(plugin.getClassName());
        if (!Advice.class.isAssignableFrom(pluginClass)) {
            throw new RuntimeException("插件配置错误");
        }
        return (Advice) pluginClass.newInstance();
    }

    /**
     * 用 ClassLoader 加载资源文件
     *
     * @param plugin
     * @return
     * @throws Exception
     */
    private void localJarResource(URLClassLoader classLoader, Plugin plugin) throws Exception {
        // 1. 构建 jar file 协议
        URL targetUrl = new URL("jar:file:/" + plugin.getLocalAddress() + "!/");
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
            this.cacheUrlConnection(plugin.getId(), targetUrl);
        } else {
            log.debug("plugin {} has been load", plugin.getLocalAddress());
        }
    }

    /**
     * 删除本地插件 jar 包
     *
     * @param plugin
     */
    private void deleteLocalPlugin(Plugin plugin) {
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
     * @param plugin
     */
    private void download(Plugin plugin) {
        String address = plugin.getAddress();
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
            plugin.setLocalAddress(localAddress);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            close(inputStream);
            close(readChannel);
            close(outputStream);
            close(writeChannel);
        }
    }

    /**
     * 修改 spring proxy bean 的 advice
     *
     * @param plugin
     * @param advice
     */
    private void modifyAdvisedAdvice(Plugin plugin, Advice advice) {
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
                log.debug("bean {}, add advice {}", ((Advised) bean).getTargetSource(), advice);
                continue;
            }
            // 删除，判断是否有这个 Advice
            Advice candidateAdvice = this.findAdvice(className, (Advised) bean);
            if (candidateAdvice != null) {
                // 删除
                ((Advised) bean).removeAdvice(candidateAdvice);
                log.debug("bean {}, remove advice {}", ((Advised) bean).getTargetSource(), candidateAdvice);
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
            JarURLConnection jarURLConnection = jarCache.get(id);
            if (jarURLConnection == null) {
                log.debug("未找到插件 {} 对应的 jarURLConnection", pluginCache.get(id));
                return;
            }
            jarURLConnection.getJarFile().close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 释放资源
     *
     * @param closeable
     */
    private void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
