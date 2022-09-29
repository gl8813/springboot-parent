package com.gl.springbootcommon.model;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PageBean implements Serializable {

    private static final long serialVersionUID = 4019710706496067454L;
    /**
     * 默认第一页
     */
    private static final Long DEFAULT_PAGE = 1L;
    /**
     * 默认每页一百条数据
     */
    private static final Long DEFAULT_PAGE_SIZE = 100L;
    /**
     * 默认升序
     */
    private static final String ASC = "asc";
    /**
     * 当前页数
     */
    private Long currentPage;
    /**
     * 跳转 页面名
     */
    private String currentPageName;
    /**
     * 跳转至 页面名
     */
    private String jumpPageName;
    /**
     * 跳页功能 起跳页idlist
     */
    private List<String> idArray;

    /**
     * 跳页功能 仅供方法使用
     */
    private List<String> methodIdArray;

    /**
     * 每页数据数量
     */
    private Long pageSize;
    /**
     * 数据排序配置集合
     */
    private List<OrderOptions> orderOptions;

    /**
     * 用户模糊查询内容（可匹配：用户编号，用户名，在线状态，性别） 是否拥有选择列属性功能
     */
    private List<FindInfo> findInfos;

    private boolean onPage = true;

    public PageBean() {
        this.currentPage = DEFAULT_PAGE;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Page makePaging() {
        Page paging = new Page<>();
        if (null != orderOptions && !orderOptions.isEmpty()) {
            paging.setOrders(this.orderOptions.stream().map(item -> new OrderItem(item.getColumnName(), item.getOrder().equals(ASC))).collect(Collectors.toList()));
        }
        return paging.setCurrent(this.currentPage).setSize(this.pageSize);
    }

    /**
     * 排序类
     */
    @Data
    public static class OrderOptions {
        /**
         * 排序依据字段
         */
        private String columnName;

        /**
         * 排序类型
         */
        private String order;
    }

    /**
     * 搜索条件列及值
     */
    @Data
    public static class FindInfo {
        /**
         * 列名
         */
        private String propData;

        /**
         * 值
         */
        private String findInfo;
    }

    public PageBean returnPageBean() {
        return this;
    }
}