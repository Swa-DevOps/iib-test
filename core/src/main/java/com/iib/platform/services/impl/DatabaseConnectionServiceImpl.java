
package com.iib.platform.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourcePool;
import com.iib.platform.cfdenach.models.CFDEnachSessionForm;
import com.iib.platform.common.objects.EnachSSO;
import com.iib.platform.common.utils.EncryptDecrypt;
import com.iib.platform.services.DatabaseConnectionService;

@Component(immediate = true, service = { DatabaseConnectionService.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Enach Database Connection service" })
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String ENACH_PL = "enach-pl";
	private static final String ENACH_MND = "enach-mandate";
	private static final String CFD_ENACH_MND = "cfd-enach-mandate";
	
	private static String DBENCRYPTYKEY= "!ndus!nd!ndus!nd";
	
	static final String LOG_CONSTANT = "Exception in DatabaseConnectionService";
	@Reference
	private DataSourcePool source;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#storeOtpDetails(java.lang
	 * .String, java.lang.String)
	 */
	private String queryonBasis(String appId, String key) {
		String query = "";
		if (key.equalsIgnoreCase("")) {
			if (appId.equalsIgnoreCase(ENACH_PL))
				query = "UPDATE enachpl SET updatetime = ?,otpno = ? WHERE mobileno = ?";

			if (appId.equalsIgnoreCase(ENACH_MND))
				query = "UPDATE enachmandate SET updatetime = ?,otpno = ? WHERE mobileno = ?";
			
			if (appId.equalsIgnoreCase(CFD_ENACH_MND))
				query = "UPDATE cfdenachmandate SET updatetime = ?,otpno = ?, step = ? WHERE mobileno = ?";
		} else {
			if (appId.equalsIgnoreCase(ENACH_PL))
				query = "UPDATE enachpl SET updatetime = ?,otpno = ?, mandatedata = ?, cifdetails = ?, session = ?, uniqueKey = ?, billDeskCheck = ?, LoanAPICompleteCheck = ?, otpCount=? WHERE mobileno = ?";

			if (appId.equalsIgnoreCase(ENACH_MND))
				query = "UPDATE enachmandate SET updatetime = ?,otpno = ?, mandatedata = ?, cifdetails = ?, session = ?, uniqueKey = ?, uniqueKeyDob = ?, otpDobCall = ?  WHERE mobileno = ?";
			
			if (appId.equalsIgnoreCase(CFD_ENACH_MND))
				query = "UPDATE cfdenachmandate SET updatetime = ?,otpno = ?, mandatedata = ?, cifdetails = ?, session = ?, uniqueKey = ?, uniqueKeyDob = ?, otpDobCall = ?  , step = ?  WHERE mobileno = ?";
		}

		return query;

	}
	
	@Override
	public int checkCountOfOTP(String mobileNumber,String appId, String key, String action){
		int count = -1;
		boolean recordAlreadyExist = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return count;
			}
		
			String query = "";

			if (appId.equalsIgnoreCase(CFD_ENACH_MND))
				query = "SELECT noofCalls FROM cfdenachmandate WHERE mobileno = ? AND session = ?";
			
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1,mobileNumber);
				pstmt.setString(2,key);
				
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
						count = rs.getInt("noofCalls");
						log.info("Count we set as {} ", count);
					}
				}
				
			}	
			
			if(!recordAlreadyExist)
			{
				java.sql.Timestamp sqlCurrentDate1 = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
				if (appId.equalsIgnoreCase(CFD_ENACH_MND)) {
					query = "UPDATE cfdenachmandate SET updatetime = ?, noofCalls = ? WHERE mobileno = ?";
					try (PreparedStatement pstmt = connection.prepareStatement(query)) {
						pstmt.setTimestamp(1, sqlCurrentDate1);
						pstmt.setInt(2, 0);
						pstmt.setString(3, mobileNumber);
						
						log.info("Count we set to zero as {} {}  ", count, pstmt.toString() );
						
						int updateRecord = pstmt.executeUpdate();

						if (updateRecord > 0) {
							count = 0;
						}
				}
				}
			}
			
			if(action.equalsIgnoreCase("check")) {
				
			return count;
			}
			
			else if(action.equalsIgnoreCase("update"))
			{
				java.sql.Timestamp sqlCurrentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
				if (appId.equalsIgnoreCase(CFD_ENACH_MND))
					query = "UPDATE cfdenachmandate SET updatetime = ?, noofCalls = ? WHERE mobileno = ? AND session = ?";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {
						pstmt.setTimestamp(1, sqlCurrentDate);
						count = count +1;
						pstmt.setInt(2, count);
						pstmt.setString(3, mobileNumber);
						pstmt.setString(4, key);
						
						log.info("Count we updated as {} {}  ", count, pstmt.toString() );
						
						int updateRecord = pstmt.executeUpdate();

						if (updateRecord > 0) {
							count = 9;
						}
						
						return count;
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return count;
	}
	
	
	private boolean updateOTPDatabase(String mobileNumber, String otp, String appId, String key) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			String query = queryonBasis(appId, key);

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				java.sql.Timestamp sqlCurrentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
				if (key.equalsIgnoreCase("")) {
					pstmt.setTimestamp(1, sqlCurrentDate);
					pstmt.setString(2, otp + "|0|0");
					if (appId.equalsIgnoreCase(CFD_ENACH_MND)) {
						pstmt.setString(3, "otpgenerated");
						pstmt.setString(4, mobileNumber);
					}else
					pstmt.setString(3, mobileNumber);
				} else {
					pstmt.setTimestamp(1, sqlCurrentDate);
					pstmt.setString(2, otp + "|0|0");
					pstmt.setString(3, "");
					pstmt.setString(4, "");
					pstmt.setString(5, key);
					if (appId.equalsIgnoreCase(ENACH_PL)) {
						pstmt.setString(6, "");
						pstmt.setString(7, "");
						pstmt.setString(8, "");
						pstmt.setInt(9, 0);
						pstmt.setString(10, mobileNumber);
					}
					if (appId.equalsIgnoreCase(ENACH_MND)) {
						pstmt.setString(6, "");
						pstmt.setString(7, "");
						pstmt.setInt(8, 0);
						pstmt.setString(9, mobileNumber);
					}
					if (appId.equalsIgnoreCase(CFD_ENACH_MND)) {
						pstmt.setString(6, "");
						pstmt.setString(7, "");
						pstmt.setInt(8, 0);
						pstmt.setString(9, "otpgenerated");
						pstmt.setString(10, mobileNumber);
					}

				}

				int updateRecord = pstmt.executeUpdate();

				if (updateRecord > 0) {
					recordUpdatedSuccess = true;
				}
			}
		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}
		return recordUpdatedSuccess;
	}

	private boolean insertOTPDatabase(String mobileNumber, String otp, String appId, String key) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			String query = "";
			if (key.equalsIgnoreCase("")) {
				if (appId.equalsIgnoreCase(ENACH_PL))
					query = "INSERT INTO enachpl(updatetime,otpno,mobileno) VALUES(?,?,?);";

				if (appId.equalsIgnoreCase(ENACH_MND) && (key.equalsIgnoreCase("")))
					query = "INSERT INTO enachmandate(updatetime,otpno,mobileno) VALUES(?,?,?);";
				
				if (appId.equalsIgnoreCase(CFD_ENACH_MND) && (key.equalsIgnoreCase("")))
					query = "INSERT INTO cfdenachmandate(updatetime,otpno,mobileno) VALUES(?,?,?);";

			} else {
				if (appId.equalsIgnoreCase(ENACH_PL))
					query = "INSERT INTO enachpl(updatetime,otpno,mobileno,session) VALUES(?,?,?,?);";

				if (appId.equalsIgnoreCase(ENACH_MND))
					query = "INSERT INTO enachmandate(updatetime,otpno,mobileno,session) VALUES(?,?,?,?);";

				if (appId.equalsIgnoreCase(CFD_ENACH_MND))
					query = "INSERT INTO cfdenachmandate(updatetime,otpno,mobileno,session,step) VALUES(?,?,?,?,'otpgenerated');";

			}
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				java.sql.Timestamp sqlCurrentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

				pstmt.setTimestamp(1, sqlCurrentDate);
				pstmt.setString(2, otp + "|0|0");
				pstmt.setString(3, mobileNumber);
				if (key.equalsIgnoreCase(""))
					log.info("KEY is Blank");
				else
					pstmt.setString(4, key);
				int recordUpdated = pstmt.executeUpdate();

				if (recordUpdated > 0) {
					recordUpdatedSuccess = true;
				}
			}
		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}
		return recordUpdatedSuccess;
	}

	@Override
	public boolean insertEnachCancelDatabase(String mobileNumber, String urmnumbers, String requestid, String crmId, String crmerror, String cancelApiresult) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			String query = "";
					query = "INSERT INTO enachcancellogs(uid,mobileNo,urmnno,cancelApiresult,crmId,crmerror) VALUES(?,?,?,?,?,?);";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, requestid);
				pstmt.setString(2,mobileNumber);
				pstmt.setString(3, urmnumbers);
				pstmt.setString(4, cancelApiresult);
				pstmt.setString(5,crmId);
				pstmt.setString(6, crmerror);
				int recordUpdated = pstmt.executeUpdate();

				if (recordUpdated > 0) {
					recordUpdatedSuccess = true;
				}
			}
		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}
		return recordUpdatedSuccess;
	}

	
	@Override
	public boolean storeOtpDetails(String mobileNumber, String otp, String appId) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			boolean recordAlreadyExist = false;

			String query = "";

			if (appId.equalsIgnoreCase(ENACH_PL))
				query = "SELECT otpno FROM enachpl WHERE mobileno = ?";

			if (appId.equalsIgnoreCase(ENACH_MND))
				query = "SELECT otpno FROM enachmandate WHERE mobileno = ?";
			
			if (appId.equalsIgnoreCase(CFD_ENACH_MND))
				query = "SELECT otpno FROM enachmandate WHERE mobileno = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mobileNumber);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}

			if (recordAlreadyExist) {
				/*
				 * Update Record Exist
				 */
				recordUpdatedSuccess = updateOTPDatabase(mobileNumber, otp, appId, "");

			} else {

				recordUpdatedSuccess = insertOTPDatabase(mobileNumber, otp, appId, "");

			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return recordUpdatedSuccess;
	}

	
	
	@Override
	public boolean storeOtpDetailsWithKey(String mobileNumber, String otp, String appId, String key) {

		String temp = mobileNumber + otp + appId + key;
		log.debug("storeOtpDetailsWithKey{}", temp);
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			boolean recordAlreadyExist = false;

			/*
			 * Check record exist against mobile number or not
			 */
			String query = "";

			if (appId.equalsIgnoreCase(ENACH_PL))
				query = "SELECT otpno FROM enachpl WHERE mobileno = ? ";

			if (appId.equalsIgnoreCase(ENACH_MND))
				query = "SELECT otpno FROM enachmandate WHERE mobileno = ? ";
			
			if (appId.equalsIgnoreCase(CFD_ENACH_MND))
				query = "SELECT otpno FROM cfdenachmandate WHERE mobileno = ? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mobileNumber);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}
			if (recordAlreadyExist) {
				/*
				 * Update Record Exist
				 */

				recordUpdatedSuccess = updateOTPDatabase(mobileNumber, otp, appId, key);
			} else {

				recordUpdatedSuccess = insertOTPDatabase(mobileNumber, otp, appId, key);

			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return recordUpdatedSuccess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#validateOtpDetails(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public boolean validateOtpDetails(String mobileNumber, String enteredOtp, String appId) {
		boolean validOtp = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return validOtp;
			}
			/*
			 * Check record exist against mobile number or not
			 */
			String query = "";

			if (appId.equalsIgnoreCase(ENACH_MND))
				query = "SELECT otpno FROM enachmandate WHERE mobileno = ? ;";

			if (appId.equalsIgnoreCase(ENACH_PL))
				query = "SELECT otpno,updatetime FROM enachpl WHERE mobileno = ? ;";
			
			if (appId.equalsIgnoreCase(CFD_ENACH_MND))
				query = "SELECT otpno FROM cfdenachmandate WHERE mobileno = ? ;";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mobileNumber);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						String otpVal = rs.getString(1);
						long diff=0;
						if (appId.equalsIgnoreCase(ENACH_PL)) {

							String time=rs.getDate(2).toString() + " " + rs.getTime(2).toString();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
								
							Calendar cal = Calendar.getInstance();
							Date date = cal.getTime();
							String formattedDate = format.format(date);
							Date d1 = null;
							Date d2 = null;
							
							d1 = format.parse(formattedDate);
							d2 = format.parse(time);
								
							
							diff = (d1.getTime() - d2.getTime());
							diff=diff/(60 * 1000) % 60;
							 
							 log.info("Difference in time {}   ", diff);
							
						}
						
						if (StringUtils.isNotBlank(otpVal)) {
							String[] otpValArray = otpVal.split("\\|");
							if (otpValArray.length > 1 && otpValArray[0].equalsIgnoreCase(enteredOtp)) {
								
								validOtp = true;
								if(diff>=15)
									validOtp = false;
									
							}
						}
					}

				}
			}
		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return validOtp;
		}
		return validOtp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#validateOtpDetails(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public boolean validateOtpDetailsWithKey(String mobileNumber, String enteredOtp, String appId, String mKey,
			String encryptKey) {
		boolean validOtp = false;

		try (Connection connection = getConnection()) {
			if (null == connection) {
				return validOtp;
			}

			log.info("Query {} {} {} {}",mobileNumber,enteredOtp,appId,mKey);
			
			String query = "";

			if (appId.equalsIgnoreCase(ENACH_MND))
				query = "SELECT otpno FROM enachmandate WHERE mobileno = ? AND session = ?;";
			
			if (appId.equalsIgnoreCase(CFD_ENACH_MND))
				query = "SELECT otpno FROM cfdenachmandate WHERE mobileno = ? AND session = ?;";

			if (appId.equalsIgnoreCase(ENACH_PL))
				query = "SELECT otpno FROM enachpl WHERE mobileno = ? AND session = ? ;";

			log.info("Query {} ",query);
			
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mobileNumber);
				pstmt.setString(2, mKey);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						String otpVal = rs.getString(1);
						log.info("Query {}",otpVal);
						if (StringUtils.isNotBlank(otpVal)) {
							String[] otpValArray = otpVal.split("\\|");
							
							log.info("Query for two values {} {}",enteredOtp, otpValArray[0]);
							if (otpValArray.length > 1 && otpValArray[0].equalsIgnoreCase(enteredOtp)) {
								validOtp = true;
								if (appId.equalsIgnoreCase(ENACH_MND)) {
									query = "UPDATE enachmandate SET uniqueKey = ? WHERE mobileno = ? AND session = ?";
									try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
										pstmt1.setString(1, encryptKey);
										pstmt1.setString(2, mobileNumber);
										pstmt1.setString(3, mKey);
										pstmt1.executeUpdate();

									}
								}
								
								if (appId.equalsIgnoreCase(CFD_ENACH_MND)) {
									query = "UPDATE cfdenachmandate SET uniqueKey = ?, step='validOtp' WHERE mobileno = ? AND session = ?";
									try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
										pstmt1.setString(1, encryptKey);
										pstmt1.setString(2, mobileNumber);
										pstmt1.setString(3, mKey);
										pstmt1.executeUpdate();

									}
								}

							}else { if (appId.equalsIgnoreCase(CFD_ENACH_MND)) {
									query = "UPDATE cfdenachmandate SET step='invalidOtp' WHERE mobileno = ? AND session = ?";
									try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
								
										pstmt1.setString(1, mobileNumber);
										pstmt1.setString(2, mKey);
										pstmt1.executeUpdate();

									}
								}

								
							}
								
						}
					}

				}
			}
		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return validOtp;
		}
		return validOtp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#updateMandateDetails(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public boolean updateMandateDetails(String mobileNumber, String mandateData, String appId) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}

			/*
			 * Check record exist against mobile number or not
			 */
			String query = "";

			if (appId.equalsIgnoreCase(ENACH_MND))
				query = "UPDATE enachmandate SET updatetime = ?,mandatedata = ? WHERE mobileno = ?";
			
			if (appId.equalsIgnoreCase(CFD_ENACH_MND))
				query = "UPDATE cfdenachmandate SET updatetime = ?,mandatedata = ? WHERE mobileno = ?";

			if (appId.equalsIgnoreCase(ENACH_PL))
				query = "UPDATE enachpl SET updatetime = ?,mandatedata = ? WHERE mobileno = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				java.sql.Timestamp sqlCurrentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

				pstmt.setTimestamp(1, sqlCurrentDate);
				pstmt.setString(2, mandateData);
				pstmt.setString(3, mobileNumber);

				int updateRecord = pstmt.executeUpdate();

				if (updateRecord > 0) {
					recordUpdatedSuccess = true;
				}

			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return recordUpdatedSuccess;
		}

		return recordUpdatedSuccess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getMandateDetails(java.
	 * lang.String)
	 */
	@Override
	public String getMandateDetails(String mobileNumber, String appId) {

		String mandateDatadetails = null;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return mandateDatadetails;
			}

			String query = "";

			if (appId.equalsIgnoreCase(ENACH_MND))
				query = "SELECT mandatedata FROM enachmandate WHERE mobileno = ?";
			
			if (appId.equalsIgnoreCase(CFD_ENACH_MND))
				query = "SELECT cfdmandatedata FROM enachmandate WHERE mobileno = ?";

			if (appId.equalsIgnoreCase(ENACH_PL))
				query = "SELECT mandatedata FROM enachpl WHERE mobileno = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mobileNumber);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						mandateDatadetails = rs.getString(1);
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return mandateDatadetails;
		}

		return mandateDatadetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getMandateDetails(java.
	 * lang.String)
	 */
	@Override
	public String checkforapp(String emandateNumber) {
		String appid = "none";
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return appid;
			}

			String query = "SELECT * FROM enachcreatedet WHERE digireferenceid = ? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, emandateNumber);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						
							appid = "ENACH";
							
	
					}
				}
			}
			
			query = "SELECT * FROM cfdenachcreatedet WHERE digireferenceid = ? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, emandateNumber);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						
							appid = "CFDENACH";
							
	
					}
				}
			}
			
			
		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return appid;
		}
		log.info("Value we fetched from Database Call for APP CHECK Servlet {}  ",appid );
		return appid;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getMandateDetails(java.
	 * lang.String)
	 */
	@Override
	public List<String> getForMandateCreate(String emandateNumber) {
		List<String> mandateDatadetails = new ArrayList<>();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return mandateDatadetails;
			}

			String query = "SELECT * FROM enachcreatedet WHERE digireferenceid = ? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, emandateNumber);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						mandateDatadetails.add(rs.getString(1));
						mandateDatadetails.add(rs.getString(2));
						mandateDatadetails.add(rs.getString(3));
						mandateDatadetails.add(rs.getString(4));
						mandateDatadetails.add(rs.getString(5));
						mandateDatadetails.add(rs.getString(6));
						mandateDatadetails.add(rs.getString(7));
						mandateDatadetails.add(rs.getString(8));
						mandateDatadetails.add(rs.getString(9));
						mandateDatadetails.add(rs.getString(10));
						mandateDatadetails.add(rs.getString(11));
						mandateDatadetails.add(rs.getString(12));
						mandateDatadetails.add(rs.getString(13));
						mandateDatadetails.add(rs.getString(14));
						mandateDatadetails.add(rs.getString(15));
						mandateDatadetails.add(rs.getString(16));
						mandateDatadetails.add(rs.getString(17));
						mandateDatadetails.add(rs.getString(18));
						mandateDatadetails.add(rs.getString(19));
						mandateDatadetails.add(rs.getString(20));
						mandateDatadetails.add(rs.getString(21));
						mandateDatadetails.add(rs.getString(22));
						mandateDatadetails.add(rs.getString(23));
						mandateDatadetails.add(rs.getString(27));

					}
				}
			}

			query = "SELECT * FROM npciapimandateDetails WHERE apimandid = ? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, emandateNumber);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						mandateDatadetails.add(rs.getString(1));
						mandateDatadetails.add(rs.getString(2));
						mandateDatadetails.add(rs.getString(3));
						mandateDatadetails.add(rs.getString(4));
						mandateDatadetails.add(rs.getString(5));
						mandateDatadetails.add(rs.getString(6));
						mandateDatadetails.add(rs.getString(7));
						mandateDatadetails.add(rs.getString(8));
						mandateDatadetails.add(rs.getString(9));
						mandateDatadetails.add(rs.getString(10));
						mandateDatadetails.add(rs.getString(11));
						mandateDatadetails.add(rs.getString(12));
						mandateDatadetails.add(rs.getString(13));

					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return mandateDatadetails;
		}
		log.info("Value we fetched from Database Call for EnachWebhook Servlet {}  ",mandateDatadetails.toString() );
		return mandateDatadetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#updateCifDetails(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public boolean updateCifDetails(String mobileNumber, String cifDetails, String mKey) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}

			String query = "UPDATE enachmandate SET updatetime = ?,cifdetails = ? WHERE mobileno = ? AND session = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				java.sql.Timestamp sqlCurrentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

				pstmt.setTimestamp(1, sqlCurrentDate);
				pstmt.setString(2, cifDetails);
				pstmt.setString(3, mobileNumber);
				pstmt.setString(4, mKey);

				int updateRecord = pstmt.executeUpdate();

				if (updateRecord > 0) {
					recordUpdatedSuccess = true;
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return recordUpdatedSuccess;
		}

		return recordUpdatedSuccess;
	}
	
	////Database Connection Serivce for CFD Project By Niket Goel
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getMandateDetails(java.
	 * lang.String)
	 */
	@Override
	public List<String> getCFDClientDetailsSMSEMAIL(String mandateId) {
		List<String> mandateDatadetails = new ArrayList<>();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return mandateDatadetails;
			}

			String query = "SELECT mobileNo,custmomeremail,customerName,startDate,endDate,transferFrequency,maximum_amount FROM cfdenachcreatedet WHERE digireferenceid = ? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, mandateId);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						mandateDatadetails.add(rs.getString(1));
						mandateDatadetails.add(rs.getString(2));
						mandateDatadetails.add(rs.getString(3));
						mandateDatadetails.add(rs.getString(4));
						mandateDatadetails.add(rs.getString(5));
						mandateDatadetails.add(rs.getString(6));
						mandateDatadetails.add(rs.getString(7));

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return mandateDatadetails;
		}

		return mandateDatadetails;
	}

	@Override
	public boolean updateCFDNPCIDetails(String[] values) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			boolean recordAlreadyExist = false;

			/*
			 * Check record exist against mobile number or not
			 */

			String query = "SELECT * FROM cfdnpcimandateDetails WHERE mandateId = ? ;";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, values[0]);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}
			if (recordAlreadyExist) {

				query = "UPDATE cfdnpcimandateDetails SET mandatefile_name =?, mandatecustomer_ref_number=?, scheme_ref_number =?, partner_entity =?, current_status=?, transfer_timestamp=?, transfer_fail_reason =?, spo_bank_reject_reason =?, umrn=?, npci_result_timestamp=?, npci_reject_reason=?, dest_bank_result_timestamp=?, dest_bank_reject_reason=?, dest_bank_reject_code=? WHERE mandateId = ?";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(1, values[1]);
					pstmt.setString(2, values[2]);
					pstmt.setString(3, values[3]);
					pstmt.setString(4, values[4]);
					pstmt.setString(5, values[5]);
					pstmt.setString(6, values[6]);
					pstmt.setString(7, values[7]);
					pstmt.setString(8, values[8]);
					pstmt.setString(9, values[9]);
					pstmt.setString(10, values[10]);
					pstmt.setString(11, values[11]);
					pstmt.setString(12, values[12]);
					pstmt.setString(13, values[13]);
					pstmt.setString(14, values[14]);
					pstmt.setString(15, values[0]);

					int updateRecord = pstmt.executeUpdate();

					if (updateRecord > 0) {
						recordUpdatedSuccess = true;
					}
				}
			} else {
				query = "INSERT INTO cfdnpcimandateDetails(mandatefile_name,mandatecustomer_ref_number, scheme_ref_number, partner_entity, current_status, transfer_timestamp, transfer_fail_reason, spo_bank_reject_reason, umrn, npci_result_timestamp, npci_reject_reason, dest_bank_result_timestamp, dest_bank_reject_reason, dest_bank_reject_code,mandateId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(1, values[1]);
					pstmt.setString(2, values[2]);
					pstmt.setString(3, values[3]);
					pstmt.setString(4, values[4]);
					pstmt.setString(5, values[5]);
					pstmt.setString(6, values[6]);
					pstmt.setString(7, values[7]);
					pstmt.setString(8, values[8]);
					pstmt.setString(9, values[9]);
					pstmt.setString(10, values[10]);
					pstmt.setString(11, values[11]);
					pstmt.setString(12, values[12]);
					pstmt.setString(13, values[13]);
					pstmt.setString(14, values[14]);
					pstmt.setString(15, values[0]);

					int recordUpdated = pstmt.executeUpdate();

					if (recordUpdated > 0) {
						recordUpdatedSuccess = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return recordUpdatedSuccess;
	}



	@Override
	public boolean updateCFDNPCIAPIDetails(String[] values) {
		boolean recordUpdatedSuccess = false;

		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}

			boolean recordAlreadyExist = false;

			/*
			 * Check record exist against mobile number or not
			 */

			String query = "SELECT * FROM cfdnpciapimandateDetails WHERE apimandid = ? ;";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, values[0]);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}
			

			if (recordAlreadyExist) {

				query = "UPDATE cfdnpciapimandateDetails SET msg_id =?, customer_ref_number=?, scheme_ref_number =?, npci_txn_id =?, current_status=?, txn_timestamp=?, txn_reject_code =?, umrn =?, txn_reject_reason=?, id=?, created_at=?, event=?, authmode =?  WHERE apimandid = ? ";

				try (PreparedStatement pstmt = connection.prepareStatement(query)) {
					pstmt.setString(1, values[1]);
					pstmt.setString(2, values[2]);
					pstmt.setString(3, values[3]);
					pstmt.setString(4, values[4]);
					pstmt.setString(5, values[5]);
					pstmt.setString(6, values[6]);
					pstmt.setString(7, values[7]);
					pstmt.setString(8, values[8]);
					pstmt.setString(9, values[9]);
					pstmt.setString(10, values[10]);
					pstmt.setString(11, values[11]);
					pstmt.setString(12, values[12]);
					pstmt.setString(13, values[13]);
					pstmt.setString(14, values[0]);

					int updateRecord = pstmt.executeUpdate();

					if (updateRecord > 0) {
						recordUpdatedSuccess = true;
						

						if (values[5].contains("success"))
						{
							query = "UPDATE cfdenachcreatedet SET step ='digiojourneycomplete', digistatus='Authentication Success' WHERE digireferenceid = ? ";

						}else if(values[5].contains("failed"))
						{
							query = "UPDATE cfdenachcreatedet SET step ='digiojourneycomplete', digistatus='Signing cancelled'  WHERE digireferenceid = ? ";
						}
						
						try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
							
							pstmt1.setString(1, values[0]);
							updateRecord = pstmt1.executeUpdate();
						}
						
					}
				}
			} else {
				/*
				 * Insert Record Does Not Exist
				 */
				// Use prepared statements to protected against SQL injection attacks

				query = "INSERT INTO cfdnpciapimandateDetails(apimandid,msg_id,customer_ref_number,scheme_ref_number,npci_txn_id,current_status,txn_timestamp,txn_reject_code,umrn,txn_reject_reason,id,created_at,event, authmode) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(1, values[0]);
					pstmt.setString(2, values[1]);
					pstmt.setString(3, values[2]);
					pstmt.setString(4, values[3]);
					pstmt.setString(5, values[4]);
					pstmt.setString(6, values[5]);
					pstmt.setString(7, values[6]);
					pstmt.setString(8, values[7]);
					pstmt.setString(9, values[8]);
					pstmt.setString(10, values[9]);
					pstmt.setString(11, values[10]);
					pstmt.setString(12, values[11]);
					pstmt.setString(13, values[12]);
					pstmt.setString(14, values[13]);

					int recordUpdated = pstmt.executeUpdate();

					if (recordUpdated > 0) {
						recordUpdatedSuccess = true;
						if (values[5].contains("success"))
						{
							query = "UPDATE cfdenachcreatedet SET step ='digiojourneycomplete', digistatus='Authentication Success' WHERE digireferenceid = ? ";

						}else if(values[5].contains("failed"))
						{
							query = "UPDATE cfdenachcreatedet SET step ='digiojourneycomplete', digistatus='Signing cancelled'  WHERE digireferenceid = ? ";
						}
						
						try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
							
							pstmt1.setString(1, values[0]);
							recordUpdated = pstmt1.executeUpdate();
						}
						
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return recordUpdatedSuccess;
		}

		return recordUpdatedSuccess;
	}

	@Override
	public boolean getCFDStatusUpdate(CFDEnachSessionForm sessionValues) {
		
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			String query="";
			if(sessionValues.getDigioID()==null || sessionValues.getDigioID().equalsIgnoreCase(""))
				query = "UPDATE cfdenachcreatedet SET step = ?,digistatus = ? WHERE sessionid = ?";
			
			else
				query = "UPDATE cfdenachcreatedet SET step = ?,digistatus = ? WHERE digireferenceid = ?";
			
			
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, sessionValues.getStep());
				pstmt.setString(2, sessionValues.getDigioStatus());
				if(sessionValues.getDigioID()==null || sessionValues.getDigioID().equalsIgnoreCase(""))
					pstmt.setString(3, sessionValues.getSession());
				else
					pstmt.setString(3, sessionValues.getDigioID());

				int updateRecord = pstmt.executeUpdate();

				if (updateRecord > 0) {
					recordUpdatedSuccess = true;
				}
			}
			
			log.info("query we passed and result {} {}",query,recordUpdatedSuccess);

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return recordUpdatedSuccess;
		}
			
			return  recordUpdatedSuccess;
	}
	

	
	@Override
	public boolean updateFluxResultCFD(String emandateNumber, String status, String errorMessage) {

		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}

			String umrn = "";
			String query = "SELECT umrn FROM cfdnpciapimandateDetails WHERE apimandid = ? ";
			log.info("Result we get {}", query);
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, emandateNumber);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						
						umrn = rs.getString("umrn");
						
					}
				}
			}

			
			query = "UPDATE cfdenachcreatedet SET fluxstatus = ?,fluxMessage = ?, urnno=?, step = ? WHERE digireferenceid = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, status);
				pstmt.setString(2, errorMessage);
				pstmt.setString(3, umrn);
				pstmt.setString(4, emandateNumber);

				int updateRecord = pstmt.executeUpdate();

				if (updateRecord > 0) {
					recordUpdatedSuccess = true;
				}

			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return recordUpdatedSuccess;
		}

		return recordUpdatedSuccess;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public List<EnachSSO> getCFDenachdetailsforExl(String query, String parametervalueofDate) {
		
		List<EnachSSO> enachObjects = new ArrayList<>();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return enachObjects;
			}
			if (query.equalsIgnoreCase(""))
			 query = "SELECT * FROM cfdenachcreatedet";
			log.info("Result we get {}", query);
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						EnachSSO enachObject = new EnachSSO();	
						enachObject.setDetcustomeracc(rs.getString("customerAccountNo"));
						enachObject.setDetcustomerbank(rs.getString("destinationBank"));
						enachObject.setDetcustomerifsc(rs.getString("destinationBankId"));
						enachObject.setDetcustomername(rs.getString("customerName"));
						enachObject.setAccountno(rs.getString("accountNo"));
						enachObject.setAppid(rs.getString("appid"));
						enachObject.setCifid(rs.getString("refno"));
						enachObject.setDigioid(rs.getString("digireferenceid"));
						enachObject.setEnachresult(rs.getString("digiresponse"));
						enachObject.setEnachstatus(rs.getString("digistatus"));
						enachObject.setEmailid(rs.getString("custmomeremail"));
						enachObject.setEmiamount(rs.getString("emiAmount"));
						enachObject.setEnddate(rs.getString("endDate"));
						enachObject.setFrequency(rs.getString("transferFrequency"));
						enachObject.setMobileno(rs.getString("mobileNo"));
						enachObject.setRefno(rs.getString("referralCode"));
						enachObject.setEmiamount(rs.getString("emiAmount"));
						enachObject.setStartdate(rs.getString("startDate"));
						enachObject.setEnddate(rs.getString("endDate") != "1900-01-01"?rs.getString("endDate"):"");
						enachObject.setStep(rs.getString("step"));
						enachObject.setFluxmessage(rs.getString("fluxMessage"));
						enachObject.setFluxstatus(rs.getString("fluxstatus"));
						enachObject.setSession(rs.getString("sessionid"));
						enachObject.setSmiamount(rs.getString("maximum_amount"));
						enachObject.setUpdatedtime(rs.getDate("updated_date").toString());
						enachObject.setCreatedtime(rs.getDate("created_date").toString());
						query = "SELECT * FROM cfdnpciapimandateDetails where apimandid ='"+enachObject.getDigioid()+"';";
						try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
							try (ResultSet rs1 = pstmt1.executeQuery()) {

								while (rs1.next()) {							
									enachObject.setUrmn(rs1.getString("umrn"));
									enachObject.setNpcistatus(rs1.getString("current_status"));
									enachObject.setNpcitxid(rs1.getString("npci_txn_id"));
									enachObject.setNpcirejectreason(rs1.getString("txn_reject_reason"));
									enachObject.setVendorAPIRef(rs1.getString("msg_id"));
									enachObject.setAuthmode(rs1.getString("authmode"));
									
								}
							}
							}
						
							enachObjects.add(enachObject);
						}

				}
			}
			
			query = "SELECT mobileno,step,updated_date FROM cfdenachmandate where updated_date >= '"+parametervalueofDate+"' AND step NOT LIKE 'WithDealID_%';";
			log.info("Result we get {}", query);
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						EnachSSO enachObject = new EnachSSO();
						enachObject.setUpdatedtime(rs.getString("updated_date"));
						enachObject.setMobileno(rs.getString("mobileno"));
						enachObject.setStep(rs.getString("step"));
						
						enachObjects.add(enachObject);
					}
				}
			}
			
			
			

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return (new ArrayList<EnachSSO>());
		}

		return enachObjects;
	}
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public CFDEnachSessionForm getCFDenachdetails(String sessionid, String allresult) {
		
		CFDEnachSessionForm enachObject = new CFDEnachSessionForm();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return enachObject;
			}

			String query = "SELECT * FROM cfdenachcreatedet WHERE digireferenceid = ? ";
			log.info("Result we get {}", query);
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, sessionid);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						enachObject.setAccdetcustacc(rs.getString("customerAccountNo"));
						enachObject.setAccdetcustbank(rs.getString("destinationBank"));
						enachObject.setAccdetcustifsc(rs.getString("destinationBankId"));
						enachObject.setAccdetcustname(rs.getString("customerName"));
						enachObject.setAccountno(rs.getString("accountNo"));
						enachObject.setAppid(rs.getString("appid"));
						enachObject.setCifid(rs.getString("refno"));
						enachObject.setDigioID(sessionid);
						enachObject.setDigioResponse(rs.getString("digiresponse"));
						enachObject.setDigioStatus(rs.getString("digistatus"));
						enachObject.setEmail(rs.getString("custmomeremail"));
						enachObject.setEmiamount(rs.getString("emiAmount"));
						enachObject.setEnddate(rs.getString("endDate"));
						enachObject.setFrequency(rs.getString("transferFrequency"));
						enachObject.setKey(rs.getString("requestid"));
						enachObject.setMobileno(rs.getString("mobileNo"));
						enachObject.setRefno(rs.getString("referralCode"));
						enachObject.setSmiamount(rs.getString("maximum_amount"));
						enachObject.setStartdate(rs.getString("startDate"));
						enachObject.setUniqueId(rs.getString("uniqueKey"));

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return (new CFDEnachSessionForm());
		}

		return enachObject;
	}
	
	
	
	
	@Override
	public boolean cfdcreateNachDetails(CFDEnachSessionForm sessionValues)
	/*public boolean cfdcreateNachDetails(String uniqueRequestId, String customername, String customerEmail,
			String customerRefNumber, String confirmAccountNo, String totalAmount, String referralCode,
			String totalInstallments, String aadharNo, String destinationBank, String destinationBankId,
			String customerAccountNo, String maximumamount, String transferFrequency, String mobileNo, String startDate,
			String endDate, String digistatus, String digireferenceid, String digiresponse, String uniqueKey)*/ {

		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			/*
			 * Insert Record Does Not Exist
			 */
			// Use prepared statements to protected against SQL injection attacks
			String query = "INSERT INTO cfdenachcreatedet(requestid,customerName,refno,accountNo,emiAmount,referralCode,totalInstallments,aadharNo,destinationBank,destinationBankId,"
					+ "customerAccountNo,maximum_amount,transferFrequency,mobileNo,startDate,endDate,step,digistatus,digireferenceid,digiresponse,schedulestatus,fluxstatus,custmomeremail,appid,uniqueKey,sessionid) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			//	java.sql.Timestamp sqlCurrentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

				pstmt.setString(1, sessionValues.getUniqueId());
				pstmt.setString(2, sessionValues.getAccdetcustname());
				pstmt.setString(3, sessionValues.getCifid());
				pstmt.setString(4, EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, sessionValues.getAccountno()));
				pstmt.setString(5, sessionValues.getEmiamount());
				pstmt.setString(6, sessionValues.getRefno());
				pstmt.setString(7, "NA");
				pstmt.setString(8, "NA");
				pstmt.setString(9, sessionValues.getAccdetcustbank());
				pstmt.setString(10, sessionValues.getAccdetcustifsc());
				pstmt.setString(11, EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, sessionValues.getAccdetcustacc()));
				pstmt.setString(12, sessionValues.getSmiamount());
				pstmt.setString(13, sessionValues.getFrequency());
				pstmt.setString(14, EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, sessionValues.getMobileno()));
				pstmt.setString(15, sessionValues.getStartdate());
				pstmt.setString(16, sessionValues.getEnddate());
				pstmt.setString(17, sessionValues.getStep());
				pstmt.setString(18, sessionValues.getDigioStatus());
				pstmt.setString(19, sessionValues.getDigioID());
				pstmt.setString(20, sessionValues.getDigioResponse());
				pstmt.setString(21, "false");
				pstmt.setString(22, "false");
				pstmt.setString(23, sessionValues.getEmail());
				pstmt.setString(24, sessionValues.getAppid());
				pstmt.setString(25, sessionValues.getKey());
				pstmt.setString(26, sessionValues.getSession());

				int recordUpdated = pstmt.executeUpdate();

				if (recordUpdated > 0) {
					recordUpdatedSuccess = true;
				}

			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return recordUpdatedSuccess;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCreateEnachSession(
	 * java.lang.String)
	 */
	@Override
	public boolean getCFDCreateEnachSession(String mKey, String mobileNo, String customerName, String custmomeremail) {
		boolean cifDatadetails = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return false;
			}

			String query = "SELECT * FROM cfdenachcreatedet WHERE uniqueKey = ? AND mobileNo =? AND customerName =? AND custmomeremail = ?  ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mKey);
				pstmt.setString(2, EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobileNo));
				pstmt.setString(3, customerName);
				pstmt.setString(4, custmomeremail);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						cifDatadetails = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return cifDatadetails;
		}

		return cifDatadetails;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#updateCFDCifDetails(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public boolean updateCFDCifDetails(String mobileNumber, String cifDetails, String mKey) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			if (cifDetails.equalsIgnoreCase("ciffetchIssue") || cifDetails.contains("WithDealID_")) {
			String query = "UPDATE cfdenachmandate SET updatetime = ?, step=? WHERE mobileno = ? AND session = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				java.sql.Timestamp sqlCurrentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

				pstmt.setTimestamp(1, sqlCurrentDate);
				pstmt.setString(2, cifDetails);	
				pstmt.setString(3, mobileNumber);
				pstmt.setString(4, mKey);

				int updateRecord = pstmt.executeUpdate();

				if (updateRecord > 0) {
					recordUpdatedSuccess = true;
				}
			}
			}else {
				
				String query = "UPDATE cfdenachmandate SET updatetime = ?,cifdetails = ?, step=? WHERE mobileno = ? AND session = ?";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					java.sql.Timestamp sqlCurrentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

					pstmt.setTimestamp(1, sqlCurrentDate);
					pstmt.setString(2, cifDetails);
					pstmt.setString(3, "ciffetchdone");				
					pstmt.setString(4, mobileNumber);
					pstmt.setString(5, mKey);

					int updateRecord = pstmt.executeUpdate();

					if (updateRecord > 0) {
						recordUpdatedSuccess = true;
					}
				}
			}
			
			
			

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return recordUpdatedSuccess;
		}

		return recordUpdatedSuccess;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getLoginFSSession(java.
	 * lang.String)
	 */
	@Override
	public boolean getLoginCFDFSSession(String mKey, String mobileNo, String encryptKey) {

		boolean cifDatadetails = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return false;
			}

			String query = "SELECT * FROM cfdenachmandate WHERE uniqueKey = ? AND mobileNo =? AND session =? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, encryptKey);
				pstmt.setString(2, mobileNo);
				pstmt.setString(3, mKey);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						cifDatadetails = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return cifDatadetails;
		}

		return cifDatadetails;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCifDetails(java.lang.
	 * String)
	 */
	@Override
	public String getCFDCifDetails(String mobileNumber, String mKey, String encryptKey) {

		String cifDatadetails = null;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return cifDatadetails;
			}

			String query = "SELECT cifdetails FROM cfdenachmandate WHERE mobileno = ? AND session = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mobileNumber);
				pstmt.setString(2, mKey);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						cifDatadetails = rs.getString(1);

						/*if (!encryptKey.equalsIgnoreCase("manageEnach")) {
							query = "UPDATE enachmandate SET uniqueKeyDob = ? WHERE mobileno = ? AND session = ?";
							try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
								pstmt1.setString(1, encryptKey);
								pstmt1.setString(2, mobileNumber);
								pstmt1.setString(3, mKey);
								pstmt1.executeUpdate();

							}
						}*/

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return cifDatadetails;
		}

		return cifDatadetails;
	}


	
	//// END FOR CFD PROJECT
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCifDetails(java.lang.
	 * String)
	 */
	@Override
	public String getCifDetails(String mobileNumber, String mKey, String encryptKey) {

		String cifDatadetails = null;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return cifDatadetails;
			}

			String query = "SELECT cifdetails FROM enachmandate WHERE mobileno = ? AND session = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mobileNumber);
				pstmt.setString(2, mKey);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						cifDatadetails = rs.getString(1);

						if (!encryptKey.equalsIgnoreCase("manageEnach")) {
							query = "UPDATE enachmandate SET uniqueKeyDob = ? WHERE mobileno = ? AND session = ?";
							try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
								pstmt1.setString(1, encryptKey);
								pstmt1.setString(2, mobileNumber);
								pstmt1.setString(3, mKey);
								pstmt1.executeUpdate();

							}
						}

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return cifDatadetails;
		}

		return cifDatadetails;
	}

	@Override
	public Connection getConnectionforOutside() {
		DataSource dataSource = null;
		Connection con = null;
		try {
			// Inject the DataSourcePool right here!
			log.info("GET CONNECTION");
			dataSource = (DataSource) source.getDataSource("EnachMandateConnection");
			con = dataSource.getConnection();

			log.info("CONNECTION is returned");
			return con;

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}
		return null;
	}

	
	private Connection getConnection() {
		DataSource dataSource = null;
		Connection con = null;
		try {
			// Inject the DataSourcePool right here!
			log.info("GET CONNECTION");
			dataSource = (DataSource) source.getDataSource("EnachMandateConnection");
			con = dataSource.getConnection();

			log.info("CONNECTION is returned");
			return con;

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}
		return null;
	}

	@Override
	public boolean createNachDetails(String uniqueRequestId, String customername, String customerEmail,
			String customerRefNumber, String confirmAccountNo, String totalAmount, String referralCode,
			String totalInstallments, String aadharNo, String destinationBank, String destinationBankId,
			String customerAccountNo, String maximumamount, String transferFrequency, String mobileNo, String startDate,
			String endDate, String digistatus, String digireferenceid, String digiresponse, String uniqueKey, String tnc ) {

		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			/*
			 * Insert Record Does Not Exist
			 */
			// Use prepared statements to protected against SQL injection attacks
			String query = "INSERT INTO enachcreatedet(requestid,customerName,refno,accountNo,totalAmount,referralCode,totalInstallments,aadharNo,destinationBank,destinationBankId,"
					+ "customerAccountNo,maximum_amount,transferFrequency,mobileNo,startDate,endDate,udpateDate,digistatus,digireferenceid,digiresponse,schedulestatus,fluxstatus,custmomeremail,ifscLabel,uniqueKey) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			
			//for tnc
			//String query = "INSERT INTO enachcreatedet(requestid,customerName,refno,accountNo,totalAmount,referralCode,totalInstallments,aadharNo,destinationBank,destinationBankId,"
			//		+ "customerAccountNo,maximum_amount,transferFrequency,mobileNo,startDate,endDate,udpateDate,digistatus,digireferenceid,digiresponse,schedulestatus,fluxstatus,custmomeremail,ifscLabel,uniqueKey, tnc) "
			//		+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				java.sql.Timestamp sqlCurrentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

				pstmt.setString(1, uniqueRequestId);
				pstmt.setString(2, customername);
				pstmt.setString(3, customerRefNumber);
				pstmt.setString(4, EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, confirmAccountNo));
				pstmt.setString(5, totalAmount);
				pstmt.setString(6, referralCode);
				pstmt.setString(7, totalInstallments);
				pstmt.setString(8, aadharNo);
				pstmt.setString(9, destinationBank);
				pstmt.setString(10, destinationBankId);
				pstmt.setString(11, EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, customerAccountNo));
				pstmt.setString(12, maximumamount);
				pstmt.setString(13, transferFrequency);
				pstmt.setString(14, EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobileNo));
				pstmt.setString(15, startDate);
				pstmt.setString(16, endDate);
				pstmt.setTimestamp(17, sqlCurrentDate);
				pstmt.setString(18, digistatus);
				pstmt.setString(19, digireferenceid);
				pstmt.setString(20, digiresponse);
				pstmt.setString(21, "false");
				pstmt.setString(22, "false");
				pstmt.setString(23, customerEmail);
				pstmt.setString(24, destinationBankId);
				pstmt.setString(25, uniqueKey);
				//pstmt.setString(26, tnc);

				int recordUpdated = pstmt.executeUpdate();

				if (recordUpdated > 0) {
					recordUpdatedSuccess = true;
				}

			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return recordUpdatedSuccess;
	}

	@Override
	public boolean updateFluxResult(String emandateNumber, String status, String errorMessage) {

		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}

			String query = "UPDATE enachcreatedet SET fluxstatus = ?,fluxMessage = ? WHERE digireferenceid = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, status);
				pstmt.setString(2, errorMessage);
				pstmt.setString(3, emandateNumber);

				int updateRecord = pstmt.executeUpdate();

				if (updateRecord > 0) {
					recordUpdatedSuccess = true;
				}

			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return recordUpdatedSuccess;
		}

		return recordUpdatedSuccess;
	}

	@Override
	public boolean updateNPCIDetails(String[] values) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}
			boolean recordAlreadyExist = false;

			/*
			 * Check record exist against mobile number or not
			 */

			String query = "SELECT * FROM npcimandateDetails WHERE mandateId = ? ;";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, values[0]);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}
			if (recordAlreadyExist) {

				query = "UPDATE npcimandateDetails SET mandatefile_name =?, mandatecustomer_ref_number=?, scheme_ref_number =?, partner_entity =?, current_status=?, transfer_timestamp=?, transfer_fail_reason =?, spo_bank_reject_reason =?, umrn=?, npci_result_timestamp=?, npci_reject_reason=?, dest_bank_result_timestamp=?, dest_bank_reject_reason=?, dest_bank_reject_code=? WHERE mandateId = ?";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(1, values[1]);
					pstmt.setString(2, values[2]);
					pstmt.setString(3, values[3]);
					pstmt.setString(4, values[4]);
					pstmt.setString(5, values[5]);
					pstmt.setString(6, values[6]);
					pstmt.setString(7, values[7]);
					pstmt.setString(8, values[8]);
					pstmt.setString(9, values[9]);
					pstmt.setString(10, values[10]);
					pstmt.setString(11, values[11]);
					pstmt.setString(12, values[12]);
					pstmt.setString(13, values[13]);
					pstmt.setString(14, values[14]);
					pstmt.setString(15, values[0]);

					int updateRecord = pstmt.executeUpdate();

					if (updateRecord > 0) {
						recordUpdatedSuccess = true;
					}
				}
			} else {
				query = "INSERT INTO npcimandateDetails(mandatefile_name,mandatecustomer_ref_number, scheme_ref_number, partner_entity, current_status, transfer_timestamp, transfer_fail_reason, spo_bank_reject_reason, umrn, npci_result_timestamp, npci_reject_reason, dest_bank_result_timestamp, dest_bank_reject_reason, dest_bank_reject_code,mandateId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(1, values[1]);
					pstmt.setString(2, values[2]);
					pstmt.setString(3, values[3]);
					pstmt.setString(4, values[4]);
					pstmt.setString(5, values[5]);
					pstmt.setString(6, values[6]);
					pstmt.setString(7, values[7]);
					pstmt.setString(8, values[8]);
					pstmt.setString(9, values[9]);
					pstmt.setString(10, values[10]);
					pstmt.setString(11, values[11]);
					pstmt.setString(12, values[12]);
					pstmt.setString(13, values[13]);
					pstmt.setString(14, values[14]);
					pstmt.setString(15, values[0]);

					int recordUpdated = pstmt.executeUpdate();

					if (recordUpdated > 0) {
						recordUpdatedSuccess = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return recordUpdatedSuccess;
	}

	@Override
	public boolean updateNPCIAPIDetails(String[] values) {
		boolean recordUpdatedSuccess = false;

		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}

			boolean recordAlreadyExist = false;

			/*
			 * Check record exist against mobile number or not
			 */

			String query = "SELECT * FROM npciapimandateDetails WHERE apimandid = ? ;";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, values[0]);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}

			if (recordAlreadyExist) {

				query = "UPDATE npciapimandateDetails SET msg_id =?, customer_ref_number=?, scheme_ref_number =?, npci_txn_id =?, current_status=?, txn_timestamp=?, txn_reject_code =?, umrn =?, txn_reject_reason=?, id=?, created_at=?, event=?, authmode =?  WHERE apimandid = ? ";

				try (PreparedStatement pstmt = connection.prepareStatement(query)) {
					pstmt.setString(1, values[1]);
					pstmt.setString(2, values[2]);
					pstmt.setString(3, values[3]);
					pstmt.setString(4, values[4]);
					pstmt.setString(5, values[5]);
					pstmt.setString(6, values[6]);
					pstmt.setString(7, values[7]);
					pstmt.setString(8, values[8]);
					pstmt.setString(9, values[9]);
					pstmt.setString(10, values[10]);
					pstmt.setString(11, values[11]);
					pstmt.setString(12, values[12]);
					pstmt.setString(13, values[13]);
					pstmt.setString(14, values[0]);

					int updateRecord = pstmt.executeUpdate();

					if (updateRecord > 0) {
						recordUpdatedSuccess = true;
					}
				}
			} else {
				/*
				 * Insert Record Does Not Exist
				 */
				// Use prepared statements to protected against SQL injection attacks

				query = "INSERT INTO npciapimandateDetails(apimandid,msg_id,customer_ref_number,scheme_ref_number,npci_txn_id,current_status,txn_timestamp,txn_reject_code,umrn,txn_reject_reason,id,created_at,event,authmode) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(1, values[0]);
					pstmt.setString(2, values[1]);
					pstmt.setString(3, values[2]);
					pstmt.setString(4, values[3]);
					pstmt.setString(5, values[4]);
					pstmt.setString(6, values[5]);
					pstmt.setString(7, values[6]);
					pstmt.setString(8, values[7]);
					pstmt.setString(9, values[8]);
					pstmt.setString(10, values[9]);
					pstmt.setString(11, values[10]);
					pstmt.setString(12, values[11]);
					pstmt.setString(13, values[12]);
					pstmt.setString(14, values[13]);

					int recordUpdated = pstmt.executeUpdate();

					if (recordUpdated > 0) {
						recordUpdatedSuccess = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return recordUpdatedSuccess;
	}

	@Override
	public boolean updateDigioResult(String requestId, String eMandateId, String talismaId, String message) {
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return recordUpdatedSuccess;
			}

			String query = "UPDATE enachcreatedet SET  digistatus =? digireferenceid=? WHERE requestid = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, message);
				pstmt.setString(2, eMandateId);
				pstmt.setString(3, requestId);

				int updateRecord = pstmt.executeUpdate();

				if (updateRecord > 0) {
					recordUpdatedSuccess = true;
				}

			}
		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return recordUpdatedSuccess;
		}

		return recordUpdatedSuccess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getMandateDetails(java.
	 * lang.String)
	 */
	@Override
	public List<String> getMandateIdforFailStatus() {
		List<String> mandateDatadetails = new ArrayList<>();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return mandateDatadetails;
			}

			String query = "SELECT digireferenceid FROM enachcreatedet WHERE fluxstatus= ?  OR fluxstatus=?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, "fail");
				pstmt.setString(2, "FAIL");

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						mandateDatadetails.add(rs.getString(1));

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return mandateDatadetails;
		}
		return mandateDatadetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getMandateDetails(java.
	 * lang.String)
	 */
	@Override
	public List<String> getClientDetailsSMSEMAIL(String mandateId, String appid) {
		List<String> mandateDatadetails = new ArrayList<>();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return mandateDatadetails;
			}

			String query = "SELECT mobileNo,custmomeremail,customerName,startDate,endDate,transferFrequency,maximum_amount FROM enachcreatedet WHERE digireferenceid = ? ";
			if(appid.equalsIgnoreCase("CFDENACH"))
				query = "SELECT mobileNo,custmomeremail,customerName,startDate,endDate,transferFrequency,maximum_amount FROM cfdenachcreatedet WHERE digireferenceid = ? ";
			
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, mandateId);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						mandateDatadetails.add(rs.getString(1));
						mandateDatadetails.add(rs.getString(2));
						mandateDatadetails.add(rs.getString(3));
						mandateDatadetails.add(rs.getString(4));
						mandateDatadetails.add(rs.getString(5));
						mandateDatadetails.add(rs.getString(6));
						mandateDatadetails.add(rs.getString(7));

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return mandateDatadetails;
		}

		return mandateDatadetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getMandateDetails(java.
	 * lang.String)
	 */
	@Override
	public List<String> getMandateIdforNullUrn() {

		List<String> mandateDatadetails = new ArrayList<>();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return mandateDatadetails;
			}

			String query = "SELECT digireferenceid FROM enachcreatedet WHERE urnno IS NULL OR urnno ='' OR urnno ='NULL' OR urnno= 'null' OR urnno = 'Null'";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						mandateDatadetails.add(rs.getString(1));

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return mandateDatadetails;
		}

		return mandateDatadetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getMandateDetails(java.
	 * lang.String)
	 */
	@Override
	public List<String> getFluxDetailsforNullUrn(String mandateId) {
		List<String> mandateDatadetails = new ArrayList<>();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return mandateDatadetails;
			}

			String query = "SELECT npci_txn_id,msg_id,customer_ref_number FROM npciapimandateDetails WHERE apimandid = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mandateId);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						mandateDatadetails.add(rs.getString(1));
						mandateDatadetails.add(rs.getString(2));
						mandateDatadetails.add(rs.getString(3));

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return mandateDatadetails;
		}

		return mandateDatadetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCreateEnachSession(
	 * java.lang.String)
	 */
	@Override
	public boolean getCreateEnachSession(String mKey, String mobileNo, String customerName, String custmomeremail) {
		boolean cifDatadetails = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return false;
			}

			String query = "SELECT * FROM enachcreatedet WHERE uniqueKey = ? AND mobileNo =? AND customerName =? AND custmomeremail = ?  ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mKey);
				pstmt.setString(2, mobileNo);
				pstmt.setString(3, customerName);
				pstmt.setString(4, custmomeremail);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						cifDatadetails = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return cifDatadetails;
		}

		return cifDatadetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iib.platform.services.DatabaseConnectionService#
	 * getSetEnachBillDeskSession(java.lang.String)
	 */
	@Override
	public String getSetEnachBillDeskSession(String mobileNo, String session, String billDeskvalue) {

		String result = "";
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return "";
			}

			if (billDeskvalue.equalsIgnoreCase("")) {
				String query = "SELECT billDeskCheck FROM enachpl WHERE session = ? AND mobileNo =?";

				try (PreparedStatement pstmt = connection.prepareStatement(query)) {
					pstmt.setString(1, session);
					pstmt.setString(2, mobileNo);
					try (ResultSet rs = pstmt.executeQuery()) {
						while (rs.next()) {
							result = rs.getString(1);
						}
					}
				}

			} else {
				String query = "UPDATE enachpl SET billDeskCheck = ? WHERE mobileno = ? AND session = ?";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {
					pstmt.setString(1, billDeskvalue);
					pstmt.setString(2, mobileNo);
					pstmt.setString(3, session);

					int updateRecord = pstmt.executeUpdate();

					if (updateRecord > 0) {
						result = "billDeskCheckAdded";
					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return result;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iib.platform.services.DatabaseConnectionService#
	 * getSetEnachBillDeskSession(java.lang.String)
	 */
	@Override
	public String getSetEnachPLLoginSession(String mobileNo, String session, String loanAPICompleteCheck) {
		String result = "";
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return "";
			}
			if (loanAPICompleteCheck.equalsIgnoreCase("")) {
				String query = "SELECT LoanAPICompleteCheck FROM enachpl WHERE session = ?";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {
					pstmt.setString(1, session);
					try (ResultSet rs = pstmt.executeQuery()) {
						while (rs.next()) {
							result = rs.getString(1);
						}
					}
				}
			} else {
				String query = "UPDATE enachpl SET LoanAPICompleteCheck = ? WHERE mobileno = ? AND session = ?";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {
					pstmt.setString(1, loanAPICompleteCheck);
					pstmt.setString(2, mobileNo);
					pstmt.setString(3, session);
					int updateRecord = pstmt.executeUpdate();

					if (updateRecord > 0) {
						result = "LoanAPICompleteCheckAdded";
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return result;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getLoginFSSession(java.
	 * lang.String)
	 */
	@Override
	public boolean getLoginFSSession(String mKey, String mobileNo, String encryptKey) {

		boolean cifDatadetails = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return false;
			}

			String query = "SELECT * FROM enachmandate WHERE uniqueKey = ? AND mobileNo =? AND session =? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, encryptKey);
				pstmt.setString(2, mobileNo);
				pstmt.setString(3, mKey);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						cifDatadetails = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return cifDatadetails;
		}

		return cifDatadetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getLoginDOBSession(java.
	 * lang.String)
	 */
	@Override
	public boolean getEnachPLOTPSession(String mKey, String mobileNo, String encryptKey) {
		String query = "";
		boolean cifDatadetails = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return false;
			}

			query = "SELECT * FROM enachpl WHERE mobileNo =? AND session =? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, mobileNo);
				pstmt.setString(2, mKey);

				try (ResultSet rs = pstmt.executeQuery()) {

					if (rs.getFetchSize() == 0)
						cifDatadetails = true;

					while (rs.next()) {
						log.info("I m here Only insie while");

						int callseq = rs.getInt("otpCount");
						if (callseq <= 3) {
							callseq = callseq + 1;
							cifDatadetails = false;
							query = "UPDATE enachpl SET otpCount = ?  WHERE mobileno = ? AND session = ?";
							try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
								pstmt1.setInt(1, callseq);
								pstmt1.setString(2, mobileNo);
								pstmt1.setString(3, mKey);
								pstmt1.executeUpdate();
							}
						} else {
							cifDatadetails = true;
							log.info("Notiinh is find s PL 5");
						}

					}

				}

			}
		} catch (Exception e) {
			log.info("Erron in Database Operation of Details PL 5");
			log.error(LOG_CONSTANT, e);
			return cifDatadetails;
		}

		log.debug("Final return is called from here-----{}", cifDatadetails);
		return cifDatadetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getLoginDOBSession(java.
	 * lang.String)
	 */
	@Override
	public boolean getLoginDOBSession(String mkey, String mobileNo, String encryptKey) {
		String query = "";
		boolean cifDatadetails = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return false;
			}
			query = "SELECT * FROM enachmandate WHERE mobileNo =? AND session =? ";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, mobileNo);
				pstmt.setString(2, mkey);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						int callseq = rs.getInt("otpDobCall");

						if (callseq <= 3) {
							callseq = callseq + 1;
							cifDatadetails = false;
							query = "UPDATE enachmandate SET otpDobCall = ?, uniqueKeyDob = ?  WHERE mobileno = ? AND session = ?";
							try (PreparedStatement pstmt1 = connection.prepareStatement(query)) {
								pstmt1.setInt(1, callseq);
								pstmt1.setString(2, encryptKey);
								pstmt1.setString(3, mobileNo);
								pstmt1.setString(4, mkey);

								pstmt1.executeUpdate();
							}
						} else {
							cifDatadetails = true;

						}

					}

				}

			}
		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return cifDatadetails;
		}

		return cifDatadetails;
	}

	@Override
	public boolean updateURMNONENACH(String urmn, String mandateId) {
		String query = "";
		boolean recordUpdatedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return false;
			}
			//query = "UPDATE enachcreatedet SET urnno = ?  WHERE mandateId = ? ";
			query = "UPDATE enachcreatedet SET urnno = ?  WHERE digireferenceid = ? ";		
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, urmn);
				pstmt.setString(2, mandateId);

				int updateRecord = pstmt.executeUpdate();

				if (updateRecord > 0) {
					recordUpdatedSuccess = true;
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return recordUpdatedSuccess;
		}

		return recordUpdatedSuccess;
	}

	/* Database Section for CFD PL NEW PROJECT
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#setCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public boolean setCFDplsession(String[] values) {
		boolean insertedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return insertedSuccess;
			}
			boolean recordAlreadyExist = false;

			/*
			 * Check record exist against mobile number or not
			 */

			String query = "SELECT session FROM cfdplssosessiondetails WHERE session = ? ;";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, values[12]);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}
			if (!recordAlreadyExist) {

				query = "INSERT INTO cfdplssosessiondetails(session,mobileno,emailid,cifid,dealno,detcustomername,detcustomerbank,detcustomerifsc,detcustomeracc,startdate,enddate,frequency,emiamount,createdate,redirecturl) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
				//mobileno,emailid,cifid,dealno,detcustomername,detcustomerbank,detcustomerifsc,detcustomeracc,startdate,enddate,frequency,emiamount, id, createdate 	
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(1, values[12]);
					pstmt.setString(2, values[0]);
					pstmt.setString(3, values[1]);
					pstmt.setString(4, values[2]);
					pstmt.setString(5, values[3]);
					pstmt.setString(6, values[4]);
					pstmt.setString(7, values[5]);
					pstmt.setString(8, values[6]);
					pstmt.setString(9, values[7]);
					pstmt.setString(10, values[8]);
					pstmt.setString(11, values[9]);
					pstmt.setString(12, values[10]);
					pstmt.setString(13, values[11]);
					pstmt.setString(15, values[15]);
					
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				        java.util.Date utilDate = format.parse(values[13]);
				        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());	
				        log.info("values they pass ::: {}", sqlDate);
					pstmt.setDate(14, sqlDate);				
					int recordUpdated = pstmt.executeUpdate();

					if (recordUpdated > 0) {
						insertedSuccess = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return insertedSuccess;
	}

	/* Database Section for CFD PL NEW PROJECT
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#setCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public boolean updateCFDplsession(String session, String billdesk, String status) {
		boolean updateSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return updateSuccess;
			}
			boolean recordAlreadyExist = false;

			/*
			 * Check record exist against mobile number or not
			 */

			String query = "SELECT session FROM cfdplssosessiondetails WHERE session = ? ;";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, session);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}
			if (recordAlreadyExist) {
				log.info(" we get here");
				query = "update cfdplssosessiondetails set modifieddate= current_timestamp(), billdeskresult=?, result=? where session = ?;";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(3, session);
					pstmt.setString(1, billdesk);
					pstmt.setString(2, status);
					log.info(" we get here jeee {}", pstmt.toString());
					int recordUpdated = pstmt.executeUpdate();

					if (recordUpdated > 0) {
						updateSuccess = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return updateSuccess;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public List<String> getCFDplsession(String sessionid) {
		List<String> cfdpldetails = new ArrayList<>();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return cfdpldetails;
			}

			String query = "SELECT * FROM cfdplssosessiondetails WHERE session = ? ";
			log.info("Result we get {}", query);
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, sessionid);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						cfdpldetails.add(rs.getString(1));
						cfdpldetails.add(rs.getTimestamp(15).toString());
						cfdpldetails.add(rs.getString(16));
						cfdpldetails.add(rs.getString(17));

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return cfdpldetails;
		}

		return cfdpldetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public List<String> getCFDplsession(String sessionid, String allresult) {
		List<String> cfdpldetails = new ArrayList<>();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return cfdpldetails;
			}

			String query = "SELECT * FROM cfdplssosessiondetails WHERE session = ? ";
			log.info("Result we get {}", query);
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, sessionid);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						cfdpldetails.add(rs.getString(1));
						cfdpldetails.add(rs.getString(2));
						cfdpldetails.add(rs.getString(3));
						cfdpldetails.add(rs.getString(4));
						cfdpldetails.add(rs.getString(5));
						cfdpldetails.add(rs.getString(6));
						cfdpldetails.add(rs.getString(7));
						cfdpldetails.add(rs.getString(8));
						cfdpldetails.add(rs.getString(9));
						cfdpldetails.add(rs.getString(10));
						cfdpldetails.add(rs.getString(11));
						cfdpldetails.add(rs.getString(12));
						cfdpldetails.add(rs.getString(13));
						cfdpldetails.add(rs.getTimestamp(15).toString());
						

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return cfdpldetails;
		}

		return cfdpldetails;
	}

	/* Database Section for CFD Phase 2 NEW PROJECT
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#setCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public boolean setCFDenachsession(String[] values) {
		boolean insertedSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return insertedSuccess;
			}
			boolean recordAlreadyExist = false;

			/*
			 * Check record exist against mobile number or not
			 */

			String query = "SELECT session FROM cfdenachssosessiondetails WHERE session = ? ;";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, values[12]);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}
			if (!recordAlreadyExist) {

				query = "INSERT INTO cfdenachssosessiondetails(session,mobileno,emailid,cifid,accountno,detcustomername,detcustomerbank,detcustomerifsc,detcustomeracc,startdate,enddate,frequency,emiamount,createdate,redirecturl,clickedit,autopayfactor,refno,appid) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
				//mobileno,emailid,cifid,dealno,detcustomername,detcustomerbank,detcustomerifsc,detcustomeracc,startdate,enddate,frequency,emiamount, id, createdate 	
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(1, values[12]);
					pstmt.setString(2, values[0]);
					pstmt.setString(3, values[1]);
					pstmt.setString(4, values[2]);
					pstmt.setString(5, values[3]);
					pstmt.setString(6, values[4]);
					pstmt.setString(7, values[5]);
					pstmt.setString(8, values[6]);
					pstmt.setString(9, values[7]);
					pstmt.setString(10, values[8]);
					pstmt.setString(11, values[9]);
					pstmt.setString(12, values[10]);
					pstmt.setString(13, values[11]);
					pstmt.setString(15, values[15]);
					pstmt.setString(16, values[16]);
					pstmt.setString(17, values[17]);
					pstmt.setString(18, values[18]);
					pstmt.setString(19, values[19]);
					
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				        java.util.Date utilDate = format.parse(values[13]);
				        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());	
				        log.info("values they pass ::: {}", sqlDate);
					pstmt.setDate(14, sqlDate);				
					int recordUpdated = pstmt.executeUpdate();

					if (recordUpdated > 0) {
						insertedSuccess = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return insertedSuccess;
	}

	/* Database Section for CFD PL NEW PROJECT
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#setCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public boolean updateCFDenachsession(String session, String eanchResult, String status) {
		boolean updateSuccess = false;
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return updateSuccess;
			}
			boolean recordAlreadyExist = false;

			/*
			 * Check record exist against mobile number or not
			 */

			String query = "SELECT session FROM cfdenachssosessiondetails WHERE session = ? ;";

			try (PreparedStatement pstmt = connection.prepareStatement(query)) {

				pstmt.setString(1, session);

				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
						recordAlreadyExist = true;
					}
				}
			}
			if (recordAlreadyExist) {
				log.info(" we get here");
				query = "update cfdenachssosessiondetails set modifieddate= current_timestamp(), billdeskresult=?, result=? where session = ?;";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {

					pstmt.setString(3, session);
					pstmt.setString(1, eanchResult);
					pstmt.setString(2, status);
					log.info(" we get here jeee {}", pstmt.toString());
					int recordUpdated = pstmt.executeUpdate();

					if (recordUpdated > 0) {
						updateSuccess = true;
					}
				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
		}

		return updateSuccess;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public EnachSSO getCFDenachsession(String sessionid) {
		List<String> cfdpldetails = new ArrayList<>();
		EnachSSO enachObject = new EnachSSO();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return enachObject;
			}

			//String query = "SELECT * FROM cfdenachssosessiondetails WHERE session = ? ";
			String query = "SELECT * FROM cfdenachcreatedet WHERE sessionid = '"+sessionid+"';";
			
			log.info("Result we get {}", query);
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			//	pstmt.setString(1, sessionid);
				log.info("Result we get {}", query);
				try (ResultSet rs = pstmt.executeQuery()) {
					log.info("Result we get {}", query);
					while (rs.next()) {
						log.info("Result we get {}", query);
						/*enachObject.setSession(rs.getString(1));
						enachObject.setEnachresult(rs.getString(15));
						enachObject.setEnachstatus(rs.getString(16));
						enachObject.setCreatedtime(rs.getString(21));
						enachObject.setUpdatedtime(rs.getString(22));*/
						enachObject.setSession(sessionid);
						enachObject.setEnachresult(rs.getString("digistatus")==null?"":rs.getString("digistatus"));
						enachObject.setDetcustomeracc(rs.getString("customerAccountNo")==null?"":rs.getString("customerAccountNo"));
						enachObject.setDetcustomerbank(rs.getString("destinationBank")==null?"":rs.getString("destinationBank"));
						enachObject.setDetcustomerifsc(rs.getString("destinationBankId")==null?"":rs.getString("destinationBankId"));
						enachObject.setDetcustomername(rs.getString("customerName")==null?"":rs.getString("customerName"));
						enachObject.setDigioid(rs.getString("digireferenceid")==null?"":rs.getString("digireferenceid"));
						enachObject.setFluxstatus(rs.getString("fluxstatus")==null?"":rs.getString("fluxstatus"));
						enachObject.setFluxmessage(rs.getString("fluxMessage")==null?"":rs.getString("fluxMessage"));
						enachObject.setEmailid(rs.getString("custmomeremail")==null?"":rs.getString("custmomeremail"));
						enachObject.setMobileno(rs.getString("mobileNo")==null?"":rs.getString("mobileNo"));
						enachObject.setSmiamount(rs.getString("maximum_amount")==null?"":rs.getString("maximum_amount"));	
						enachObject.setEmiamount(rs.getString("emiAmount")==null?"":rs.getString("emiAmount"));
						enachObject.setCifid(rs.getString("refno")==null?"":rs.getString("refno"));
						enachObject.setAccountno(rs.getString("accountNo")==null?"":rs.getString("accountNo"));
						enachObject.setUrmn(rs.getString("urnno")==null?"":rs.getString("urnno"));
						enachObject.setFrequency(rs.getString("transferFrequency")==null?"":rs.getString("transferFrequency"));				
						enachObject.setStartdate(rs.getString("startDate")==null?"":rs.getString("startDate"));
						enachObject.setEnddate(rs.getString("endDate")=="1900-01-01"?"":rs.getString("endDate"));
						enachObject.setUpdatedtime(rs.getDate("updated_date").toString()==null?(rs.getDate("created_date").toString()+" " + rs.getTime("created_date").toString()):(rs.getDate("updated_date").toString()+" "+ rs.getTime("updated_date").toString()));
						enachObject.setReferralCode(rs.getString("referralCode")==null?"":rs.getString("referralCode"));
						
						
						log.info("Result we get {}", query);
						String query1 = "SELECT * FROM cfdnpciapimandateDetails where apimandid ='"+enachObject.getDigioid()+"';";
						try (PreparedStatement pstmt1 = connection.prepareStatement(query1)) {
							try (ResultSet rs1 = pstmt1.executeQuery()) {
								log.info("Result we get {}", query1);
								while (rs1.next()) {
									enachObject.setUrmn(rs1.getString("umrn")==null?"":rs1.getString("umrn"));
									enachObject.setNpcistatus(rs1.getString("current_status")==null?"":rs1.getString("current_status"));
									enachObject.setNpcitxid(rs1.getString("npci_txn_id")==null?"":rs1.getString("npci_txn_id"));
									enachObject.setNpcirejectreason(rs1.getString("txn_reject_reason")==null?"":rs1.getString("txn_reject_reason"));
								}
							}
							}
						
						
						

					}

				}
			}

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return (new EnachSSO());
		}

		return enachObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.DatabaseConnectionService#getCFDplsession(java.
	 * lang.String)
	 */
	@Override
	public EnachSSO getCFDenachsession(String sessionid, String allresult) {
		List<String> cfdpldetails = new ArrayList<>();
		EnachSSO enachObject = new EnachSSO();
		try (Connection connection = getConnection()) {
			if (null == connection) {
				return enachObject;
			}

			String query = "SELECT * FROM cfdenachssosessiondetails WHERE session = ? ";
			log.info("Result we get {}", query);
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, sessionid);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {

						enachObject.setSession(rs.getString(1));
						enachObject.setMobileno(rs.getString(2));
						enachObject.setEmailid(rs.getString(3));
						enachObject.setCifid(rs.getString(4));
						enachObject.setAccountno(rs.getString(5));
						enachObject.setDetcustomername(rs.getString(6));
						enachObject.setDetcustomerbank(rs.getString(7));
						enachObject.setDetcustomerifsc(rs.getString(8));
						enachObject.setDetcustomeracc(rs.getString(9));
						enachObject.setStartdate(rs.getString(10));
						enachObject.setEnddate(rs.getString(11));
						enachObject.setFrequency(rs.getString(12));
						enachObject.setEmiamount(rs.getString(13));
						enachObject.setRedirecturl(rs.getString(17));
						enachObject.setClickedit(rs.getString(18));
						enachObject.setRefno(rs.getString(19));
						enachObject.setAutopayfactor(rs.getString(20));
						enachObject.setCreatedtime(rs.getString(22));
						enachObject.setAppid(rs.getString(21));	
						enachObject.setUnauthstatus("authorize");	
					}

				}
			}
			
			query = "SELECT sessionid, created_date FROM cfdenachcreatedet WHERE sessionid = ? and created_date > CURRENT_TIMESTAMP - INTERVAL 24 HOUR; ";
			
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, sessionid);
				try (ResultSet rs = pstmt.executeQuery()) {

					while (rs.next()) {
												
						enachObject.setUnauthstatus("unauthorize");
					}
				}
			}
			
			

		} catch (Exception e) {
			log.error(LOG_CONSTANT, e);
			return (new EnachSSO());
		}

		return enachObject;
	}

	

	@Override
	public ResultSet getSelectedResult(String sql, Connection connection) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			if (connection != null) {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(sql);
				return rs;
			} else {
				log.error("connection null");
			}
		} catch (SQLException e) {
			log.error("SQL Exception in  getSelectedResult {} ", e);
		}
		return null;
	}

}
