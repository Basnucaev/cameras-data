package com.basnukaev.camerasdata.mapstruct;

import com.basnukaev.camerasdata.dto.AggregatedCameraData;
import com.basnukaev.camerasdata.dto.Camera;
import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CameraDataMapper {

    @Mapping(target = "id", source = "camera.id")
    @Mapping(target = "urlType", source = "sourceData.urlType")
    @Mapping(target = "videoUrl", source = "sourceData.videoUrl")
    @Mapping(target = "value", source = "tokenData.value")
    @Mapping(target = "ttl", source = "tokenData.ttl")
    AggregatedCameraData toAggregated(Camera camera, CameraSourceData sourceData, CameraTokenData tokenData);
}
