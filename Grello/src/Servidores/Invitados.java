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
 * Servlet implementation class Invitados
 */
@WebServlet("/Invitados")
public class Invitados extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Invitados() {
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
		ArrayList <JSONObject> arrayBuscar = new ArrayList<JSONObject>();
		
		System.out.println("Estoy en el metodo get de Invitados");
		Integer board_id = Integer.parseInt(request.getParameter("board_id"));
		System.out.println("El card_id es "+ board_id);
		
		try {
			System.out.println("comenzamos con leer los invitados");
			arrayBuscar= db.LeerTableroInvitado(board_id);
			if(!arrayBuscar.isEmpty()) {
				mensaje.put("status", 200).put("response", arrayBuscar);
				System.out.println("Todo bien se leyeron los invitados: "+arrayBuscar+"\n");
			}else {
				mensaje.put("status", 500).put("response", "No hay invitados que leer");
				System.out.println("No hay invitados");
			}
		} catch (SQLException e) {
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
			if(db.VerificarUsuario(data.getString("user_username"))) {
				System.out.println("comenzamos con agregar invitado al tablero");
				boolean status = db.AgregarInvitado(data);
				if(status) {
					mensaje.put("status", 200).put("response", "El invitado fue incluido");
				}else {
					mensaje.put("status", 500).put("response", "No se pudo agregar el usuarios");
				}
			}else {
				mensaje.put("status", 409).put("response", "No existe el usuario");
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
		ArrayList<JSONObject> arrayBuscar = new ArrayList<JSONObject>();
		
		System.out.println("La data es: "+ data);
		
		try {
			System.out.println("Entramos en borrar Invitado");
			if(db.SeleccionarAdmin(data.getInt("id"))) {
				System.out.println("Admin de la pagina");
				System.out.println("estado: "+db.LeerEstadoPersona(data));
				if(db.LeerEstadoPersona(data)) {
					System.out.println("comenzamos borrar al admin cuando se es admin de la pagina");
					arrayBuscar = db.LeerAdmin(data);
					if( arrayBuscar.size() > 1) {
						System.out.println("Mas de un admin");
						if(db.BorrarPersona(data)) {
							mensaje.put("status", 200).put("response", "Fue borrado el administrador");
							System.out.println("Ya se borro el Administrador");
						}else {
							mensaje.put("status", 500).put("response", "No se pudo borrar el administrador");
						}
					}else {
						mensaje.put("status", 409).put("response", "No hay mas administradores");
					}
				}else {
					System.out.println("comenzamos borrar al invitados cuando se es admin de la pagina");
					if(db.BorrarInvitado(data)) {
						mensaje.put("status", 200).put("response", "Fue borrado el invitado");
						System.out.println("Ya se borro el invitado");
					}else {
						mensaje.put("status", 500).put("response", "No se pudo borrar el invitado");
					}
				}
			}else {
				if(db.LeerEstado(data)) {
					if(db.BorrarInvitado(data)) {
						mensaje.put("status", 200).put("response", "Fue borrado el invitado");
					}else {
						mensaje.put("status", 500).put("response", "No se pudo borrar el invitado");
					}
				}else {
					mensaje.put("status", 408).put("response", "Solo los admin pueden borrar a los invitados");
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
		ArrayList<JSONObject> arrayBuscar = new ArrayList<JSONObject>();
		
		System.out.println("La data es: "+ data);
		
		try {
			System.out.println(db.LeerEstado(data));
			if(db.SeleccionarAdmin(data.getInt("id"))) {
				System.out.println("Admin de la pagina");
				if("Invitado".equals(data.getString("type_board_user_desccription"))) {
					System.out.println("Entramos en actualizar de Admin a invitado");
					arrayBuscar = db.LeerAdmin(data);
					if( arrayBuscar.size() > 1) {
						System.out.println("Estamos aqui");
						if(db.ActualizarInvitado(data)) {
							mensaje.put("status", 200).put("response", "Se actualizo el estado");
							System.out.println("Todo bien se actualizo el estado");
						}else {
							mensaje.put("status", 500).put("response", "No se puedo actualizar el estado");
							System.out.println("No se actualizo el estado");
						}
					}else {
						mensaje.put("status", 409).put("response", "No hay mas administradores");
					}
				}else {
					System.out.println("Entramos en actualizar de invitado a Admin");
					if(db.ActualizarInvitado(data)) {
						mensaje.put("status", 200).put("response", "Se actualizo el estado");
						System.out.println("Todo bien se actualizo el estado");
					}else {
						mensaje.put("status", 500).put("response", "No se puedo actualizar el estado");
						System.out.println("No se actualizo el estado");
					}
				}
			}else {
				if(db.LeerEstado(data)) {
					System.out.println("comenzamos actualizar el estado de los invitados o administradores");
					if("Invitado".equals(data.getString("type_board_user_desccription"))) {
						System.out.println("Entramos en actualizar de Admin a invitado");
						arrayBuscar = db.LeerAdmin(data);
						if( arrayBuscar.size() > 1) {
							System.out.println("Estamos aqui");
							if(db.ActualizarInvitado(data)) {
								mensaje.put("status", 200).put("response", "Se actualizo el estado");
								System.out.println("Todo bien se actualizo el estado");
							}else {
								mensaje.put("status", 500).put("response", "No se puedo actualizar el estado");
								System.out.println("No se actualizo el estado");
							}
						}else {
							mensaje.put("status", 409).put("response", "No hay mas administradores");
						}
					}else {
						System.out.println("Entramos en actualizar de invitado a Admin");
						if(db.ActualizarInvitado(data)) {
							mensaje.put("status", 200).put("response", "Se actualizo el estado");
							System.out.println("Todo bien se actualizo el estado");
						}else {
							mensaje.put("status", 500).put("response", "No se puedo actualizar el estado");
							System.out.println("No se actualizo el estado");
						}
					}	
				}else {
					mensaje.put("status", 408).put("response", "Solo los admin pueden cambiar el estado de los invitados");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		
		
		out.print(mensaje.toString());
		}

}
