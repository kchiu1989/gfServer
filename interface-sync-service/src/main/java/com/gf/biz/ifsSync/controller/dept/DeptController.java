package com.gf.biz.ifsSync.controller.dept;


import com.gf.biz.ifsSync.entity.ZcSysOu;
import com.gf.biz.common.GfResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门信息
 */
@RestController("deptController")
@RequestMapping("/lvshi/dept")
public class DeptController {
    private static final Logger logger = LoggerFactory.getLogger(DeptController.class);

    //@Autowired
    //private ZcSysOuMapper zcSysOuMapper;


    @RequestMapping(value="/getDeptListByUserCode",method= RequestMethod.GET)
    public GfResult<Map<String,Object>> getDeptListByUserCode(@RequestParam("userCode") String userCode, @RequestParam("deptCode") String deptCode){
        logger.info("userCode:{},deptCode:{}",userCode,deptCode);
        List<ZcSysOu> deptList=null;//zcSysOuMapper.getDeptListByDeptCode(userCode);
        if(deptList==null || deptList.size()==0){
            deptList = new ArrayList<>();
        }

        Map<String,Object> rltMap= new HashMap<String,Object>();
        rltMap.put("index",1);
        rltMap.put("total",deptList.size());
        rltMap.put("size",deptList.size());
        rltMap.put("rows",deptList);
        return GfResult.success(rltMap);

    }
}
