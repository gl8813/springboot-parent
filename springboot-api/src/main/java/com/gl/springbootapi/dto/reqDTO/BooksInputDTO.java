package com.gl.springbootapi.dto.reqDTO;

import lombok.Data;

@Data
public class BooksInputDTO {
    /**
     * 书籍id
     */
    private Integer id;

    /**
     * 书籍类型
     */
    private String type;

    /**
     * 书籍名称
     */
    private String name;

    /**
     * 书籍描述
     */
    private String description;
}
