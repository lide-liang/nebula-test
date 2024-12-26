package com.ldl.nebula.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lideliang
 * @date 2024/12/14 10:24
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdgeId implements Serializable {
    private int ranking;
    private int type;
    private String dst;
    private String src;
    private String name;
}