package com.ldl.nebula.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ldl.nebula.config.NebulaGraphProperties;
import com.ldl.nebula.factory.NebulaSessionFactory;
import com.ldl.nebula.service.GraphService;
import com.ldl.nebula.util.JacksonUtil;
import com.ldl.nebula.vo.NebulaResult;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lideliang
 * @date 2024/12/16 15:15
 */
@Service
@Slf4j
public class GraphServiceImpl implements GraphService {

    @Resource
    private NebulaSessionFactory sessionFactory;

    @Resource
    private NebulaGraphProperties properties;

    @Override
    public <T> NebulaResult<T> query(String gql) {
        Session session = null;
        try {
            log.info("GQL: {}", gql);
            session = sessionFactory.getSession();
            NebulaResult<Void> res = query(session, "USE " + properties.getSpace());
            if (!res.isSuccess() || res.getResults() == null || res.getResults().size() == 0) {
                log.error("Failed to use space:{}", properties.getSpace());
                return null;
            }
            if (!properties.getSpace().equals(res.getResults().get(0).getSpaceName())) {
                log.error("Failed to use space:{}, result:{}", properties.getSpace(), res.getResults().get(0).getSpaceName());
                return null;
            }
            return query(session, gql);
        } catch (IOErrorException e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (session != null) {
                session.release();
            }
        }
    }

    private <T> NebulaResult<T> query(Session session, String gql) throws IOErrorException {
        String json = session.executeJson(gql);
        return JacksonUtil.extractByType(json, new TypeReference<NebulaResult<T>>() {});
    }
}