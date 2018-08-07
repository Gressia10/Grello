package Servidores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Grello.Queries;

/**
 * Servlet implementation class Buscador
 */
@WebServlet("/Buscador")
public class Buscador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Buscador() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		Queries db = new Queries();
		ArrayList<JSONObject> arrayBuscar = new ArrayList<JSONObject>();
		
		System.out.println("Estoy en el metodo get del buscador");
		String board_name = (request.getParameter("board_name"));
		String []part = board_name.split("\\?");
        board_name = part[0];
		board_name = '%'+board_name+'%';
		String []partId = part[1].split("\\=");
		Integer id= Integer.parseInt(partId[1]);
		System.out.println("La busqueda es: "+ board_name + " el id es: "+id);
		
		
		try {
			System.out.println("comenzamos con el buscador");
			if(db.SeleccionarAdmin(id)) {
				System.out.println("Admin de la pagina");
				arrayBuscar = db.BuscadorAdmin(board_name);
				if(!arrayBuscar.isEmpty()) {
					mensaje.put("status", 201).put("response", arrayBuscar);
					System.out.println("Todo bien hay datos que devolver");
				}else {
					mensaje.put("status", 201).put("response", arrayBuscar);
					System.out.println("No hay datos que devolver");
				}
			}else {
				arrayBuscar = db.Buscador(board_name);
				if(!arrayBuscar.isEmpty()) {
					mensaje.put("status", 200).put("response", arrayBuscar);
					System.out.println("Todo bien hay datos que devolver");
				}else {
					mensaje.put("status", 200).put("response", arrayBuscar);
					System.out.println("No hay datos que devolver");
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		out.println(mensaje.toString());
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
