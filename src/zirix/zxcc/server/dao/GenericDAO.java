/*ZIRIX CONTROL CENTER - GENERIC DAO
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: Mï¿½RIO DE Sï¿½ VERA
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import zirix.zxcc.server.*;

public abstract class GenericDAO<T> {

	public static String AUTO_INCREMENT_PK_NAME = "AUTO";
	public static int AUTO_INCREMENT_PK_VALUE = -1;
	public static String CAN_DELETE_COLUMN_NAME = "deleted";
	
	public abstract void loadAttsFromResultSet(ResultSet res) throws SQLException;
	
	public static PkList createKey(String[] names,int[] values) {
		
		PkList key = new PkList();
		
		for(int i=0;i < key.size();i++)			
			key.put(names[i], new Integer(values[i]));
		
		return key;				
	}
	
           
    
    /*
    * the CRUD operators
    */
	public void Create() throws SQLException{ //Don't need to return GENERATED KEY
		PreparedStatement stmt = null;
        Connection con = DAOManager.getInstance().getConnection();

        String query = "INSERT INTO " + getTableName() + " (";
    	
        Iterator<String> kit = getAttList().keySet().iterator();
    	while (kit.hasNext()) {
    		Object key = kit.next();
    		query = query.concat(key.toString());
    		if (kit.hasNext())
    			query = query.concat(",");        		
    	}	    	

    	query = query.concat(") VALUES(");
    	
    	int valuesCount = 0;

    	if (!autoIncrement_) {

	    	Iterator<String> kit1 = getPkList().keySet().iterator();
	    	while (kit1.hasNext()) {
	    		valuesCount++;
	    		String key = kit1.next();
	    		query = query.concat("?");
	    		if (kit1.hasNext())
	    			query = query.concat(",");
	    	}
    	}

    	// then Atts...
    	Iterator<String> kit2 = getAttList().keySet().iterator();
    	if(kit2.hasNext() && !autoIncrement_)
    		query = query.concat(",");

    	while (kit2.hasNext()) {
    		valuesCount++;
    		String key = kit2.next();
    		query = query.concat("?");
    		if (kit2.hasNext())
    			query = query.concat(",");
    	}

    	query = query.concat(")");
    	System.err.println("\n\n Query = " + query);
        try
        {
	        stmt = con.prepareStatement(query);
	        int i = 1;

	        if (!autoIncrement_) {
		    	// PKs first
		        Iterator<Integer> vit1 = getPkList().values().iterator();
		    	while (vit1.hasNext()) {
		    		Object value = vit1.next();
		    		stmt.setObject(i++, value);
		    	}
	        }

	    	// then Atts...
	        Iterator<Object> vit2 = getAttList().values().iterator();

	    	while (vit2.hasNext()) {
	    		Object value = vit2.next();
	    		stmt.setObject(i++, value);
	    	}
	        stmt.executeUpdate();
	        db_sync_ = true;
        }
        catch(SQLException e){
        	db_sync_ = false;
        	throw new SQLException("CREATE failed, no rows affected..." + e);
        }
        finally {
			if(ZXMain.LOCAL_.compareTo("SQLSERVER") == 0){
        		con.commit();
    		}
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
	}
	
    public PkList create() throws SQLException { //Need to return GENERATED KEY

        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        Connection con = DAOManager.getInstance().getConnection(); 

        String query = "INSERT INTO " + getTableName() + " (";

	// this will preserve key/value order...
    	Iterator<String> kit = getAttList().keySet().iterator();
    	while (kit.hasNext()) {
    		Object key = kit.next();
    		query = query.concat(key.toString());
    		if (kit.hasNext())
    			query = query.concat(",");        		
    	}	    	

    	query = query.concat(") VALUES(");
    	
    	// PKs first...
    	int valuesCount = 0;
    	
    	if (!autoIncrement_) {
    	
	    	Iterator<String> kit1 = getPkList().keySet().iterator();    	        	
	    	while (kit1.hasNext()) {
	    		valuesCount++;
	    		String key = kit1.next();
	    		query = query.concat("?");
	    		if (kit1.hasNext())
	    			query = query.concat(",");        		
	    	}	    	
    	}
        	
    	// then Atts...
    	Iterator<String> kit2 = getAttList().keySet().iterator();
    	if(kit2.hasNext() && !autoIncrement_)
    		query = query.concat(",");
    	
    	while (kit2.hasNext()) {
    		valuesCount++;
    		String key = kit2.next();
    		query = query.concat("?");
    		if (kit2.hasNext())
    			query = query.concat(","); 
    	}

    	query = query.concat(")");
    	
        try
        {
	        stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
	        int i = 1;

	        if (!autoIncrement_) {
		    	// PKs first
		        Iterator<Integer> vit1 = getPkList().values().iterator();
		    	while (vit1.hasNext()) {
		    		Object value = vit1.next();
		    		stmt.setObject(i++, value);
		    	}
	        }

	    	// then Atts...
	        Iterator<Object> vit2 = getAttList().values().iterator();

	    	while (vit2.hasNext()) {
	    		Object value = vit2.next();
	    		stmt.setObject(i++, value);
	    	}

	        int affectedRows = stmt.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("CREATE failed, no rows affected.");
	        }

	        if (autoIncrement_) {

		        // PRIMARY KEYS ARE INTEGER BY DEFAULT
		        generatedKeys = stmt.getGeneratedKeys();

		        int columnCount = generatedKeys.getMetaData().getColumnCount();

		        String[] pkNames = new String[columnCount];
		        int[] pkValues = new int[columnCount];

		        // COLUMN NAMES OK
		        for (int j=0;j < columnCount;j++)
		        	pkNames[j] = generatedKeys.getMetaData().getColumnName(j);

		        // PK VALUES
		        for (int j=0;j < columnCount;j++)
		        	pkValues[j] = generatedKeys.getInt(j);

		        db_sync_ = true;

		        PkList pkList = createKey(pkNames,pkValues);

		        pkList_.clear();

		        pkList_.putAll(pkList);
	        }
	        return pkList_;
        }
        catch(SQLException e){
        	db_sync_ = false;
        	throw e;
        }
        finally {
			if(ZXMain.LOCAL_.compareTo("SQLSERVER") == 0){
        		con.commit();
    		}
        	if (generatedKeys != null) generatedKeys.close();
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
    }

    public void read() throws SQLException {
    	AttList pkAttList = new AttList();
    	Iterator<String> kit = getPkList().keySet().iterator();
    	while (kit.hasNext()) {
    		String key = kit.next();
    		Integer value = getPkList().get(key);
    		pkAttList.put(key, value);
    	}
    	conditionalRead(pkAttList);
    }

    public void conditionalRead(AttList condAttList) throws SQLException {
    	String query = "SELECT * FROM " + getTableName() + " WHERE ";
        PreparedStatement stmt = null;
        ResultSet res = null;
        Connection con = DAOManager.getInstance().getConnection();
        try
        {
        	Iterator<String> kit = condAttList.keySet().iterator();
        	while (kit.hasNext()) {
        		String key = kit.next();
        		query = query.concat(key + "=?");

        		if (kit.hasNext()) {
        			query.concat(" AND ");
        		}
        	}

	        stmt = con.prepareStatement(query);

	        Iterator<Object> vit = condAttList.values().iterator();
	        int i = 1;
        	while (vit.hasNext()) {
        		Object value = vit.next();
        		stmt.setObject(i++, value);
        	}

	        res = stmt.executeQuery();
	        if (res.next())
	        	loadAttsFromResultSet(res);

	        else throw new SQLException("NO CORRESPONDING ROW for conditional read  : " + query);

	        db_sync_ = true;
        }

        catch(SQLException e){

            db_sync_ = false;
            throw e;
        }

        finally {
        	if (res != null) res.close();
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
    }

    public void update() throws SQLException {
    	if (getAttList().isEmpty()) throw new SQLException("no attributes to update on table " + getTableName());
    	if (getPkList().isEmpty()) throw new SQLException("no pk to update on table " + getTableName());

    	String query = "UPDATE " + getTableName() + " SET ";
        PreparedStatement stmt = null;
        Connection con = DAOManager.getInstance().getConnection();

        // Atts first this time...
    	Iterator<String> kit1 = getAttList().keySet().iterator();
    	while (kit1.hasNext()) {
    		String key = kit1.next();
    		query = query.concat(key + "=?");
    		if (kit1.hasNext())
    			query = query.concat(",");
    	}

    	query = query.concat(" WHERE ");

    	// PKs then...
    	Iterator<String> kit2 = getPkList().keySet().iterator();
    	while (kit2.hasNext()) {
    		String key = kit2.next();
    		query = query.concat(key + "=?");
    		if (kit2.hasNext())
    			query = query.concat(",");
    	}

        try
        {
	        stmt = con.prepareStatement(query);

	    	// Atts first...
	        Iterator<Object> vit2 = getAttList().values().iterator();

	        int i=1;
	    	while (vit2.hasNext()) {
	    		Object value = vit2.next();
	    		stmt.setObject(i++, value);
	    	}

	        // PKs then...
	        Iterator<Integer> vit1 = getPkList().values().iterator();

	    	while (vit1.hasNext()) {
	    		Object value = vit1.next();
	    		stmt.setObject(i++, value);
	    	}

	        stmt.executeUpdate();

	        db_sync_ = true;
        }

        catch(SQLException e){
            db_sync_ = false;
            throw e;
        }
        finally {
			if(ZXMain.LOCAL_.compareTo("SQLSERVER") == 0){
        		con.commit();
    		}
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
    }

    public void conditionalUpdate(AttList condAttList) throws SQLException {

    	if (getAttList().isEmpty()) throw new SQLException("no attributes to update on table " + getTableName());
    	if (getPkList().isEmpty()) throw new SQLException("no pk to update on table " + getTableName());

    	String query = "UPDATE " + getTableName() + " SET ";
        PreparedStatement stmt = null;       
        Connection con = DAOManager.getInstance().getConnection(); 

        // Atts first this time...
    	Iterator<String> kit1 = getAttList().keySet().iterator();
    	while (kit1.hasNext()) {
    		String key = kit1.next();
    		query = query.concat(key + "=?");
    		if (kit1.hasNext())
    			query = query.concat(",");        		
    	}
        
    	query = query.concat(" WHERE ");
    	
    	// PKs then...
    	Iterator<String> kit2 = getPkList().keySet().iterator();
    	while (kit2.hasNext()) {
    		String key = kit2.next();
    		query = query.concat(key + "=?");
    		if (kit2.hasNext())
    			query = query.concat(",");        		
    	}

        try {
        	Iterator<String> kit = condAttList.keySet().iterator();


		if (kit.hasNext()) 
			query = query.concat(" AND ");
        	while (kit.hasNext()) {
        		String key = kit.next();

        		query = query.concat(key + "=?");        		

        		if (kit.hasNext()) {
        			query.concat(" AND ");        			
        		}
        	}

	        stmt = con.prepareStatement(query);

	    	// Atts first...
	        Iterator<Object> vit2 = getAttList().values().iterator();
	      
	        int i=1;
	    	while (vit2.hasNext()) {
	    		Object value = vit2.next();
	    		stmt.setObject(i++, value);        		        		
	    	}
	        	        	        	       
	        // PKs then...
	        Iterator<Integer> vit1 = getPkList().values().iterator();
	        
	    	while (vit1.hasNext()) {
	    		Object value = vit1.next();
	    		stmt.setObject(i++, value);        		        		
	    	}	    
	    	
	        stmt.executeUpdate();
	        
	        db_sync_ = true;
        }
                       
        catch(SQLException e){ 
        	
            db_sync_ = false;
            throw e; 
            
        }
        
        finally {
			if(ZXMain.LOCAL_.compareTo("SQLSERVER") == 0){
        		con.commit();
    		}
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }            	                    	       	   
    }

    public void delete() throws SQLException {
    	if(getCanDelete()){
	    	String query = "DELETE FROM " + getTableName() + " WHERE ";
	
	        PreparedStatement stmt = null;
	        Connection con = DAOManager.getInstance().getConnection();
	
	        if (!db_sync_) throw new SQLException("DAO state not in sync with database while DELETE call " + query);
	
	        try
	        {
	        	// Pks only...
	        	Iterator<String> kit = getPkList().keySet().iterator();
	        	while (kit.hasNext()) {
	        		String key = kit.next();
	        		query = query.concat(key + "=?");
	        		if (kit.hasNext())
	        			query = query.concat(",");
	        	}
	
		        stmt = con.prepareStatement(query);
	
		        Iterator<Integer> vit = getPkList().values().iterator();
		        int i = 1;
	        	while (vit.hasNext()) {
	        		Object value = vit.next();
	        		stmt.setObject(i++, value);
	        	}
	
	            stmt.executeUpdate();
	
	            db_sync_ = false;
	        }
	
	        catch(SQLException e){
	
	            db_sync_ = false;
	            throw e;
	
	        }
	
	        finally {
				if(ZXMain.LOCAL_.compareTo("SQLSERVER") == 0){
	        		con.commit();
	    		}
	        	if (stmt != null) stmt.close();
	        	DAOManager.getInstance().closeConnection(con);
	        }
    	}
    	else{
    		if (getPkList().isEmpty()) throw new SQLException("no pk to update on table " + getTableName());


    		String query = "UPDATE " + getTableName() + " SET deleted = 1";
    		PreparedStatement stmt = null;
            Connection con = DAOManager.getInstance().getConnection();

    		query = query.concat(" WHERE ");

        	// PKs then...
        	Iterator<String> kit2 = getPkList().keySet().iterator();
        	while (kit2.hasNext()) {
        		String key = kit2.next();
        		query = query.concat(key + "=?");
        		if (kit2.hasNext())
        			query = query.concat(",");
        	}
        	try
            {            	
    	        stmt = con.prepareStatement(query);

    	    	// Atts first...
    	        Iterator<Object> vit2 = getAttList().values().iterator();
    	      
    	        int i=1;
    	    	while (vit2.hasNext()) {
    	    		Object value = vit2.next();
    	    		stmt.setObject(i++, value);        		        		
    	    	}
    	        	        	        	       
    	        // PKs then...
    	        Iterator<Integer> vit1 = getPkList().values().iterator();
    	        
    	    	while (vit1.hasNext()) {
    	    		Object value = vit1.next();
    	    		stmt.setObject(i++, value);        		        		
    	    	}	    
    	    	
    	        stmt.executeUpdate();
    	        
    	        db_sync_ = true;
            }
                           
            catch(SQLException e){ 
            	
                db_sync_ = false;
                throw e; 
                
            }
            
            finally {
    			if(ZXMain.LOCAL_.compareTo("SQLSERVER") == 0){
            		con.commit();
        		}
            	if (stmt != null) stmt.close();
            	DAOManager.getInstance().closeConnection(con);
            }            	                    	       	   
    	}
    }

    //
    // Accessors/Mutators
    //
    private final String tableName_;    
    private AttList attList_;
    private PkList pkList_;
    private boolean autoIncrement_;
    public boolean canDelete_ = false;
    
    // are you up to date with the database
    private boolean db_sync_ = false;

    public AttList getAttList(){return attList_;}
    public PkList getPkList(){return pkList_;}
    public void setPkList(PkList list) {pkList_ = list;}   
    public Integer getPkValueFor(String pkName) {return pkList_.get(pkName);}
    public void setAttList(AttList list) {attList_ = list;}
    public Object getAttValueFor(String attName) {return attList_.get(attName);}   
    public void setAttValueFor(String attName,Object value){ attList_.put(attName, value);}

    public boolean isDBSync() {return db_sync_;}    
    public String getTableName() {return tableName_;}
    
    public void setCanDelete(boolean canDelete){ canDelete_ = canDelete;}
    public boolean getCanDelete(){return canDelete_;}
    
    
    //
    // Constructors
    //
    protected GenericDAO(String tableName) {
        tableName_ = tableName;        
        db_sync_ = false;     
        pkList_ = createKey(new String[] {AUTO_INCREMENT_PK_NAME}, new int[] {AUTO_INCREMENT_PK_VALUE});
        setAttList(new AttList());
    	autoIncrement_ = true;
    }    
       
    protected GenericDAO(String tableName,PkList pkList) {
    	this(tableName);
        setPkList(pkList);  
        autoIncrement_ = false;
    }  
}
