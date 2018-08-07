package Servidores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Grello.Queries;

/**
 * Servlet implementation class Comentarios
 */
@WebServlet("/Comentarios")
public class Comentarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comentarios() {
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
		ArrayList <JSONObject> arrayComment = new ArrayList<JSONObject>();
		
		System.out.println("Estoy en el metodo get de Comentarios");
		Integer card_id = Integer.parseInt(request.getParameter("card_id"));
		System.out.println("El card_id es "+ card_id);
		
		try {
			System.out.println("comenzamos con el leer comentario");
			arrayComment = db.LeerComentario(card_id);
				if(!arrayComment.isEmpty()) {
					mensaje.put("status", 200).put("response", arrayComment);
					System.out.println("Todo bien hay datos que devolver");
				}else {
					mensaje.put("status", 200).put("response", arrayComment);
					System.out.println("No hay datos que devolver");
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
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();

		System.out.println("La data es: "+ data);
		
		try {
			System.out.println("Entramos en crear cometarios");
			boolean status = db.CrearComentario(data);
			if (status) {
				System.out.println("El comentario fue creado");
				mensaje.put("status", 200).put("response","El comentario fue creado");	
			}else {
				mensaje.put("status", 500).put("response","El comentario no fue creado");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		out.println(mensaje.toString());	
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		JSONObject commentData = new JSONObject();
		
		System.out.println("La data es: "+ data);
		
		try {
			if(db.SeleccionarAdmin(data.getInt("id"))) {
				System.out.println("Admin de la pagina");
				if(db.BorrarComentario(data)) {
					mensaje.put("status", 200).put("response", "Fue borrado el comentario");
				}else {
					mensaje.put("status", 500).put("response", "No se pudo borrar el comentario");
				}
			}else {
				commentData = db.LeerComentarioEspecifico(data);
				if((data.getInt("id")) == (commentData.getInt("user_id"))) {
					System.out.println("Entramos en borrar Comentario");
					if(db.BorrarComentario(data)) {
						mensaje.put("status", 200).put("response", "Fue borrado el comentario");
					}else {
						mensaje.put("status", 500).put("response", "No se pudo borrar el comentario");
					}
				}else {
					mensaje.put("status", 409).put("response","No es el creador del comentario a borrar");
					System.out.println("No es el creador del comentario a borrar");
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		out.println(mensaje.toString());	
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		System.out.println("La data es: "+ data);
		
		JSONObject commentData = new JSONObject();
		
		try {
			System.out.println("Empezamos Actualizar commentario");
			commentData = db.LeerComentarioEspecifico(data);
			System.out.println("El id del creador es: " +commentData.getInt("user_id"));
			System.out.println("El id del usuario actual es: "+ data.getInt("id"));
			if((data.getInt("id")) == (commentData.getInt("user_id"))) {
				boolean status = db.ActualizarComentario(data);
				if (status) {
						mensaje.put("status", 200).put("response", "El comentario fue actualizado");
				}else {
					mensaje.put("status", 500).put("response","El comentario no fue actualizado");
				}	
			}else {
				mensaje.put("status", 409).put("response","No es el creador del comentario a modificar");
				System.out.println("No es el creador del comentario a modificar");
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		out.print(mensaje.toString());
		}

}
