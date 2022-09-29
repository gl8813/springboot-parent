package com.gl.springbootservice.dto.input;

import lombok.Data;

import java.util.List;

@Data
public class RemoveBatchInputDTO {
    private List<String> idList;
}
