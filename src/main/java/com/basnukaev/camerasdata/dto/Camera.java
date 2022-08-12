package com.basnukaev.camerasdata.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Camera {
    private Long id;
    private String sourceDataUrl;
    private String tokenDataUrl;
}
