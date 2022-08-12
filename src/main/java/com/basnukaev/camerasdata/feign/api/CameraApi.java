package com.basnukaev.camerasdata.feign.api;

import com.basnukaev.camerasdata.dto.Camera;
import com.basnukaev.camerasdata.dto.CameraSourceData;
import com.basnukaev.camerasdata.dto.CameraTokenData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "CameraApi", url = "https://www.mocky.io", path = "v2")
public interface CameraApi {

    @GetMapping(value = "/5c51b9dd3400003252129fb5")
    List<Camera> getListOfAvailableCameras();

    @GetMapping(value = "{path}")
    CameraSourceData getCameraSourceDataByUrl(
            @PathVariable(value = "path") String path,
            @RequestParam(name = "mocky-delay", required = false) String requestParam);

    @GetMapping(value = "{path}")
    CameraSourceData getCameraSourceDataByUrl(@PathVariable(value = "path") String path);

    @GetMapping(value = "{path}")
    CameraTokenData getCameraTokenDataByUrl(
            @PathVariable(value = "path") String path,
            @RequestParam(name = "mocky-delay", required = false) String requestParam);

    @GetMapping(value = "{path}")
    CameraTokenData getCameraTokenDataByUrl(@PathVariable(value = "path") String path);
}
