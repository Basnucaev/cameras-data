package com.basnukaev.camerasdata.service;


import com.basnukaev.camerasdata.dto.AggregatedCameraData;
import com.basnukaev.camerasdata.dto.Camera;
import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;

import java.util.concurrent.CompletableFuture;

public interface AsyncCameraDataMapper {

    CompletableFuture<AggregatedCameraData> toAggregated(Camera camera, CameraSourceData sourceData, CameraTokenData tokenData);
}
