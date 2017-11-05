package org.medplus.assetmanagementcore.dao;
import java.util.List;

import org.medplus.assetmanagementcore.exceptions.EmployeeException;
import org.medplus.assetmanagementcore.model.Asset;
import org.medplus.assetmanagementcore.model.AssetMapping;
import org.medplus.assetmanagementcore.model.NewTypeRequest;
import org.medplus.assetmanagementcore.model.Request;
import org.medplus.assetmanagementcore.utils.AssetStatus;
import org.medplus.assetmanagementcore.utils.AssetType;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;


public interface AssetDao {
	public int updateAssetStatus(long assetId, AssetStatus status,
			String ModifiedBy) throws DataAccessException;

	public int addAsset(Asset asset) throws DataAccessException,
			DataIntegrityViolationException;

	public List<Asset> getAllAssets() throws DataAccessException;

	public Asset getAsset(long assetId) throws DataAccessException;

	public List<Asset> getAssetByStatus(String status)
			throws DataAccessException;

	public List<Asset> getEmployeeAssets(String empId)
			throws DataAccessException;

	public List<NewTypeRequest> getNewAssetTypeRequests()
			throws DataAccessException;

	public List<Request> getAllAssetRequests() throws DataAccessException;

	public int saveAssetRequest(String assetType, String empId,String remark)
			throws DataIntegrityViolationException, DataAccessException;

	public int saveNewAssetTypeRequest(String requestedBy, String assetType,
			String assetName, String remarks) throws DataAccessException,
			DataIntegrityViolationException;

	public List<Request> getAssetRequestsByEmployee(String empId)
			throws DataAccessException;

	public int allocateAsset(String allocatedTo, long assetId,
			String allocatedBy) throws DataAccessException,
			DataIntegrityViolationException;

	public int deAllocateAsset(long assetId, String deallocatedBy)
			throws DataAccessException;

	public int updateAsset(Asset asset) throws DataAccessException;

	public List<Asset> getAssetByType(AssetType type)
			throws DataAccessException;
	public List<AssetMapping> getAssetMappingLog() throws DataAccessException;
	
	public int removeAssetRequest(Request request) throws DataAccessException;

	int addAssetType(String assetType);

	List<String> getAllAssetTypes() throws DataAccessException;

	public List<NewTypeRequest> getNewAssetRequestsByEmployee(String requestedBy);

	List<AssetMapping> getAllocatedAssets() throws DataAccessException;
	
	public int updateRequestRemark(final String remark,final String requestedby,String assettype,String status);

	public int updateNewRemark(String reason, String requestedby,
			String assettype);
}