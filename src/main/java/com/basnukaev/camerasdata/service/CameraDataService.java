package com.basnukaev.camerasdata.service;

import com.basnukaev.camerasdata.dto.AggregatedCameraData;

import java.util.List;

public interface CameraDataService {

    List<AggregatedCameraData> getAggregatedData() throws Exception;
}

