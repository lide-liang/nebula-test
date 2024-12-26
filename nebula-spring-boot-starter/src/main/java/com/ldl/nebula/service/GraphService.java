package com.ldl.nebula.service;

import com.ldl.nebula.vo.NebulaResult;

/**
 * @author lideliang
 * @date 2024/12/16 15:25
 */
public interface GraphService {
    <T> NebulaResult<T> query(String gql);
}
