package com.basnukaev.camerasdata.service.implementation;

import com.basnukaev.camerasdata.dto.AggregatedCameraData;
import com.basnukaev.camerasdata.dto.Camera;
import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;
import com.basnukaev.camerasdata.mapstruct.CameraDataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncCameraDataMapperImplTest {
    @Mock
    CameraDataMapper cameraDataMapper;
    @InjectMocks
    AsyncCameraDataMapperImpl asyncCameraDataMapper;

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
    void shouldReturnCompletableFutureOfAggregatedDataFromGivenCamerasData() throws Exception {
        //given
        when(cameraDataMapper.toAggregated(firstCamera, firstCameraSourceData, firstCameraTokenData))
                .thenReturn(firstAggregatedCameraData);
        when(cameraDataMapper.toAggregated(secondCamera, secondCameraSourceData, secondCameraTokenData))
                .thenReturn(secondAggregatedCameraData);

        //when
        CompletableFuture<AggregatedCameraData> firstExpected = asyncCameraDataMapper
                .toAggregated(firstCamera, firstCameraSourceData, firstCameraTokenData);
        CompletableFuture<AggregatedCameraData> secondExpected = asyncCameraDataMapper
                .toAggregated(secondCamera, secondCameraSourceData, secondCameraTokenData);
        //then
        verify(cameraDataMapper).toAggregated(firstCamera, firstCameraSourceData, firstCameraTokenData);
        verify(cameraDataMapper).toAggregated(secondCamera, secondCameraSourceData, secondCameraTokenData);

        assertThat(firstAggregatedCameraData).isEqualTo(firstExpected.get());
        assertThat(secondAggregatedCameraData).isEqualTo(secondExpected.get());
    }
}