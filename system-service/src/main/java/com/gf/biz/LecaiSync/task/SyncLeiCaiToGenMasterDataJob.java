package com.gf.biz.LecaiSync.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.LecaiSync.mapper.LecaiGangweiMapper;
import com.gf.biz.LecaiSync.po.LecaiGangwei;
import com.gf.biz.LecaiSync.util.lecaiSyncUtil;
import com.xxl.job.core.handler.IJobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;


public class SyncLeiCaiToGenMasterDataJob extends IJobHandler {

    private final static Logger log = LoggerFactory.getLogger(SyncLeiCaiToGenMasterDataJob.class);
    private static final String lecai_corpId="56f837e57cbed68e607737b6e18f9a3b";
    private static final String lecai_secret="55d39d743443229547c10ac92e817aab";
    private static final String lecai_agent="joyhr";
    private static final Integer pageNo=1;
    private static final Integer pageSize=200;
    @Override
    public void execute() throws Exception {
        //String accessTokenMap=lecaiSyncUtil.getLecaiAccessToken(lecai_corpId,lecai_agent,lecai_secret);
        //log.info("accessTokenMap:{}",accessTokenMap);//测试token
        //Map gangweiInfoMap=lecaiSyncUtil.getLecaiGangweiInfo(lecai_corpId,1,100);
        //log.info("gangweiInfoMap:{}",gangweiInfoMap.get("data"));
        log.info("开始同步乐才岗位信息");
        syncLecaiGangweiInfo();//获取乐才岗位信息并插入到本地的库中
    }

    /**
     * 开始向库中插入乐才数据
     * @return
     * @param
     */
    private void syncLecaiGangweiInfo() {
        Map gangweiInfoMap = lecaiSyncUtil.getLecaiGangweiInfo(lecai_corpId, pageNo, pageSize);//获取乐才岗位信息
        JSONArray gangweiInfo = (JSONArray) gangweiInfoMap.get("data");//获取data对应的数据
        LecaiGangweiMapper lecaiGangweiMapper = SpringBeanUtil.getBean(LecaiGangweiMapper.class);//Mapper注入
        LecaiGangwei gangweiData;
        if(gangweiInfo!=null) {//判定是否获取到数据
            try {
                Integer total = Integer.parseInt(gangweiInfoMap.get("total").toString());//获取总条数
                Integer totalPage = Integer.parseInt(gangweiInfoMap.get("totalpage").toString());//获取总页数

                    for (int i = 1; i <= totalPage; i++) {//遍历所有页数
                        if (i != 1) {//如果是第一页不需要获取数据
                            gangweiInfoMap = lecaiSyncUtil.getLecaiGangweiInfo(lecai_corpId, i, pageSize);//再次获取乐才岗位信息
                            gangweiInfo = (JSONArray) gangweiInfoMap.get("data");//再次获取data对应的数据
                        }
                        for (int j = 0; j < gangweiInfo.size(); j++) {//开始逐条数据的插入
                            JSONObject single = (JSONObject) gangweiInfo.get(j);//获取单个数据
                            if (!single.isEmpty()) {//获取数据不为空
                                gangweiData=new LecaiGangwei();
                                gangweiData.setPostId(single.getString("id"));
                                gangweiData.setParentPostId(single.getString("postPId"));
                                gangweiData.setPostName(single.getString("postName"));
                                gangweiData.setPositionName(single.getString("positionName"));
                                gangweiData.setRankTop(single.getString("rankTop"));
                                gangweiData.setRankLower(single.getString("rankLower"));
                                gangweiData.setMember(single.getInteger("member"));
                                // gangweiData.setOrganName(single.getString("organName"));
                                gangweiData.setOutGangweiCode(single.getLong("gangweiCode"));
                                gangweiData.setStatus(single.getString("status"));
                                gangweiData.setOrganId(single.getString("organId"));
                                gangweiData.setCreatedTime(new Date());
                                gangweiData.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                                gangweiData.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                                gangweiData.setAwaitFlag("1");
                                lecaiGangweiMapper.insert(gangweiData);//插入数据库
                                log.info("插入成功:", gangweiData);
                            } else {
                                log.info("插入错误");
                                break;
                            }
                        }
                    }
                    log.info("成功插入全部数据");
                }catch(Exception e1){
                    log.error(e1.getMessage(), e1);//出错日志
                }
            }else{
                log.info("没有数据");
            }
    }
}
