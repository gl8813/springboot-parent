package com.gl.springbootapi.dto.reqDTO;

import lombok.Data;

import java.util.List;

@Data
public class RemoveBatchInputDTO {
    private List<String> idList;
}
