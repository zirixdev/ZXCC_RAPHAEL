/*ZIRIX CONTROL CENTER - SCHEDULE
DESENVOLVIDO POR RAPHAEL B. MARQUES

CLIENTE: ZIRIX SOLU��ES EM RASTREAMENTO
TECNOLOGIAS UTILIZADAS: JAVA*/
package zirix.zxcc.server;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import zirix.zxcc.server.dao.*;
public class Schedule {
	private SchedWorkDAO dao_ = null;
	private Integer WORK_ID_ = null;
	public Schedule(int workID){
		setPk(workID);
	}
	public void setPk(int pkVal) {
		WORK_ID_ = pkVal;
		PkList pkList = new PkList();
	    pkList.put("WORK_ID",WORK_ID_);
	    dao_ = new SchedWorkDAO(pkList);
	    try {
	    	dao_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {}
	}
	public void changeState(int PK_COLUMN) throws SQLException{
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT " + ZXMain.DB_NAME_ + "DEFINED_PROCESS_STATE.NEXT_STATE_ID "
					+ "                                                           FROM " + ZXMain.DB_NAME_ + "DEFINED_PROCESS_STATE "
					+ "                                                          WHERE " + ZXMain.DB_NAME_ + "DEFINED_PROCESS_STATE.STATE_NUM = " + (Integer)dao_.getAttValueFor("PROCESS_STATE_ID")
					+ "                                                            AND " + ZXMain.DB_NAME_ + "DEFINED_PROCESS_STATE.PROCESS_ID = " + (Integer)dao_.getAttValueFor("DEFINED_PROCESS_ID"));
			for (int i=0;i < values.size();i++) {
				String[] attList = new String[1];
				attList[0] = (String) values.get(i)[0].toString();
				setNextState(Integer.parseInt(attList[0]));
			}
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}finally{}
		createSchedWork((Integer)dao_.getAttValueFor("PROCESS_ID"),(Integer)dao_.getAttValueFor("DEFINED_PROCESS_ID"),nextState_,0,PK_COLUMN);
		try{
			DAOManager.getInstance().executeUpdate("UPDATE " + ZXMain.DB_NAME_ + "SCHED_PROCESS "
					+ 							  "   SET STATE_ID = " + nextState_
					+ 							  " WHERE PROCESS_ID = " + (Integer)dao_.getAttValueFor("PROCESS_ID"));
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally{}
	}
	public static void createSchedWork(int PROCESS_ID, int DEFINED_PROCESS_ID, int STATE_ID, int DEPENDENCY_WORK_ID, int PK_COLUMN) throws SQLException{
		SchedWorkDAO daoSchedWork = new SchedWorkDAO();
		Vector<String[]> DefinedWork = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT WORK_NAME "				//00
					+														  "     , RESTRICTION_ID "			//01
					+ 														  "     , PROCESS_STATE_ID "		//02
					+ 														  "     , WORK_ALERT_ID "			//03
					+ 														  "     , DEPENDENCY_WORK_ID "		//04
					+ 														  "     , WORK_GROUP_ID "			//05
					+ 														  "     , WORK_ID "					//06
					+ 														  "  FROM " + ZXMain.DB_NAME_ + "DEFINED_WORK "
					+ 														  " WHERE PROCESS_STATE_ID=" + STATE_ID
					+ 														  "   AND PROCESS_ID=" + DEFINED_PROCESS_ID
					+ 														  "   AND DEPENDENCY_WORK_ID=" + DEPENDENCY_WORK_ID +";");
		   for (int i=0;i < values.size();i++) {
			   String[] attList = new String[7];
			   attList[0] = (String) values.get(i)[0];
			   attList[1] = values.get(i)[1].toString();
			   attList[2] = values.get(i)[2].toString();
			   attList[3] = values.get(i)[3].toString();
			   attList[4] = values.get(i)[4].toString();
			   attList[5] = values.get(i)[5].toString();
			   attList[6] = values.get(i)[6].toString();
			   DefinedWork.add(attList);
		   }
	   }catch(SQLException ex){
		   ex.printStackTrace();
	   }finally{
		   for(int i=0; i<DefinedWork.size();i++){
			   daoSchedWork.setAttValueFor("WORK_NAME",(DefinedWork.elementAt(i)[0].trim()).toString());
			   daoSchedWork.setAttValueFor("RESTRICTION_ID",Integer.parseInt(DefinedWork.elementAt(i)[1].trim()));
			   daoSchedWork.setAttValueFor("PROCESS_STATE_ID",Integer.parseInt(DefinedWork.elementAt(i)[2].trim()));
			   daoSchedWork.setAttValueFor("WORK_ALERT_ID",Integer.parseInt(DefinedWork.elementAt(i)[3].trim()));
			   daoSchedWork.setAttValueFor("DEPENDENCY_WORK_ID",Integer.parseInt(DefinedWork.elementAt(i)[4].trim()));
			   daoSchedWork.setAttValueFor("WORK_GROUP_ID",Integer.parseInt(DefinedWork.elementAt(i)[5].trim()));
			   daoSchedWork.setAttValueFor("DEFINED_WORK_ID",Integer.parseInt(DefinedWork.elementAt(i)[6].trim()));
			   daoSchedWork.setAttValueFor("PROCESS_ID",PROCESS_ID);
			   daoSchedWork.setAttValueFor("DEFINED_PROCESS_ID",DEFINED_PROCESS_ID);
			   daoSchedWork.setAttValueFor("PK_COLUMN",PK_COLUMN);
			   daoSchedWork.setAttValueFor("WORK_STATE_ID",0);
			   daoSchedWork.setAttValueFor("ALERT_STATUS",0);
			   daoSchedWork.Create();
		   }
	   }
	}
	public static void createSameWork(int WORK_ID) throws SQLException{
		SchedWorkDAO daoSchedWork = new SchedWorkDAO();
		Vector<String[]> workToCreate = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT WORK_NAME "								//00
					+ 														   "     , PROCESS_ID "								//01
					+ 														   "     , RESTRICTION_ID "							//02
					+ 														   "     , PROCESS_STATE_ID "						//03
					+ 														   "     , WORK_ALERT_ID "							//04
					+ 														   "     , WORK_GROUP_ID "							//05
					+ 														   "     , DEPENDENCY_WORK_ID "						//06
					+ 														   "     , DEFINED_PROCESS_ID "						//07
					+ 														   "     , DEFINED_WORK_ID "						//08
					+ 														   "     , PK_COLUMN "								//09
					+														   "     , SCHED_TIMESTAMP + INTERVAL '1' DAY "		//10
					+ 														   "  FROM " + ZXMain.DB_NAME_ + "SCHED_WORK "
					+ 														   " WHERE WORK_ID = " + WORK_ID + ";");
			for(int i=0;i < values.size();i++){
				String[] attList = new String[11];
				attList[0] = (String) values.get(i)[0];
				attList[1] = values.get(i)[1].toString();
				attList[2] = values.get(i)[2].toString();
				attList[3] = values.get(i)[3].toString();
				attList[4] = values.get(i)[4].toString();
				attList[5] = values.get(i)[5].toString();
				attList[6] = values.get(i)[6].toString();
				attList[7] = values.get(i)[7].toString();
				attList[8] = values.get(i)[8].toString();
				attList[9] = values.get(i)[9].toString();
				attList[10] = values.get(i)[10].toString();
				workToCreate.add(attList);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			for(int i=0; i<workToCreate.size();i++){
				daoSchedWork.setAttValueFor("WORK_NAME",(workToCreate.elementAt(i)[0].trim()).toString());
				daoSchedWork.setAttValueFor("PROCESS_ID",Integer.parseInt(workToCreate.elementAt(i)[1].trim()));
				daoSchedWork.setAttValueFor("RESTRICTION_ID",Integer.parseInt(workToCreate.elementAt(i)[2].trim()));
				daoSchedWork.setAttValueFor("PROCESS_STATE_ID",Integer.parseInt(workToCreate.elementAt(i)[3].trim()));
				daoSchedWork.setAttValueFor("WORK_ALERT_ID",Integer.parseInt(workToCreate.elementAt(i)[4].trim()));
				daoSchedWork.setAttValueFor("WORK_GROUP_ID",Integer.parseInt(workToCreate.elementAt(i)[5].trim()));
				daoSchedWork.setAttValueFor("DEPENDENCY_WORK_ID",Integer.parseInt(workToCreate.elementAt(i)[6].trim()));
				daoSchedWork.setAttValueFor("DEFINED_PROCESS_ID",Integer.parseInt(workToCreate.elementAt(i)[7].trim()));
				daoSchedWork.setAttValueFor("DEFINED_WORK_ID",Integer.parseInt(workToCreate.elementAt(i)[8].trim()));
				daoSchedWork.setAttValueFor("PK_COLUMN",Integer.parseInt(workToCreate.elementAt(i)[9].trim()));
				daoSchedWork.setAttValueFor("SCHED_TIMESTAMP",workToCreate.elementAt(i)[10].trim());
				daoSchedWork.setAttValueFor("WORK_STATE_ID",0);
				daoSchedWork.setAttValueFor("ALERT_STATUS",0);
				daoSchedWork.Create();
			}
		}
	}
	public static void createSchedWorkAgendamento(int PROCESS_ID, int DEFINED_PROCESS_ID, int STATE_ID, int DEPENDENCY_WORK_ID, int PK_COLUMN, String DATETIME_AGENDAMENTO) throws SQLException{
		SchedWorkDAO daoSchedWork = new SchedWorkDAO();
		Vector<String[]> DefinedWork = new Vector<String[]>();
		try{
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT WORK_NAME "				//00
					+														  "     , RESTRICTION_ID "			//01
					+ 														  "     , PROCESS_STATE_ID "		//02
					+ 														  "     , WORK_ALERT_ID "			//03
					+ 														  "     , DEPENDENCY_WORK_ID "		//04
					+ 														  "     , WORK_GROUP_ID "			//05
					+ 														  "     , WORK_ID "					//06
					+ 														  "  FROM " + ZXMain.DB_NAME_ + "DEFINED_WORK "
					+ 														  " WHERE PROCESS_STATE_ID=" + STATE_ID
					+ 														  "   AND PROCESS_ID=" + DEFINED_PROCESS_ID
					+ 														  "   AND DEPENDENCY_WORK_ID=" + DEPENDENCY_WORK_ID +";");
		   for (int i=0;i < values.size();i++) {
			   String[] attList = new String[7];
			   attList[0] = (String) values.get(i)[0];
			   attList[1] = values.get(i)[1].toString();
			   attList[2] = values.get(i)[2].toString();
			   attList[3] = values.get(i)[3].toString();
			   attList[4] = values.get(i)[4].toString();
			   attList[5] = values.get(i)[5].toString();
			   attList[6] = values.get(i)[6].toString();
			   DefinedWork.add(attList);
		   }
	   }catch(SQLException ex){
		   ex.printStackTrace();
	   }finally{
		   for(int i=0; i<DefinedWork.size();i++){
			   daoSchedWork.setAttValueFor("WORK_NAME",(DefinedWork.elementAt(i)[0].trim()).toString());
			   daoSchedWork.setAttValueFor("RESTRICTION_ID",Integer.parseInt(DefinedWork.elementAt(i)[1].trim()));
			   daoSchedWork.setAttValueFor("PROCESS_STATE_ID",Integer.parseInt(DefinedWork.elementAt(i)[2].trim()));
			   daoSchedWork.setAttValueFor("WORK_ALERT_ID",Integer.parseInt(DefinedWork.elementAt(i)[3].trim()));
			   daoSchedWork.setAttValueFor("DEPENDENCY_WORK_ID",Integer.parseInt(DefinedWork.elementAt(i)[4].trim()));
			   daoSchedWork.setAttValueFor("WORK_GROUP_ID",Integer.parseInt(DefinedWork.elementAt(i)[5].trim()));
			   daoSchedWork.setAttValueFor("DEFINED_WORK_ID",Integer.parseInt(DefinedWork.elementAt(i)[6].trim()));
			   daoSchedWork.setAttValueFor("SCHED_TIMESTAMP",DATETIME_AGENDAMENTO);
			   daoSchedWork.setAttValueFor("PROCESS_ID",PROCESS_ID);
			   daoSchedWork.setAttValueFor("DEFINED_PROCESS_ID",DEFINED_PROCESS_ID);
			   daoSchedWork.setAttValueFor("PK_COLUMN",PK_COLUMN);
			   daoSchedWork.setAttValueFor("WORK_STATE_ID",0);
			   daoSchedWork.setAttValueFor("ALERT_STATUS",0);
			   daoSchedWork.Create();
		   }
	   }
	}
	private int nextState_;
    public void setNextState(int nextState) {nextState_ = nextState;}
}
