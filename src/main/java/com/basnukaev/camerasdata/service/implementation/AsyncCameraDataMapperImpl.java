package com.basnukaev.camerasdata.service.implementation;

import com.basnukaev.camerasdata.dto.AggregatedCameraData;
import com.basnukaev.camerasdata.dto.Camera;
import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;
import com.basnukaev.camerasdata.mapstruct.CameraDataMapper;
import com.basnukaev.camerasdata.service.AsyncCameraDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncCameraDataMapperImpl implements AsyncCameraDataMapper {
    private final CameraDataMapper cameraDataMapper;

    @Async
    @Override
    public CompletableFuture<AggregatedCameraData> toAggregated(Camera camera,
                                                                CameraSourceData sourceData,
                                                                CameraTokenData tokenData) {
        AggregatedCameraData aggregatedCameraData = cameraDataMapper.toAggregated(camera, sourceData, tokenData);

        return CompletableFuture.completedFuture(aggregatedCameraData);
    }
}
