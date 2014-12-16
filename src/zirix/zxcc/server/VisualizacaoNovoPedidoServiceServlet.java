/*ZIRIX CONTROL CENTER - VIZUALIZAÃÃO NOVO PEDIDO SERVICE SERVLET
DESENVOLVIDO POR ZIRIX SOLUï¿½ï¿½ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zirix.zxcc.server.dao.DAOManager;
import zirix.zxcc.server.dao.PkList;
import zirix.zxcc.server.mock.*;

@WebServlet( name="VisualizacaoNovoPedidoService", urlPatterns = {"/services/visualizacaoNovoPedido"}, loadOnStartup=1)
public class VisualizacaoNovoPedidoServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public VisualizacaoNovoPedidoServiceServlet() {
		super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String WORK_ID = request.getParameter("WORK_ID");
		String COD_USUARIO = request.getParameter("COD_USUARIO").trim();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}
