package org.medplus.assetmanagementwebapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.medplus.assetmanagementcore.exceptions.AssetException;
import org.medplus.assetmanagementcore.exceptions.AuthenticationException;
import org.medplus.assetmanagementcore.exceptions.EmployeeException;
import org.medplus.assetmanagementcore.model.Asset;
import org.medplus.assetmanagementcore.model.AssetMapping;
import org.medplus.assetmanagementcore.model.Employee;
import org.medplus.assetmanagementcore.model.NewTypeRequest;
import org.medplus.assetmanagementcore.model.Request;
import org.medplus.assetmanagementcore.service.AssetService;
import org.medplus.assetmanagementcore.service.EmployeeService;
import org.medplus.assetmanagementcore.utils.AssetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AssetController implements HandlerExceptionResolver {

	@Autowired
	AssetService assetService;

	@Autowired
	EmployeeService employeeService;
	@Autowired
	JdbcTemplate template;
	@RequestMapping(value = "/tempurl", method = RequestMethod.GET)
    public ModelAndView go(){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("EmpHome");
		return mav;
	}
	
	@RequestMapping(value = "/addAsset", method = RequestMethod.GET)
	public ModelAndView getAssetForm() {
		Asset asset = new Asset();
		ModelAndView mav = new ModelAndView();
		mav.addObject(asset);
		mav.setViewName("Asset");
		return mav;
	}

	@InitBinder
	public final void initBinderUsuariosFormValidator(
			final WebDataBinder binder, final Locale locale) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				locale);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/addAsset", method = RequestMethod.POST)
	public ModelAndView addAsset(@ModelAttribute("asset") Asset asse,
			BindingResult result) {
		ModelAndView mav = new ModelAndView("AdminHome");
		String msg = null;
		String response;
		try {

			response = assetService.addAsset(asse);

			if (response.equals("Failed to insert")) {
				msg = "add asset failed";
			} else {
				msg = " Asset added Successfully";
			}
		} catch (EmployeeException | AssetException | AuthenticationException e) {
			mav.addObject("message", e.getMessage());
		}
		mav.addObject("message", msg);
		return mav;
	}

	@RequestMapping(value = "/viewAssets", method = RequestMethod.GET)
	public ModelAndView viewAssetsForm(@RequestParam("role") String role) {
		ModelAndView mav = new ModelAndView("ViewAssets");
		List<Asset> assetlist;
		try {
			assetlist = assetService.getAllAssets();
			mav.addObject("assets", assetlist);
			mav.addObject("requestrole", role);
			mav.addObject("viewdetails", "All Assets");
			return mav;
		} catch (AssetException e) {
			mav.addObject("message", e.getMessage());
		}
		return mav;
	}

	@RequestMapping(value = "/viewAssetsByStatus", method = RequestMethod.GET)
	public ModelAndView viewAssetForm(@RequestParam("status") String status) {
		ModelAndView mav = new ModelAndView("ViewAssets");
		List<Asset> assetlist;
		try {
			assetlist = assetService.getAssetsByStatus(status);
			mav.addObject("assets", assetlist);
			if (status.equals("A")) {
				mav.addObject("viewdetails", "Available Assets");
			} else if (status.equals("N"))
				mav.addObject("viewdetails", "Allocated Assets");
		} catch (AssetException e) {
			mav.addObject("message", e.getMessage());
		}
		return mav;
	}

	@RequestMapping(value = "/allocateAsset", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(
			@RequestParam("employeeID") String employeeID,
			@RequestParam("assetID") String assetID,
			@RequestParam("assignedBy") String assignedBy,
			@RequestParam("file") MultipartFile file) {
		String rows = "";
		String message = "";
		ModelAndView mav = new ModelAndView();
		try {
			rows = assetService.allocateAsset(employeeID,
					Long.parseLong(assetID), assignedBy);
		} catch (NumberFormatException | AssetException
				| AuthenticationException | EmployeeException e1) {

			message="Invalid EmployeeId";
		}
		if (rows.equals("success")) {
			if (file!=null&&!file.isEmpty()) {
				try {
					final byte[] bytes = file.getBytes();
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator
							+ "AssetManagementForms");
					if (!dir.exists())
						dir.mkdirs();
					File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + employeeID + "-" + assetID
							+ new Date());
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					message = "Allocation Successful, You successfully uploaded Signed Form="
							+ employeeID + "-" + assetID;
				} catch (MaxUploadSizeExceededException | IOException e) {
					message = e + "You failed to upload " + employeeID + "-"
							+ assetID + " => " + e.getMessage();
				}
			} else {
				message=" Allocation Successful The file uploaded was empty";
			}
		}
		mav.setViewName("Home");
		mav.addObject("message", message);
		return mav;
	}

	@RequestMapping(value = "/allocateAsset", method = RequestMethod.GET)
	public ModelAndView getAllocationForm(
			@RequestParam("assetID") String assetID) {
		ModelAndView mav = new ModelAndView("Allocation");
		mav.addObject("assetID", assetID);
		return mav;
	}

	@RequestMapping(value = "/deallocateAsset", method = RequestMethod.GET)
	public ModelAndView getDeAllocationForm(
			@RequestParam("assetID") String assetID) {
		ModelAndView mav = new ModelAndView("DeAllocation");
		mav.addObject("assetID", assetID);
		return mav;
	}

	@RequestMapping(value = "/deallocateAsset", method = RequestMethod.POST)
	public ModelAndView DeallocateAsset(
			@RequestParam("assetID") String assetID,
			@RequestParam("deassignedBy") String deassignedBy)
			throws NumberFormatException, AuthenticationException {
		ModelAndView mav = new ModelAndView("Home");
		String message = null;
		String response = null;
		try {
			System.out.println("Deallocating");
			response = assetService.deAllocateAsset(Long.parseLong(assetID),
					deassignedBy);
			System.out.println(response);
		} catch (AssetException | EmployeeException e) {
			message = e.getMessage() + "Deallocation Failed";
		}
		if (response.equals("success")) {
			message = "Deallocated asset successfully";
		} else {
			message = "Deallocation failed";
		}
		mav.addObject("message", message);
		return mav;
	}

	@RequestMapping(value = "/ViewAssetRequests", method = RequestMethod.GET)
	public ModelAndView viewAssetRequestForm() {
		String msg = null;
		ModelAndView mav = new ModelAndView("ViewAssetRequests");
		List<Request> getAllAssetRequests;
		
		try {
			List<NewTypeRequest> newAssetRequests = assetService
					.getNewAssetTypeRequests();
		
			mav.addObject("newAssetRequests", newAssetRequests);
			getAllAssetRequests = assetService.getAllAssetRequests();
			mav.addObject("assetRequests", getAllAssetRequests);

		} catch (AssetException e) {
			msg = e.getErrorMessage();
		}
		mav.addObject("message", msg);
		return mav;
	}

	@RequestMapping(value = "/empassets", method = RequestMethod.GET)
	public ModelAndView EmployeeAssets(
			//@RequestParam("username") String username,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("EmployeeAsset");
		List<Asset> assetlist = null;
		Employee employee = null;
		String username="";
	       HttpSession session=request.getSession(false);
	       if(session!=null)
	    	   username=(String)session.getAttribute("username");
		try {
			assetlist = assetService.getAssetsOfEmployee(username);
		} catch (AssetException e1) {
			mav.addObject("message", e1.getMessage());
		}
		mav.addObject("assets", assetlist);
		try {
			employee = employeeService.getEmployee(username);
		} catch (EmployeeException e) {
			mav.addObject("message", e.getMessage());
		}

		mav.addObject("emp", employee);
		return mav;

	}

	@RequestMapping(value = "/emprequest", method = RequestMethod.GET)
	public ModelAndView EmployeeRequest(
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("EmployeeRequests");
		List<Request> requestList;
		String username="";
	       HttpSession session=request.getSession(false);
	       if(session!=null)
	    	   username=(String)session.getAttribute("username");
		try {
			requestList = assetService.getAssetRequests(username);
			mav.addObject("requestList", requestList);
           
			List<NewTypeRequest> newAssetRequests = assetService
					.getNewAssetTypeRequests(username);
        

			mav.addObject("newAssetRequests", newAssetRequests);
		} catch (AssetException e) {
			mav.addObject("message", e.getMessage());
		}
		return mav;
	}

	@RequestMapping(value = "assetrequest", method = RequestMethod.GET)
	public ModelAndView postNewTypeAssetRequestForm(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("NewRequest");
		return mav;

	}

	@RequestMapping(value = "assetrequest", method = RequestMethod.POST)
	public ModelAndView postNewTypeAssetRequest(
			
			@RequestParam("assetType") String assetType,
			@RequestParam("assetName") String assetName,
			@RequestParam("remarks") String remarks,
			HttpServletRequest request, HttpServletResponse response) {
		String userName="";
		ModelAndView mav = new ModelAndView("EmpHome");
		String msg = 	null;
       HttpSession session=request.getSession(false);
       if(session!=null)
    	 userName=(String)session.getAttribute("username");
		
		try {
			msg = assetService.saveNewAssetTypeRequest(userName, assetType,
					assetName,remarks);
			mav.addObject("message","Request Posted Successfully...");
		} catch (AuthenticationException | AssetException e) {
			mav.addObject("message", e.getMessage() + ":" + e);
		}

		return mav;

	}

	@RequestMapping(value = "postAssetRequests", method = RequestMethod.GET)
	public ModelAndView postAssetRequestForm(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("Request");
		List<String> types;
		try {
			types = assetService.getAllAssetTypes();
			mav.addObject("types",types);
		} catch (AssetException e) {
			mav.addObject("message", "No Assets to view");
		}
		return mav;

	}

	@RequestMapping(value = "postAssetRequests", method = RequestMethod.POST)
	public ModelAndView postAssetRequest(
			@RequestParam("remark") String remark,
			@RequestParam("type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("Request");
		String requestedBy="";
	       HttpSession session=request.getSession(false);
	       if(session!=null)
	    	   requestedBy=(String)session.getAttribute("username");
	    String msg;
		try {
			msg = assetService.saveAssetRequest(type,
					requestedBy,remark);
			mav.addObject("message", msg);
			mav.addObject("message","Request posted successfully");
		} catch (AssetException | AuthenticationException e) {
			mav.addObject("message", e.getMessage() + ":" + e);
		}
		return mav;

	}

	@RequestMapping(value = "/UpdateAsset", method = RequestMethod.GET)
	public ModelAndView updateAssetForm(@RequestParam("assetID") long assetID,
			HttpServletRequest request, HttpServletResponse response) {
		Asset asset = new Asset();
		ModelAndView mav = new ModelAndView("UpdateAsset");
		try {
			asset = assetService.getAsset(assetID);
			mav.addObject(asset);
			mav.addObject("viewdetails", "Update Asset Form");
			} catch (AssetException e) {
			mav.addObject("message", e.getErrorMessage());
		}
		return mav;
	}

	@RequestMapping(value = "/UpdateAsset", method = RequestMethod.POST)
	public ModelAndView updateAsset(@ModelAttribute("asset") Asset asset,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("AdminHome");
		String msg=null;
		String result = null;
		

			asset.setModifiedBy(request.getSession(false)
					.getAttribute("username").toString());
			try {
				result = assetService.updateAsset(asset);
			} catch (AssetException | AuthenticationException e) {
				msg = e.getMessage()+"record not updated";
			}
			if (result.equals("Failed to Update")) {
				msg = "record not updated";
			} else {
				msg = "record  updated";
			}
		
		mav.addObject("message", msg);
		return mav;
	}

	@RequestMapping(value = "/asset-mapping-log", method = RequestMethod.GET)
	public ModelAndView viewAssetMappingLog() {
		ModelAndView mav = new ModelAndView("ViewLog");
		List<AssetMapping> assetMappingLog;
		try {
			assetMappingLog = assetService.getAssetMappingLog();
			mav.addObject("assetMappingLog", assetMappingLog);
			return mav;
		} catch (AssetException e) {
			mav.addObject("message", e.getMessage());
		}
		return mav;
	}

	@RequestMapping(value = "assetInfo", method = RequestMethod.GET)
	public ModelAndView viewAssetInfo(@RequestParam("assetId") long assetId) {
		ModelAndView mav = new ModelAndView("AssetInfo");
		Asset asset;
		try {
			asset = assetService.getAsset(assetId);
			mav.addObject("asset", asset);
		} catch (AssetException e) {
			mav.addObject("message", e.getMessage());
		}
		return mav;

	}

	@RequestMapping(value = "/removeAssetRequest", method = RequestMethod.GET)
	public String RemoveRequest(@RequestParam("employeeId") String employeeId,
			@RequestParam("type") String type) {
		Request request = new Request();
		request.setEmployeeId(employeeId);
		request.setAssetType(type);
		try {
			assetService.removeAssetRequest(request);
		} catch (AssetException e) {
		}
		return "redirect:/ViewAssetRequests";
	}

	@RequestMapping(value = "/viewAssetsByType", method = RequestMethod.GET)
	public ModelAndView viewAssetByTypeForm(@RequestParam("type") AssetType type) {
		ModelAndView mav = new ModelAndView("ViewAssets");
		List<Asset> assetlist;
		try {
			assetlist = assetService.getAssetByType(type);
			mav.addObject("assets", assetlist);
			mav.addObject("viewdetails", "Available Assets");
		} catch (AssetException e) {
			mav.addObject("message", e.getMessage());
		}
		return mav;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception exc) {
		ModelAndView mav = new ModelAndView("Home");
		if (exc instanceof MaxUploadSizeExceededException) {
			response.setContentType("text/html");
			response.setStatus(HttpStatus.REQUEST_ENTITY_TOO_LARGE.value());

			Long maxSizeInBytes = ((MaxUploadSizeExceededException) exc)
					.getMaxUploadSize();

			String message = "Maximum upload size of " + maxSizeInBytes
					+ " Bytes per attachment exceeded";

			mav.addObject("message", message);

		}
		return mav;
	}
	//change status
	@RequestMapping(value = "/reject-request", method = RequestMethod.GET)
	public ModelAndView rejectRequest(
			@RequestParam("reason") String reason,
			@RequestParam("empId") String requestedby,
			@RequestParam("type") String assettype
			)
	{
		
        int count=assetService.updateRequestRemark(reason, requestedby, assettype,"Rejected");
		ModelAndView mav = new ModelAndView("Home");
		return mav;
	}
	
	@RequestMapping(value = "/reject-remark", method = RequestMethod.GET)
	public ModelAndView rejectRemark(
			@RequestParam("reason") String reason,
			@RequestParam("empId") String requestedby,
			@RequestParam("type") String assettype)
	{
        int count=assetService.updateNewRemark(reason, requestedby, assettype);
		ModelAndView mav = new ModelAndView("Home");
		return mav;
	}
	
	@RequestMapping(value = "/addAssetType", method = RequestMethod.GET)
	public ModelAndView getAssetTypeForm() {
		Asset asset = new Asset();
		ModelAndView mav = new ModelAndView();
		mav.addObject(asset);
		mav.setViewName("AddAssetType");
		return mav;
	}

	@RequestMapping(value = "/addAssetType", method = RequestMethod.POST)
	public ModelAndView addAssetType(
			@RequestParam("AssetTypeName") String AssetTypeName) {

		ModelAndView mav = new ModelAndView("Home");

		String msg = null;
		String response;
		try {

			response = assetService.addAssetType(AssetTypeName);

			if (response.equals("Failed to insert")) {
				msg = "failed to add asset type";
			} else {
				msg = " AssetType added Successfully";
			}
		} catch (EmployeeException | AssetException | AuthenticationException e) {
			mav.addObject("message", e.getMessage());
		}
		mav.addObject("message", msg);
		return mav;
	}
	@RequestMapping(value = "/getAllocatedAssets", method = RequestMethod.GET)
	public ModelAndView getAllocatedAssetsForm() {
		ModelAndView mav = new ModelAndView("GetAllocatedAssets");
		List<AssetMapping> assetlist;
		try {
			assetlist = assetService.getAllocatedAssets();
			mav.addObject("assets", assetlist);
			
		} catch (AssetException e) {
			mav.addObject("message", e.getMessage());
		}
		return mav;
	}

}
