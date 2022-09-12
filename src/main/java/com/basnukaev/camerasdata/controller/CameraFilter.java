package com.basnukaev.camerasdata.controller;

public class CameraFilter {

  static final class GeographicalConstraints {

    final Integer zoom;
    final Integer radius;
    Integer tile;
    Integer cells;
    Integer area;
    final Float lat;
    final Float lng;

    private int hc;

    GeographicalConstraints(Integer zoom,
                            Integer tile,
                            Integer cells,
                            Integer area,
                            Integer radius,
                            Float lat,
                            Float lng) {
      this.zoom = zoom;
      this.radius = radius;
      this.tile = tile;
      this.cells = cells;
      this.area = area;
      this.lat = lat;
      this.lng = lng;
    }

    void clearCoordinateConstraints() {
      this.area = null;
      this.tile = null;
      this.cells = null;
    }
  }

  private final GeographicalConstraints geographicalConstraints;

  public CameraFilter(GeographicalConstraints geographicalConstraints) {
    this.geographicalConstraints = geographicalConstraints;
  }
}
