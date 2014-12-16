/*ZIRIX CONTROL CENTER - DAO MANAGER
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import zirix.zxcc.server.*;

public class DAOManager {

	public static final int CONNECTION_CHECK_TIMEOUT_IN_SECS = 10;

	public static DAOManager getInstance() {
        return DAOManagerSingleton.INSTANCE.get();
    }  

    public Connection getConnection() throws SQLException {
        try{
        	
        	Connection con = src.getConnection();
		con.setAutoCommit(true);

        	return con;
        }
        catch(SQLException e) { throw e; }
    }
    
    @SuppressWarnings("finally")
	public Connection getLocalConnection() throws SQLException {
    	
    	if(ZXMain.LOCAL_.compareTo("SQLSERVER") == 0){
	    	String url="jdbc:sqlserver://192.168.0.50:1433;integratedSecurity=true";
	    	Connection conn = null;
	    	try{
	    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    	    conn = DriverManager.getConnection(url);
	    	}catch (Exception ex){
	    		ex.printStackTrace();
	    	}finally{
	    		return conn;
	    	}
    	}else if(ZXMain.LOCAL_.compareTo("DEV") == 0){
    		String url="jdbc:mysql://192.168.0.50/ZX_CC_DEV";
        	Connection conn = null;
        	try{
        		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        		conn = DriverManager.getConnection (url, "root", "pinguim01");
        	}catch (Exception ex){
        		ex.printStackTrace();
        	}finally{
        		return conn;
        	}
    	}else{
    		String url="jdbc:mysql://192.168.0.32/ZX_CC_PROD";
        	Connection conn = null;
        	try{
        		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        		conn = DriverManager.getConnection (url, "root", "");
        	}catch (Exception ex){
        		ex.printStackTrace();
        	}finally{
        		return conn;
        	}
    	}
    }
    
    public void executeUpdate(String query) throws SQLException {
		PreparedStatement stmt = null;
		Connection con = getConnection();
		try
		{                       	        	
		   stmt = con.prepareStatement(query);
		   stmt.executeUpdate();        			        		        
		} catch(SQLException e){ 
		    throw e; 
		}
		finally {
			if(ZXMain.LOCAL_.compareTo("SQLSERVER") == 0){
				con.commit();
			}
			if (stmt != null) stmt.close();
			closeConnection(con);
		} 
    }
    
    public ArrayList<Object[]> executeQuery(String query) throws SQLException {
    	    	
        PreparedStatement stmt = null;
        ResultSet res = null;
        Connection con = getConnection();
                
        try
        {                       	        	
	       stmt = con.prepareStatement(query);
	       res = stmt.executeQuery();
	        
	        ArrayList<Object[]> values = new ArrayList<Object[]>();	    	       
	        
	        int colCount = res.getMetaData().getColumnCount();               	      
	        
	        while (res.next()) {
	        	
	        	Object[] rowValues = new Object[colCount];
	        	for (int i=1;i <= colCount;i++)	        	
	        		rowValues[i-1] = res.getObject(res.getMetaData().getColumnName(i));
	        		
	        	
	        	values.add(rowValues);
	        	
	        }

	        return values;	        			        		        
	        
        } catch(SQLException e){ 
	        
        	            
            throw e; 
        }
        
        finally {
			if(ZXMain.LOCAL_.compareTo("SQLSERVER") == 0){
        		con.commit();
    		}
        	if (res != null) res.close();
        	if (stmt != null) stmt.close();
        	closeConnection(con);
        }            	                
    	
    }
 
    public void closeConnection(Connection con) throws SQLException {
        try
        {
            if(con!=null && con.isValid(CONNECTION_CHECK_TIMEOUT_IN_SECS))
                con.close();
        }
        catch(SQLException e) { throw e; }
    }
    
    //Private
    private DataSource src;
  

    private DAOManager() throws Exception {
        try
        {
        	// Look up the JNDI data source only once at init time
  	      Context envCtx = (Context) new InitialContext().lookup("java:comp/env");
  	      src = (DataSource) envCtx.lookup("jdbc/poolConn");
  	      
        } catch(Exception e) { throw e; }
    }

    private static class DAOManagerSingleton {

        public static final ThreadLocal<DAOManager> INSTANCE;
        static
        {
            ThreadLocal<DAOManager> dm;
            try
            {
                dm = new ThreadLocal<DAOManager>(){
                	
                    @Override
                    protected DAOManager initialValue() {
                        try
                        {
                            return new DAOManager();
                        }
                        catch(Exception e)
                        {
                            return null;
                        }
                    }
                };
            }
            
            catch(Exception e) {
            	dm = null;
            }
                            
            INSTANCE = dm;
        }        
    }
}
