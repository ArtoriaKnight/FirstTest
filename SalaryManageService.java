/**
 * 
 */
package cn.ztwb.hrd.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.boris.expr.Expr;
import org.boris.expr.ExprException;
import org.boris.expr.ExprFunction;
import org.boris.expr.ExprVariable;
import org.boris.expr.IEvaluationContext;
import org.boris.expr.engine.DependencyEngine;
import org.boris.expr.engine.EngineProvider;
import org.boris.expr.engine.Range;
import org.springframework.stereotype.Service;

import cn.ztwb.hrd.bean.basic.BDutyBase;
import cn.ztwb.hrd.bean.basic.BIncomeTax;
import cn.ztwb.hrd.bean.basic.BProDepartment;
import cn.ztwb.hrd.bean.basic.BSalaryFixedItem;
import cn.ztwb.hrd.bean.basic.BSalarySet;
import cn.ztwb.hrd.bean.basic.BSalaryTypeMapping;
import cn.ztwb.hrd.bean.basic.BStaff;
import cn.ztwb.hrd.bean.basic.BUser;
import cn.ztwb.hrd.bean.basic.BVar;
import cn.ztwb.hrd.bean.expand.StaffSecurityAmount;
import cn.ztwb.hrd.bean.sub.SSalSalBillDetail;
import cn.ztwb.hrd.bean.sub.SSalaryFormula;
import cn.ztwb.hrd.bean.sub.SSalaryPayFormula;
import cn.ztwb.hrd.bean.sub.SStaffHousefund;
import cn.ztwb.hrd.bean.sub.SStaffHousefundResult;
import cn.ztwb.hrd.bean.sub.SStaffSecurity;
import cn.ztwb.hrd.bean.sub.SStaffSecurityDetail;
import cn.ztwb.hrd.bean.sub.SstaffCalculate;
import cn.ztwb.hrd.bean.temp.TSalExcelData;
import cn.ztwb.hrd.bean.temp.TSalFormulaItem;
import cn.ztwb.hrd.bean.temp.TSalarySubmitRemindItem;
import cn.ztwb.hrd.bean.temp.tsalaryExcelDateTemp;
import cn.ztwb.hrd.bean.work.WDeclareSecurity;
import cn.ztwb.hrd.bean.work.WSalBill;
import cn.ztwb.hrd.bean.work.WSalErrorExcelData;
import cn.ztwb.hrd.bean.work.WSalWelfAmount;
import cn.ztwb.hrd.dao.SalaryBillDao;
import cn.ztwb.hrd.dao.SalaryCalculationRulesDao;
import cn.ztwb.hrd.dao.SalaryImportDao;
import cn.ztwb.hrd.dao.SalaryMangeDao;
import cn.ztwb.hrd.dao.SalarySetDao;
import cn.ztwb.hrd.dao.SalaryTypeMappingDao;
import cn.ztwb.hrd.dao.StaffInfoDao;
import cn.ztwb.hrd.dao.VirtualBillDao;
import cn.ztwb.hrd.dao.WelfAmountDao;
import cn.ztwb.hrd.main.pojo.BSalaryStaff;
import cn.ztwb.hrd.main.pojo.SalaryFormula;
import cn.ztwb.hrd.util.CalculationUtil;
import cn.ztwb.hrd.util.CompareUtil;
import cn.ztwb.hrd.util.Constant;
import cn.ztwb.hrd.util.ExcelUtil;
import cn.ztwb.hrd.util.ExportSalaryUitl;
import cn.ztwb.hrd.util.FormatUtil;
import cn.ztwb.hrd.util.JSONTOOL;
import cn.ztwb.hrd.util.JsonUtil;
import cn.ztwb.hrd.util.PageUtil;

/**
 * @project_name hrd
 * @title SalaryManageService.java
 * @author Administrator
 * @date 2015年8月13日下午4:53:42
 * @version 1.0
 * @since 1.0
 */
@Service
public class SalaryManageService {
	@Resource
	private SalaryTypeMappingDao salaryTypeMappingDao;
	@Resource
	private SalaryBillDao salaryBillDao;
	@Resource
	private SalaryMangeDao salaryMangeDao;
	@Resource
	private WelfAmountDao welfAmountDao;
	@Resource
	private SalarySetDao salarySetDao;
	@Resource
	private StaffInfoDao staffDao;
	@Resource
	CalculationWelfAmountService welfAmountService;
	@Resource
	private ProDepartmentService proDepartmentService;
	@Resource
	private VirtualBillDao virtualBillDao;
	@Resource
	private SalarySetService salarySetService;
	@Resource
	private SalaryImportDao salaryImportdao;
	@Resource
	private SalaryCalculationRulesDao salCalculateDao;
	@Resource
    private InsuredInforSearchService inforSearchService;
	@Resource
	private SecurityService securityService;
	@Resource
	private HousefundService housefundService;
	
	/**
	 * 获得工资项名称列
	 * 
	 * @param salarySetId
	 * @return
	 */

	public List<BSalaryTypeMapping> getSalaryColumnType(int proId, int salarySetId) {

		BSalaryTypeMapping typeMapping = new BSalaryTypeMapping();
		typeMapping.setProId(proId);
		// typeMapping.setSalarySetId(salarySetId);
		List<BSalaryTypeMapping> salTypeMapList = salaryTypeMappingDao.getSalaryMappingByObject(typeMapping,
				salarySetId);
		return salTypeMapList;
	}

	/**
	 * 获得工资临时表的明细数据,分页
	 * 
	 * @param salarySetId
	 * @param salaryDate
	 * @param page
	 * @param rows
	 * @param user 
	 * @param page2
	 * @return
	 */
	public JSONArray getTSalExcelData(WSalBill salBill, String IDCard, String staffName, String rows, String page, BUser user,int actionType) {
		JSONArray jsonList = new JSONArray();
		int size = PageUtil.getPageSize(rows);
		int start = PageUtil.getPageStart(size, page);
		List<TSalExcelData> tsalexcelDataList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salaryBillId", salBill.getSalaryBillId());
		map.put("start", start);
		map.put("size", size);
		map.put("staffName", staffName);
		map.put("IDCard", IDCard);
		map.put("user", user);

		tsalexcelDataList = this.salaryMangeDao.getTSalExcelDataBySalBillId(map,actionType);

		for (TSalExcelData p : tsalexcelDataList) {
			JSONObject jsonObj = JSONObject.fromObject(p.getSalaryExcelData());
			jsonObj.put("staffId", Integer.valueOf(p.getStaffId()));
			jsonObj.put("staffName", p.getStaffName());
			jsonObj.put("IDCard", p.getIDCard());
			jsonObj.put("proName", p.getPro().getProName());
			jsonObj.put("salaryTempId", Integer.valueOf(p.getSalaryTempId()));

			jsonObj.put("salaryPayable", p.getSalaryPayable());
			if(actionType==1){
				jsonObj.put("personEndowment", p.getPersonEndowment());
				jsonObj.put("personMedical", p.getPersonMedical());
				jsonObj.put("personIllnessMedical", p.getPersonIllnessMedical());
				jsonObj.put("personUnemploye", p.getPersonUnemploye());
				
				jsonObj.put("personSecurityAdd", p.getPersonSecurityAdd());
				jsonObj.put("personHousefund", p.getPersonHousefund());
				jsonObj.put("personHousefundAdd", p.getPersonHousefundAdd());
				jsonObj.put("personUnionFee", p.getPersonUnionFee());
				jsonObj.put("personRecord", p.getPersonRecord());
				
				jsonObj.put("deductTaxProject", p.getDeductTaxProject());
				jsonObj.put("personIncomeTax", p.getPersonIncomeTax());
				jsonObj.put("personInsurance", p.getPersonInsurance());
				jsonObj.put("personSecurityAdjust", p.getPersonSecurityAdjust());
				jsonObj.put("personHousefundAdjust", p.getPersonHousefundAdjust());
				
				jsonObj.put("personWelfDiff", p.getPersonWelfDiff());
				jsonObj.put("personHousefundDiff", p.getPersonHousefundDiff());
				jsonObj.put("personSecurityRadix", p.getPersonSecurityRadix());
				jsonObj.put("companySecurityRadix", p.getCompanySecurityRadix());
				jsonObj.put("otherAmount", p.getOtherAmount());
				
				jsonObj.put("personTaxAdjust", p.getPersonTaxAdjust());
				jsonObj.put("personTaxAdjustBal", p.getPersonTaxAdjustBal());
				jsonObj.put("taxableSalary", p.getTaxableSalary());
				jsonObj.put("deductionSum", p.getDeductionSum());
				jsonObj.put("salaryNetIncome", p.getSalaryNetIncome());
				
				jsonObj.put("unitEndowment", p.getUnitEndowment());
				jsonObj.put("unitMedical", p.getUnitMedical());
				jsonObj.put("unitInjury", p.getUnitInjury());
				jsonObj.put("unitBirth", p.getUnitBirth());
				jsonObj.put("unitUnemploye", p.getUnitUnemploye());
				
				jsonObj.put("unitSecurityAdd", p.getUnitSecurityAdd());
				jsonObj.put("unitHousefund", p.getUnitHousefund());
				jsonObj.put("unitHousefundAdd", p.getUnitHousefundAdd());
				jsonObj.put("unitUnionFee", p.getUnitUnionFee());
				jsonObj.put("unitUnionSecurityFee", p.getUnitUnionSecurityFee());
				
				jsonObj.put("unitResidualDeposit", p.getUnitResidualDeposit());
				jsonObj.put("unitResidualSecurityDeposit", p.getUnitResidualSecurityDeposit());
				jsonObj.put("unitRecord", p.getUnitRecord());
				jsonObj.put("laborAgencyFee", p.getLaborAgencyFee());
				jsonObj.put("laborAgencyFeeAdjust", p.getLaborAgencyFeeAdjust());
				
				jsonObj.put("otherFee", p.getOtherFee());
				jsonObj.put("unitInsurance", p.getUnitInsurance());
				jsonObj.put("otherFeeAdjust", p.getOtherFeeAdjust());
				jsonObj.put("unitAgentAmount", p.getUnitAgentAmount());
				jsonObj.put("unitSecurityAdjust", p.getUnitSecurityAdjust());
				
				jsonObj.put("unitHousefundAdjust", p.getUnitHousefundAdjust());
				jsonObj.put("unitWelfDiff", p.getUnitWelfDiff());
				jsonObj.put("unitHousefundDiff", p.getUnitHousefundDiff());
				jsonObj.put("remark", p.getRemark());
				jsonObj.put("payTotal", p.getPayTotal());
				
				jsonObj.put("taxSalAccume", p.getTaxSalAccume());
				jsonObj.put("yearTaxAdjust", p.getYearTaxAdjust());
				jsonObj.put("finalIncomeTax", p.getFinalIncomeTax());
				jsonObj.put("incomeTaxAccume", p.getIncomeTaxAccume());
				jsonObj.put("yearTaxAccume", p.getYearTaxAccume());
				jsonObj.put("buckledIncTaxAccume", p.getBuckledIncTaxAccume());
				jsonObj.put("buckledYearTaxAccume", p.getBuckledYearTaxAccume());
			}
			jsonObj.put("salaryBillId", Integer.valueOf(p.getSalaryBillId()));
			jsonList.add(jsonObj.toString());
		}
		return jsonList;
	}
	
	/**
	 * 获得工资数目
	 * 
	 * @param proId
	 * @param salarySetId
	 * @param salaryDate
	 * @param salaryPayCount
	 * @return
	 */
	public int getTSalExcelDataCount(int proId, int salarySetId, String salaryDate, int importCount,
			String salaryBelongsDate) {
		TSalExcelData p = new TSalExcelData();
		p.setProId(proId);
		p.setSalarySetId(salarySetId);
		p.setSalaryDate(salaryDate);
		// p.setSalaryPayCount(salaryPayCount);
		p.setImportCount(importCount);
		p.setSalaryBelongsDate(salaryBelongsDate);
		return salaryMangeDao.getTSalExcelDataCount(p);
	}

	/**
	 * 获取有异常的Excel数据
	 * 
	 * @param errorData
	 * @param rows
	 * @param page
	 * @return
	 */
	public JSONArray getSalErrorExcelData(WSalErrorExcelData errorData, String rows, String page) {
		List<WSalErrorExcelData> list = salaryMangeDao.getSalErrorExcelData(errorData, rows, page);

		JSONArray jsonList = new JSONArray();
		for (WSalErrorExcelData p : list) {
			JSONObject jsonObj = JSONObject.fromObject(p.getSalaryExcelData());
			jsonObj.put("staffName", p.getStaffName());
			jsonObj.put("IDCard", p.getIDCard());
			jsonObj.put("ExceptionsReason", p.getExceptionsReason());
			jsonObj.put("errorExcelDataId", p.getErrorExcelDataId());
			jsonList.add(jsonObj.toString());
		}
		return jsonList;
	}
	/**
	 * 组成福利明细json
	 * @param salBill
	 * @param staffId
	 * @param proId
	 * @param salExcelData
	 * @return
	 */
	public JSONObject getWelfareJsonData(int staffId, int proId, TSalExcelData salExcelData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffId", staffId);
		map.put("proId", proId);
		WDeclareSecurity decSec = welfAmountDao.getWelfareBySalInfo(map);
		SStaffHousefund staffHouse = welfAmountDao.getStaffHouseBySalInfo(map);
		JSONObject jsonObj = JSONObject.fromObject(salExcelData.getSalaryExcelData());
		if (decSec!=null) {
			jsonObj.put("securityName", decSec.getSecurityName());
			jsonObj.put("secAreaName", decSec.getAreaName());
			jsonObj.put("secStartTime", decSec.getInsuredTime()==null?decSec.getsInjuryInsuredTime():decSec.getInsuredTime());
			jsonObj.put("secInsuredBase", decSec.getInsuredBase());
		}
		if (staffHouse!=null) {
			jsonObj.put("houseName", staffHouse.getHousefundName());
			jsonObj.put("houAreaName", staffHouse.getAreaName());
			jsonObj.put("houStartTime", staffHouse.getStartPayDate());
		}
		jsonObj.put("personHouseFundDate", salExcelData.getPersonHouseFundDate());
		jsonObj.put("unitHouseFundDate", salExcelData.getUnitHouseFundDate());
		
		jsonObj.put("staffId", Integer.valueOf(salExcelData.getStaffId()));
		jsonObj.put("staffName", salExcelData.getStaffName());
		jsonObj.put("IDCard", salExcelData.getIDCard());
		jsonObj.put("proName", salExcelData.getPro().getProName());
		jsonObj.put("salaryTempId", Integer.valueOf(salExcelData.getSalaryTempId()));

		jsonObj.put("salaryPayable", salExcelData.getSalaryPayable());
		jsonObj.put("personEndowment", salExcelData.getPersonEndowment());
		jsonObj.put("personMedical", salExcelData.getPersonMedical());
		jsonObj.put("personIllnessMedical", salExcelData.getPersonIllnessMedical());
		jsonObj.put("personUnemploye", salExcelData.getPersonUnemploye());

		jsonObj.put("personSecurityAdd", salExcelData.getPersonSecurityAdd());
		jsonObj.put("personHousefund", salExcelData.getPersonHousefund());
		jsonObj.put("personHousefundAdd", salExcelData.getPersonHousefundAdd());
		jsonObj.put("personUnionFee", salExcelData.getPersonUnionFee());
		jsonObj.put("personRecord", salExcelData.getPersonRecord());

		jsonObj.put("deductTaxProject", salExcelData.getDeductTaxProject());
		jsonObj.put("personIncomeTax", salExcelData.getPersonIncomeTax());
		jsonObj.put("personInsurance", salExcelData.getPersonInsurance());
		jsonObj.put("personSecurityAdjust", salExcelData.getPersonSecurityAdjust());
		jsonObj.put("personHousefundAdjust", salExcelData.getPersonHousefundAdjust());
		
		jsonObj.put("personWelfDiff", salExcelData.getPersonWelfDiff());
		jsonObj.put("personHousefundDiff", salExcelData.getPersonHousefundDiff());
		jsonObj.put("personSecurityRadix", salExcelData.getPersonSecurityRadix());
		jsonObj.put("companySecurityRadix", salExcelData.getCompanySecurityRadix());
		jsonObj.put("otherAmount", salExcelData.getOtherAmount());
		
		jsonObj.put("personTaxAdjust", salExcelData.getPersonTaxAdjust());
		jsonObj.put("personTaxAdjustBal", salExcelData.getPersonTaxAdjustBal());
		jsonObj.put("taxableSalary", salExcelData.getTaxableSalary());
		jsonObj.put("deductionSum", salExcelData.getDeductionSum());
		jsonObj.put("salaryNetIncome", salExcelData.getSalaryNetIncome());

		jsonObj.put("unitEndowment", salExcelData.getUnitEndowment());
		jsonObj.put("unitMedical", salExcelData.getUnitMedical());
		jsonObj.put("unitInjury", salExcelData.getUnitInjury());
		jsonObj.put("unitBirth", salExcelData.getUnitBirth());
		jsonObj.put("unitUnemploye", salExcelData.getUnitUnemploye());

		jsonObj.put("unitSecurityAdd", salExcelData.getUnitSecurityAdd());
		jsonObj.put("unitHousefund", salExcelData.getUnitHousefund());
		jsonObj.put("unitHousefundAdd", salExcelData.getUnitHousefundAdd());
		jsonObj.put("unitUnionFee", salExcelData.getUnitUnionFee());
		jsonObj.put("unitUnionSecurityFee", salExcelData.getUnitUnionSecurityFee());

		jsonObj.put("unitResidualDeposit", salExcelData.getUnitResidualDeposit());
		jsonObj.put("unitResidualSecurityDeposit", salExcelData.getUnitResidualSecurityDeposit());
		jsonObj.put("unitRecord", salExcelData.getUnitRecord());
		jsonObj.put("laborAgencyFee", salExcelData.getLaborAgencyFee());
		jsonObj.put("laborAgencyFeeAdjust", salExcelData.getLaborAgencyFeeAdjust());

		jsonObj.put("otherFee", salExcelData.getOtherFee());
		jsonObj.put("unitInsurance", salExcelData.getUnitInsurance());
		jsonObj.put("otherFeeAdjust", salExcelData.getOtherFeeAdjust());
		jsonObj.put("unitAgentAmount", salExcelData.getUnitAgentAmount());
		jsonObj.put("unitSecurityAdjust", salExcelData.getUnitSecurityAdjust());

		jsonObj.put("unitHousefundAdjust", salExcelData.getUnitHousefundAdjust());
		jsonObj.put("unitWelfDiff", salExcelData.getUnitWelfDiff());
		jsonObj.put("unitHousefundDiff", salExcelData.getUnitHousefundDiff());
		jsonObj.put("remark", salExcelData.getRemark());
		jsonObj.put("payTotal", salExcelData.getPayTotal());
		
		jsonObj.put("taxSalAccume", salExcelData.getTaxSalAccume());
		jsonObj.put("yearTaxAdjust", salExcelData.getYearTaxAdjust());
		jsonObj.put("finalIncomeTax", salExcelData.getFinalIncomeTax());
		jsonObj.put("incomeTaxAccume", salExcelData.getIncomeTaxAccume());
		jsonObj.put("yearTaxAccume", salExcelData.getYearTaxAccume());
		jsonObj.put("buckledIncTaxAccume", salExcelData.getBuckledIncTaxAccume());
		jsonObj.put("buckledYearTaxAccume", salExcelData.getBuckledYearTaxAccume());

		jsonObj.put("salaryBillId", Integer.valueOf(salExcelData.getSalaryBillId()));

		return jsonObj;
	}
	/**
	 * 获取有异常Excel数据的条数
	 * 
	 * @param errorData
	 * @return
	 */
	public int getSalErrorExcelDataCount(WSalErrorExcelData errorData) {
		return salaryMangeDao.getSalErrorExcelDataCount(errorData);
	}

	/***
	 * 获取当前项目部下的社保信息
	 **/
	public List<SstaffCalculate> getStaffSecurityList(int proId, String staffName,
			String staffIDCard, String SecurityBelongsDate, String securityId, String rows, String page) {
		/* 根据客户名称获取客户ID */
		Map<String, Object> map = new HashMap<String, Object>();
		if (!"".equals(page)&&!"".equals(rows)) {
			int size = PageUtil.getPageSize(rows);
			int start = PageUtil.getPageStart(size, page);
			map.put("start", start);
			map.put("size", size);
		}
		map.put("proId", proId);
		map.put("staffName", staffName);
		map.put("staffIDCard", staffIDCard);
		map.put("SecurityBelongsDate", SecurityBelongsDate);
		map.put("securityId", securityId);
		return salaryMangeDao.getStaffSecurityList(map);
	}

	/***
	 * 获取当前项目部下的社保信息
	 **/
	public List<SstaffCalculate> exportStaffSecurityList(int proId, String CustomerUnit, String staffName,
			String staffIDCard, String SecurityBelongsDate, String securityId, String rows, String page) {
		int size = PageUtil.getPageSize(rows);
		int start = PageUtil.getPageStart(size, page);
		return salaryMangeDao.exportStaffSecurityList(proId, CustomerUnit, staffName, staffIDCard, SecurityBelongsDate,
				securityId, start, size);
	}

	public int getStaffSecurityCount(int proId, String staffName, String staffIDCard,
			String SecurityBelongsDate, String securityId) {
		return salaryMangeDao.getStaffSecuritycount(proId, staffName, staffIDCard, SecurityBelongsDate,
				securityId);
	}

	// 当前项目部下已参保人员
	public List<WDeclareSecurity> getSssecList(String proId, String staffName, String staffIDCard, String securityId,String securityDate) {
		return salaryMangeDao.getSssecList(proId,  staffName, staffIDCard, securityId,securityDate);
	}

	/**
	 * 是否算过社保
	 **/
	public int getSecurityBeen(int proId, String SecurityBelongsDate, int staffId) {
		return welfAmountDao.getSalWelfCount(proId, SecurityBelongsDate, staffId);
	}

	/**
	 * 获取一个员工的所有社保账号
	 * 
	 * @param salaryStaff
	 * @param staffSecurityList
	 * @return
	 */
	public List<SStaffSecurity> findSameSecurity(int staffId, List<SStaffSecurity> staffSecurityList) {
		List<SStaffSecurity> findSecurityList = new ArrayList<SStaffSecurity>();
		for (SStaffSecurity p : staffSecurityList) {
			if (p.getStaffId() == staffId) {
				findSecurityList.add(p);
			}
		}
		return findSecurityList;
	}

	/****
	 * 社保计算
	 * @param securityId 
	 * @throws ParseException 
	 */
	public void addSstaffCalculate(String proId, String SecurityBelongsDate,
			List<WDeclareSecurity> staffSecuList, String staffName, String securityId, String staffIDCard,
			 BUser user) throws ParseException {

		List<SstaffCalculate> stList = getStaffSecurityList(Integer.parseInt(proId), staffName, staffIDCard,
				SecurityBelongsDate, securityId, "", "");
		List<SstaffCalculate> tempCalculateList = new ArrayList<SstaffCalculate>();
		BProDepartment bp = proDepartmentService.findProDepartmentById(Integer.parseInt(proId));
		// 计算详情
		for (WDeclareSecurity ss : staffSecuList) {
			if (CalculationUtil.DateToInt(ss.getApplyTime(), SecurityBelongsDate)<=0) {
				continue;
			}
			SstaffCalculate staffCalculate = new SstaffCalculate();
			if(CompareUtil.compare_state(ss.getState())>-1){
				staffCalculate = salaryMangeDao.findTaSaralySecurityTotal(ss.getStaffId(),ss.getApplyTime(),ss.getPro().getProId(),1);//小于申报月的社保合计		
			}
			staffCalculate.setProId(Integer.parseInt(proId));
			staffCalculate.setSecurityBelongsDate(SecurityBelongsDate);
			staffCalculate.setCustomerUnit(bp.getCustomerUnitId());
			staffCalculate.setSecuritySettlementDate(SecurityBelongsDate);
			staffCalculate.setCreateUser(user.getTrueName());
			List<SStaffSecurityDetail> staffSecurDetailList = staffDao.getStaffSecurityDetailListBymap(ss.getStaffSecurityId(), ss.getState());
			staffCalculate.setStaffId(ss.getStaffId());
			staffCalculate.setStaffSecurityId(ss.getStaffSecurityId());
			staffCalculate.setStaffIDCard(ss.getStaffSIdCard());
			staffCalculate.setStaffName(ss.getStaffSName());
			staffCalculate.setSecurityName(ss.getSecurityName());
			staffCalculate.setSecurityId(ss.getSecurityId());
			SecurityDetailCalculate(staffSecurDetailList,ss.getIsSupplement(),ss.getApplyTime(),SecurityBelongsDate,ss.getDataType(), staffCalculate);
			tempCalculateList.add(staffCalculate);
		}
		updateAndAddHousefundResult(stList,tempCalculateList);
	}
	
	/**
	 * 社保计算
	 * @param housefList
	 * @param houseResList
	 * @date 2015年10月21日下午5:57:46
	 * @author yingkaibing
	 */
	public void updateAndAddHousefundResult(List<SstaffCalculate>  housefList, List<SstaffCalculate> houseResList) {
		List<SstaffCalculate> upHouseList = new ArrayList<SstaffCalculate>();
		List<SstaffCalculate> addHouseList = new ArrayList<SstaffCalculate>();
		boolean flag = true;
		for (int i = 0; i < houseResList.size(); i++) {
			for (int j = 0; j < housefList.size(); j++) {
				if (houseResList.get(i).getStaffSecurityId()==housefList.get(j).getStaffSecurityId()&&houseResList.get(i).getSecurityBelongsDate().equals(housefList.get(j).getSecurityBelongsDate())) {
					upHouseList.add(houseResList.get(i));
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (flag) {
				addHouseList.add(houseResList.get(i));
			}
		}
		if (upHouseList.size()>0) {
			salaryMangeDao.updateSstaffResult(upHouseList);
		}
		for (SstaffCalculate sstaffCalculate : addHouseList) {
			salaryMangeDao.addSstaffCalculate(sstaffCalculate);
		}
	}
	


	/**
	 * 社保计算
	 * @param ss
	 * @return
	 * @date 2015年10月21日下午4:38:10
	 * @author yingkaibing
	 * @param staffCalculate 
	 * @throws ParseException 
	 */
	public SstaffCalculate SecurityDetailCalculate(List<SStaffSecurityDetail> staffSecurDetailList,int isSupplement,
			                                       String applyTime,String securityBelongsDate,int dataType, SstaffCalculate staffCalculate) throws ParseException {
		StaffSecurityAmount sSecurityAmount = inforSearchService.getSecurityTotal(staffSecurDetailList, 0, dataType, 2);	// 参保后，每个月的正常社保
		staffCalculate.setUnitEndowment(sSecurityAmount.geteUnitAmount());
		staffCalculate.setPersonEndowment(sSecurityAmount.getePersonAmount());
		staffCalculate.setUnitMedical(sSecurityAmount.getmUnitAmount());
		staffCalculate.setPersonMedical(sSecurityAmount.getmPersonAmount());
		staffCalculate.setUnitUnemploye(sSecurityAmount.getUmUnitAmount());
		staffCalculate.setPersonUnemploye(sSecurityAmount.getUmPersonAmount());
		staffCalculate.setUnitInjury(sSecurityAmount.getInUnitAmount());
		staffCalculate.setUnitBirth(sSecurityAmount.getbUnitAmount());
		staffCalculate.setUnitResidualDeposit(sSecurityAmount.getRdUnitAmount());
		staffCalculate.setUnitUnionFee(sSecurityAmount.getUfUnitAmount());
		//1.判断社保所属月等于参保时间，包括补缴
		if(CalculationUtil.formatterTimeStr(applyTime)==CalculationUtil.formatterTimeStr(securityBelongsDate)){
			for (SStaffSecurityDetail sSDetail : staffSecurDetailList) {
				 Map<String,Double> resultMap =  inforSearchService.getSuppAmount(sSDetail);
				switch (sSDetail.getTypeId()) {
				case Constant.pension:
					staffCalculate.setUnitSecurityAdd(resultMap.get("unitSuppAmount"));
					staffCalculate.setPersonSecurityAdd(resultMap.get("personSuppAmount"));
					staffCalculate.setUnitSecurityAddMon(resultMap.get("unitMonthAmount"));
					staffCalculate.setPersonSecurityAddMon(resultMap.get("personMonthAmount"));
					staffCalculate.setUnitSecurityAddYear(resultMap.get("unitYearAmount"));
					staffCalculate.setPersonSecurityAddYear(resultMap.get("personYearAmount"));
					break;
				case Constant.medical:
					staffCalculate.setUnitOfPayment(resultMap.get("unitSuppAmount"));
					staffCalculate.setPersonalPayment(resultMap.get("personSuppAmount"));
					staffCalculate.setUnitOfPaymentMon(resultMap.get("unitMonthAmount"));
					staffCalculate.setPersonalPaymentMon(resultMap.get("personMonthAmount"));
					staffCalculate.setUnitOfPaymentYear(resultMap.get("unitYearAmount"));
					staffCalculate.setPersonalPaymentYear(resultMap.get("personYearAmount"));
					break;
				case Constant.injury:
					staffCalculate.setInductrialInjuryPaymentUnit(resultMap.get("unitSuppAmount"));
					staffCalculate.setInductrialInjuryPaymentUnitMon(resultMap.get("unitMonthAmount"));
					staffCalculate.setInductrialInjuryPaymentUnitYear(resultMap.get("unitYearAmount"));
					break;
				case Constant.birth:
					staffCalculate.setInductrialInjuryMaternityUnit(resultMap.get("unitSuppAmount"));
					staffCalculate.setInductrialInjuryMaternityUnitMon(resultMap.get("unitMonthAmount"));
					staffCalculate.setInductrialInjuryMaternityUnitYear(resultMap.get("unitYearAmount"));
					break;
				case Constant.unemployment:
					staffCalculate.setPaymentOfUnemployment(resultMap.get("unitSuppAmount"));
					staffCalculate.setPaymentOfUnemployedPeople(resultMap.get("personSuppAmount"));
					staffCalculate.setPaymentOfUnemploymentMon(resultMap.get("unitMonthAmount"));
					staffCalculate.setPaymentOfUnemployedPeopleMon(resultMap.get("personMonthAmount"));
					staffCalculate.setPaymentOfUnemploymentYear(resultMap.get("unitYearAmount"));
					staffCalculate.setPaymentOfUnemployedPeopleYear(resultMap.get("personYearAmount"));
					break;
				case Constant.disability_insurance:
					staffCalculate.setPaymentOfResidualUnit(resultMap.get("unitSuppAmount"));
					staffCalculate.setPaymentOfResidualUnitMon(resultMap.get("unitMonthAmount"));
					staffCalculate.setPaymentOfResidualUnitYear(resultMap.get("unitYearAmount"));
					break;
				case Constant.labour_union_fee:
					staffCalculate.setPaymentOfUnionDues(resultMap.get("unitSuppAmount"));
					staffCalculate.setPaymentOfUnionDuesMon(resultMap.get("unitMonthAmount"));
					staffCalculate.setPaymentOfUnionDuesYear(resultMap.get("unitYearAmount"));
					break;
				default:
					break;
				}
			}
		}
		return staffCalculate;

	}
	

	/*** 社保列表导出 ***/
	public String getExcelFileName(List<SstaffCalculate> staffSecurityList, String titleName, String pathName)
			throws Exception {
		// excel表头名
		List<String> titleList = SstaffCalculate.getSecurityTitle();
		List<List<String>> dataList = new ArrayList<List<String>>();
		for (int i = 0; i < staffSecurityList.size(); i++) {
			List<String> data = new ArrayList<String>();
			data.add(staffSecurityList.get(i).getPro().getProName());
			data.add(staffSecurityList.get(i).getStaffIDCard());
			data.add(staffSecurityList.get(i).getStaffName());
			data.add(staffSecurityList.get(i).getSecurityName());
			data.add(String.valueOf(staffSecurityList.get(i).getUnitEndowment()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonEndowment()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitMedical()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonMedical()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitUnemploye()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonUnemploye()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitInjury()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitBirth()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitResidualDeposit()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitUnionFee()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitSecurityAdd()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonSecurityAdd()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitOfPayment()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonalPayment()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfUnemployment()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfUnemployedPeople()));
			data.add(String.valueOf(staffSecurityList.get(i).getInductrialInjuryPaymentUnit()));
			data.add(String.valueOf(staffSecurityList.get(i).getInductrialInjuryMaternityUnit()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfResidualUnit()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfUnionDues()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitSecurityAddMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonSecurityAddMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitOfPaymentMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonalPaymentMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfUnemploymentMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfUnemployedPeopleMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getInductrialInjuryPaymentUnitMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getInductrialInjuryMaternityUnitMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfResidualUnitMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfUnionDuesMon()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitSecurityAddYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonSecurityAddYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitOfPaymentYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonalPaymentYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfUnemploymentYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfUnemployedPeopleYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getInductrialInjuryPaymentUnitYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getInductrialInjuryMaternityUnitYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfResidualUnitYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getPaymentOfUnionDuesYear()));
			data.add(String.valueOf(staffSecurityList.get(i).getUnitSTotalAdjust()));
			data.add(String.valueOf(staffSecurityList.get(i).getPersonSTotalAdjust()));
			dataList.add(data);
		}
		return ExcelUtil.exaple(dataList, titleList, titleName, pathName);
	}

    /**
     * 异常数据列表导出
     * @param fileName
     * @param columnList
     * @param salFormula
     * @param errorList
     * @param pathName
     * @param dataType
     * @param salarySetId
     * @param proId
     * @return
     * @throws Exception
     * @date 2015年12月1日下午2:38:17
     * @author yingkaibing 
     */
	@SuppressWarnings("rawtypes")
	public String getExcelFileError(String fileName, List<BSalaryTypeMapping> columnList,
			List<SSalaryFormula> salFormula, List<WSalErrorExcelData> errorList, String pathName, int dataType,
			int salarySetId, String proName) throws Exception {
		List<String> titleList = ExportSalaryUitl.getSalaryCalculationTitle(columnList, salFormula,1);
		List<List<String>> dataList = new ArrayList<List<String>>();
		for (WSalErrorExcelData salExcelData : errorList) {
			List<String> data = new ArrayList<String>();
			data.add(salExcelData.getStaffName());
			data.add(salExcelData.getIDCard());
			data.add(proName);
			JSONObject salaryExcelJson = JSONObject.fromObject(salExcelData.getSalaryExcelData());
			for (BSalaryTypeMapping salaryTypeMapping : columnList) {
				if (salaryTypeMapping.getSalaryType().getTypeId() != 2 && salaryTypeMapping.getSalaryType().getTypeId() != 1) {
					for (Iterator iter = salaryExcelJson.keys(); iter.hasNext();) { 
						String key = (String)iter.next();  
						if(salaryTypeMapping.getTypeMappingId()==Integer.parseInt(key.replace("p", ""))){
							data.add(String.valueOf(salaryExcelJson.get(key)));
						}
				  }
				}
			}
			data.add(String.valueOf(salExcelData.getSalaryPaySum()));
			dataList.add(data);
		}
		return ExcelUtil.exaple(dataList, titleList, fileName, pathName);
	}

	/**
	 * 工资名称
	 * @param proId
	 * @param salaryDate
	 * @param state
	 * @return
	 * @date 2016年2月5日下午3:37:36
	 * @author yingkaibing
	 */
	public List<WSalBill> findsalaryNameSelect(String proId, String salaryDate,String state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("proId", proId);
		map.put("salaryDate", salaryDate);
		String[] states = null;
		if(null!=state &&!"".equals(state)){
			if(state.equals("1")){
				states = new String[]{"1"};
			}else if(state.equals("2")){
				states = new String[]{"2"};
			}else if(state.equals("3")){
				states = new String[]{"0","-2","-10"};
			}
			map.put("states", states);
		}
		return salaryMangeDao.findsalaryNameList(map);
	}

	public List<TSalExcelData> findCalculationSalary(int proId, String salaryBillId, String salaryDate,
			String staffName, String IDCard) {
		return salaryMangeDao.findCalculationSalary(proId, salaryBillId, salaryDate, staffName, IDCard);
	}

	/***
	 * 工资计算 2015-09-02 15:52
	 ***/
	public void updateCalculation(int proId, int salarySetId, String salaryDate, String salaryBelongsDate,
			int salaryPayCount, String personSecurityDate, String personHouseFundDate, String unitSecurityDate,
			String unitHouseFundDate, List<TSalExcelData> salExcelDataList, BUser user) {

		// 根据ID得到年终奖类的ID
		BSalarySet bsalary = salarySetDao.getsalaryByid(salarySetId);
		/*
		 * 是否合并计税 0表示合并，1表示不合并 *
		 */
		int Consolidatedtax = bsalary.getConsolidatedtax();// 判断计税方式
		String ID = bsalary.getSalarySetType().getId();// 判断年终奖类费税结算类型
		// 先更新Excel表中调整项
		salaryMangeDao.updateSalChangeData(salExcelDataList);

		// 工资固定项Map,存储关系*
		Map<String, BSalaryFixedItem> fixedItemMap = new HashMap<String, BSalaryFixedItem>();
		// 未转换公式项Map,存储关系
		Map<Integer, SSalaryFormula> formulaMap = new HashMap<Integer, SSalaryFormula>();
		// 转换后的公式Map
		Map<Integer, String> formulaFormatterMap = new HashMap<Integer, String>();

		// 获取固定项
		List<BSalaryFixedItem> salaryFixedItemList = salarySetDao.getFixedItemList();
		for (BSalaryFixedItem p : salaryFixedItemList) {
			fixedItemMap.put(String.valueOf("p" + p.getFixedItemId()), p); // 根据Id获得对象*
			fixedItemMap.put(p.getFixedItemName(), p); // 根据工资固定项获得对象*
		}

		// 获得24项公式,key:salaryFormulaName
		//
		List<SSalaryFormula> salaryFormulaList = salarySetDao.getSalaryFormulaListBySalaySetId(salarySetId);
		for (SSalaryFormula p : salaryFormulaList) {
			formulaMap.put(p.getFixedItem().getFixedItemId(), p);
		}

		// 解析24项公式(递归分解)
		for (SSalaryFormula p1 : salaryFormulaList) {
			String relation = p1.getSalaryFormulaRelation();
			if ("".equals(relation) || null == relation) {
				formulaFormatterMap.put(p1.getFixedItem().getFixedItemId(), "");
				continue;
			}

			List<String> formulaIdList = FormatUtil.FormatSalaryFormula(relation); // 正则表达式提取字符串中以p开头的字符串
			relation = getSalaryFormulaRelation(relation, formulaIdList, fixedItemMap, formulaMap);

			// 将解析好的公式放入Map中
			formulaFormatterMap.put(p1.getFixedItem().getFixedItemId(), relation);
		}

		// 两个静态变量赋值,为24个公式提供依据
		SalaryFormula.fixedItemMap = fixedItemMap;
		SalaryFormula.formulaFormatterMap = formulaFormatterMap;
		/**
		 * 判断是否合并计税_yzf
		 ***/
		if (Consolidatedtax == 0) {
			// 获取历史计税工资之和以及历史税收__合并计税
			salaryMangeDao.updateupdateHistoryDutyMarge(proId, salarySetId, salaryDate, salaryPayCount);
		} else if (Consolidatedtax == 1) {
			// 获取历史计税工资之和以及历史税收__不合并计税
			salaryMangeDao.updateHistoryDuty(proId, salarySetId, salaryDate, salaryPayCount);
		}

		// 计算应发工资
		List<TSalExcelData> tsalExcelDataOld = salaryPayFormulaCalculate(proId, salarySetId, salaryDate,
				salaryBelongsDate, salaryPayCount);
		List<Integer> salaryTempIdList = new ArrayList<Integer>(); // 用来判断是否有社保数据的
		// 获得项目部下社保账号
		List<SStaffSecurity> staffSecurityList = staffDao.getStaffSecurityListByProId(proId);

		// 获取项目部下的公积金信息
		List<SStaffHousefund> staffHousefundList = staffDao.getStaffHouseFundByProId(proId);

		// 查询出计税标准
		BIncomeTax tax = new BIncomeTax();
		List<BIncomeTax> incomeTaxs = salaryMangeDao.getIncomeTax(tax);

		// 遍历应发工资计算好的数据，计算每个人社保+公积金
		TSalFormulaItem salForItem = null;

		if (ID.equals("50302")) {
			/**
			 * 如果是年终奖类_yzf
			 */
			BDutyBase dutyBase = new BDutyBase();
			for (int i = 0; i < tsalExcelDataOld.size(); i++) {

				TSalExcelData p = tsalExcelDataOld.get(i);
				BStaff staff = new BStaff();
				staff.setStaffName(p.getStaffName());
				staff.setIDCard(p.getIDCard());

				BSalaryStaff bs = new BSalaryStaff();
				bs.setStaff(staff);
				bs.settSalExcelData(p);
				bs.staffSalaryCalculate(salForItem);
				tsalExcelDataOld.set(i, bs.getTsalExcelDataOfCal(incomeTaxs, dutyBase, ID));
			}

		} else {
			for (int i = 0; i < tsalExcelDataOld.size(); i++) {
				// 查询个税基数
				BDutyBase sqdutyBase = new BDutyBase();
				BDutyBase dutyBase = salaryMangeDao.getDutyBase(sqdutyBase);
				TSalExcelData p = tsalExcelDataOld.get(i);

				BStaff staff = new BStaff();
				staff.setStaffName(p.getStaffName());
				staff.setIDCard(p.getIDCard());

				salaryTempIdList.add(p.getSalaryTempId());

				// 将同一个人的社保账号放入对应的BSalaryStaff对象中(公积金同理)
				// 判断当前员工是否已经计算过社保,并且工资计算不是第一次
				int isCal = welfAmountDao.getSalWelfCount(proId, salaryDate, p.getStaffId());
				if (isCal == 0 || salaryPayCount == 1) {
					List<SStaffSecurity> getSecurityList = findSameSecurity(p.getStaffId(), staffSecurityList);

					SStaffHousefund staffHousefund = findSameHouseFund(p.getStaffId(), staffHousefundList);

					// 计算员社保、公积金
					salForItem = salWelfCalculate(salaryDate, salaryBelongsDate, getSecurityList, staffHousefund,
							p.getStaffId(), personSecurityDate, personHouseFundDate, unitSecurityDate,
							unitHouseFundDate, proId, p.getSalaryTempId());
				} else { //
					// 计算过社保，那么本次社保公积金总和就是上次发放工资的社保公积金调整项 salForItem =
					salaryMangeDao.getLastSalaryWelfAdjust(proId, p.getStaffId(), salaryDate, salaryPayCount);
				}

				//  计算工资类别
				salForItem = getSalaryTypeAmount(salForItem, p, user);
				BSalaryStaff salaryStaff = new BSalaryStaff();
				salaryStaff.setStaff(staff);
				salaryStaff.settSalExcelData(p);

				salaryStaff.staffSalaryCalculate(salForItem);
				// 计算完社保公积金以及24项固定公示后进行数据更新(将计算的结果重新给TSalExcelData赋值)
				tsalExcelDataOld.set(i, salaryStaff.getTsalExcelDataOfCal(incomeTaxs, dutyBase, ID));
			}
		}

		salaryMangeDao.updateCalculation(tsalExcelDataOld);

	}

	/**
	 * 计算应发工资
	 * 
	 * @param proId
	 * @param salarySetId
	 * @param salaryDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<TSalExcelData> salaryPayFormulaCalculate(int proId, int salarySetId, String salaryDate,
			String salaryBelongsDate, int salaryPayCount) {

		// 获得应发工资公式
		String salaryPayFormulaStr = "";
		String dutyPayFormulaStr = "";
		SSalaryPayFormula salaryPayFormula = new SSalaryPayFormula();
		salaryPayFormula.setProId(proId);
		salaryPayFormula.setSalarySetId(salarySetId);
		salaryPayFormula.setSalaryBelongsDate(salaryBelongsDate);
		salaryPayFormula.setSalaryDate(salaryDate);
		salaryPayFormula.setSalaryPayCount(salaryPayCount);
		salaryPayFormula = salarySetDao.findSalaryPayFormula(salaryPayFormula);

		// 如果没有设置公式则进行提示
		if (salaryPayFormula == null) {
			return null;
		}

		salaryPayFormulaStr = salaryPayFormula.getSalaryPayFormula();
		dutyPayFormulaStr = salaryPayFormula.getDutyPayFormula();
		// 获得加减项的映射关系,为接下来工资数据JSON解析类型判断提供依据
		Map<Integer, Integer> calMap = new HashMap<Integer, Integer>();
		BSalaryTypeMapping typeMapping = new BSalaryTypeMapping();
		typeMapping.setProId(proId);
		// typeMapping.setSalarySetId(salarySetId);
		List<BSalaryTypeMapping> salTypeMapList = salaryTypeMappingDao.getSalaryMappingByObject(typeMapping,
				salarySetId);
		for (BSalaryTypeMapping p : salTypeMapList) {
			calMap.put(p.getTypeMappingId(), p.getCalculateType()); // 存储了excelItemName和calculateType的关系
		}

		// 重新获得工资数据
		TSalExcelData tsalexcelData = new TSalExcelData();
		tsalexcelData.setProId(proId);
		tsalexcelData.setSalarySetId(salarySetId);
		tsalexcelData.setSalaryDate(salaryDate);
		tsalexcelData.setSalaryPayCount(salaryPayCount);
		List<TSalExcelData> tsalexcelDataList = salaryMangeDao.getTSalExcelData(tsalexcelData);

		// 解析JSON,并且计算应发工资
		String eval_salaryPayFormul = salaryPayFormulaStr.replaceAll("\\[", "").replaceAll("\\]", "");
		// 导入工资的计税工资
		String eval_dutyPayFormual = dutyPayFormulaStr.replaceAll("\\[", "").replaceAll("\\]", "");

		// JSONArray calJsonArr = new JSONArray();
		// for(TSalExcelData p:tsalexcelDataList){
		for (int i = 0; i < tsalexcelDataList.size(); i++) {
			TSalExcelData p = tsalexcelDataList.get(i);
			JSONObject jsonObj = JSONObject.fromObject(p.getSalaryExcelData());
			Iterator<String> iter = jsonObj.keys();
			try {
				DependencyEngine e = new DependencyEngine(new EngineProvider() {
					@Override
					public void valueChanged(Range arg0, Expr arg1) {
					}

					@Override
					public void validate(ExprVariable arg0) throws ExprException {
					}

					@Override
					public void inputChanged(Range arg0, Expr arg1) {
					}

					@Override
					public Expr evaluateVariable(IEvaluationContext arg0, ExprVariable arg1) throws ExprException {
						return null;
					}

					@Override
					public Expr evaluateFunction(IEvaluationContext arg0, ExprFunction arg1) throws ExprException {
						return null;
					}
				});

				while (iter.hasNext()) {
					String key = iter.next().toString();
					String value = (String) jsonObj.get(key);
					int calculateType = calMap.get(Integer.parseInt(key.replaceAll("p", "")));

					if (1 == calculateType) {
						Double fval = !"".equals(value) ? Double.parseDouble(value) : 0;
						e.set(key, fval + "");
					} else if (-1 == calculateType) {
						Double fval = !"".equals(value) ? Double.parseDouble(value) : 0;
						e.set(key, fval * (-1) + "");
					}
				}
				// 应发工资
				e.set("result1", "=" + eval_salaryPayFormul);
				// 导入工资的计税工资
				e.set("result2", "=" + eval_dutyPayFormual);
				// // 导入工资的不计税工资
				// e.set("result3", "=" + eval_no);
				// Object result = e.getValue(Range.valueOf("result1"));
				p.setSalaryPayable(FormatUtil.FormatStringToDouble(e.getValue(Range.valueOf("result1")).toString()));
				// 把查询出来的历史计税工资临时保存到 dutyPayableHistory
				p.setDutyPayableHistory(p.getDutyPayable());
				p.setDutyPayable(FormatUtil.FormatStringToDouble(e.getValue(Range.valueOf("result2")).toString()));
				tsalexcelDataList.set(i, p);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return tsalexcelDataList;
	}

	/**
	 * 计算工资类别的金额
	 * 
	 * @param salForItem
	 * @param salData
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private TSalFormulaItem getSalaryTypeAmount(TSalFormulaItem salForItem, TSalExcelData salData, BUser user) {
		// 获取符合条件的数据
		List<TSalExcelData> salDataAll = salaryMangeDao.getSalExcelForSalaryType(salData);
		// 查询出工资类别的映射关系
		List<BSalaryTypeMapping> salTypeMapping = salaryTypeMappingDao.getSalaryMapByProId(salData.getProId());

		for (TSalExcelData p : salDataAll) {

			JSONObject jsonObj = JSONObject.fromObject(p.getSalaryExcelData());
			// 解析json
			Map<String, String> map = JsonUtil.toMap(jsonObj.toString());
			for (BSalaryTypeMapping st : salTypeMapping) {
				if (st.getItemId() != 0 && st.getTypeId() != 1 && st.getTypeId() != 2) {
					double f = FormatUtil.FormatStringToDouble(map.get("p" + st.getTypeMappingId()));
					salForItem.setValue(st.getTypeId(), f);
				}
			}
		}
		return salForItem;
	}

	/**
	 * 递归获得公式内容，直到没有p10开头的公式(p10开头表示可能公式套公式)
	 * 
	 * @param salaryFormulaRelation
	 * @param formulaIdList
	 * @param fixedItemMap
	 * @param formulaMap
	 * @return
	 */
	private String getSalaryFormulaRelation(String salaryFormulaRelation, List<String> formulaIdList,
			Map<String, BSalaryFixedItem> fixedItemMap, Map<Integer, SSalaryFormula> formulaMap) {

		// 如果Id!=1001并且parentId=10 代表可能存在可分解的公式
		for (String p : formulaIdList) {
			BSalaryFixedItem item = fixedItemMap.get(p);
			if (item.getFixedItemId() != 1001 && item.getParentId() == 10) {
				SSalaryFormula salaryFormula = formulaMap.get(item.getFixedItemId());
				salaryFormulaRelation = salaryFormulaRelation.replace(p,
						"(" + salaryFormula.getSalaryFormulaRelation() + ")");
			}
		}

		// 判断是否存在可以分解的公式项
		List<String> listOne = FormatUtil.FormatSalaryFormula(salaryFormulaRelation);
		List<String> listTemp = new ArrayList<String>();
		for (String p : listOne) {
			BSalaryFixedItem item = fixedItemMap.get(p);
			if (item.getFixedItemId() != 1001 && item.getParentId() == 10) {
				listTemp.add(p);
			}
		}

		// 如果没有可以分解的项则返回表达式
		if (listTemp.size() == 0) {
			return salaryFormulaRelation;
		} else {
			return getSalaryFormulaRelation(salaryFormulaRelation, listOne, fixedItemMap, formulaMap);
		}
	}

	/**
	 * TODO 工资计算导出
	 * 
	 * @param columnList
	 * @param salFormula
	 * @param salaryDataList
	 * @param realPath
	 * @return
	 * @date 2015年9月6日上午9:19:54
	 * @author kuro
	 * @throws Exception
	 */
	public String exportSalaryList(String fileName, List<BSalaryTypeMapping> columnList,
			List<SSalaryFormula> salFormula, List<TSalExcelData> salaryDataList, String realPath) throws Exception {
		List<String> titleList = ExportSalaryUitl.getSalaryCalculationTitle(columnList, salFormula,2);
		List<List<String>> dataList = new ArrayList<List<String>>();
		for (TSalExcelData p : salaryDataList) {
			List<String> data = new ArrayList<String>();
			JSONTOOL jsontool = new JSONTOOL();
			data.add(p.getStaffName());
			data.add(p.getIDCard());
			data.add(p.getPro().getProName());
			
			JSONTOOL salaryExcelJson = JSONTOOL.ParseString(p.getSalaryExcelData());
			for (BSalaryTypeMapping salaryTypeMapping : columnList) {
				if (salaryTypeMapping.getSalaryType().getTypeId() != 2 && salaryTypeMapping.getSalaryType().getTypeId() != 1) {
					if(salaryExcelJson.getMap().containsKey("p"+salaryTypeMapping.getTypeMappingId())){
								data.add(String.valueOf(FormatUtil.formatterNum(
									salaryExcelJson.GetObjString("p"+salaryTypeMapping.getTypeMappingId()))));
						}else{
							data.add("0");
						}
				}
			}
			
			data.add(String.valueOf(p.getSalaryPayable()));
			
			jsontool.AddObject("staffId", Integer.valueOf(p.getStaffId()));
			jsontool.AddObject("personEndowment", p.getPersonEndowment());
			jsontool.AddObject("personMedical", p.getPersonMedical());
			jsontool.AddObject("personIllnessMedical", p.getPersonIllnessMedical());
			jsontool.AddObject("personUnemploye", p.getPersonUnemploye());

			jsontool.AddObject("personSecurityAdd", p.getPersonSecurityAdd());
			jsontool.AddObject("personHousefund", p.getPersonHousefund());
			jsontool.AddObject("personHousefundAdd", p.getPersonHousefundAdd());
			jsontool.AddObject("personUnionFee", p.getPersonUnionFee());
			jsontool.AddObject("personRecord", p.getPersonRecord());

			jsontool.AddObject("deductTaxProject", p.getDeductTaxProject());
			jsontool.AddObject("personIncomeTax", p.getPersonIncomeTax());
			jsontool.AddObject("personInsurance", p.getPersonInsurance());
			jsontool.AddObject("personSecurityAdjust", p.getPersonSecurityAdjust());
			jsontool.AddObject("personHousefundAdjust", p.getPersonHousefundAdjust());
			
			jsontool.AddObject("personWelfDiff", p.getPersonWelfDiff());
			jsontool.AddObject("personHousefundDiff", p.getPersonHousefundDiff());
			jsontool.AddObject("personSecurityRadix", p.getPersonSecurityRadix());
			jsontool.AddObject("companySecurityRadix", p.getCompanySecurityRadix());
			jsontool.AddObject("otherAmount", p.getOtherAmount());
			
			jsontool.AddObject("personTaxAdjust", p.getPersonTaxAdjust());
			jsontool.AddObject("personTaxAdjustBal", p.getPersonTaxAdjustBal());
			jsontool.AddObject("taxableSalary", p.getTaxableSalary());
			jsontool.AddObject("deductionSum", p.getDeductionSum());
			jsontool.AddObject("salaryNetIncome", p.getSalaryNetIncome());

			jsontool.AddObject("unitEndowment", p.getUnitEndowment());
			jsontool.AddObject("unitMedical", p.getUnitMedical());
			jsontool.AddObject("unitInjury", p.getUnitInjury());
			jsontool.AddObject("unitBirth", p.getUnitBirth());
			jsontool.AddObject("unitUnemploye", p.getUnitUnemploye());

			jsontool.AddObject("unitSecurityAdd", p.getUnitSecurityAdd());
			jsontool.AddObject("unitHousefund", p.getUnitHousefund());
			jsontool.AddObject("unitHousefundAdd", p.getUnitHousefundAdd());
			jsontool.AddObject("unitUnionFee", p.getUnitUnionFee());
			jsontool.AddObject("unitUnionSecurityFee", p.getUnitUnionSecurityFee());

			jsontool.AddObject("unitResidualDeposit", p.getUnitResidualDeposit());
			jsontool.AddObject("unitResidualSecurityDeposit", p.getUnitResidualSecurityDeposit());
			jsontool.AddObject("unitRecord", p.getUnitRecord());
			jsontool.AddObject("laborAgencyFee", p.getLaborAgencyFee());
			jsontool.AddObject("laborAgencyFeeAdjust", p.getLaborAgencyFeeAdjust());

			jsontool.AddObject("otherFee", p.getOtherFee());
			jsontool.AddObject("unitInsurance", p.getUnitInsurance());
			jsontool.AddObject("otherFeeAdjust", p.getOtherFeeAdjust());
			jsontool.AddObject("unitAgentAmount", p.getUnitAgentAmount());
			jsontool.AddObject("unitSecurityAdjust", p.getUnitSecurityAdjust());

			jsontool.AddObject("unitHousefundAdjust", p.getUnitHousefundAdjust());
			jsontool.AddObject("unitWelfDiff", p.getUnitWelfDiff());
			jsontool.AddObject("unitHousefundDiff", p.getUnitHousefundDiff());
			jsontool.AddObject("remark", p.getRemark());
			jsontool.AddObject("payTotal", p.getPayTotal());
			
			jsontool.AddObject("taxSalAccume", p.getTaxSalAccume());
			jsontool.AddObject("yearTaxAdjust", p.getYearTaxAdjust());
			jsontool.AddObject("finalIncomeTax", p.getFinalIncomeTax());
			jsontool.AddObject("incomeTaxAccume", p.getIncomeTaxAccume());
			jsontool.AddObject("yearTaxAccume", p.getYearTaxAccume());
			jsontool.AddObject("buckledIncTaxAccume", p.getBuckledIncTaxAccume());
			jsontool.AddObject("buckledYearTaxAccume", p.getBuckledYearTaxAccume());
			
			for (SSalaryFormula salaryFormula : salFormula) {
				if(jsontool.getMap().containsKey(salaryFormula.getFixedItem().getStaffItemName())){
					data.add(String.valueOf(FormatUtil.formatterNum(
							jsontool.GetObjString(salaryFormula.getFixedItem().getStaffItemName()))));
				}
		}
		    data.add(p.getRemark());
			dataList.add(data);
		}
		return ExcelUtil.exaple(dataList, titleList, fileName, realPath);
	}

	/**
	 * 查询工资未发提醒
	 * 
	 * @param sal
	 * @param rows
	 * @param page
	 * @return
	 */
	public List<WSalBill> findSalSubRemindList(TSalExcelData sal, String rows, String page, BUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tsalexcelData", sal);
		map.put("user", user);
		if (rows!=null&&page!=null) {
			int size = PageUtil.getPageSize(rows);
			int start = PageUtil.getPageStart(size, page);
			map.put("start", start);
			map.put("size", size);
		}
		return salaryMangeDao.findSalSubRemindList(map);

	}


	/**
	 * 查询工资未发提醒数据条数
	 * 
	 * @param sal
	 * @return
	 */
	public int findSalSubRemindCount(TSalExcelData sal, BUser user) {
		return salaryMangeDao.findSalSubRemindCount(sal, user);
	}

	/**
	 * 根据Ids查询数据
	 * 
	 * @param salaryTempId
	 * @param rows
	 * @param page
	 * @return
	 */
	public List<TSalExcelData> getSalBillDetail(int salaryBillId, String rows, String page,
			String staffName,String IDCard,String isAdjust) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (rows!=null&&page!=null) {
			int size = PageUtil.getPageSize(rows);
			int start = PageUtil.getPageStart(size, page);
			map.put("start", start);
			map.put("size", size);
		}
		map.put("salaryBillId", salaryBillId);
		map.put("staffName", staffName);
		map.put("IDCard", IDCard);
		if(isAdjust!=null&&!"".equals(isAdjust)){
			map.put("isAdjust","on".equals(isAdjust) ? Constant.CHECKBOX_ON: Constant.CHECKBOX_OFF);
		}
		return salaryMangeDao.getSalBillDetail(map);
	}
	
	/**
	 * 对账单明细——工资明细导出
	 * 
	 * @param salaryTempId
	 * @param rows
	 * @param page
	 * @author wuzhipeng
	 */
	public String exportSalBillDetail(List<TSalExcelData> salExcelData, String realPath) throws Exception {
		List<String> titleList = TSalExcelData.getSalBillDetailTitle();
		List<List<String>> dataList = new ArrayList<List<String>>();
		for(TSalExcelData item:salExcelData ){
			
			List<String> data = new ArrayList<String>();
			data.add(item.getStaffName());
			data.add(item.getIDCard());
			
			data.add(item.getSalaryPayable()+"");
			data.add(item.getPersonEndowment()+"");
			data.add(item.getPersonMedical()+"");
			data.add(item.getPersonUnemploye()+"");
			data.add(item.getPersonSecurityAdd()+"");
			data.add(item.getPersonHousefund()+"");
			data.add(item.getPersonHousefundAdd()+"");
			data.add(item.getPersonUnionFee()+"");
			data.add(item.getPersonRecord()+"");
			data.add(item.getPersonInsurance()+"");
			data.add(item.getPersonSecurityAdjust()+"");
			data.add(item.getPersonHousefundAdjust()+"");
			data.add(item.getPersonWelfDiff()+"");
			data.add(item.getPersonHousefundDiff()+"");
			data.add(item.getOtherAmount()+"");
			data.add(item.getTaxableSalary()+"");
			data.add(item.getDeductTaxProject()+"");
			data.add(item.getTaxSalAccume()+"");
			data.add(item.getYearTaxAdjust()+"");
			data.add(item.getPersonIncomeTax()+"");
			data.add(item.getPersonTaxAdjustBal()+"");
			data.add(item.getPersonTaxAdjust()+"");
			data.add(item.getFinalIncomeTax()+"");
			data.add(item.getDeductionSum()+"");
			data.add(item.getSalaryNetIncome()+"");
			data.add(item.getCompanySecurityRadix()+"");
			data.add(item.getUnitEndowment()+"");
			data.add(item.getUnitMedical()+"");
			data.add(item.getUnitInjury()+"");
			data.add(item.getUnitBirth()+"");
			data.add(item.getUnitUnemploye()+"");
			data.add(item.getUnitSecurityAdd()+"");
			data.add(item.getUnitHousefund()+"");
			data.add(item.getUnitHousefundAdd()+"");
			data.add(item.getUnitUnionFee()+"");
			data.add(item.getUnitUnionSecurityFee()+"");
			data.add(item.getUnitResidualDeposit()+"");
			data.add(item.getUnitResidualSecurityDeposit()+"");
			data.add(item.getUnitRecord()+"");
			data.add(item.getLaborAgencyFee()+"");
			data.add(item.getLaborAgencyFeeAdjust()+"");
			data.add(item.getOtherFee()+"");
			data.add(item.getUnitInsurance()+"");
			data.add(item.getOtherFeeAdjust()+"");
			data.add(item.getUnitAgentAmount()+"");
			data.add(item.getUnitSecurityAdjust()+"");
			data.add(item.getUnitHousefundAdjust()+"");
			data.add(item.getUnitWelfDiff()+"");
			data.add(item.getUnitHousefundDiff()+"");
			data.add(item.getRemark()+"");
			data.add(item.getPayTotal()+"");
			dataList.add(data);
		}
		return ExcelUtil.exaple(dataList, titleList, "工资明细表信息", realPath);
	}
	
	/**
	 * 对账单明细——企业对账单明细导出
	 * 
	 * @param salaryTempId
	 * @param rows
	 * @param page
	 * @author wuzhipeng
	 */
	public String exportUnitSalBillDetail(List<TSalExcelData> salExcelData, String realPath) throws Exception {
		List<String> titleList = TSalExcelData.getUnitSalBillDetailTitle();
		List<List<String>> dataList = new ArrayList<List<String>>();
		for(TSalExcelData item:salExcelData ){
			List<String> data = new ArrayList<String>();
			data.add(item.getStaffName());
			data.add(item.getIDCard());
			data.add(String.valueOf(item.getSalaryPayable()));
			data.add(item.getUnitEndowment()+"");
			data.add(item.getUnitMedical()+"");
			data.add(item.getUnitInjury()+"");
			data.add(item.getUnitBirth()+"");
			data.add(item.getUnitUnemploye()+"");
			data.add(item.getUnitSecurityAdd()+"");
			data.add(item.getUnitHousefund()+"");
			data.add(item.getUnitHousefundAdd()+"");
			data.add(item.getUnitUnionFee()+"");
			data.add(item.getUnitUnionSecurityFee()+"");
			data.add(item.getUnitResidualDeposit()+"");
			data.add(item.getUnitResidualSecurityDeposit()+"");
			data.add(item.getUnitRecord()+"");
			data.add(item.getLaborAgencyFee()+"");
			data.add(item.getLaborAgencyFeeAdjust()+"");
			data.add(item.getOtherFee()+"");
			data.add(item.getUnitInsurance()+"");
			data.add(item.getOtherFeeAdjust()+"");
			data.add(item.getUnitAgentAmount()+"");
			data.add(item.getUnitSecurityAdjust()+"");
			data.add(item.getUnitHousefundAdjust()+"");
			data.add(item.getUnitWelfDiff()+"");
			data.add(item.getUnitHousefundDiff()+"");
			data.add(item.getRemark()+"");
			data.add(item.getPayTotal()+"");
			dataList.add(data);
		}
		return ExcelUtil.exaple(dataList, titleList, "企业对账单明细表信息", realPath);
	}
	
	/**
	 * 对账单明细——个人对账单明细导出
	 * 
	 * @param salaryTempId
	 * @param rows
	 * @param page
	 * @author wuzhipeng
	 */
	public String exportPersonSalBillDetail(List<TSalExcelData> salExcelData, String realPath) throws Exception {
		List<String> titleList = TSalExcelData.getPersonSalBillDetailTitle();
		List<List<String>> dataList = new ArrayList<List<String>>();
		for(TSalExcelData item:salExcelData ){
			List<String> data = new ArrayList<String>();
			data.add(item.getStaffName());
			data.add(item.getIDCard());
			data.add(item.getSalaryPayable()+"");
			data.add(item.getPersonEndowment()+"");
			data.add(item.getPersonUnemploye()+"");
			data.add(item.getPersonSecurityAdd()+"");
			data.add(item.getPersonHousefund()+"");
			data.add(item.getPersonHousefundAdd()+"");
			data.add(item.getPersonHousefundAdd()+"");
			data.add(item.getPersonRecord()+"");
			data.add(item.getTaxableSalary()+"");//计税工资→扣税项目
			data.add(item.getPersonIncomeTax()+"");
			data.add(item.getPersonInsurance()+"");
			data.add(item.getPersonSecurityAdjust()+"");
			data.add(item.getPersonHousefundAdjust()+"");
			data.add(item.getPersonWelfDiff()+"");
			data.add(item.getPersonHousefundDiff()+"");
			data.add(item.getOtherAmount()+"");
			data.add(item.getPersonTaxAdjust()+"");
			data.add(item.getPersonTaxAdjustBal()+"");
			data.add(item.getDeductTaxProject()+"");//扣税项目→计税工资
			data.add(item.getDeductionSum()+"");
			data.add(item.getSalaryNetIncome()+"");
			dataList.add(data);
		}
		return ExcelUtil.exaple(dataList, titleList, "个人对账单明细表信息", realPath);
	}
	
	/**
	 * 对账单明细——社保对账单明细导出
	 * 
	 * @param salaryTempId
	 * @param rows
	 * @param page
	 * @author wuzhipeng
	 */
	public String exportSecuritySalBillDetail(List<TSalExcelData> salExcelData, String realPath) throws Exception {
		List<String> titleList = TSalExcelData.getSecuritySalBillDetailTitle();
		List<List<String>> dataList = new ArrayList<List<String>>();
		for(TSalExcelData item:salExcelData ){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("staffId", item.getStaffId());
			map.put("proId", item.getPro().getProId());
			WDeclareSecurity decSec = welfAmountDao.getWelfareBySalInfo(map);
			List<String> data = new ArrayList<String>();
			if(item.getPro() == null){
				data.add("");
			}else{
				data.add(item.getPro().getProName());
			}
			data.add(item.getIDCard());
			data.add(item.getStaffName());
			if (decSec==null) {
				data.add("");
				data.add("");
				data.add("");
			}else {
				data.add(decSec.getSecurityName());
				data.add(decSec.getAreaName());
				data.add(decSec.getInsuredTime());
			}
			data.add(String.valueOf(decSec.getInsuredBase()));
			data.add(item.getPersonSecurityDate());
			data.add(item.getUnitSecurityDate());
			data.add(item.getPersonEndowment()+"");
			data.add(item.getPersonMedical()+"");
			data.add(item.getPersonUnemploye()+"");
			data.add(item.getPersonWelfDiff()+"");
			data.add(item.getPersonSecurityAdd()+"");
			data.add(item.getPersonSecurityAdjust()+"");
			data.add(item.getPersonUnionFee()+"");
			data.add(item.getPersonRecord()+"");
			data.add(item.getPersonInsurance()+"");
			data.add(item.getUnitEndowment()+"");
			data.add(item.getUnitMedical()+"");
			data.add(item.getUnitInjury()+"");
			data.add(item.getUnitBirth()+"");
			data.add(item.getUnitUnemploye()+"");
			data.add(item.getUnitUnionSecurityFee()+"");
			data.add(item.getUnitResidualSecurityDeposit()+"");
			data.add(item.getUnitSecurityAdd()+"");
			data.add(item.getUnitWelfDiff()+"");
			data.add(item.getUnitSecurityAdjust()+"");
			data.add(item.getUnitUnionFee()+"");
			data.add(item.getUnitResidualDeposit()+"");
			data.add(item.getUnitRecord()+"");
			data.add(item.getUnitInsurance()+"");
			dataList.add(data);
		}
		return ExcelUtil.exaple(dataList, titleList, "社保对账单明细表信息", realPath);
	}
	
	/**
	 * 对账单明细——公积金对账单明细导出
	 * 
	 * @param salaryTempId
	 * @param rows
	 * @param page
	 * @author wuzhipeng
	 */
	public String exportHousefundSalBillDetail(List<TSalExcelData> salExcelData, String realPath) throws Exception {
		List<String> titleList = TSalExcelData.getHousefundSalBillDetailTitle();
		List<List<String>> dataList = new ArrayList<List<String>>();
		for(TSalExcelData item:salExcelData ){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("staffId", item.getStaffId());
			map.put("proId", item.getPro().getProId());
			SStaffHousefund staffHouse = welfAmountDao.getStaffHouseBySalInfo(map);
			List<String> data = new ArrayList<String>();
			data.add(item.getStaffName());
			data.add(item.getIDCard());
			if (staffHouse==null) {
				data.add("");
				data.add("");
				data.add("");
			}else {
				data.add(staffHouse.getHousefundName());
				data.add(staffHouse.getAreaName());
				data.add(staffHouse.getStartPayDate());
			}
			data.add(item.getPersonHouseFundDate());
			data.add(item.getUnitHouseFundDate());
			data.add(item.getPersonHousefund()+"");
			data.add(item.getPersonHousefundAdd()+"");
			data.add(item.getPersonHousefundDiff()+"");
			data.add(item.getPersonHousefundAdjust()+"");
			data.add(item.getUnitHousefund()+"");
			data.add(item.getUnitHousefundAdd()+"");
			data.add(item.getUnitHousefundDiff()+"");
			data.add(item.getUnitHousefundAdjust()+"");
			dataList.add(data);
		}
		return ExcelUtil.exaple(dataList, titleList, "公积金对账单明细表信息", realPath);
	}

	public List<TSalExcelData> getSalCalDetail(int salaryBillId,int size,int start) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salaryBillId", salaryBillId);
		if(size!=-1&&start!=-1){
			map.put("size", size);
			map.put("start", start);
		}
		List<TSalExcelData> salExcList = salaryMangeDao.getSalBillDetail(map);
//		for (TSalExcelData excelData : salExcList) {
//			excelData.setPersonWelfDiff(0f);
//			excelData.setPersonHousefundDiff(0f);
//			excelData.setPersonTaxAdjustBal(0f);
//			excelData.setUnitWelfDiff(0f);
//			excelData.setUnitHousefundDiff(0f);
//		}
		return salExcList;
	}
	/**
	 * 根据ids查询数据条数
	 * @param salaryTempId
	 * @return
	 */
	public int getSalBillDetailCount(int salaryBillId,String staffName,String IDCard,String isAdjust) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salaryBillId", salaryBillId);
		map.put("staffName", staffName);
		map.put("IDCard", IDCard);
		if(isAdjust!=null&&!"".equals(isAdjust)){
			map.put("isAdjust","on".equals(isAdjust) ? Constant.CHECKBOX_ON: Constant.CHECKBOX_OFF);
		}
		return salaryMangeDao.getSalBillDetailCount(map);
	}
	
	public List<SstaffCalculate> findSalarySocialList(int salaryBillId, String rows, String page, String salaryDate) {
		int size = PageUtil.getPageSize(rows);
		int start = PageUtil.getPageStart(size, page);
		return salaryMangeDao.findSalarySocialList(salaryBillId, start, size, salaryDate);
	}
	public int findSalarySocialCount(int salaryBillId, String salaryDate){
		return salaryMangeDao.findSalarySocialCount(salaryBillId, salaryDate);	
	}

	public List<SStaffHousefundResult> findhousefundCalculationDet(int salaryBillId, String rows, String page, String salaryDate) {
		int size = PageUtil.getPageSize(rows);
		int start = PageUtil.getPageStart(size, page);
		return salaryMangeDao.findhousefundCalculationDet(salaryBillId, start, size, salaryDate);
	}
	public int findhousefundCalculationCount(int salaryBillId, String salaryDate){
		return salaryMangeDao.findhousefundCalculationCount(salaryBillId, salaryDate);
	}

	/**
	 * 根据状态获取工资导入数据
	 * 
	 * @param state
	 * @param date
	 * @return
	 */
	public List<TSalExcelData> getTSalExcelByState(int state, String date) {
		return salaryMangeDao.getTSalExcelByState(state, date);
	}

	/**
	 * 获取一个员工的公积金信息
	 * 
	 * @param salaryStaff
	 * @param staffHousefundList
	 * @return
	 */
	private SStaffHousefund findSameHouseFund(int staffId, List<SStaffHousefund> staffHousefundList) {
		SStaffHousefund findHouseFund = new SStaffHousefund();
		for (SStaffHousefund p : staffHousefundList) {
			if (p.getStaff().getStaffId() == staffId) {
				findHouseFund = p;
				break;
			}
		}

		return findHouseFund;
	}

	/**
	 * 计算员工社保、公积金
	 * 
	 * @param staffSecurityList
	 * @param staffHousefund
	 * @param staffId
	 * @param proId
	 * @param j
	 */
	private TSalFormulaItem salWelfCalculate(String salaryDate, String salaryBelongsDate,
			List<SStaffSecurity> staffSecurityList, SStaffHousefund staffHousefund, int staffId,
			String personSecurityDate, String personHouseFundDate, String unitSecurityDate, String unitHouseFundDate,
			int proId, int salaryTempId) {
		List<WSalWelfAmount> salWelfAmount = welfAmountService.getSalWelfAmount(salaryDate, salaryBelongsDate, staffId,
				proId, personSecurityDate, staffSecurityList, staffHousefund, salaryTempId);
		TSalFormulaItem sfItem = new TSalFormulaItem();
		// 获取到每个险种的应缴额
		for (int i = 0; i < salWelfAmount.size(); i++) {
			WSalWelfAmount sWAmount = salWelfAmount.get(i);
			// 如果是社保当月应缴额
			if (sWAmount.getStype() == 0) {
				sfItem.setPersonEndowment(sfItem.getPersonEndowment() + sWAmount.getPersonEndowment());
				sfItem.setPersonMedical(sfItem.getPersonMedical() + sWAmount.getPersonMedical()); // 个人医疗保险
				sfItem.setPersonIllnessMedical(sfItem.getPersonIllnessMedical() + sWAmount.getPersonIllnessMedical()); // 个人补充医疗
				sfItem.setPersonInjury(sfItem.getPersonInjury() + sWAmount.getPersonInjury()); // 个人工伤应缴额
				sfItem.setPersonBirth(sfItem.getPersonBirth() + sWAmount.getPersonBirth()); // 个人生育应缴额
				sfItem.setPersonUnemploye(sfItem.getPersonUnemploye() + sWAmount.getPersonUnemployment()); // 个人失业应缴额
				sfItem.setPersonResidualDeposit(
						sfItem.getPersonResidualDeposit() + sWAmount.getPersonResidualDeposit()); // 个人残保金应缴额
				sfItem.setPersonUnionFee(sfItem.getPersonUnionFee() + sWAmount.getUnitUnionFee()); // 个人工会费应缴额

				sfItem.setUnitEndowment(sfItem.getUnitEndowment() + sWAmount.getUnitEndowment()); // 单位养老应缴额
				sfItem.setUnitMedical(sfItem.getUnitMedical() + sWAmount.getUnitMedical()); // 单位医疗保险
				sfItem.setUnitIllnessMedical(sfItem.getUnitIllnessMedical() + sWAmount.getUnitIllnessMedical()); // 单位补充医疗
				sfItem.setUnitInjury(sfItem.getUnitInjury() + sWAmount.getUnitInjury()); // 单位工伤应缴额
				sfItem.setUnitBirth(sfItem.getUnitBirth() + sWAmount.getUnitBirth()); // 单位生育应缴额
				sfItem.setUnitUnemploye(sfItem.getUnitUnemploye() + sWAmount.getUnitUnemployment()); // 单位失业应缴额
				sfItem.setUnitResidualDeposit(sfItem.getUnitResidualDeposit() + sWAmount.getUnitResidualDeposit()); // 单位残保金应缴额
				sfItem.setUnitUnionFee(sfItem.getUnitUnionFee() + sWAmount.getUnitUnionFee());
			} else {
				sfItem.setPersonSecurityAdd(sfItem.getPersonSecurityAdd() + sWAmount.getPersonEndowment()
						+ sWAmount.getPersonMedical() + sWAmount.getPersonIllnessMedical() + sWAmount.getPersonInjury()
						+ sWAmount.getPersonBirth() + sWAmount.getPersonUnemployment()
						+ sWAmount.getPersonResidualDeposit()); // 个人社保补缴

				sfItem.setUnitSecurityAdd(sfItem.getUnitSecurityAdd() + sWAmount.getUnitUnionFee()
						+ sWAmount.getUnitEndowment() + sWAmount.getUnitMedical() + sWAmount.getUnitIllnessMedical()
						+ sWAmount.getUnitInjury() + sWAmount.getUnitBirth() + sWAmount.getUnitUnemployment()
						+ sWAmount.getUnitResidualDeposit() + sWAmount.getUnitUnionFee()); // 单位社保补缴
			}

			// 公积金
			if (sWAmount.getHtype() == 0) {
				sfItem.setPersonHousefund(sfItem.getPersonHousefund() + sWAmount.getPersonHousefund()); // 个人公积金缴应缴额
				sfItem.setUnitHousefund(sfItem.getUnitHousefund() + sWAmount.getUnitHousefund());
			} else {
				sfItem.setPersonHousefundAdd(sfItem.getPersonHousefundAdd() + sWAmount.getPersonHousefund()); // 个人补充公积金应缴总额
				sfItem.setUnitHousefundAdd(sfItem.getUnitHousefundAdd() + sWAmount.getUnitHousefund());
			}
		}

		// 社保 公积金合计
		sfItem.setPersonSecurityAmount(sfItem.getPersonEndowment() + sfItem.getPersonMedical()
				+ sfItem.getPersonIllnessMedical() + sfItem.getPersonInjury() + sfItem.getPersonBirth()
				+ sfItem.getPersonUnemploye() + sfItem.getPersonResidualDeposit() + sfItem.getPersonUnionFee()
				+ sfItem.getPersonSecurityAdd() + sfItem.getPersonRecord());

		sfItem.setUnitSecurityAmount(sfItem.getUnitEndowment() + sfItem.getUnitMedical()
				+ sfItem.getUnitIllnessMedical() + sfItem.getUnitInjury() + sfItem.getUnitBirth()
				+ sfItem.getUnitUnemploye() + sfItem.getUnitResidualDeposit() + sfItem.getUnitUnionFee()
				+ sfItem.getUnitSecurityAdd() + sfItem.getUnitRecord());

		sfItem.setPersonHousefundAmount(sfItem.getPersonHousefund() + sfItem.getPersonHousefundAdd()); // 个人公积金应缴总额
		sfItem.setUnitHousefundAmount(sfItem.getUnitHousefund() + sfItem.getUnitHousefundAdd());

		return sfItem;
	}

	/**
	 * 获取要引入差异库的人员
	 * 
	 * @param proId
	 * @param salaryBillId
	 * @param staffId
	 * @return
	 * @date 2015年9月23日下午7:59:00
	 * @author kuro
	 */
	public TSalExcelData findPersonExcelData(int proId, int salaryBillId, String staffId) {

		return salaryMangeDao.findPersonExcelData(proId, salaryBillId, Integer.parseInt(staffId));
	}

	public void updateSalVirAndTSalEx(TSalExcelData tSalData) {
		salaryMangeDao.updateTSalExcel(tSalData);
	}

	/**
	 * 获取映射字段集合
	 * 
	 * @param typeMappingIds
	 * @return
	 * @date 2015年9月26日下午4:10:17
	 * @author yingkaibing
	 */
	public List<BSalaryTypeMapping> findImportMappingForSalaryCal(String typeMappingIds) {
		return salaryImportdao.findImportMappingForSalaryCal(typeMappingIds);
	}

	/** 根据ID获取数据 **/
	public tsalaryExcelDateTemp findTSalExcelDataByID(int salaryTempId) {
		return salaryImportdao.findTSalExcelDataByID(salaryTempId);
	}

	/** 修改银行发放清单支付状态 ***/
	public int updateBankSlip(int salaryTempId) {
		return salaryImportdao.updateBankSlip(salaryTempId);
	}

	/** 计算完的固定项更新 **/
	public void upDateTSalExcelData(List<TSalExcelData> salExcelDataList) {
		salaryMangeDao.upDateTSalExcelData(salExcelDataList);
	}

	/**
	 * 添加37项
	 * 
	 * @param tSalExcelData
	 **/
	public void addSalBillDetail(List<SSalSalBillDetail> personSalBillDetailList, TSalExcelData tSalExcelData) {
		SSalSalBillDetail proSalBillDetail = new SSalSalBillDetail(); // 37项计算
		for (SSalSalBillDetail salBillDetail : personSalBillDetailList) {
			proSalBillDetail.setSalaryPayable(proSalBillDetail.getSalaryPayable() + salBillDetail.getSalaryPayable());
			proSalBillDetail.setPersonDonateMoneyTotal(
					proSalBillDetail.getPersonDonateMoneyTotal() + salBillDetail.getPersonDonateMoneyTotal());
			proSalBillDetail
					.setPersonEndowment(proSalBillDetail.getPersonEndowment() + salBillDetail.getPersonEndowment());
			proSalBillDetail
					.setPersonHousefund(proSalBillDetail.getPersonHousefund() + salBillDetail.getPersonHousefund());
			proSalBillDetail.setPersonHousefundAdd(
					proSalBillDetail.getPersonHousefundAdd() + salBillDetail.getPersonHousefundAdd());
			proSalBillDetail.setPersonHousefundTotal(
					proSalBillDetail.getPersonHousefundTotal() + salBillDetail.getPersonHousefundTotal());
			proSalBillDetail
					.setPersonIncomeTax(proSalBillDetail.getPersonIncomeTax() + salBillDetail.getPersonIncomeTax());
			proSalBillDetail
					.setPersonInsurance(proSalBillDetail.getPersonInsurance() + salBillDetail.getPersonInsurance());
			proSalBillDetail.setPersonMedical(proSalBillDetail.getPersonMedical() + salBillDetail.getPersonMedical());
			proSalBillDetail.setPersonRecord(proSalBillDetail.getPersonRecord() + salBillDetail.getPersonRecord());
			proSalBillDetail.setPersonSecurityAdd(
					proSalBillDetail.getPersonSecurityAdd() + salBillDetail.getPersonSecurityAdd());
			proSalBillDetail.setPersonSecurityTotal(
					proSalBillDetail.getPersonSecurityTotal() + salBillDetail.getPersonSecurityTotal());
			proSalBillDetail
					.setPersonUnemploye(proSalBillDetail.getPersonUnemploye() + salBillDetail.getPersonUnemploye());
			proSalBillDetail
					.setPersonUnionFee(proSalBillDetail.getPersonUnionFee() + salBillDetail.getPersonUnionFee());
			proSalBillDetail
					.setUnitAgentAmount(proSalBillDetail.getUnitAgentAmount() + salBillDetail.getUnitAgentAmount());
			proSalBillDetail.setUnitBirth(proSalBillDetail.getUnitBirth() + salBillDetail.getUnitBirth());
			proSalBillDetail.setUnitEndowment(proSalBillDetail.getUnitEndowment() + salBillDetail.getUnitEndowment());
			proSalBillDetail.setUnitHousefund(proSalBillDetail.getUnitHousefund() + salBillDetail.getUnitHousefund());
			proSalBillDetail
					.setUnitHousefundAdd(proSalBillDetail.getUnitHousefundAdd() + salBillDetail.getUnitHousefundAdd());
			proSalBillDetail.setUnitHousefundTotal(
					proSalBillDetail.getUnitHousefundTotal() + salBillDetail.getUnitHousefundTotal());
			proSalBillDetail.setUnitInjury(proSalBillDetail.getUnitInjury() + salBillDetail.getUnitInjury());
			proSalBillDetail.setUnitInsurance(proSalBillDetail.getUnitInsurance() + salBillDetail.getUnitInsurance());
			proSalBillDetail.setUnitMedical(proSalBillDetail.getUnitMedical() + salBillDetail.getUnitMedical());
			proSalBillDetail.setUnitRecord(proSalBillDetail.getUnitRecord() + salBillDetail.getUnitRecord());
			proSalBillDetail.setUnitResidualDeposit(
					proSalBillDetail.getUnitResidualDeposit() + salBillDetail.getUnitResidualDeposit());
			proSalBillDetail.setUnitResidualSecurityDeposit(
					proSalBillDetail.getUnitResidualSecurityDeposit() + salBillDetail.getUnitResidualSecurityDeposit());
			proSalBillDetail
					.setUnitSecurityAdd(proSalBillDetail.getUnitSecurityAdd() + salBillDetail.getUnitSecurityAdd());
			proSalBillDetail.setUnitSecurityTotal(
					proSalBillDetail.getUnitSecurityTotal() + salBillDetail.getUnitSecurityTotal());
			proSalBillDetail.setUnitUnemploye(proSalBillDetail.getUnitUnemploye() + salBillDetail.getUnitUnemploye());
			proSalBillDetail.setUnitUnionFee(proSalBillDetail.getUnitUnionFee() + salBillDetail.getUnitUnionFee());
			proSalBillDetail.setUnitUnionSecurityFee(
					proSalBillDetail.getUnitUnionSecurityFee() + salBillDetail.getUnitUnionSecurityFee());
			proSalBillDetail
					.setLaborAgencyFee(proSalBillDetail.getLaborAgencyFee() + salBillDetail.getLaborAgencyFee());
			proSalBillDetail.setOtherAmount(proSalBillDetail.getOtherAmount() + salBillDetail.getOtherAmount());
			proSalBillDetail.setOtherFee(proSalBillDetail.getOtherFee() + salBillDetail.getOtherFee());
			proSalBillDetail
					.setSalaryNetIncome(proSalBillDetail.getSalaryNetIncome() + salBillDetail.getSalaryNetIncome());
			proSalBillDetail.setPayTotal(proSalBillDetail.getPayTotal() + salBillDetail.getPayTotal());

		}
		proSalBillDetail.setSalaryBillId(tSalExcelData.getSalaryBillId());
		proSalBillDetail.setProId(tSalExcelData.getPro().getProId());
		if(tSalExcelData.getPro().getCustomerUnit()!=null){
			proSalBillDetail.setCustomerId(tSalExcelData.getPro().getCustomerUnit().getCustomerUnitId());
			proSalBillDetail.setCustomerName(tSalExcelData.getPro().getCustomerUnit().getCustomerUnitName());
		}else{
			proSalBillDetail.setCustomerId(-1);
			proSalBillDetail.setCustomerName("无");
		}
		proSalBillDetail.setCount(personSalBillDetailList.size());
		
		salaryMangeDao.addSalBillDetail(proSalBillDetail);

	}

	/** 删除临时表 **/
	public void deleteSalImportFixed(TSalExcelData tSalExcelData) {
		salaryMangeDao.deleteSalImportFixed(tSalExcelData);
	}
	/******/
	public void deleteStaffSecurityList(List<SstaffCalculate> staffSecurityList){
		salaryMangeDao.deleteStaffSecurityList(staffSecurityList);
	}
    
	/**
	 * 查询总数据条数
	 * @param salBill
	 * @param iDCard
	 * @param staffName
	 * @return
	 * @date 2015年10月16日下午12:51:21
	 * @author yingkaibing
	 * @param user 
	 */
	public int findTSalExcelDataCount(WSalBill salBill, String iDCard,
			String staffName, BUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salaryBillId", salBill.getSalaryBillId());
		map.put("staffName", staffName);
		map.put("IDCard", iDCard);
		map.put("user", user);
		return salaryMangeDao.findTSalExcelDataCount(map);
	}

	public List<TSalExcelData> getSalBillDetail(int salaryBillId, int state, String salaryDate,BVar othVar, List<Integer> unitGroupId, List<Integer> salSetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salaryBillId", salaryBillId);
		map.put("payUnitId", unitGroupId);
		map.put("invalState", Constant.INVALID_DATA);
		map.put("delState", Constant.SAL_BILL_STATE_1_);
		map.put("salaryDate", salaryDate);
		map.put("varId", othVar.getId());
		map.put("salSetId", salSetId);
		return salaryMangeDao.getStaffExcDetail(map);
	}
    
	/**
	 * 导出错误数据
	 * @param errorList
	 * @return
	 * @date 2016年1月6日下午7:51:45
	 * @author yingkaibing
	 * @throws Exception 
	 */
	public String exportErrorData(List<WSalErrorExcelData> errorList,String pathName) throws Exception {
		List<String> titleList = WSalErrorExcelData.getTitleList();
		List<List<String>> dataList = new ArrayList<List<String>>();
		for (WSalErrorExcelData wData : errorList) {
			List<String> data = new ArrayList<String>();
			data.add(wData.getStaffName());
			data.add(wData.getIDCard());
			data.add(wData.getExceptionsReason());
			dataList.add(data);
		}
		// TODO Auto-generated method stub
		return ExcelUtil.exaple(dataList, titleList, "工资导入错误数据", pathName);
	}
    
	/**
	 * 更新已计算的社保和公积金人员
	 * @param securityMaps
	 * @param housefundMaps
	 * @date 2016年3月8日下午8:34:23
	 * @author yingkaibing
	 */
	public void updateSecurityAndHousefundScaleState(
			List<Map<String, Object>> securityMaps,
			List<Map<String, Object>> housefundMaps) {
		// TODO Auto-generated method stub
		salaryMangeDao.updateSecurityAndHousefundScaleState(securityMaps,housefundMaps);
	}
    
	/**
	 * 获取在职人员
	 * @param calProId
	 * @return
	 * @date 2016年3月8日下午10:02:09
	 * @author yingkaibing
	 */
	public int getProCountByProId(String calProId) {
		
		return staffDao.getProCountByProId(calProId);
	}
	/**
	 * 引入差额后更新37
	 * @param salBillTotal
	 */
	public int updateSalBillDetail(SSalSalBillDetail salBillTotal) {
		return salaryMangeDao.updateSalBillDetail(salBillTotal);
	}

	/**
	 * 工资计算导出
	 * @param salaryBillId
	 * @return
	 * @date 2016年5月10日上午9:26:10
	 * @author yingkaibing
	 */
	public List<BSalaryTypeMapping> findImportMappingForSalaryExport(
			String salaryBillId) {
		
		return salaryMangeDao.findImportMappingForSalaryExport(salaryBillId);
	}
	/**
	 * 获取工资导入的映射ID
	 * @param salaryBillId
	 * @return
	 * @date 2016-7-15下午4:42:14
	 * @author Kuro
	 */
	public String getTypeMapIds(String salaryBillId) {
		
		return salaryBillDao.getTypeMapIds(Integer.parseInt(salaryBillId));
	}

}