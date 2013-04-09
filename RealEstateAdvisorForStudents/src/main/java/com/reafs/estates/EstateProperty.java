package com.reafs.estates;

import com.reafs.output.types.PlotSize;
import com.reafs.output.types.RealEstateLocation;
import com.reafs.output.types.RealEstateType;

public class EstateProperty {

	private RealEstateType realEstateType;
	private PlotSize plotSize;
	private RealEstateLocation realEstateLocation;
	private boolean isSuitable = true;

	public EstateProperty() {
	}

	public EstateProperty(RealEstateType realEstateType, PlotSize plotSize,
			RealEstateLocation realEstateLocation) {
		this.realEstateType = realEstateType;
		this.plotSize = plotSize;
		this.realEstateLocation = realEstateLocation;
	}

	public RealEstateType getRealEstateType() {
		return realEstateType;
	}

	public void setRealEstateType(RealEstateType realEstateType) {
		this.realEstateType = realEstateType;
	}

	public PlotSize getPlotSize() {
		return plotSize;
	}

	public void setPlotSize(PlotSize plotSize) {
		this.plotSize = plotSize;
	}

	public boolean isSuitable() {
		return isSuitable;
	}

	public void setSuitable(boolean isSuitable) {
		this.isSuitable = isSuitable;
	}

	public RealEstateLocation getRealEstateLocation() {
		return realEstateLocation;
	}

	public void setRealEstateLocation(RealEstateLocation realEstateLocation) {
		this.realEstateLocation = realEstateLocation;
	}

	@Override
	public String toString() {
		return "EstateProperty [realEstateType=" + realEstateType
				+ ", plotSize=" + plotSize + ", realEstateLocation="
				+ realEstateLocation + ", isSuitable=" + isSuitable + "]";
	}

}
