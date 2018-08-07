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
 * Servlet implementation class Tableros
 */
@WebServlet("/Tableros")
public class Tableros extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tableros() {
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
		ArrayList<JSONObject> dataBoard = new ArrayList<JSONObject>();
		
		//String id = (request.getParameter("id"));
		Integer user_id = Integer.parseInt(request.getParameter("user_id"));

		
		try {
			System.out.println("Comenzamos con la lectura");
			dataBoard = db.LeerTablero(user_id);
			if(!dataBoard.isEmpty()) {
				mensaje.put("status", 200).put("response", dataBoard);
				System.out.println("Se ha realizado la lectura del tablero");
				System.out.println(dataBoard);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		JSONObject userBoard = new JSONObject();
	
		
		String a = data.getString("tipo").toString();
		System.out.println(data);
		System.out.println("a: "+a);
	
		
		if("crear".equals(a)) {
			System.out.println("Entramos en crear Tablero");
			try {
				System.out.println("comenzamos");
				if(!db.BuscarTablero(data.getString("board_name"))) {
					System.out.println("Nombre del tablero correcto");
					boolean status = db.CrearTablero(data);
					 userBoard= db.InformacionTablero(data);
					if(status) {
						if(userBoard.length() > 0) {
							boolean nuevaTabla= db.InsertarUsuTa(userBoard.getInt("board_id"), data);
							System.out.println("Ya se creo el tablero");
							if (nuevaTabla) {
								mensaje.put("status", 200).put("response", userBoard);
								System.out.println("Ya se le asigno el tablero al usuario");
							}else {
								mensaje.put("status", 500).put("response","No se le puedo asignar el tablero a el usuario");
							}
						}else {
							mensaje.put("status", 500).put("response:","No se pudo crear el tablero");
						}
					}else {
					mensaje.put("status", 400).put("response:","No retorno ningun dato la tabla de tablero");
					}
				}else {
					mensaje.put("status", 400).put("response:","Este nombre de tablero ya existe");
			}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			
		}else if("leerTipo".equals(a)) {
			try {
			userBoard = db.LeerTipoTablero(data);
				if(userBoard.length() > 0) {
					mensaje.put("status", 200).put("response", userBoard);
					System.out.println("Se realizo la lectura del tipo de tablero");
				}else {
					mensaje.put("status", 200).put("response", "No se pudo realizar la lectura del tipo de tablero");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
		}
		
		out.println(mensaje.toString());
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		ArrayList<JSONObject> dataBoard = new ArrayList<JSONObject>();
		
			
		System.out.println("Entramos en borrar Tablero");
		try {
			if(db.SeleccionarAdmin(data.getInt("id"))) {
				System.out.println("Admin de la pagina");
				if(db.BorrarTablero(data)) {
					mensaje.put("status", 200).put("response", "El tablero fue borrado");
					System.out.println("El tablero fue borrado de la tabla board");
				}else {
					mensaje.put("status", 500).put("response","No se puedo borrar");
				}
			}else {
				System.out.println("Usuario de la pagina");
				dataBoard = db.LeerUsuario(data);
				System.out.println("Esto" +dataBoard);
				if(dataBoard.size() == 1){
					System.out.println("Opcion 1");
					if (db.BorrarTablero(data)) {
						mensaje.put("status", 200).put("response", "El tablero fue borrado");
						System.out.println("El tablero fue borrado de la tabla board");
					}else {
						mensaje.put("status", 500).put("response","No se puedo borrar");
					}
				}else{
					System.out.println("Opcion 2");
					if(db.LeerEstado(data)) {
						System.out.println("Opcion Admin");
						
						int i = - dataBoard.size()+ db.LeerAdmin(data).size() ;
						System.out.println("i: "+ i);
						if(i > 1) {
							System.out.println("Mas de un  Admin: ");
							if(db.BorrarUsuTa(data)) {
								System.out.println("Fue borrado el tablero de la tabla user_board");
								mensaje.put("status", 200).put("response","Fue borrado de la tabla user_boards");
								/*boolean status = db.BorrarTablero(data);
								if (status) {
									mensaje.put("status", 200).put("response", "El tablero fue borrado");
									System.out.println("El tablero fue borrado de la tabla board");
								}else {
									System.out.println("No se pudo borrar de la tabla boards");
									mensaje.put("status", 500).put("response","No se pudo borrar de la tabla boards");
								}*/
							}else {
								mensaje.put("status", 500).put("response","No se pudo borrar de la tabla user_boards");
								System.out.println("No fue borrado el tablero de la tabla user_board");
							}
						}else {
							mensaje.put("status", 409).put("response","Debe quedar un administrador");
						}
					}else {
						System.out.println("Opcion Invitado");
						if(db.BorrarUsuTa(data)) {
							System.out.println("Fue borrado el tablero de la tabla user_board");
							mensaje.put("status", 200).put("response", "El tablero fue borrado de user_board");
							/*boolean status = db.BorrarTablero(data);
							if (status) {
								mensaje.put("status", 200).put("response", "El tablero fue borrado");
								System.out.println("El tablero fue borrado de la tabla board");
							}else {
								mensaje.put("status", 500).put("response","No se puedo borrar");
							}*/
						}else {
							mensaje.put("status", 500).put("response","No se puedo borrar el tablero de la tabla user_boards");
							System.out.println("No fue borrado el tablero de la tabla user_board");
						}
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
		/*JSONObject userBoard = new JSONObject();
		ArrayList<JSONObject> dataBoard = new ArrayList<JSONObject>();
		ArrayList<JSONObject> admin = new ArrayList<JSONObject>();*/
		
		try {
			boolean status = db.ActualizarTablero(data);
			if (status) {
					mensaje.put("status", 200).put("response", "El tablero fue actualizado");
			}else {
				mensaje.put("status", 500).put("response","El tablero no fue actualizado");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		
		out.print(mensaje.toString());
		}

}
