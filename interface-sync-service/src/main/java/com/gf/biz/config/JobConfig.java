package com.gf.biz.config;


import com.gf.biz.elemeData.task.SyncElemeShopRatingInfoDtlJobHandler;
import com.gf.biz.elemeData.task.SyncMerchantAccountInfoDtlJobHandler;
import com.gf.biz.fangdengRead.task.CalculateFandengReadDeptScoreJobHandler;
import com.gf.biz.fangdengRead.task.CalculateFandengReadPersonalRltJobHandler;
import com.gf.biz.ifsSync.job.shenfang.IfScoreMvJob;

import com.gf.biz.meituanwmData.task.SyncMtwmShopRatingInfoJobHandler;
import com.gf.biz.shenfangData.task.SyncIfScoreMvDataJobHandler;
import com.gf.biz.operateIndicatorScore.task.CalculateSfScoreJobHandler;
import com.gf.biz.operateIndicatorScore.task.CalculateTcScoreJobHandler;
import com.gf.biz.operateIndicatorScore.task.CalculateWmScoreJobHandler;
import com.gf.biz.tiancaiIfsData.task.SyncTiancaiCeDataJobHandler;
import com.gf.biz.tiancaiIfsData.task.SyncTiancaiCeGetPointsJobHander;
import com.gf.biz.wmPerformanceAllPoints.task.SyncGetWMAllPointsJobHandle;
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
        XxlJobExecutor.registJobHandler("syncTiancaiCeDataJobHandler", new SyncTiancaiCeDataJobHandler());
        XxlJobExecutor.registJobHandler("syncTiancaiCeGetPointsJobHander", new SyncTiancaiCeGetPointsJobHander());
        XxlJobExecutor.registJobHandler("syncMerchantAccountInfoDtlJobHandler", new SyncMerchantAccountInfoDtlJobHandler());
        XxlJobExecutor.registJobHandler("syncElemeShopRatingInfoDtlJobHandler", new SyncElemeShopRatingInfoDtlJobHandler());
        XxlJobExecutor.registJobHandler("syncMtwmShopRatingInfoJobHandler", new SyncMtwmShopRatingInfoJobHandler());
        XxlJobExecutor.registJobHandler("syncGetWMAllPointsJobHandle", new SyncGetWMAllPointsJobHandle());

        XxlJobExecutor.registJobHandler("syncIfScoreMvDataJobHandler", new SyncIfScoreMvDataJobHandler());
        XxlJobExecutor.registJobHandler("calculateWmScoreJobHandler", new CalculateWmScoreJobHandler());

        XxlJobExecutor.registJobHandler("calculateSfScoreJobHandler", new CalculateSfScoreJobHandler());

        XxlJobExecutor.registJobHandler("calculateTcScoreJobHandler", new CalculateTcScoreJobHandler());

        XxlJobExecutor.registJobHandler("calculateFandengReadPersonalRltJobHandler", new CalculateFandengReadPersonalRltJobHandler());
        XxlJobExecutor.registJobHandler("calculateFandengReadDeptScoreJobHandler", new CalculateFandengReadDeptScoreJobHandler());


    }


}