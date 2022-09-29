package com.gl.springbootapi.dto.reqDTO;

import lombok.Data;

@Data
public class PageInputDTO {
    /**
     * 分页 第几页
     */
    private Integer currentPage;
    /**
     * 分页 每页条数
     */
    private Integer pageSize;
    /**
     * 分页 排序
     */
    private String sort = "desc";
    /**
     * 分页 排序 列
     */
    private String columnName;
}
