package org.medplus.assetmanagementcore.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.medplus.assetmanagementcore.dao.AssetDao;
import org.medplus.assetmanagementcore.exceptions.EmployeeException;
import org.medplus.assetmanagementcore.model.Asset;
import org.medplus.assetmanagementcore.model.AssetMapping;
import org.medplus.assetmanagementcore.model.NewTypeRequest;
import org.medplus.assetmanagementcore.model.Request;
import org.medplus.assetmanagementcore.utils.AssetAllocation;
import org.medplus.assetmanagementcore.utils.AssetStatus;
import org.medplus.assetmanagementcore.utils.AssetType;
import org.medplus.assetmanagementcore.utils.Queries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AssetDaoImpl implements AssetDao {

	@Autowired
	JdbcTemplate template;

	@Override
	public List<Asset> getAllAssets() throws DataAccessException {
		return template.query(Queries.getAllAsset, new RowMapper<Asset>() {
			public Asset mapRow(ResultSet rs, int row) throws SQLException {
				Asset e = new Asset();
				e.setAssetId(rs.getLong(1));
				e.setSerialNumber(rs.getString(2));
				e.setAssetName(rs.getString(3));
				e.setAssetType((rs.getString(4)));
				e.setCost(rs.getDouble(5));
				e.setStatus(AssetStatus.getName(rs.getString(6)));
				e.setCreatedBy(rs.getString(7));
				e.setCreatedDate(new java.util.Date(rs.getDate(8).getTime()));
				return e;
			}
		});

	}

	@Override
	public int addAsset(final Asset asset)
			throws DataIntegrityViolationException {

		int resultCount = template.update(Queries.addNewAsset,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement pst)
							throws SQLException {
						pst.setString(1, asset.getSerialNumber());
						pst.setString(2, asset.getAssetName());
						pst.setString(3, asset.getAssetType().toString());
						pst.setDouble(4, asset.getCost());
						pst.setString(5, asset.getStatus().value);
						pst.setString(6, asset.getCreatedBy());
						pst.setDate(7, new java.sql.Date(new Date().getTime()));
						//pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
						pst.setString(8, asset.getModel());
					}
				});
		return resultCount;
	}
    @Transactional
	@Override
	public int updateAssetStatus(final long assetId, final AssetStatus status,
			final String modifiedBy) throws DataAccessException {
		int resultCountAsset = 0;
		int resultCountAssetLog = updateAssetToLog(assetId, modifiedBy);
		if (resultCountAssetLog != 0) {
			resultCountAsset = template.update(Queries.updateAssetStatus,
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement pst)
								throws SQLException {
							pst.setString(1, status.value);
							pst.setString(2, modifiedBy);
							pst.setDate(3,
									new java.sql.Date(new Date().getTime()));
							pst.setLong(4, assetId);
						}
					});
		}
		return resultCountAsset;

	}

	private int updateAssetToLog(long assetId, final String modifiedBy)
			throws DataAccessException {

		final Asset asset = getAsset(assetId);
		int resultCountAssetLog = template.update(Queries.UPDATE_ASSET_TO_LOG_,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement pst)
							throws SQLException {

						pst.setLong(1, asset.getAssetId());
						pst.setString(3, asset.getAssetName());
						pst.setString(2, asset.getSerialNumber());
						pst.setString(4, asset.getAssetType().toString());
						pst.setDouble(5, asset.getCost());
						pst.setString(6, asset.getStatus().value);
						pst.setString(7, asset.getCreatedBy());
						pst.setDate(8, new java.sql.Date(asset.getCreatedDate()
								.getTime()));
						pst.setString(9, modifiedBy);
						pst.setDate(10, new java.sql.Date(new Date().getTime()));

					}
				});
		return resultCountAssetLog;

	}

	@Override
	public List<Asset> getAssetByStatus(String status)
			throws DataAccessException {

		Object args[] = { status };
		return template.query(Queries.getAssetByStatus, args,
				new RowMapper<Asset>() {
					public Asset mapRow(ResultSet rs, int row)
							throws SQLException {
						Asset asset = new Asset();
						asset.setAssetId(rs.getLong(1));
						asset.setSerialNumber(rs.getString(2));
						asset.setAssetName(rs.getString(3));
						asset.setAssetType((rs.getString(4)));
						asset.setCost(rs.getDouble(5));
						asset.setStatus(AssetStatus.getName(rs.getString(6)));
						asset.setCreatedBy(rs.getString(7));
						asset.setCreatedDate(new java.util.Date(rs.getDate(8)
								.getTime()));
						return asset;
					}
				});

	}

	@Override
	public List<Asset> getEmployeeAssets(String empId)
			throws DataAccessException {
		Object args[] = { empId };

		return template.query(Queries.getEmployeeAsset, args,
				new RowMapper<Asset>() {
					public Asset mapRow(ResultSet rs, int row)
							throws SQLException {
						Asset asset = new Asset();
						asset.setAssetId(rs.getLong(1));
						asset.setSerialNumber(rs.getString(2));
						asset.setAssetName(rs.getString(3));
						asset.setAssetType((rs.getString(4)));
						asset.setCost(rs.getDouble(5));
						asset.setStatus(AssetStatus.getName(rs.getString(6)));
						asset.setCreatedBy(rs.getString(7));
						asset.setCreatedDate(new java.util.Date(rs.getDate(8)
								.getTime()));
						return asset;
					}
				});

	}

	@Override
	public List<Request> getAllAssetRequests() throws DataAccessException {

		return template.query(Queries.getAllAssetRequests,
				new RowMapper<Request>() {
					public Request mapRow(ResultSet rs, int row)
							throws SQLException {
						Request request = new Request();
						request.setEmployeeId(rs.getString(1));
						request.setEmployeeName(rs.getString(2));
						request.setAssetType((rs.getString(3)));
						request.setRequestDate(new java.util.Date(rs.getDate(4)
								.getTime()));
                         request.setStatus(rs.getString(5));
						return request;

					}
				});

	}

	@Override
	public int saveAssetRequest(final String assetType,
			final String requestedBy,final String remark) throws DataAccessException,
			DataIntegrityViolationException {

		int resultCount = template.update(Queries.postAssetRequest,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement pst)
							throws SQLException {
						pst.setString(1, requestedBy);
						pst.setString(2, assetType.toString());
						pst.setDate(3, new java.sql.Date(new Date().getTime()));
						pst.setString(4, "Requested");
						pst.setString(5,remark);
						

					}
				});
		return resultCount;
	}

	@Override
	public List<Request> getAssetRequestsByEmployee(String empId)
			throws DataAccessException {
		Object args[] = { empId };

		return template.query(Queries.getAssetRequestsByEmployee, args,
				new RowMapper<Request>() {
					public Request mapRow(ResultSet rs, int row)
							throws SQLException {
						Request request = new Request();
						request.setEmployeeId(rs.getString(1));
						request.setAssetType((rs.getString(2)));
						request.setRequestDate(new java.util.Date(rs.getDate(3)
								.getTime()));
                        request.setStatus(rs.getString(4));
                        request.setRemark(rs.getString(5));
						return request;

					}
				});

	}
    
	@Transactional
	@Override
	public int allocateAsset(final String assignedTo, final long assetId,
			final String assignedBy) throws DataAccessException,
			DataIntegrityViolationException {
		int resultCountLog = 0;
		int resultCount = 0;

		resultCount = template.update(Queries.allocateAsset,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement pst)
							throws SQLException {
						pst.setString(1, assignedTo);
						pst.setString(2, assignedBy);
						pst.setLong(3, assetId);
						pst.setString(4, AssetAllocation.Allocated.value);
						pst.setDate(5, new java.sql.Date(new Date().getTime()));
					}
				});
		if (resultCount > 0){
			resultCountLog = updateMappingToLog(assetId,
					AssetAllocation.Allocated);
			
		}
		int requestResult=updateRequestStatus(assignedTo,"Allocated",assetId);

		return resultCountLog;
	}
    private int updateRequestStatus(final String assignedTo,final String status ,final long assetId){
    	int result= template.update(Queries.updateAssetRequestStatus,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement pst)
							throws SQLException {
						pst.setString(1,status);
						pst.setString(2, assignedTo);
						pst.setString(3, getTypeOfAsset(assetId));
					}
				});
    	return result;
    	
    }
    private String getTypeOfAsset(long assetId){
    	
    	String sql = Queries.getAssetType;
		Object args[] = { assetId };
		String r = template.queryForObject(sql, args, (String.class));

		return r;
    }
	
	@Transactional
	@Override
	public int deAllocateAsset(final long assetId, final String deAllocatedBy)
			throws DataAccessException {

		int resultCount = 0;
		int resultCountLog = updateMappingToLog(assetId,
				AssetAllocation.DeAllocated);
		String sql = Queries.deallocateAsset;
		Object args[] = { assetId };
		if (resultCountLog > 0)
			resultCount = template.update(sql, args);
		/*if(true)
			throw new RuntimeException("transactional error");*/
		
		//int requestResult=updateRequestStatus(assignedTo,"Deallocated",assetId);
		return resultCount;
	}

	private int updateMappingToLog(final long assetId,
			final AssetAllocation status) throws DataAccessException {

		final AssetMapping map = getAssetMapping(assetId);

		int resultCount = template.update(Queries.UPDATE_ASSET_MAPPING_TO_LOG,
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement pst)
							throws SQLException {

						pst.setString(1, map.getEmployeeId());
						pst.setString(2, map.getAssignedBy());
						pst.setLong(3, map.getAssetId());
						pst.setDate(4, new java.sql.Date(map.getAssignedDate()
								.getTime()));
						if (status.equals(AssetAllocation.Allocated)) {
							pst.setDate(5, null);
						} else
							pst.setDate(5, new java.sql.Date(map
									.getReturnDate().getTime()));

						pst.setString(6, status.value.toString());

					}
				});

		return resultCount;

	}

	private AssetMapping getAssetMapping(long assetId)
			throws DataAccessException {
		Object args[] = { assetId };
		return template.query(Queries.getAssetMapping, args,
				new ResultSetExtractor<AssetMapping>() {

					public AssetMapping extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							AssetMapping e = new AssetMapping();
							e.setEmployeeId(rs.getString(1));
							e.setAssignedBy(rs.getString(2));
							e.setAssetId(rs.getInt(3));
							e.setAssignedDate(new java.util.Date(rs.getDate(4)
									.getTime()));

							e.setReturnDate((new Date()));

							e.setStatus(AssetAllocation.getName(rs.getString(6)));

							return e;
						} else
							return null;
					}
				});
	}

	@Override
	public List<AssetMapping> getAssetMappingLog() throws DataAccessException {
		return template.query(Queries.getAssetMappingLog, new RowMapper<AssetMapping>() {
			public AssetMapping mapRow(ResultSet rs, int row) throws SQLException {
				AssetMapping e = new AssetMapping();
				e.setEmployeeId(rs.getString(1));
				e.setAssignedBy(rs.getString(2));
				e.setAssetId(rs.getInt(3));
				e.setAssignedDate(new java.util.Date(rs.getDate(4)
						.getTime()));
				if (AssetAllocation.getName(rs.getString(6)).equals(AssetAllocation.DeAllocated)) {
					e.setReturnDate(new java.util.Date(rs.getDate(5)
							.getTime()));
				}
				e.setStatus(AssetAllocation.getName(rs.getString(6)));
				return e;
			}
		});

	}
	@Override
	public Asset getAsset(long assetId) throws DataAccessException {
		Object args[] = { assetId };

		return template.query(Queries.getAsset, args,
				new ResultSetExtractor<Asset>() {

					public Asset extractData(ResultSet rs) throws SQLException,
							DataAccessException {
						if (rs.next()) {
							Asset asset = new Asset();
							asset.setAssetId(rs.getLong(1));
							asset.setSerialNumber(rs.getString(2));
							asset.setAssetName(rs.getString(3));
							asset.setAssetType((rs
									.getString(4)));
							asset.setCost(rs.getDouble(5));
							asset.setStatus(AssetStatus.getName(rs.getString(6)));
							asset.setCreatedDate(new java.util.Date(rs.getDate(
									8).getTime()));
							return asset;
						} else
							return null;
					}
				});

	}

	@Override
	public int saveNewAssetTypeRequest(final String requestedBy,
			final String assetType, final String assetName,final String remarks)
			throws DataAccessException, DataIntegrityViolationException {
		int resultCount = 0;
		resultCount = template.update(Queries.postNewAssetTypeRequest,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement pst)
							throws SQLException {
						pst.setString(1, requestedBy);
						pst.setString(2, assetType);
						pst.setString(3, assetName);
						pst.setDate(4, new java.sql.Date(new Date().getTime()));
						pst.setString(5,remarks);

					}
				});

		return resultCount;

	}

	@Transactional
	@Override
	public int updateAsset(final Asset asset) throws DataAccessException {
		int resultCount = 0;
		int resultCountLog = updateAssetToLog(asset.getAssetId(),
				asset.getModifiedBy());
		if (resultCountLog != 0) {
			resultCount = template.update(Queries.updateAsset,
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement pst)
								throws SQLException {

							pst.setString(1, asset.getAssetName());
							pst.setString(2, asset.getSerialNumber());
							pst.setDouble(3, asset.getCost());
							pst.setDate(4,
									new java.sql.Date(new Date().getTime()));
							pst.setString(5, asset.getModifiedBy());
							pst.setLong(6, asset.getAssetId());

						}
					});
		}
		return resultCount;
	}

	@Override
	public List<NewTypeRequest> getNewAssetTypeRequests()
			throws DataAccessException {

		return template.query(Queries.getAllNewAssetRequests,
				new RowMapper<NewTypeRequest>() {
					public NewTypeRequest mapRow(ResultSet rs, int row)
							throws SQLException {
						NewTypeRequest e = new NewTypeRequest();
						e.setEmployeeId(rs.getString(1));
						e.setAssetType((rs.getString(2)));
						e.setAssetName(rs.getString(3));
						e.setRequestDate(new java.util.Date(rs.getDate(4)
								.getTime()));
						 e.setRemark(rs.getString(5));
						return e;

					}
				});

	}

	@Override
	public List<Asset> getAssetByType(AssetType type)
			throws DataAccessException {
		Object args[] = { type.toString() };
		return template.query(Queries.getAssetByType, args,
				new RowMapper<Asset>() {
					public Asset mapRow(ResultSet rs, int row)
							throws SQLException {
						Asset asset = new Asset();
						asset.setAssetId(rs.getInt(1));
						asset.setSerialNumber(rs.getString(2));
						asset.setAssetName(rs.getString(3));
						asset.setAssetType((rs.getString(4)));
						asset.setCost(rs.getDouble(5));
						asset.setStatus(AssetStatus.getName(rs.getString(6)));
						asset.setCreatedBy(rs.getString(7));
						asset.setCreatedDate(new java.util.Date(rs.getDate(8)
								.getTime()));
						return asset;
					}
				});
	}
	@Override
	public int removeAssetRequest(final Request request) throws DataAccessException {
		
			int resultCount=template.update(Queries.removeAssetRequest,new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement pst) throws SQLException {	
				pst.setString(1,request.getEmployeeId());		
				pst.setString(2,request.getAssetType().toString());		
			}
			
			});
		
			return resultCount;
		}
	
	@Override
	public int addAssetType(final String assetType) {
		int resultCount = template.update(Queries.addAssetType,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement pst)
							throws SQLException {
						pst.setString(1,assetType);
						
					}
		
				});
		return resultCount;
	}

	@Override
	public List<String> getAllAssetTypes() throws DataAccessException {
		return template.query(Queries.getAllAssetTypes, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				String message=null;
				message=rs.getString(1);
				return message;
			}
		});

}

	@Override
	public List<NewTypeRequest> getNewAssetRequestsByEmployee(String requestedBy) {
		Object args[] = { requestedBy };

		return template.query(Queries.getNewAssetRequestsByEmployee, args,
				new RowMapper<NewTypeRequest>() {
					public NewTypeRequest mapRow(ResultSet rs, int row)
							throws SQLException {
						NewTypeRequest request = new NewTypeRequest();
						request.setEmployeeId(rs.getString(1));
						request.setAssetType((rs.getString(2)));
					
						request.setAssetName(rs.getString(3));
						request.setRequestDate(new java.util.Date(rs.getDate(4)
								.getTime()));
						
						request.setRemark(rs.getString(5));

						return request;

					}
				});
	}

	@Override
	public List<AssetMapping> getAllocatedAssets()
			throws DataAccessException {

		

		return template.query(Queries.getAllocatedAssets,
				new RowMapper<AssetMapping>() {
			
					public AssetMapping mapRow(ResultSet rs, int row)
							throws SQLException {
						
						AssetMapping assetMapping = new AssetMapping();
						assetMapping.setAssetId(rs.getLong(1));
						assetMapping.setEmployeeId(rs.getString(2));
						assetMapping.setEmployeeName(rs.getString(3));
						assetMapping.setAssignedBy(rs.getString(4));
						assetMapping.setAssetType(rs.getString(5));

						assetMapping.setAssetName(rs.getString(6));
						assetMapping.setAssignedDate(rs.getDate(7));
					
						return assetMapping;
					}
				});

	}

	@Override
	public int updateRequestRemark(final String remark, final String requestedby, final String assettype,final String status) {
		
		
		int resultCount = 0;
		
			resultCount = template.update(Queries.updateAssetRemark,
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement pst)
								throws SQLException {
							pst.setString(1,remark);
							pst.setString(2, status);
							pst.setString(3,requestedby);
							pst.setString(4, assettype);
						}
					});
			
			System.out.println("VAIO");
		return resultCount;
	}

	@Override
	public int updateNewRemark(final String reason, final String requestedby,
			final String assettype) {
		int resultCount = 0;

		resultCount = template.update(Queries.updateNewRemark,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement pst)
							throws SQLException {
						pst.setString(1,reason);
						pst.setString(2,requestedby);
						pst.setString(3, assettype);
					}
				});
		
	return resultCount;
	}
}
