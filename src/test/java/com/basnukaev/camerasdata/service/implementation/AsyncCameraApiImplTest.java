package com.basnukaev.camerasdata.service.implementation;

import com.basnukaev.camerasdata.dto.AggregatedCameraData;
import com.basnukaev.camerasdata.dto.Camera;
import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;
import com.basnukaev.camerasdata.feign.api.CameraApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncCameraApiImplTest {
    @Mock
    private CameraApi cameraApi;

    @InjectMocks
    AsyncCameraApiImpl asyncCameraApi;

    private final List<Camera> testCameraList = List.of(
            new Camera(
                    1L,
                    "http://www.mocky.io/v2/5c51b230340000094f129f5d",
                    "http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s"),
            new Camera(
                    2L,
                    "http://www.mocky.io/v2/5c51b2e6340000a24a129f5f?mocky-delay=100ms",
                    "http://www.mocky.io/v2/5c51b5ed340000554e129f7e")
    );

    private final List<CameraSourceData> testCameraSourceDataList = List.of(
            new CameraSourceData(
                    "LIVE",
                    "rtsp://127.0.0.1/1"),
            new CameraSourceData(
                    "ARCHIVE",
                    "rtsp://127.0.0.1/2")
    );

    private final List<CameraTokenData> testCameraTokenDataList = List.of(
            new CameraTokenData(
                    "fa4b588e-249b-11e9-ab14-d663bd873d93",
                    120),
            new CameraTokenData(
                    "fa4b5b22-249b-11e9-ab14-d663bd873d93",
                    60)
    );

    private final List<AggregatedCameraData> testAggregatedCameraDataList = List.of(
            new AggregatedCameraData(
                    1L,
                    "LIVE",
                    "rtsp://127.0.0.1/1",
                    "fa4b588e-249b-11e9-ab14-d663bd873d93",
                    120),
            new AggregatedCameraData(
                    2L,
                    "ARCHIVE",
                    "rtsp://127.0.0.1/2",
                    "fa4b5b22-249b-11e9-ab14-d663bd873d93",
                    60)
    );

    private final Camera firstCamera = testCameraList.get(0);
    private final Camera secondCamera = testCameraList.get(1);
    private final CameraSourceData firstCameraSourceData = testCameraSourceDataList.get(0);
    private final CameraSourceData secondCameraSourceData = testCameraSourceDataList.get(1);
    private final CameraTokenData firstCameraTokenData = testCameraTokenDataList.get(0);
    private final CameraTokenData secondCameraTokenData = testCameraTokenDataList.get(1);
    private final AggregatedCameraData firstAggregatedCameraData = testAggregatedCameraDataList.get(0);
    private final AggregatedCameraData secondAggregatedCameraData = testAggregatedCameraDataList.get(1);

    @Test
    void shouldReturnCompletableFutureOfCameraSourceDataFromGivenSourceUrl() throws Exception {
        //given
        String firstSourceDataUrl = firstCamera.getSourceDataUrl();
        String secondSourceDataUrl = secondCamera.getSourceDataUrl();

        String firstPath = "5c51b230340000094f129f5d";
        String firstRequestParam = ""; // у первой ссылки нет параметра, поэтому мы вызываем метод без параметра
        doReturn(firstCameraSourceData).when(cameraApi).getCameraSourceDataByUrl(firstPath);

        String secondPath = "5c51b2e6340000a24a129f5f";
        String secondRequestParam = "100ms";
        doReturn(secondCameraSourceData).when(cameraApi).getCameraSourceDataByUrl(secondPath, secondRequestParam);

        //when
        CompletableFuture<CameraSourceData> firstExpected = asyncCameraApi.getCameraSourceData(firstSourceDataUrl);
        CompletableFuture<CameraSourceData> secondExpected = asyncCameraApi.getCameraSourceData(secondSourceDataUrl);

        //then
        verify(cameraApi).getCameraSourceDataByUrl(firstPath);
        verify(cameraApi).getCameraSourceDataByUrl(secondPath, secondRequestParam);

        assertThat(firstCameraSourceData).isEqualTo(firstExpected.get());
        assertThat(secondCameraSourceData).isEqualTo(secondExpected.get());
    }

    @Test
    void shouldReturnCompletableFutureOfCameraTokenDataFromGivenTokenUrl() throws Exception {
        //given
        String firstTokenDataUrl = firstCamera.getTokenDataUrl();
        String secondTokenDataUrl = secondCamera.getTokenDataUrl();

        String firstPath = "5c51b5b6340000554e129f7b";
        String firstRequestParam = "1s";
        doReturn(firstCameraTokenData).when(cameraApi).getCameraTokenDataByUrl(firstPath, firstRequestParam);

        String secondPath = "5c51b5ed340000554e129f7e";
        String secondRequestParam = ""; // у второй ссылки нет параметра, поэтому мы вызываем метод без параметра
        doReturn(secondCameraTokenData).when(cameraApi).getCameraTokenDataByUrl(secondPath);

        //when
        CompletableFuture<CameraTokenData> firstExpected = asyncCameraApi.getCameraTokenData(firstTokenDataUrl);
        CompletableFuture<CameraTokenData> secondExpected = asyncCameraApi.getCameraTokenData(secondTokenDataUrl);

        //then
        verify(cameraApi).getCameraTokenDataByUrl(firstPath, firstRequestParam);
        verify(cameraApi).getCameraTokenDataByUrl(secondPath);

        assertThat(firstCameraTokenData).isEqualTo(firstExpected.get());
        assertThat(secondCameraTokenData).isEqualTo(secondExpected.get());
    }
}