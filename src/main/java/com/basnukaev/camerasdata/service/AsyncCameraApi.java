package com.basnukaev.camerasdata.service;

import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;

import java.util.concurrent.CompletableFuture;

public interface AsyncCameraApi {

    CompletableFuture<CameraSourceData> getCameraSourceData(String sourceDataUrl);
    CompletableFuture<CameraTokenData> getCameraTokenData(String tokenDataUrl);
}
