package com.gf.biz.tiancaiIfsData.task;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.HttpClientUtil;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.ifsSync.entity.*;
import com.gf.biz.ifsSync.mapper.*;
import com.gf.biz.ifsSync.po.IfScoreEntity;
import com.gf.biz.tiancaiIfsData.entity.*;
import com.gf.biz.tiancaiIfsData.mapper.*;
import com.xxl.job.core.handler.IJobHandler;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.util.*;

import static cn.hutool.core.bean.BeanUtil.copyProperties;

/**
 * 调用天财商龙顾客反馈
 */
public class SyncTiancaiCeDataJobHandler extends IJobHandler {
    private final static Logger log = LoggerFactory.getLogger(SyncTiancaiCeDataJobHandler.class);

    private static final Logger logger = LoggerFactory.getLogger(SyncTiancaiCeDataJobHandler.class);
    private static final String secret="MQ0WINT60DXP7U7R09A70V6Z1SEDIGYH";
    private static final String systype="54547QDLVSCY485398";//商户标识
    private static final String groupId="140386";
    private static final String InitString="Init886688";
    public void execute() throws Exception {


//        log.info("开始同步天才评论数据");
//        syncCreateCommentData();
//
        log.info("开始同步 genius 评论数据");
        syncSiftEmployeeData();
//        log.info("开始同步 genius 数据");
//        syncAverageAllSumPoint();


    }
    public void syncCreateCommentData(){
        String lastId=InitString;
        //systype	是	string	商户标识
        //data	是	string	对data参数json化后的加密字符串

        //data参数：
        //mcid	否	int	门店ID（传入此值则只返回对应门店的数据）
        //beginTime	是	string	开始时间
        //endTime	是	string	结束时间
        //lastid	否	string	上一次的最后订单ID

        JSONObject jo= new JSONObject();
        //jo.put("mcid", groupId);
        jo.put("beginTime","2024-08-01 00:00:00");
        jo.put("endTime","2024-09-01 00:00:00");
        int pageNo=0;
        while(lastId!=null) {
            pageNo++;
            if(lastId.equals(InitString)){
                lastId = null;
            }
            log.info("lastId:{}", lastId);
            jo.put("lastid", lastId);
            SimpleStringCypher cypher = new SimpleStringCypher(secret);
            IfScoreCeMapper ifScoreCeMapper = SpringBeanUtil.getBean(IfScoreCeMapper.class);
            try {
                String secretData = cypher.encrypt(jo.toJSONString());
                Map<String, String> params = new HashMap<>();
                params.put("systype", systype);
                params.put("data", secretData);
                String resultContent = HttpClientUtil.postFormUrlEncoded("http://o2oapi.wuuxiang.com/external/evaluateinfo/GetEvaluateInfo.htm", null, params);
                //解密
                String finalContent = cypher.decrypt(resultContent);
                JSONObject result = JSONObject.parseObject(finalContent);
                if (result.getJSONObject("data") != null) {
                    logger.info("resultContent:{}", result.getJSONObject("data"));
                }
                log.info("result:{}", result);
                lastId = result.getString("lastid");
                log.info("LastId:{}",lastId);
                log.info("lastId:{}", result.getString("lastid"));
                JSONObject data = result.getJSONObject("data");
                JSONArray columnNames = data.getJSONArray("columnNames");
//                for (int i = 0; i < columnNames.size(); i++) {
//                    logger.info("resultContent:{}", columnNames.get(i));
//                }
                IfScoreCe ifScoreCe = null;
                JSONArray records = data.getJSONArray("records");
                for (int i = 0; i < records.size(); i++) {
                    //logger.info("resultContent:{}",records.get(i));
                    for (int j = 0; j < records.getJSONArray(i).size(); j++) {
                        logger.info("resultContent:{}", records.getJSONArray(i));
                        try {
                            ifScoreCe = new IfScoreCe();
                            ifScoreCe.setGcId(records.getJSONArray(i).getString(0));
                            ifScoreCe.setMcId(records.getJSONArray(i).getString(1));
                            ifScoreCe.setRegion(records.getJSONArray(i).getString(2));
                            ifScoreCe.setCity(records.getJSONArray(i).getString(3));
                            ifScoreCe.setMcName(records.getJSONArray(i).getString(4));
                            ifScoreCe.setGcName(records.getJSONArray(i).getString(5));
                            ifScoreCe.setTemplateId(records.getJSONArray(i).getString(6));
                            ifScoreCe.setIfId(records.getJSONArray(i).getString(7));
                            ifScoreCe.setTime(records.getJSONArray(i).getString(8));
                            ifScoreCe.setTemplateName(records.getJSONArray(i).getString(9));
                            ifScoreCe.setSendNickName(records.getJSONArray(i).getString(10));
                            ifScoreCe.setMobile(records.getJSONArray(i).getString(11));
                            ifScoreCe.setName(records.getJSONArray(i).getString(12));
                            ifScoreCe.setValue(records.getJSONArray(i).getString(13));
                            ifScoreCe.setRemarks(records.getJSONArray(i).getString(14));
                            ifScoreCe.setStar(records.getJSONArray(i).getString(15));
                            ifScoreCe.setOpenId(records.getJSONArray(i).getString(16));
                            ifScoreCe.setTips(records.getJSONArray(i).getString(17));
                            ifScoreCe.setRemark(records.getJSONArray(i).getString(18));
                            ifScoreCe.setIsMultiSelect(records.getJSONArray(i).getString(19));
                            ifScoreCe.setOrderId(records.getJSONArray(i).getString(20));
                            ifScoreCe.setBusinessNo(records.getJSONArray(i).getString(21));
                            ifScoreCe.setSelectType(records.getJSONArray(i).getString(22));
                            ifScoreCe.setIfKey(records.getJSONArray(i).getString(7) + "-" + records.getJSONArray(i).getString(12) + "-" + records.getJSONArray(i).getString(13) + "-" + records.getJSONArray(i).getString(19));
                            ifScoreCe.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                            ifScoreCe.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                            ifScoreCe.setCreatedTime(new Date());
                            ifScoreCe.setValidFlag("0");
                            ifScoreCeMapper.insert(ifScoreCe);
                        } catch (Exception e) {
                            logger.error("insert error:{}", JSONObject.toJSONString(ifScoreCe));
                        }
                    }
                }
                //logger.info("resultContent:{}",columnNames.size());
                //logger.info("resultContent:{}",columnNames);
                //logger.info("resultContent:{}",data.getJSONArray("records"));
                //logger.info("finalContent:{}",finalContent);
                //logger.info("result:{}",result);
            } catch (Exception e) {
                logger.error("encrypt error", e);
            }
        }
        log.info("pageNo:{}", pageNo);
        log.info("同步评论数据结束");
    }
    public void syncSiftEmployeeData(){
        LecaiUserInfo lecaiUserInfo = null;
        LecaiUserInfoMapper lecaiUserInfoMapper = SpringBeanUtil.getBean(LecaiUserInfoMapper.class);//Mapper注入
        IfScoreCeMapper ifScoreCeMapper = SpringBeanUtil.getBean(IfScoreCeMapper.class);//Mapper注入
//        QueryWrapper<LecaiUserInfo> wrapper = new QueryWrapper<>();
//        wrapper.ne("user_status", "40");//离职
//        wrapper.ne("user_status", "50");//退休
//        wrapper.orderByAsc("dept_name");//根据dept_name升序排列
//        List<LecaiUserInfo> userInfoList = lecaiUserInfoMapper.selectList(wrapper);
//        for(LecaiUserInfo userInfo:userInfoList){
//            lecaiUserInfo = userInfo;
//            QueryWrapper<IfScoreCe> wrapper1 = new QueryWrapper<>();
//            wrapper1.orderByAsc("mc_name");//根据dept_name升序排列
//            List<IfScoreCe> ifScoreCeList = ifScoreCeMapper.selectList(wrapper1);
//            //try {
//                for (IfScoreCe ifScoreCe : ifScoreCeList) {
//                    if (lecaiUserInfo.getDeptName().startsWith(ifScoreCe.getMcName())) {
//                        log.info("开始进行判断:{}", ifScoreCe.getMcName()+"-"+lecaiUserInfo.getDeptName());
//                        if (lecaiUserInfo.getPhone().equals(ifScoreCe.getMobile())||ifScoreCe.getMobile()==null) {
//                            UpdateWrapper<IfScoreCe> updateWrapper = new UpdateWrapper<>();
//                            IfScoreCe ifScoreCe1 = new IfScoreCe();
//                            ifScoreCe1.setValidFlag("1");
//                            updateWrapper.eq("id", ifScoreCe.getId());
//                            //todo，因为如果更新为空的时候，不会 更新
//                            ifScoreCeMapper.update(ifScoreCe1, updateWrapper);
//                            log.info("本店员工:{}", lecaiUserInfo.getUsername()+"-",lecaiUserInfo.getPhone());
//                        } else {
//                            log.info("顾客名字:{}", lecaiUserInfo.getUsername()+"-",lecaiUserInfo.getPhone());
//                        }
//                    }
//                }
////            }catch (Exception e){
////                logger.error("syncSiftEmployeeData error",e);
////            }
//
//        }
        QueryWrapper<IfScoreCe> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("mc_name");//根据mc_name升序排列
        List<IfScoreCe> ifScoreCeList = ifScoreCeMapper.selectList(wrapper);
        Set<String> hasSyncCache = new HashSet<>();
        for(int i=0 ;i<ifScoreCeList.size();){//可优化点，一次更新6条数据
            IfScoreCe ifScoreCe = ifScoreCeList.get(i);
            QueryWrapper<LecaiUserInfo> wrapper1 = new QueryWrapper<>();
            wrapper1.ne("user_status", "40");//离职
            wrapper1.ne("user_status", "50");//退休
            //wrapper1.like("dept_name", ifScoreCe.getMcName());
            wrapper1.orderByAsc("dept_name");//根据dept_name升序排列
            List<LecaiUserInfo> userInfoList = lecaiUserInfoMapper.selectList(wrapper1);
            int l=0;
            LecaiUserInfo userInfoFlag=null;
            try {
                if(!hasSyncCache.contains(ifScoreCe.getMobile())) {
                    for (LecaiUserInfo userInfo : userInfoList) {
                        //log.info("开始进行判断:{}", ifScoreCe.getMcName()+"-"+userInfo.getDeptName());
                        if (userInfo.getDeptName().startsWith(ifScoreCe.getMcName())) {
                            if (userInfo.getPhone().equals(ifScoreCe.getMobile())) {
                                l++;
                                userInfoFlag = userInfo;
                                hasSyncCache.add(ifScoreCe.getMobile());
                                log.info("本店员工:{}", userInfoFlag.getUsername() + "-" + userInfoFlag.getPhone());
                            }
                            //log.info("顾客名字:{}", userInfo.getUsername()+"-"+userInfo.getPhone());
                        }
                    }
                } else if(l>0||hasSyncCache.contains(ifScoreCe.getMobile())){
                    hasSyncCache.add(userInfoFlag.getPhone());
                    UpdateWrapper<IfScoreCe> updateWrapper = new UpdateWrapper<>();
                    IfScoreCe ifScoreCe1 = new IfScoreCe();
                    ifScoreCe1.setValidFlag("1");
                    updateWrapper.eq("id", ifScoreCe.getId());
                    //todo，因为如果更新为空的时候，不会 更新
                    ifScoreCeMapper.update(ifScoreCe1, updateWrapper);
                    i++;//如果是本店评论，i++
                    //log.info("本店员工:{}", userInfoFlag.getUsername()+"-"+userInfoFlag.getPhone());
                } else{
                    i=i+6;//如果不是本店评论，i=i+6
                    log.info("正常评论:{}",ifScoreCe.getSendNickName()+"-"+ifScoreCe.getMobile());
                }
            }catch (Exception e){
                logger.error("syncSiftEmployeeData error",e);
            }
        }
        log.info("盘查完毕");


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
                BigDecimal getPoint = BigDecimal.valueOf((Float.valueOf(ifScoreEntity.getStar())+Float.valueOf(ifScoreEntity1.getStar())+Float.valueOf(ifScoreEntity2.getStar())+Float.valueOf(ifScoreEntity3.getStar())+Float.valueOf(ifScoreEntity4.getStar()))/5);
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
                    bfScoreCeStatistics.setStatus(1);
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
