package com.baconbao.manager_service.openfeign;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO {
    private  String message;
    private  Integer id;
}
