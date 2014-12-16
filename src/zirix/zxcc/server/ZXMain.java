/*ZIRIX CONTROL CENTER - ZX MAIN
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

public class ZXMain {

	public static String URL_ADRESS_= "";
	public static String DB_NAME_= "";
	public static String LOCAL_= "";

	public static void main(String[] args) {

	}

	public static void setUrlAdress(String UrlAdress){ URL_ADRESS_ = UrlAdress;}
	public static void setDbName(String DbName){ DB_NAME_ = DbName;}
	public static void setLocal(String Local){ LOCAL_ = Local;}

    public static String getLocal(){return LOCAL_;}
    public static String getDbName(){return DB_NAME_;}
    public static String getAdress(){return URL_ADRESS_;}

}
