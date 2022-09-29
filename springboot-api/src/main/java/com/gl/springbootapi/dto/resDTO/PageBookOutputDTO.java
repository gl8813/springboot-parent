package com.gl.springbootapi.dto.resDTO;


import lombok.Data;

import java.util.List;
@Data
public class PageBookOutputDTO {
    private List<BooksOutputDTO> records;
    private long total;
    private long size;
    private long currentPage;
    private long pages;
}
