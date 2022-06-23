package edu.zjnu.core;

import edu.zjnu.core.support.EyasEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description: BootStrap
 * @author: 杨海波
 * @date: 2022-06-01 19:49
 **/
public class Eyas {

    private static final Logger logger = LoggerFactory.getLogger(Eyas.class);

    /**
     * 应用上下文
     */
    private ApplicationContext applicationContext;

    /**
     * 应用上下文配置环境路径
     */
    private static final String CONTEXT_CONFIG_LOCATION = "classpath:spring.xml";

    /**
     * 应用环境
     */
    private EyasEnvironment environment;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 是否已经完成应用初始化
     */
    private static volatile boolean inited = false;

    /**
     * 应用实例
     */
    private static final Eyas APP = new Eyas();

    /**
     * 禁止外部实例化
     */
    private Eyas() {
    }

    public static synchronized void init(String... args) {
        // 幂等初始化过程（Eyas#getEyas的情况，实际上加的双重检查锁保证线程安全，防止重复初始化造成上下文失真）
        if (inited) {
            return;
        }
        logger.info("####################平台开始初始化####################");
        APP.initContext();
        APP.initEyas();
        handleCommand(args);
        inited = true;
        logger.info("####################平台初始化已完成####################");
    }

    public static Eyas getApp() {
        if (!inited) {
            synchronized (Eyas.class) {
                init();
            }
        }
        return APP;
    }

    /**
     * 初始化Eyas
     */
    private void initEyas() {
        name = environment.getProperty("eyas.name", "eyas");
    }

    private static void handleCommand(String[] args) {
        // todo
    }

    /**
     * 初始化应用环境
     */
    private void initContext() {
        this.applicationContext = new ClassPathXmlApplicationContext(CONTEXT_CONFIG_LOCATION);
        this.environment = this.applicationContext.getBean(EyasEnvironment.class);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public String getName() {
        return name;
    }

}
