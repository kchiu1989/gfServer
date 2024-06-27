package com.gf.common.config;

import com.gf.biz.ifsSync.job.meituanwm.SyncMtwmShopRatingJobHandler;
import com.gf.biz.ifsSync.job.shenfang.IfScoreMvJob;
import com.gf.biz.kpiRuleEngine.job.TestExecuteExpressionJob;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * job config
 *
 * @author Gf
 */
@Configuration
public class JobConfig {
    private Logger logger = LoggerFactory.getLogger(JobConfig.class);

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.appname}")
    private String appname;

    @Value("${xxl.job.executor.address}")
    private String address;

    @Value("${xxl.job.executor.ip}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;


    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setAddress(address);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);

        return xxlJobSpringExecutor;
    }


    @PostConstruct
    public void initJobs() {
        // 手动注册IJobHandler

        XxlJobExecutor.registJobHandler("syncIfScoreMvJobHandler", new IfScoreMvJob());
        XxlJobExecutor.registJobHandler("syncMtwmShopRatingJobHandler", new SyncMtwmShopRatingJobHandler());
        XxlJobExecutor.registJobHandler("testExecuteExpressionJob", new TestExecuteExpressionJob());


    }


}