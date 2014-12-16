/*ZIRIX CONTROL CENTER - ZXCC CONSTANTS SERVLET
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@WebServlet( name="ZXCCConstantsServlet", urlPatterns = {"/services/constants"}, loadOnStartup=1)
public class ZXCCConstantsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static int READ = 0;
	public static int WRITE = 1;

    public ZXCCConstantsServlet() {
        main();
    }

    public static void main() {

    	try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (new File("webapps/zxcc/js/VariaveisZXCC.xml"));

			doc.getDocumentElement ().normalize ();

			NodeList listOfPersons = doc.getElementsByTagName("parametros");

			for(int s=0; s<listOfPersons.getLength() ; s++){
				Node firstPersonNode = listOfPersons.item(s);
				if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){

					Element firstPersonElement = (Element)firstPersonNode;

					NodeList firstNameList = firstPersonElement.getElementsByTagName("db_name");
					Element firstNameElement = (Element)firstNameList.item(0);
					NodeList textFNList = firstNameElement.getChildNodes();
					ZXMain.setDbName((String)textFNList.item(0).getNodeValue().trim());

					NodeList lastNameList = firstPersonElement.getElementsByTagName("adress");
					Element lastNameElement = (Element)lastNameList.item(0);
					NodeList textLNList = lastNameElement.getChildNodes();
					ZXMain.setUrlAdress((String)textLNList.item(0).getNodeValue().trim());

					NodeList ageList = firstPersonElement.getElementsByTagName("local");
					Element ageElement = (Element)ageList.item(0);
					NodeList textAgeList = ageElement.getChildNodes();
					ZXMain.setLocal((String)textAgeList.item(0).getNodeValue().trim());
				}
			}
		}catch (SAXParseException err) {
			System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
			System.out.println(" " + err.getMessage ());
		}catch (SAXException e) {
			Exception x = e.getException ();
			((x == null) ? e : x).printStackTrace ();
		}catch (Throwable t) {
			t.printStackTrace ();
		}
	}
}
