package com.basnukaev.camerasdata.service.implementation;

import com.basnukaev.camerasdata.dto.AggregatedCameraData;
import com.basnukaev.camerasdata.dto.Camera;
import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;
import com.basnukaev.camerasdata.feign.api.CameraApi;
import com.basnukaev.camerasdata.service.AsyncCameraApi;
import com.basnukaev.camerasdata.service.AsyncCameraDataMapper;
import com.basnukaev.camerasdata.service.CameraDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CameraDataServiceImpl implements CameraDataService {
    private final CameraApi cameraApi;
    private final AsyncCameraApi asyncCameraApi;
    private final AsyncCameraDataMapper asyncCameraDataMapper;

    @Override
    public List<AggregatedCameraData> getAggregatedData() throws Exception{
        List<Camera> cameras = cameraApi.getListOfAvailableCameras();

        List<CompletableFuture<CameraSourceData>> completableFuturesWithSourceData = new ArrayList<>();
        List<CompletableFuture<CameraTokenData>> completableFuturesWithTokenData = new ArrayList<>();

        for (Camera camera : cameras) {
            completableFuturesWithSourceData.add(asyncCameraApi.getCameraSourceData(camera.getSourceDataUrl()));
            completableFuturesWithTokenData.add(asyncCameraApi.getCameraTokenData(camera.getTokenDataUrl()));
        }

        List<CompletableFuture<AggregatedCameraData>> completableFuturesWithAggregatedCameraData = new ArrayList<>();
        for (int current = 0; current < cameras.size(); current++) {
            Camera camera = cameras.get(current);
            CameraSourceData cameraSourceData = completableFuturesWithSourceData.get(current).get();
            CameraTokenData cameraTokenData = completableFuturesWithTokenData.get(current).get();

            completableFuturesWithAggregatedCameraData.add(
                    asyncCameraDataMapper.toAggregated(camera, cameraSourceData, cameraTokenData));
        }

        return completableFuturesWithAggregatedCameraData.parallelStream()
                .map(current -> {
                    try {
                        return current.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
