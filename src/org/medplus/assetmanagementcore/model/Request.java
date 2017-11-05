package org.medplus.assetmanagementcore.model;

import java.util.Date;


public class Request {
	
	private String employeeId;
	
	private String assetType;
	
	private Date requestDate;
	
	private String employeeName;
	
	private String status;
	
	private String remark;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

		public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

		public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	@Override
	public String toString() {

		return "REQUEST[employeeId" + employeeId + "assetType" + assetType
				+ "requestDate" + requestDate + "]";
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
