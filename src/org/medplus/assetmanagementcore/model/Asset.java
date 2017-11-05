package org.medplus.assetmanagementcore.model;
import java.util.Date;

import org.medplus.assetmanagementcore.utils.AssetStatus;
public class Asset {
	private long assetId;

	private String serialNumber;

	private String assetName;

	private String assetType;

	private double cost;

	private AssetStatus status;

	private Date createdDate;

	private String createdBy;

	private String modifiedBy;

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	private Date dateModified;

	public long getAssetId() {
		return assetId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public AssetStatus getStatus() {
		return status;
	}

	public void setStatus(AssetStatus status) {
		this.status = status;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	};
	@Override
	public String toString() {
		return "Asset [assetId=" + assetId + ", serialNumber=" + serialNumber
				+ ", assetName=" + assetName + ", assetType=" + assetType
				+ ", cost=" + cost + ", status=" + status + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", dateModified=" + dateModified + "]";
	}


}
