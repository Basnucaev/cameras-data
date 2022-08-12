package com.basnukaev.camerasdata.service.implementation;

import com.basnukaev.camerasdata.dto.AggregatedCameraData;
import com.basnukaev.camerasdata.dto.Camera;
import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;
import com.basnukaev.camerasdata.feign.api.CameraApi;
import com.basnukaev.camerasdata.service.AsyncCameraApi;
import com.basnukaev.camerasdata.service.AsyncCameraDataMapper;
import com.basnukaev.camerasdata.service.CameraDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.AtLeast;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CameraDataServiceImplTest {
    @Mock
    private CameraApi cameraApi;
    @Mock
    private AsyncCameraApi asyncCameraApi;
    @Mock
    private AsyncCameraDataMapper asyncCameraDataMapper;

    @InjectMocks
    private CameraDataServiceImpl cameraDataService;

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
    void shouldReturnAggregatedDataFromGivenCamerasData() throws Exception {
        //given
        doReturn(testCameraList).when(cameraApi).getListOfAvailableCameras();

        doReturn(CompletableFuture.completedFuture(firstCameraSourceData))
                .when(asyncCameraApi).getCameraSourceData(firstCamera.getSourceDataUrl());
        doReturn(CompletableFuture.completedFuture(secondCameraSourceData))
                .when(asyncCameraApi).getCameraSourceData(secondCamera.getSourceDataUrl());

        doReturn(CompletableFuture.completedFuture(firstCameraTokenData))
                .when(asyncCameraApi).getCameraTokenData(firstCamera.getTokenDataUrl());
        doReturn(CompletableFuture.completedFuture(secondCameraTokenData))
                .when(asyncCameraApi).getCameraTokenData(secondCamera.getTokenDataUrl());

        when(asyncCameraDataMapper.toAggregated(firstCamera, firstCameraSourceData, firstCameraTokenData))
                .thenReturn(CompletableFuture.completedFuture(firstAggregatedCameraData));
        when(asyncCameraDataMapper.toAggregated(secondCamera, secondCameraSourceData, secondCameraTokenData))
                .thenReturn(CompletableFuture.completedFuture(secondAggregatedCameraData));

        //when
        List<AggregatedCameraData> expected = cameraDataService.getAggregatedData();

        //then
        verify(cameraApi, atLeastOnce()).getListOfAvailableCameras();
        verify(asyncCameraApi, atLeast(testCameraList.size())).getCameraSourceData(anyString());
        verify(asyncCameraApi, atLeast(testCameraList.size())).getCameraTokenData(anyString());
        verify(asyncCameraDataMapper, atLeast(testCameraList.size())).toAggregated(any(), any(), any());
        verify(asyncCameraDataMapper).toAggregated(firstCamera, firstCameraSourceData, firstCameraTokenData);
        verify(asyncCameraDataMapper).toAggregated(secondCamera, secondCameraSourceData, secondCameraTokenData);

        assertThat(testAggregatedCameraDataList).isEqualTo(expected);
    }
}