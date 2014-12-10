/*ZIRIX CONTROL CENTER - SCHEDULE BEAN
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLU��ES EM RASTREAMENTO
TECNOLOGIAS UTILIZADAS: JAVA*/
package zirix.zxcc.server;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.dao.*;
public class ScheduleBean {
	private Integer COD_USUARIO_ = null;
	public ScheduleBean(String pkVal) {
		COD_USUARIO_ = new Integer(pkVal);
	}
	@SuppressWarnings("finally")
	public Vector<String[]> getWork(){
		Vector<String[]> work = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT " + ZXMain.DB_NAME_ + "SCHED_WORK.WORK_ID "					//00
					+ 														   "     , " + ZXMain.DB_NAME_ + "SCHED_WORK.WORK_NAME "				//01
					+ 														   "     , " + ZXMain.DB_NAME_ + "SCHED_PROCESS.PROCESS_NAME "			//02
					+ 														   "     , IFNULL(" + ZXMain.DB_NAME_ + "SCHED_WORK.COD_USUARIO,0) "	//03
					+ 														   "     , " + ZXMain.DB_NAME_ + "SCHED_WORK.PK_COLUMN "				//04
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "SCHED_WORK "
					+ 														   "     , " + ZXMain.DB_NAME_ + "SCHED_PROCESS "
					+ 														   "     , " + ZXMain.DB_NAME_ + "WORK_USER "
					+ 														   "     , " + ZXMain.DB_NAME_ + "RESTRICTION_WORK "
					+ 														   " WHERE " + ZXMain.DB_NAME_ + "WORK_USER.WORK_GROUP_ID = " + ZXMain.DB_NAME_ + "SCHED_WORK.WORK_GROUP_ID "
					+ 														   "   AND " + ZXMain.DB_NAME_ + "SCHED_WORK.PROCESS_ID = " + ZXMain.DB_NAME_ + "SCHED_PROCESS.PROCESS_ID "
					+ 														   "   AND " + ZXMain.DB_NAME_ + "SCHED_WORK.END_TIMESTAMP IS NULL "
					+ 														   "   AND " + ZXMain.DB_NAME_ + "SCHED_WORK.SCHED_TIMESTAMP < NOW() + INTERVAL 1 DAY "
					+ 														   "   AND " + ZXMain.DB_NAME_ + "SCHED_WORK.RESTRICTION_ID = " + ZXMain.DB_NAME_ + "RESTRICTION_WORK.RESTRICTION_ID "
					+ 														   "   AND " + ZXMain.DB_NAME_ + "WORK_USER.COD_USUARIO = " + COD_USUARIO_
					+ 														   " ORDER BY (TIMEDIFF(NOW(),SCHED_WORK.SCHED_TIMESTAMP)/TIMEDIFF((SCHED_WORK.SCHED_TIMESTAMP + INTERVAL CAST(RESTRICTION_WORK.RESTRICTION_VALUE AS TIME) HOUR_SECOND),SCHED_WORK.SCHED_TIMESTAMP)*100) DESC ");
			for (int i=0;i < values.size();i++) {
				String[] attList = new String[5];
				attList[0] = (String) values.get(i)[0].toString();
				attList[1] = (String) values.get(i)[1];
				attList[2] = (String) values.get(i)[2];
				attList[3] = (String) values.get(i)[3].toString();
				attList[4] = (String) values.get(i)[4].toString();
				work.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return work;
		}
	}
	@SuppressWarnings("finally")
	public Vector<String[]> getWorkService(String workName, String processName){
		Vector<String[]> workService = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT  " + ZXMain.DB_NAME_ + "WORK_SERVICE.SERVICE_NAME "
					+ " 											 FROM " + ZXMain.DB_NAME_ + "WORK_SERVICE "
					+ " 											    , " + ZXMain.DB_NAME_ + "DEFINED_WORK "
					+ " 											    , " + ZXMain.DB_NAME_ + "DEFINED_PROCESS "
					+ "                                             WHERE " + ZXMain.DB_NAME_ + "DEFINED_WORK.PROCESS_ID =  " + ZXMain.DB_NAME_ + "WORK_SERVICE.PROCESS_ID "
					+ "                                               AND " + ZXMain.DB_NAME_ + "DEFINED_WORK.WORK_ID =  " + ZXMain.DB_NAME_ + "WORK_SERVICE.WORK_ID "
					+ "                                               AND " + ZXMain.DB_NAME_ + "DEFINED_PROCESS.PROCESS_NAME = N'" + processName + "' "
					+ "                                               AND " + ZXMain.DB_NAME_ + "DEFINED_WORK.WORK_NAME = N'" + workName + "' ");

			for (int i=0;i < values.size();i++) {
				String[] attList = new String[1];
				attList[0] = (String) values.get(i)[0];
				workService.add(attList);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return workService;
		}
	}
	public void setStartTimestamp(String work_id){
		if(work_id.compareTo("0") != 0){
			try {
				DAOManager.getInstance().executeUpdate("UPDATE " + ZXMain.DB_NAME_ + "SCHED_WORK "
						+ 							  "   SET START_TIMESTAMP = NOW() "
						+ 							  "     , COD_USUARIO = " + COD_USUARIO_
						+							  "     , WORK_STATE_ID = 1 "
						+ 							  " WHERE START_TIMESTAMP IS NULL AND WORK_ID = " + work_id);
			}catch (SQLException ex) {
				ex.printStackTrace();
			}finally{}
		}
	}
	@SuppressWarnings("finally")
	public double getPercentageTimeWork(String WORK_ID){
		double percentage = 0;
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT TIMEDIFF(NOW(),SCHED_WORK.SCHED_TIMESTAMP)/TIMEDIFF((SCHED_WORK.SCHED_TIMESTAMP + INTERVAL CAST(RESTRICTION_WORK.RESTRICTION_VALUE AS TIME) HOUR_SECOND),SCHED_WORK.SCHED_TIMESTAMP)*100 "
					+ 														   " FROM " + ZXMain.DB_NAME_ + "SCHED_WORK "
					+ 														   "    , " + ZXMain.DB_NAME_ + "RESTRICTION_WORK "
					+ 														   " WHERE SCHED_WORK.RESTRICTION_ID = RESTRICTION_WORK.RESTRICTION_ID "
					+ 														   "   AND SCHED_WORK.WORK_ID = " + WORK_ID);

			for (int i=0;i < values.size();i++) {
				String[] attList = new String[1];
				attList[0] = values.get(i)[0].toString();
				percentage = Double.parseDouble(attList[0]);
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return percentage;
		}
	}
}