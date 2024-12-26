package com.ldl.nebula.factory;

import com.fasterxml.jackson.core.type.TypeReference;

import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lideliang
 * @date 2024/12/16 15:15
 */
@Service
@Slf4j
public class GraphServiceImpl implements com.ldl.nebula.factory.GraphService {

    @Autowired
    private NebulaSessionFactory sessionFactory;

    @Override
    public <T> NebulaResult<T> query(String graphSpace, String gql) {
        Session session = null;
        try {
            log.info("GQL: {}", gql);
            session = sessionFactory.getSession();
            NebulaResult<Void> res = query(session, "USE " + graphSpace);
            if (!res.isSuccess() || res.getResults() == null || res.getResults().size() == 0) {
                log.error("Failed to use space:{}", graphSpace);
                return null;
            }
            if (!graphSpace.equals(res.getResults().get(0).getSpaceName())) {
                log.error("Failed to use space:{}, result:{}", graphSpace, res.getResults().get(0).getSpaceName());
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