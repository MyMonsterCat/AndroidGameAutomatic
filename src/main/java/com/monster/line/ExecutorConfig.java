package com.monster.line;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 线程池配置
 *
 * @author Monster
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "task")
@EnableAsync
@Slf4j
public class ExecutorConfig {

//    @Resource
//    private ExecutorParam executorParam;

    /**
     * 核心线程数
     */
    private Integer core;
    /**
     * 最大线程数
     */
    private Integer max;
    /**
     * 缓冲队列大小
     */
    private Integer queue;
    /**
     * 最大空闲时间(单位：秒)
     */
    private Integer alive;


    @Bean(name = "modelTaskExecutor")
    public ModelTaskExecutor asyncServiceExecutor() {
//        Integer core = executorParam.getCore();
//        Integer max = executorParam.getMax();
//        Integer queue = executorParam.getQueue();
//        Integer alive = executorParam.getAlive();

        ModelTaskExecutor executor = new ModelTaskExecutor();

        // 设置核心线程数
        executor.setCorePoolSize(core);
        // 设置最大线程数
        executor.setMaxPoolSize(max);
        // 设置缓冲队列大小
        executor.setQueueCapacity(queue);
        // 设置线程的最大空闲时间
        executor.setKeepAliveSeconds(alive);
        // 线程池初始化
        executor.initialize();
        //线程池名前缀
        executor.setThreadNamePrefix("Async-Service-");

        log.info("任务队列已初始化,核心线程{},最大线程{},缓冲队列{},最大空闲时间{}秒", core, max, queue, alive);

        return executor;
    }


}
