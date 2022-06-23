package edu.zjnu.core.support;

import edu.zjnu.core.exception.EyasException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

/**
 * @description: 处理配置文件
 * @author: 杨海波
 * @date: 2022-06-02 12:06
 **/
@Component
@Scope("singleton")
public class EyasEnvironment implements EnvironmentAware {

    private Environment springEnvironment;

    private Properties eyasEnvironment;

    @Override
    public void setEnvironment(Environment environment) {

        Properties prop = new Properties();
        Resource resource = new ClassPathResource(EyasConstant.PROPERTIES_CONFIG_LOCATION);

        try (InputStream inputStream = resource.getInputStream()) {
            prop.load(inputStream);
        } catch (Exception e) {
            throw new EyasException("平台配置文件加载失败");
        }

        this.eyasEnvironment = prop;
        this.springEnvironment = environment;
    }

    /**
     * eyas应用环境 > spring应用环境 > jvm应用环境
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProperty(String key, String defaultValue) {
        String value = this.eyasEnvironment.getProperty(key);

        if (null == value || EyasConstant.BLANK.equals(value)) {
            value = this.springEnvironment.getProperty(key);
        }


        if (null == value || EyasConstant.BLANK.equals(value)) {
            value = System.getProperty(key, defaultValue);
        }

        return value;
    }
}
