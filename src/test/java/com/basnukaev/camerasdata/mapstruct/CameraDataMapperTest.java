package com.basnukaev.camerasdata.mapstruct;

import com.basnukaev.camerasdata.dto.AggregatedCameraData;
import com.basnukaev.camerasdata.dto.Camera;
import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBootApplication
class CameraDataMapperTest {
    private final CameraDataMapper cameraDataMapper = new CameraDataMapperImpl();

    @Test
    void shouldCorrectlyAggregateCamerasData() {
        //given
        Camera camera = new Camera(
                1L,
                "http://www.mocky.io/v2/5c51b230340000094f129f5d",
                "http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s");
        CameraSourceData cameraSourceData = new CameraSourceData(
                "LIVE",
                "rtsp://127.0.0.1/1");
        CameraTokenData cameraTokenData = new CameraTokenData(
                "fa4b588e-249b-11e9-ab14-d663bd873d93",
                120);

        AggregatedCameraData aggregatedCameraData = new AggregatedCameraData(
                1L,
                "LIVE",
                "rtsp://127.0.0.1/1",
                "fa4b588e-249b-11e9-ab14-d663bd873d93",
                120
        );

        //when
        AggregatedCameraData expected = cameraDataMapper.toAggregated(camera, cameraSourceData, cameraTokenData);

        //then
        assertThat(aggregatedCameraData.getId()).isEqualTo(expected.getId());
        assertThat(aggregatedCameraData.getUrlType()).isEqualTo(expected.getUrlType());
        assertThat(aggregatedCameraData.getVideoUrl()).isEqualTo(expected.getVideoUrl());
        assertThat(aggregatedCameraData.getValue()).isEqualTo(expected.getValue());
        assertThat(aggregatedCameraData.getTtl()).isEqualTo(expected.getTtl());
    }
}