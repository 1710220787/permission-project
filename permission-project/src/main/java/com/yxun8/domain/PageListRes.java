package com.yxun8.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageListRes {
    private Long total;
    private List<?> rows;
}
