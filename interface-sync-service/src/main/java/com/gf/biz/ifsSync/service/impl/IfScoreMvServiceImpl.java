package com.gf.biz.ifsSync.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gf.biz.ifsSync.entity.IfScoreMv;
import com.gf.biz.ifsSync.mapper.IfScoreMvMapper;
import com.gf.biz.ifsSync.service.IfScoreMvService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gf
 * @since 2024-04-22 17:29:02
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class IfScoreMvServiceImpl extends ServiceImpl<IfScoreMvMapper, IfScoreMv> implements IfScoreMvService {

}
