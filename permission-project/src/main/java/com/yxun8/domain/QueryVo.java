package com.yxun8.domain;

import lombok.Data;

@Data
public class QueryVo {
    private int page;
    private int rows;

    private String keyword;
}
