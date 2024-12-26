package com.ldl.nebula.factory;

/**
 * @author lideliang
 * @date 2024/12/16 15:25
 */
public interface GraphService {
    <T> NebulaResult<T> query(String graphSpace, String gql);
}
