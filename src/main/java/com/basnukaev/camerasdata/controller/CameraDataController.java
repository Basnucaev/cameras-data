package com.basnukaev.camerasdata.controller;

import com.basnukaev.camerasdata.dto.AggregatedCameraData;
import com.basnukaev.camerasdata.service.CameraDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CameraDataController {
    private final CameraDataService cameraDataService;

    @GetMapping("/cameras-data")
    public ResponseEntity<List<AggregatedCameraData>> getCameraData() throws Exception {
        return new ResponseEntity<>(cameraDataService.getAggregatedData(), HttpStatus.OK);
    }
}
