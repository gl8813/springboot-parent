package com.gl.springbootcommon.constants;

public enum SortEnum {
    DESC("desc"),
    ASC("asc");
    private String sort;

    SortEnum(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }

}
