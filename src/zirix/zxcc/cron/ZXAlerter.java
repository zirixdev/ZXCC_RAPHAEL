package zirix.zxcc.cron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Vector;
import antena.mailer.ADGoogleMailer;
import javax.mail.*;
import java.util.logging.*;

public class ZXAlerter {


	public static String OVERTIME_SUBJECT = "Tarefa com tempo excedido";
	public static String RISK_SUBJECT = "Tarefa com risco alto de tempo excedido";

	public static String SCHEDED_WORK_FLAG = "0";
	public static String STARTED_WORK_FLAG = "1";
	public static String FINISHED_WORK_FLAG = "2";

	public static String ALERT_PENDING = "0";
	public static String ALERT_TOEXPIRE_SENT = "1";
	public static String ALERT_EXPIRED_SENT = "2";

	private String username_; 
	private String password_; 


	public ZXAlerter(String username,String password) {

		username_ = username.toString();
		password_ = password.toString();
	}

	public static void main(String[] args) {

	  try {

		validateInput(args);

		ZXAlerter alerter = new ZXAlerter(args[3],args[4]);

		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		Connection con = DriverManager.getConnection(args[1] + args[2]);

		// SCHEDED_WORKs
		PreparedStatement stmtTOEXPIRE = con.prepareStatement("SELECT NOW()"
				+ 											  "     , SCHED_TIMESTAMP"
				+ 											  "     , RESTRICTION_ID"
				+ 											  "     , WORK_ID"
				+ 											  "     , WORK_NAME"
				+ 											  "     , SCHED_WORK.COD_USUARIO "
				+ 											  "     , NOME_USUARIO "
				+ 											  "  FROM SCHED_WORK,USUARIO"
				+ 											  " WHERE WORK_STATE_ID = " + SCHEDED_WORK_FLAG
				+											  "   AND ALERT_STATUS = " + ALERT_PENDING
				+											  "   AND SCHED_WORK.COD_USUARIO = USUARIO.COD_USUARIO ");

		ResultSet resTOEXPIRE = stmtTOEXPIRE.executeQuery();

		// STARTED_WORKs
		PreparedStatement stmtEXPIRED = con.prepareStatement("SELECT NOW()"
				+ 											  "     , SCHED_TIMESTAMP"
				+ 											  "     , RESTRICTION_ID"
				+ 											  "     , WORK_ID"
				+ 											  "     , WORK_NAME"
				+ 											  "     , SCHED_WORK.COD_USUARIO "
				+ 											  "     , NOME_USUARIO "
				+ 											  "  FROM SCHED_WORK,USUARIO"
				+ 											  " WHERE WORK_STATE_ID != " + FINISHED_WORK_FLAG
				+											  "   AND ALERT_STATUS != " + ALERT_EXPIRED_SENT
				+											  "   AND SCHED_WORK.COD_USUARIO = USUARIO.COD_USUARIO ");

		ResultSet resEXPIRED = stmtEXPIRED.executeQuery();

		while (resTOEXPIRE.next()) {


			String now = resTOEXPIRE.getString(1);
			Timestamp now_time = Timestamp.valueOf(now);

			String restriction_id = resTOEXPIRE.getString(3);

			PreparedStatement stmt2 = con.prepareStatement("SELECT RESTRICTION_VALUE FROM RESTRICTION_WORK WHERE RESTRICTION_ID = " + restriction_id);
			ResultSet res2 = stmt2.executeQuery();

			Timestamp sched_time = resTOEXPIRE.getTimestamp(2);
			Integer work_id = resTOEXPIRE.getInt(4);
			String work_name = resTOEXPIRE.getString(5);
			String nome_usuario = resTOEXPIRE.getString(7);

			res2.next();
			Time restriction_val = res2.getTime(1);

            		//Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "will check TOEXPIRE : " + work_id); 

			if (alerter.evalTOEXPIRE(now_time,sched_time,restriction_val)) {

				PreparedStatement stmt3 = con.prepareStatement("SELECT EMAIL "
						+									   "  FROM USER_GROUP_ALERT"
						+									   " WHERE GROUP_ALERT_ID in (Select GROUP_ALERT_ID"
						+									   "                            From WORK_ALERT"
						+									   "                           Where WORK_ALERT_ID = (select WORK_ALERT_ID"
						+									   "                                                    from SCHED_WORK"
						+									   "                                                   where WORK_ID =?))");
				stmt3.setInt(1,work_id);
				ResultSet res3 = stmt3.executeQuery();

				Vector<String> email_TO_List = new Vector<String>();
				while (res3.next()) 
					email_TO_List.add(res3.getString(1));	

				alerter.notify(work_id,work_name,nome_usuario,RISK_SUBJECT,email_TO_List);
				
				PreparedStatement stmt4 = con.prepareStatement("UPDATE SCHED_WORK SET ALERT_STATUS =? where WORK_ID =?");
				stmt4.setInt(1,Integer.parseInt(ALERT_TOEXPIRE_SENT));
				stmt4.setInt(2,work_id);
				stmt4.executeUpdate();
			}
		}

		while (resEXPIRED.next()) {

			String now = resEXPIRED.getString(1);
			Timestamp now_time = Timestamp.valueOf(now);

			String resctriction_id = resEXPIRED.getString(3);

			PreparedStatement stmt2 = con.prepareStatement("SELECT RESTRICTION_VALUE FROM RESTRICTION_WORK WHERE RESTRICTION_ID = " + resctriction_id);
			ResultSet res2 = stmt2.executeQuery();

			Timestamp sched_time = resEXPIRED.getTimestamp(2);
			Integer work_id = resEXPIRED.getInt(4);
			String work_name = resEXPIRED.getString(5);
			String nome_usuario = resEXPIRED.getString(7);

			res2.next();
			Time restriction_val = res2.getTime(1);

            		//Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "will check EXPIRED : " + work_id);

			if (alerter.evalEXPIRED(now_time,sched_time,restriction_val)) {

				PreparedStatement stmt3 = con.prepareStatement("SELECT EMAIL "
						+									   "  FROM USER_GROUP_ALERT"
						+									   " WHERE GROUP_ALERT_ID in (Select GROUP_ALERT_ID"
						+									   "                            From WORK_ALERT"
						+									   "                           Where WORK_ALERT_ID = (select WORK_ALERT_ID"
						+									   "                                                    from SCHED_WORK"
						+									   "                                                   where WORK_ID =?))");
				stmt3.setInt(1,work_id);
				ResultSet res3 = stmt3.executeQuery();

				Vector<String> email_TO_List = new Vector<String>();
				while (res3.next()) 
					email_TO_List.add(res3.getString(1));	

				alerter.notify(work_id,work_name,nome_usuario,OVERTIME_SUBJECT,email_TO_List);

				PreparedStatement stmt4 = con.prepareStatement("UPDATE SCHED_WORK SET ALERT_STATUS =? where WORK_ID =?");
				stmt4.setInt(1,Integer.parseInt(ALERT_EXPIRED_SENT));
				stmt4.setInt(2,work_id);
				stmt4.executeUpdate();
			}
		}

	  } catch(Exception ex) {

		ex.printStackTrace();
	  }
	}

	public static void validateInput(String[] args) throws IllegalArgumentException {

		// including the LOGCONFIG
		if (args.length < 5)
			throw new IllegalArgumentException("Usage : ZXMailer [DATABASE URL] [JDBC USER:PASSWORD] [MAIL USERNAME] [MAIL PASSWORD] ... ");

	}

	/*
	 * avalia o cenario onde a tarefa se aproxima de 75% do tempo limite e ainda nao foi iniciada
	 * existe um problema de precisao aqui ... alteramos a razao para 60% para refletir os 75% - TODO mvera
	 */
	public boolean evalTOEXPIRE(Timestamp now,Timestamp sched,Time rest) {


		Calendar cal_sched_plus_rest = Calendar.getInstance();
		cal_sched_plus_rest.setTimeInMillis(sched.getTime());
		cal_sched_plus_rest.add(Calendar.HOUR,rest.getHours());
		cal_sched_plus_rest.add(Calendar.MINUTE,rest.getMinutes());

		Calendar cal_now = Calendar.getInstance();
		cal_now.setTimeInMillis(now.getTime());

		// just in case...
		// neste caso o EXPIRED somente serah enviado...
		//
		if (now.after(cal_sched_plus_rest.getTime()))
			return false;

            	//Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "sched plus rest is : " + cal_sched_plus_rest.getTime());
            	//Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "now is : " + cal_now.getTime());

		long diff_millis = new Double(new Double(cal_sched_plus_rest.getTimeInMillis()).doubleValue() - new Double(now.getTime()).doubleValue()).longValue(); 

		long diffMinutes = diff_millis / (60 * 1000) % 60;
		long diffHours = diff_millis / (60 * 60 * 1000) % 24;
		long diff_total_minutes = diffMinutes + diffHours*60;

		int restMinutes = rest.getMinutes();
		int restHours = rest.getHours();
		int rest_total_minutes = restMinutes + restHours*60;

            	//Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "diff_total_minutes is : " + diff_total_minutes);
            	//Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "rest_total_minutes is : " + rest_total_minutes);

		double time_ratio = (new Double(diff_total_minutes).doubleValue()*100)/new Integer(rest_total_minutes).doubleValue();
            	//Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "time_ratio is : " + time_ratio);


		if (time_ratio > .75)
			return true;

		return false;

	}

	/*
	 * avalia o cenario onde a tarefa estourou o tempo limite sem ter sido completada
	 */
	public boolean evalEXPIRED(Timestamp now,Timestamp sched,Time rest) {

		long sched_millis = sched.getTime();
		long rest_millis = rest.getTime();

		Calendar cal_sched_plus_rest = Calendar.getInstance();
		cal_sched_plus_rest.setTimeInMillis(sched.getTime());
		cal_sched_plus_rest.add(Calendar.HOUR,rest.getHours());
		cal_sched_plus_rest.add(Calendar.MINUTE,rest.getMinutes());

		Timestamp sched_plus_rest = new Timestamp(cal_sched_plus_rest.getTimeInMillis());

            	//Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "sched plus rest is : " + sched_plus_rest);
            	//Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "now is : " + now);

		if (now.after(sched_plus_rest))
			return true;

		return false;
	}


	/*
	 * envia e-mail de notificacao com o subject em questao
	 */
	public void notify(Integer work_id,String work_name,String nome_usuario,String subject,Vector<String> email_TO_List) throws MessagingException {

		ADGoogleMailer mailer = new ADGoogleMailer(username_,password_);

		String msg = "ZXCC MAILER : " + work_id + " | " + work_name + " | " + nome_usuario;

		for (int i=0;i < email_TO_List.size();i++) {
			mailer.send(email_TO_List.elementAt(i),msg,subject);
            		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, "sending email to : " + email_TO_List.elementAt(i));
		}
	}
}
