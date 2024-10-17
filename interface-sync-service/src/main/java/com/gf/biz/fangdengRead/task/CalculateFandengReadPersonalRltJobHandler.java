package com.gf.biz.fangdengRead.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gf.biz.codewave.dto.LcapUserDeptMappingDto;
import com.gf.biz.codewave.mapper.LcapUserDeptMappingMapper;
import com.gf.biz.common.CommonConstant;
import com.gf.biz.common.util.SpringBeanUtil;
import com.gf.biz.common.util.TimeUtil;
import com.gf.biz.crossdb.entity.UserInfo;
import com.gf.biz.crossdb.mapper.UserInfoMapper;
import com.gf.biz.fangdengRead.entity.IfRecordFdCr;
import com.gf.biz.fangdengRead.entity.IfRecordFdStatistic;
import com.gf.biz.fangdengRead.mapper.IfRecordFdCrMapper;
import com.gf.biz.fangdengRead.mapper.IfRecordFdNmMapper;
import com.gf.biz.fangdengRead.mapper.IfRecordFdStatisticMapper;
import com.gf.biz.tiancaiIfsData.entity.LcapDepartment4a79f3;
import com.gf.biz.tiancaiIfsData.mapper.LcapDepartment4a79f3Mapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 计算樊登每个人的读书情况 （完读和笔记）
 * 按月统计，当前月获取上个月
 */
public class CalculateFandengReadPersonalRltJobHandler extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(CalculateFandengReadPersonalRltJobHandler.class);

    @Override
    public void execute() throws Exception {
        //定时任务默认当前月跑上个月的数据
        Integer currentYear = TimeUtil.getNowYear();
        Integer currentMonth = TimeUtil.getNowMonth();
        if (currentMonth == 1) {
            currentYear--;
            currentMonth = 12;
        } else {
            currentMonth--;
        }

        String jobParam = XxlJobHelper.getJobParam();
        if (StringUtils.isNotBlank(jobParam)) {
            JSONObject xxlJobJsonObj = JSONObject.parseObject(jobParam);
            if (xxlJobJsonObj.containsKey("year") && xxlJobJsonObj.containsKey("month")) {
                currentYear = Integer.parseInt(xxlJobJsonObj.getString("year"));
                currentMonth = Integer.parseInt(xxlJobJsonObj.getString("month"));
            }

            logger.info("使用动态传参");
        }

        logger.info("年:{},月:{}", currentYear, currentMonth);


        /*
        1、按照绩效总结发生年和月，查询出 if_record_fd_cr (樊登完读记录表)的数据，按照员工姓名+员工编码进行分组的结果集，
        根据员工和员工编码排序获取到 员工姓名 userName、员工编码 userAccount、读书数量readCnt 得到结果集 resultList1

        循环resultList1；分别根据userName和userAccount查询系统人员主数据，得到主数据的用户账号(userAccount)、
        用户岗位(positionName)，若查询出了员工数据，则根据用户账号查询部门和人员关系表,得到用户所在的直属部门编码(deptCode)，之后进行新增数据操作：
        if_record_fd_statistics（樊登人员读书统计表）*/

        QueryWrapper<IfRecordFdCr> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_account,max(user_name) as user_name,count(1) as cr_count,max(dept_name) as dept_name");
        queryWrapper.eq("year", currentYear);
        queryWrapper.eq("month", currentMonth);
        queryWrapper.eq("deleted_flag", "0");
        queryWrapper.isNotNull("user_account");
        queryWrapper.groupBy(Arrays.asList("user_account"));

        IfRecordFdCrMapper ifRecordFdCrMapper = SpringBeanUtil.getBean(IfRecordFdCrMapper.class);
        List<Map<String, Object>> gpMapList = ifRecordFdCrMapper.selectMaps(queryWrapper);

        if (gpMapList != null && gpMapList.size() > 0) {

            Date currentDate = new Date();

            UserInfoMapper userInfoMapper = SpringBeanUtil.getBean(UserInfoMapper.class);
            LcapUserDeptMappingMapper lcapUserDeptMappingMapper = SpringBeanUtil.getBean(LcapUserDeptMappingMapper.class);
            UserInfo userInfo = null;
            String dingUserId = null;
            String fandengDeptName = null;
            String fandengUserName = null;
            Integer crCnt;


            Long deptId = null;
            String deptCode = null;
            String deptName = null;
            String deptClassify = null;
            Long userId = null;
            Long ifUserId = null;
            String userName = null;
            //String userAccount = null;
            String positionName = null;
            List<LcapUserDeptMappingDto> lcapUserDeptList;
            QueryWrapper<UserInfo> userQueryWrapper;
            QueryWrapper<LcapDepartment4a79f3> deptQueryWrapper = new QueryWrapper<>();
            QueryWrapper<IfRecordFdStatistic> fdStatisticQueryWrapper = new QueryWrapper<>();
            UpdateWrapper<IfRecordFdStatistic> fdStatisticUpdWrapper = new UpdateWrapper<>();
            IfRecordFdStatisticMapper ifRecordFdStatisticMapper = SpringBeanUtil.getBean(IfRecordFdStatisticMapper.class);
            LcapDepartment4a79f3Mapper lcapDepartment4a79f3Mapper = SpringBeanUtil.getBean(LcapDepartment4a79f3Mapper.class);
            IfRecordFdStatistic toAdd;

            for (Map<String, Object> gpMap : gpMapList) {


                dingUserId = gpMap.get("user_account").toString();
                fandengDeptName = gpMap.get("dept_name") == null ? "" : gpMap.get("dept_name").toString();
                fandengUserName = gpMap.get("user_name") == null ? "" : gpMap.get("user_name").toString();

                ifUserId = -1L;
                userName = fandengUserName;
                crCnt = Integer.parseInt(gpMap.get("cr_count").toString());


                logger.info("dingUserId:{},fandengUserName:{},fandengDeptName:{}", dingUserId, fandengUserName, fandengDeptName);

                //根据userAccount获取到user_info的id，再通过id获取到lcap_user表的信息，若查询不到，也需要进行留痕
                userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.clear();
                userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("if_id", dingUserId);
                userInfo = userInfoMapper.selectOne(userQueryWrapper);


                if (userInfo != null) {
                    logger.info("根据钉userId:{}，查询到用户信息id:{}", userInfo.getId());
                    logger.info("根据id,查询lcap_user表,lcap_user_department反推出部门");

                    ifUserId = userInfo.getId();
                    positionName = userInfo.getPosition();

                    lcapUserDeptList = lcapUserDeptMappingMapper.selectByIfUserId(userInfo.getId());


                    userId = -1L;


                    deptId = -1L;
                    deptCode = null;
                    deptClassify = null;
                    deptName = fandengDeptName;
                    if (lcapUserDeptList != null && lcapUserDeptList.size() > 0) {

                        userId = lcapUserDeptList.get(0).getUserId();
                        ifUserId = lcapUserDeptList.get(0).getIfUserId();
                        userName = lcapUserDeptList.get(0).getUserName();
                        // userAccount =lcapUserDeptList.get(0).getUserAccount();

                        boolean needSetDeptFlag = true;
                        if (StringUtils.isNotBlank(fandengDeptName)) {
                            deptQueryWrapper.clear();
                            deptQueryWrapper.eq("name", fandengDeptName);
                            LcapDepartment4a79f3 dept = lcapDepartment4a79f3Mapper.selectOne(deptQueryWrapper);
                            if (dept != null) {
                                deptId = dept.getId();
                                deptCode = dept.getDeptCode();
                                deptName = dept.getName();
                                deptClassify = dept.getDeptClassify();
                                needSetDeptFlag = false;
                            }
                        }


                        //如果是多条且樊登部门名称不为空，优先匹配樊登里的部门名称；匹配不到默认取第一条；
                        // 一条，直接以lcap数据为准
                        if (needSetDeptFlag) {
                            deptId = lcapUserDeptList.get(0).getDeptId();
                            deptCode = lcapUserDeptList.get(0).getDeptCode();
                            deptName = lcapUserDeptList.get(0).getDeptName();
                            deptClassify = lcapUserDeptList.get(0).getDeptClassify();
                            if (lcapUserDeptList.size() > 1 && StringUtils.isNotBlank(fandengDeptName)) {
                                for (LcapUserDeptMappingDto dto : lcapUserDeptList) {
                                    if (StringUtils.isNotBlank(dto.getDeptName()) && (dto.getDeptName().contains(fandengDeptName)
                                            || fandengDeptName.contains(dto.getDeptName()))) {


                                        deptId = dto.getDeptId();
                                        deptCode = dto.getDeptCode();
                                        deptName = dto.getDeptName();
                                        deptClassify = dto.getDeptClassify();


                                        break;
                                    }

                                }
                            }
                        }

                    }

                }

                try {
                    //查询是否存在数据，存在数据，直接更新；
                    fdStatisticQueryWrapper.clear();
                    fdStatisticQueryWrapper.eq("year", currentYear);
                    fdStatisticQueryWrapper.eq("month", currentMonth);
                    fdStatisticQueryWrapper.eq("deleted_flag", "0");
                    fdStatisticQueryWrapper.eq("user_account", dingUserId);
                    List<IfRecordFdStatistic> dbList = ifRecordFdStatisticMapper.selectList(fdStatisticQueryWrapper);

                    fdStatisticUpdWrapper.clear();
                    if (dbList != null && dbList.size() > 0) {
                        fdStatisticUpdWrapper.set("user_account", dingUserId);
                        fdStatisticUpdWrapper.set("user_id", userId);
                        fdStatisticUpdWrapper.set("if_user_id", ifUserId);
                        fdStatisticUpdWrapper.set("user_name", userName);
                        fdStatisticUpdWrapper.set("position_name", positionName);
                        fdStatisticUpdWrapper.set("dept_id", deptId);
                        fdStatisticUpdWrapper.set("dept_code", deptCode);
                        fdStatisticUpdWrapper.set("dept_name", deptName);
                        fdStatisticUpdWrapper.set("dept_classify", deptClassify);
                        fdStatisticUpdWrapper.set("read_cnt", crCnt);
                        fdStatisticUpdWrapper.set("updated_time", currentDate);
                        fdStatisticUpdWrapper.eq(CommonConstant.COLUMN_ID, dbList.get(0).getId());
                        ifRecordFdStatisticMapper.update(null, fdStatisticUpdWrapper);
                    } else {
                        toAdd = new IfRecordFdStatistic();
                        toAdd.setUserId(userId);
                        toAdd.setUserAccount(dingUserId);
                        toAdd.setIfUserId(ifUserId);
                        toAdd.setUserName(userName);
                        toAdd.setPositionName(positionName);
                        toAdd.setDeptId(deptId);
                        toAdd.setDeptCode(deptCode);
                        toAdd.setDeptName(deptName);
                        toAdd.setDeptClassify(deptClassify);
                        toAdd.setReadCnt(crCnt);
                        toAdd.setYear(currentYear);
                        toAdd.setMonth(currentMonth);
                        toAdd.setDeletedFlag(CommonConstant.STATUS_UN_DEL);
                        toAdd.setCreatedTime(currentDate);
                        toAdd.setCreatedBy(CommonConstant.DEFAULT_OPT_USER);
                        ifRecordFdStatisticMapper.insert(toAdd);
                    }


                } catch (Exception e) {
                    logger.error("insert or update error", e);
                }
            }

            /*
             * 2、按照绩效总结发生年和月，查询if_rcord_fd_nm (樊登笔记管理表)，并且exists--存在于if_record_fd_cr表的记录（匹配条件为 用户，用户账号、书名），
             * 按照员工姓名+用户账号进行分组的结果集，根据员工和用户账号排序获取到 员工姓名 userName、员工编码 userAccount、分享笔记数量nodeShareCnt 得到结果集 resultList2
             * 循环resultList2，根据userAccount、userName、年、月 查询if_record_fd_statistics（樊登人员读书统计表），若查询到记录，更新该表的note_share_cnt字段
             */

            IfRecordFdNmMapper ifRecordFdNmMapper = SpringBeanUtil.getBean(IfRecordFdNmMapper.class);
            //处理 笔记分享
            List<Map<String, Object>> groupMapList = ifRecordFdNmMapper.selectNmDataMapListByMonth(currentYear, currentMonth);

            if (groupMapList != null && groupMapList.size() > 0) {
                String nmUserAccount;
                Integer nmCnt;

                UpdateWrapper<IfRecordFdStatistic> toUpdWrapper = new UpdateWrapper<>();

                for (Map<String, Object> map : groupMapList) {
                    nmUserAccount = map.get("user_account").toString();
                    nmCnt = Integer.parseInt(map.get("note_share_count").toString());
                    logger.info("userAccount:{},nmCnt:{},准备开始更新笔记管理数", nmUserAccount, nmCnt);
                    try {
                        toUpdWrapper.clear();
                        toUpdWrapper.set("note_share_cnt", nmCnt);
                        toUpdWrapper.set("updated_time", currentDate);
                        toUpdWrapper.eq("year", currentYear);
                        toUpdWrapper.eq("month", currentMonth);
                        toUpdWrapper.eq(CommonConstant.COLUMN_DEL_FLAG, CommonConstant.STATUS_UN_DEL);
                        toUpdWrapper.eq("user_account", nmUserAccount);
                        ifRecordFdStatisticMapper.update(null, toUpdWrapper);
                    } catch (Exception e) {
                        logger.error("update nm cnt error", e);
                    }
                }
            }
        }

    }
}
