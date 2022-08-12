package com.basnukaev.camerasdata.controller;

import com.basnukaev.camerasdata.service.CameraDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CameraDataControllerTest {
    @Mock
    private CameraDataService cameraDataService;

    @InjectMocks
    private CameraDataController cameraDataController;

    @Test
    void shouldCallMethodFromCameraDataService() throws Exception {
        //given

        //when
        cameraDataController.getCameraData();

        //then
        verify(cameraDataService).getAggregatedData();
    }
}