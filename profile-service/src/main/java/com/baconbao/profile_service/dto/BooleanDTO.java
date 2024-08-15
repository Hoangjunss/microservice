package com.baconbao.profile_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BooleanDTO {
    private boolean isCheck;
}
