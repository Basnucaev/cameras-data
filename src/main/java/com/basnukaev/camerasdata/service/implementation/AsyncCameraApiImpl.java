package com.basnukaev.camerasdata.service.implementation;

import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;
import com.basnukaev.camerasdata.feign.api.CameraApi;
import com.basnukaev.camerasdata.service.AsyncCameraApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncCameraApiImpl implements AsyncCameraApi {
    private final String CORE_URI = "http://www.mocky.io/v2/";

    private final CameraApi cameraApi;

    @Async
    @Override
    public CompletableFuture<CameraSourceData> getCameraSourceData(String sourceDataUrl) {
        log.info("Thread= {}, joined method \"getCameraSourceData\"", Thread.currentThread().getName());
        String path = getPath(sourceDataUrl);
        String requestParam = getRequestParam(sourceDataUrl);

        if (sourceDataUrl.contains("=")) {
            return CompletableFuture.completedFuture(cameraApi.getCameraSourceDataByUrl(path, requestParam));
        }
        return CompletableFuture.completedFuture(cameraApi.getCameraSourceDataByUrl(path));
    }

    @Async
    @Override
    public CompletableFuture<CameraTokenData> getCameraTokenData(String tokenDataUrl) {
        log.info("Thread= {}, joined method \"getCameraTokenData\"", Thread.currentThread().getName());
        String path = getPath(tokenDataUrl);
        String requestParam = getRequestParam(tokenDataUrl);

        if (tokenDataUrl.contains("=")) {
            return CompletableFuture.completedFuture(cameraApi.getCameraTokenDataByUrl(path, requestParam));
        }
        return CompletableFuture.completedFuture(cameraApi.getCameraTokenDataByUrl(path));
    }

    private synchronized String getRequestParam(String url) {
        if (url.contains("=")) {
            return url.substring(url.indexOf("=") + 1);
        }
        return "";
    }

    private String getPath(String sourceUrl) {
        StringBuffer stringBuffer = new StringBuffer();

        AtomicInteger current = new AtomicInteger(CORE_URI.length());
        for (current.get(); current.get() < sourceUrl.length(); current.getAndIncrement()) {
            if (sourceUrl.charAt(current.get()) == '?') {
                break;
            }
            stringBuffer.append(sourceUrl.charAt(current.get()));
        }

        return stringBuffer.toString();
    }
}
