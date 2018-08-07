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
 * Servlet implementation class Columna
 */
@WebServlet("/Columna")
public class Columna extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Columna() {
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
		ArrayList <JSONObject> arrayColumn = new ArrayList<JSONObject>();
		
		System.out.println("Estoy en el metodo get de Comentarios");
		Integer board_id = Integer.parseInt(request.getParameter("board_id"));
		System.out.println("El board_id es "+ board_id);
		
		try {
			System.out.println("Comenzamos con la lectura de las columnas");
			arrayColumn = db.LeerColumnaTablero(board_id);
			if(!arrayColumn.isEmpty()) {
				mensaje.put("status", 200).put("response",arrayColumn);
				System.out.println("Se ha realizado la lectura de las columnas");
				System.out.println(arrayColumn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
		
		
		JSONObject columnData = new JSONObject();
		
		System.out.println("la data es: "+data);
		
		try {
			System.out.println("comenzamos");
			if(!db.BuscarColumna(data)){
				System.out.println("Nombre de la columna correcta");
				boolean status = db.CrearColumna(data);
				if (status) {
					System.out.println("La columna fue creada");
					columnData = db.InformacionColumna(data);
					if(columnData.length() > 0) {
						mensaje.put("status", 200).put("response",columnData);
						System.out.println("Todo realizado con exito");
					}else {
						mensaje.put("status", 200).put("response","No se pudo traer los datos de la columna");
					}
				}else {
					mensaje.put("status", 500).put("response","La columna no fue creado");
				}
			}else {
				mensaje.put("status", 400).put("response:","Este nombre de columna ya existe");
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
		ArrayList <JSONObject> arrayColumn = new ArrayList<JSONObject>();
		
		JSONObject columnData = new JSONObject();
		
		System.out.println("la data es: "+data);
		
		try {
			if(db.SeleccionarAdmin(data.getInt("id"))) {
				System.out.println("Admin de la pagina");
				if (db.BorrarColumna(data)) {
					mensaje.put("status", 200).put("response", "La columna fue borrada");
				}else {
					mensaje.put("status", 500).put("response","No se puedo borrar");
				}
			}else {
				arrayColumn = db.LeerPersonaAdmin(data);
				if(arrayColumn.size() == 1) {
					System.out.println("Admin");
					if (db.BorrarColumna(data)) {
						mensaje.put("status", 200).put("response", "La columna fue borrada");
					}else {
						mensaje.put("status", 500).put("response","No se puedo borrar");
					}
				}else {
					System.out.println("Invitado");
					columnData = db.LeerColumnaEspecifica(data);
					System.out.println("El id del creador es: " +columnData.getInt("user_id"));
					System.out.println("El id del usuario actual es: "+ data.getInt("id"));
					if((data.getInt("id")) == (columnData.getInt("user_id"))) {
						if (db.BorrarColumna(data)) {
							mensaje.put("status", 200).put("response", "La columna fue borrada");
						}else {
							mensaje.put("status", 500).put("response","No se puedo borrar");
						}
					}else {
						mensaje.put("status", 409).put("response","No es el creador de la columna a borrar");
						System.out.println("No es el creador de la columna a borrar");
					}
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
		ArrayList <JSONObject> arrayColumn = new ArrayList<JSONObject>();
		
		JSONObject columnData = new JSONObject();
		
		System.out.println("la data es: "+data);
		
		try {
			System.out.println("Empezamos Actualizar columna");
			if(db.SeleccionarAdmin(data.getInt("id"))) {
				System.out.println("Admin de la pagina");
				if (db.ActualizarColumna(data)) {
					mensaje.put("status", 200).put("response", "La columna fue actualizada");
				}else {
					mensaje.put("status", 500).put("response","la columna no fue actualizada");
				}
			}else {
				arrayColumn = db.LeerPersonaAdmin(data);
				if(arrayColumn.size() == 1) {
					System.out.println("Admin");
					if (db.ActualizarColumna(data)) {
							mensaje.put("status", 200).put("response", "El tablero fue actualizado");
					}else {
						mensaje.put("status", 500).put("response","El tablero no fue actualizado");
					}
				}else {
					System.out.println("Invitado");
					columnData = db.LeerColumnaEspecifica(data);
					System.out.println("El id del creador es: " +columnData.getInt("user_id"));
					System.out.println("El id del usuario actual es: "+ data.getInt("id"));
					if((data.getInt("id")) == (columnData.getInt("user_id"))) {
						if (db.ActualizarColumna(data)) {
								mensaje.put("status", 200).put("response", "La columna fue actualizada");
						}else {
							mensaje.put("status", 500).put("response","la columna no fue actualizada");
						}	
					}else {
						mensaje.put("status", 409).put("response","No es el creador de la columna a modificar");
						System.out.println("No es el creador de la columna a modificar");
					}
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		out.print(mensaje.toString());
		}


}
