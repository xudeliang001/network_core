package com.uca.network.core.router.service.impl;

import com.alibaba.fastjson.JSON;
import com.uca.network.common.exception.CommonException;
import com.uca.network.common.exception.ErrorCode;
import com.uca.network.common.exception.ErrorInfo;
import com.uca.network.common.utils.ResourceStatus;
import com.uca.network.core.mapper.attr.StandardattributesMapper;
import com.uca.network.core.mapper.attr.dto.StandardattributesDto;
import com.uca.network.core.mapper.router.RouterMapper;
import com.uca.network.core.mapper.router.dto.RouterDto;
import com.uca.network.core.router.controller.model.RouterReq;
import com.uca.network.core.router.service.RouterService;
import com.uca.network.core.router.vo.RouterVo;
import com.uca.network.core.subnet.service.impl.SubnetServiceImpl;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouterServiceImpl implements RouterService {

    private static final Logger logger = LoggerFactory.getLogger(SubnetServiceImpl.class);

    @Autowired
    private RouterMapper routerMapper;

    @Autowired
    private StandardattributesMapper standardattributesMapper;

    @Transactional
    @Override
    public RouterVo createRouter(RouterReq routerReq) {
        logger.info("RouterServiceImpl createRouter begin : {}", JSON.toJSONString(routerReq));
        String routerId = UUID.randomUUID().toString().replace("-", "");
        String attrId = UUID.randomUUID().toString().replace("-", "");
        Date createTime = new Date();
        Date updateTime = new Date();
        RouterDto routerDto = new RouterDto();
        routerDto.setId(routerId);
        routerDto.setCreateTime(createTime);
        routerDto.setUpdateTime(updateTime);
        routerDto.setAdminStateUp(true);
        routerDto.setName(routerReq.getName());
        routerDto.setStatus(ResourceStatus.ACTIVE.getVal());
        routerDto.setStandardAttrId(attrId);
        routerDto.setTenantId(routerReq.getTenantId());
        routerMapper.insertRouter(routerDto);

        StandardattributesDto standardattributesDto = new StandardattributesDto();
        standardattributesDto.setResourceType("routers");
        standardattributesDto.setCreateTime(createTime);
        standardattributesDto.setUpdateTime(updateTime);
        Map<String, Object> desc = new HashMap<>();
        desc.put("cidr", routerReq.getCidr());
        standardattributesDto.setDescription(JSON.toJSONString(desc));
        standardattributesDto.setId(attrId);
        standardattributesMapper.insertStandardattributes(standardattributesDto);

        RouterVo routerVo = new RouterVo();
        BeanUtils.copyProperties(routerDto, routerVo);
        routerVo.setDescription(routerReq.getCidr());
        routerVo.setTenantId(routerReq.getTenantId());
        return routerVo;
    }

    @Override
    public void deleteRouter(String tenantId, String routerId) {
        List<RouterDto> routerDtos = routerMapper.queryRoutersByParam(tenantId, routerId, null);
        if (CollectionUtils.isEmpty(routerDtos)) {
            //router存在网络不允许删除
            if (false) {

            }
            routerMapper.deleteRouterById(routerId);
        } else {
            String msg = String.format("routerId : %s , tenantId : %s", tenantId, routerId);

            ErrorInfo errorInfo = new ErrorInfo(ErrorCode.ERR_ROUTER_NOT_EXISTS, "router", msg);

            throw new CommonException(errorInfo);
        }
    }

    @Override
    public List<RouterVo> queryRouters(String tenantId) {

        return doQueryRouters(tenantId, null);
    }

    @Override
    public RouterVo queryRouterDetail(String tenantId, String routerId) {
        List<RouterVo> result = doQueryRouters(tenantId, routerId);
        if (!CollectionUtils.isEmpty(result)) {
            return result.get(0);
        }
        return new RouterVo();
    }


    private List<RouterVo> doQueryRouters(String tenantId, String routerId) {
        List<RouterDto> routerDtos = routerMapper.queryRoutersByParam(tenantId, routerId, null);

        if (!CollectionUtils.isEmpty(routerDtos)) {
            List<String> attrIds
                    = routerDtos.stream().map(router -> router.getStandardAttrId()).collect(Collectors.toList());
            List<StandardattributesDto> standardattributesDtos = standardattributesMapper.queryAttrsByParam(attrIds);

            Map<String, List<StandardattributesDto>> attrMap = standardattributesDtos.stream().
                    collect(Collectors.groupingBy(StandardattributesDto::getId));

            List<RouterVo> routerVos = new ArrayList<>();

            routerDtos.forEach(routerDto -> {
                RouterVo routerVo = new RouterVo();
                BeanUtils.copyProperties(routerDto, routerVo);

                if (attrMap.containsKey(routerDto.getStandardAttrId())) {
                    String descStr = attrMap.get(routerDto.getStandardAttrId()).get(0).getDescription();
                    routerVo.setDescription(JSON.parseObject(descStr).getString("cidr"));
                }
                routerVos.add(routerVo);

            });
            return routerVos;
        } else {
            return Lists.newArrayList();
        }
    }

}
