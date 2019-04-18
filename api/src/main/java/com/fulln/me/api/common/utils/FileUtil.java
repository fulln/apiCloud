package com.fulln.me.api.common.utils;


import com.fulln.me.api.common.properties.OrderdProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.fulln.me.api.common.constant.FileExtensionConfig.*;


@Slf4j
public class FileUtil {

    private static Properties props;
    private static Yaml yaml;

    static {
        loadProps("application.yml");
    }

    /**
     * 读取配置文件
     * 只支持单个读取,不能读取数组与list
     * 读取数组的方法还是用yaml
     */
    synchronized static private void loadProps(String name) {
        loadProps(FileUtil.class,name);
    }

    /**
     * 读取配置文件
     * 只支持单个读取,不能读取数组与list
     * 读取数组的方法还是用yaml
     */
    synchronized static private void loadProps(Class clazz,String name) {
        String extension = name.substring(name.lastIndexOf(FILE_DOT) + 1);
        if (extension.equals(FILE_PROPERTIES)) {
            //按顺序加载properties
            props = new OrderdProperties();
            try (InputStream in = clazz.getClassLoader().getResourceAsStream(name)) {
                props.load(in);
            } catch (FileNotFoundException e) {
                log.error("配置文件未找到");
            } catch (IOException e) {
                log.error("出现IOException");
            }
        } else if (extension.equals(FILE_YML)) {
            //加载yml
            try {
                YamlPropertiesFactoryBean propsBean = new YamlPropertiesFactoryBean();
                propsBean.setResources( new DefaultResourceLoader().getResource("classpath:"+name));
                props = propsBean.getObject();
            } catch (Exception e) {
                log.error("未找到对应yml文件");
            }
        } else {
            //yml 格式下的  string  转键值对
            YamlPropertiesFactoryBean propsBean = new YamlPropertiesFactoryBean();
            try {
                propsBean.setResources(new ByteArrayResource(name.getBytes(StandardCharsets.UTF_8)));
                props = propsBean.getObject();
            } catch (Exception e) {
                log.error("string转kv失败!");
            }
        }
    }

    //写入配置文件
    synchronized static public void writeProps(Properties ps, String path) {

        path = getLocalPath(path);
        try (OutputStream out = new FileOutputStream(path)) {
            ps.store(out, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("文件未找到");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件输出流开启异常");
        }
    }

    /**
     * 获取单个key
     */
    public static String getProperty(String key) {

        if (key != null && !"".equals(key)) {
            String code = props.getProperty(key);

            if (StringUtils.isEmpty(code)) {
                loadProps("application.yml");
            }
            return props.getProperty(key);
        } else {
            return null;
        }
    }


    //封装资源路径 springmvc下运行
    private static String getLocalPath(String path) throws NullPointerException {
        path = FileUtil.class.getClassLoader().getResource(path).getPath();
        return path.replace("target/classes", "src/main/resources");
    }


    /**
     * 写入文件
     */
    public static void writeToFile(File f, StringBuffer sb) {
        try (PrintStream ps = new PrintStream(new FileOutputStream(f), true)) {
            ps.println(sb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("未找到文件", e);
        }
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     */
    public static StringBuilder readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()))) {
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb;
        } catch (IOException e1) {
            log.error("未找到文件", e1);
            return null;
        }
    }


    /**
     * 获取全部的键值对
     */
    public static Properties getProps(String name) {
        loadProps(name);
        return props;
    }

    /**
     * 获取全部的键值对
     */
    public static Properties getProps(String name,Class clazz) {
        loadProps(clazz, name);
        return props;
    }

    /**
     * 获取数组类型的yaml
     *
     * @param code
     * @return
     */
    public static Object getYamlMap(String code) {
        if(yaml == null)
            yaml = new Yaml();
        return yaml.load(new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8)));
    }
}
