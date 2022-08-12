package com.basnukaev.camerasdata.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedCameraData {
    private Long id;
    private String urlType;
    private String videoUrl;
    private String value;
    private Integer ttl;
}
