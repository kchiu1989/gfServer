package com.gf.biz.tiancaiIfsData.task;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.SpringBeanUtil;

import com.gf.biz.tiancaiIfsData.entity.*;
import com.gf.biz.tiancaiIfsData.mapper.*;
import com.gf.biz.tiancaiIfsData.po.IfScoreEntity;
import com.xxl.job.core.handler.IJobHandler;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static cn.hutool.core.bean.BeanUtil.copyProperties;

/**
 * 调用天财商龙顾客反馈
 */
public class SyncTiancaiCeGetPointsJobHander extends IJobHandler {
    private final static Logger log = LoggerFactory.getLogger(SyncTiancaiCeDataJobHandler.class);

    private static final Logger logger = LoggerFactory.getLogger(SyncTiancaiCeDataJobHandler.class);
    private static final String secret="MQ0WINT60DXP7U7R09A70V6Z1SEDIGYH";
    private static final String systype="54547QDLVSCY485398";//商户标识
    private static final String groupId="352532";
    private static final String InitString="Init886688";
    public void execute() throws Exception {


////        log.info("开始同步天才评论数据");
////        syncCreateCommentData();
////
//        log.info("开始同步 genius 评论数据");
//        syncSiftEmployeeData();
        log.info("开始同步 genius 数据");
        syncAverageAllSumPoint();


    }
    public void syncAverageAllSumPoint(){
        IfScoreCeStatisticsMapper ifScoreCeStatisticsMapper = SpringBeanUtil.getBean(IfScoreCeStatisticsMapper.class);//Mapper注入
        //LecaiUserInfoMapper lecaiUserInfoMapper = SpringBeanUtil.getBean(LecaiUserInfoMapper.class);//Mapper注入
        IfScoreCeMapper ifScoreCeMapper = SpringBeanUtil.getBean(IfScoreCeMapper.class);//Mapper注入
        BfScoreCeStatisticsMapper bfScoreCeStatisticsMapper = SpringBeanUtil.getBean(BfScoreCeStatisticsMapper.class);//Mapper注入

        LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);//Mapper注入

        List<IfScoreEntity> ifScoreEntityList0 =ifScoreCeMapper.getAverageStar("整体评价");
        List<IfScoreEntity> ifScoreEntityList1 =ifScoreCeMapper.getAverageStar("请您为我们的菜品品质打分（例如：菜品口味、温度、形状等）");
        List<IfScoreEntity> ifScoreEntityList2 =ifScoreCeMapper.getAverageStar("请您为我们的服务满意度打分（例如：服务速度、服务态度等）");
        List<IfScoreEntity> ifScoreEntityList3 =ifScoreCeMapper.getAverageStar("请您为我们的餐厅环境及氛围打分（例如：卫生、装修、音乐、温度等）");
        List<IfScoreEntity> ifScoreEntityList4 =ifScoreCeMapper.getAverageStar("根据本次体验，您在接下来30天内再次光顾的可能性有多大？（5星是很有可能，1星是不可能）");
        List<IfScoreEntity> ifScoreEntityList5 =ifScoreCeMapper.getAverageStar("根据本次体验，您向家人或朋友推荐我们餐厅的可能性有多大？（5星强烈推荐，1星绝不推荐）");
//        if(ifScoreEntityList0!=null) {
//            for (IfScoreEntity ifScoreEntity : ifScoreEntityList0) {
//                log.info("门店-总体评价:" + ifScoreEntity.getMcName() + '-' + ifScoreEntity.getStar() + '-' + '-' + ifScoreEntity.getMcId());
//            }
//        }
        QueryWrapper<LcapDepartment4a79f3> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_classify", "0");
        List<LcapDepartment4a79f3> lcapDepartment4a79f3List =lcapDepartment4a79f3Mapper.selectList(wrapper);
        Map<String, String> deptMap =new HashMap<>();
        for(LcapDepartment4a79f3 lcapDepartment4a79f3:lcapDepartment4a79f3List){
            log.info("deptName:{}",lcapDepartment4a79f3.getName()+"-"+lcapDepartment4a79f3.getDeptCode());
            deptMap.put(lcapDepartment4a79f3.getName(),lcapDepartment4a79f3.getId()+"-"+lcapDepartment4a79f3.getDeptCode());
        }

        if(ifScoreEntityList1!=null){
            for(int i=0;i<ifScoreEntityList1.size();i++){
                IfScoreEntity ifScoreEntity = ifScoreEntityList1.get(i);
                IfScoreEntity ifScoreEntity1 = ifScoreEntityList2.get(i);
                IfScoreEntity ifScoreEntity2 = ifScoreEntityList3.get(i);
                IfScoreEntity ifScoreEntity3= ifScoreEntityList4.get(i);
                IfScoreEntity ifScoreEntity4 = ifScoreEntityList5.get(i);
                IfScoreCeStatistics ifScoreCeStatistics = new IfScoreCeStatistics();
                BfScoreCeStatistics bfScoreCeStatistics = new BfScoreCeStatistics();
                ifScoreCeStatistics.setDeptName(ifScoreEntity.getMcName());
                ifScoreCeStatistics.setItemScore1(ifScoreEntity.getStar());
                ifScoreCeStatistics.setItemScore2(ifScoreEntity1.getStar());
                ifScoreCeStatistics.setItemScore3(ifScoreEntity2.getStar());
                ifScoreCeStatistics.setItemScore4(ifScoreEntity3.getStar());
                ifScoreCeStatistics.setItemScore5(ifScoreEntity4.getStar());
                ifScoreCeStatistics.setUnitId(systype);
                ifScoreCeStatistics.setUnitName(ifScoreEntity.getGcName());
                ifScoreCeStatistics.setYear(Integer.valueOf(DateUtil.format(new Date(), "yyyy")));
                ifScoreCeStatistics.setMonth(Integer.valueOf(DateUtil.format(new Date(), "MM")));
                ifScoreCeStatistics.setCeCnt(Integer.valueOf(ifScoreEntity.getCeCnt()));
                ifScoreCeStatistics.setCreatedTime(new Date());
                ifScoreCeStatistics.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                ifScoreCeStatistics.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                BigDecimal getPoint = ifScoreEntity.getStar().add(ifScoreEntity1.getStar()).add(ifScoreEntity2.getStar()).add(ifScoreEntity3.getStar()).add(ifScoreEntity4.getStar()).divide(BigDecimal.valueOf(5),3, RoundingMode.HALF_UP);
                if(Integer.valueOf(ifScoreEntity.getCeCnt())<30){
                    getPoint=BigDecimal.valueOf(0);
                }
                ifScoreCeStatistics.setGetPoint(getPoint);
                if(deptMap.containsKey(ifScoreEntity.getMcName())){
                    String[] dept_data=deptMap.get(ifScoreEntity.getMcName()).split("-");
                    ifScoreCeStatistics.setDeptId(Long.valueOf(dept_data[0]));
                    if(dept_data[1]!=null) {
                        ifScoreCeStatistics.setDeptCode(dept_data[1]);
                    }else {
                        ifScoreCeStatistics.setDeptCode("10010");//部门code空值错误
                    }

                }else{
                    ifScoreCeStatistics.setDeptId(99658L);//无运营ID错误
                    ifScoreCeStatistics.setDeptCode("10086");//无运营标识错误CODE

                }
                ifScoreCeStatisticsMapper.insert(ifScoreCeStatistics);
                log.info("新增数据id:"+ifScoreCeStatistics.getId());
                if(!ifScoreCeStatistics.getDeptCode().equals("10086")&&!ifScoreCeStatistics.getDeptCode().equals("10010")){
                    copyProperties(ifScoreCeStatistics,bfScoreCeStatistics,"id");
                    bfScoreCeStatistics.setStatus(0);

                    bfScoreCeStatistics.setIfId(ifScoreCeStatistics.getId());
                    bfScoreCeStatisticsMapper.insert(bfScoreCeStatistics);
                }
                //log.info("门店-菜品品质:"+ifScoreEntity.getMcName()+'-'+ifScoreEntity.getStar()+'-'+ifScoreEntity.getCeCnt()+'-'+ifScoreEntity.getGcName());
            }
        }
//        if(ifScoreEntityList2!=null){
//            for(IfScoreEntity ifScoreEntity:ifScoreEntityList2){
//                log.info("门店-服务满意度:"+ifScoreEntity.getMcName()+'-'+ifScoreEntity.getStar());
//            }
//        }
//        if(ifScoreEntityList3!=null){
//            for(IfScoreEntity ifScoreEntity:ifScoreEntityList3){
//               // log.info("门店-餐厅环境及氛围:"+ifScoreEntity.getMcName()+'-'+ifScoreEntity.getStar());
//            }
//        }
//        if(ifScoreEntityList4!=null){
//            for(IfScoreEntity ifScoreEntity:ifScoreEntityList4){
//                //log.info("门店-光顾可能性:"+ifScoreEntity.getMcName()+'-'+ifScoreEntity.getStar());
//            }
//        }
//        if(ifScoreEntityList5!=null){
//            for(IfScoreEntity ifScoreEntity:ifScoreEntityList5){
//                //log.info("门店-推荐可能性:"+ifScoreEntity.getMcName()+'-'+ifScoreEntity.getStar());
//            }
//        }

    }



    /**
     * 加密方法
     */
    private static class SimpleStringCypher {
        private byte[] linebreak = {};
        private SecretKey key;
        private Cipher cipher;
        private Base64 coder;

        public SimpleStringCypher(String secret) {
            try {
                coder = new Base64(32, linebreak, true);
                //secret为密钥 appkey
                byte[] secrets = coder.decode(secret);

                key = new SecretKeySpec(secrets, "AES");
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");//加密方式
            } catch (Exception e) {
                logger.error("SimpleStringCypher error",e);
            }
        }

        //对data进行加密
        public synchronized String encrypt(String plainText) throws Exception {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
            return new String(coder.encode(cipherText), "UTF-8");
        }

        //对返回结果进行解密
        public synchronized String decrypt(String codedText) throws Exception {
            byte[] encypted = coder.decode(codedText.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encypted);
            return new String(decrypted, "UTF-8");
        }
    }
}
