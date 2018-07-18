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
 * Servlet implementation class Tarjetas
 */
@WebServlet("/Tarjetas")
public class Tarjetas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tarjetas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		JSONObject cardData = new JSONObject();
		ArrayList <JSONObject> arrayCard = new ArrayList<JSONObject>();
		
		String a = data.getString("tipo").toString();
		System.out.println("La date es: "+ data);
		
		if("crear".equals(a)) {
			try {
				System.out.println("comenzamos");
				if(!db.BuscarTarjeta(data.getString("card_name"))) {
					System.out.println("Nombre de la tarjeta correcta");
					boolean status = db.CrearTarjeta(data);
					cardData = db.LeerTarjeta(data);
					if (status) {
						System.out.println("La tarjeta fue creada");
						if(cardData.length() > 0) {
							mensaje.put("status", 200).put("response", cardData);
							System.out.println("Todo realizado conexito");
						}else {
							mensaje.put("status", 500).put("response","No se pudo retornar la informaion de la tarjeta");
						}
					}else {
						mensaje.put("status", 500).put("response","La tarjeta no fue creada");
					}
				}else {
					mensaje.put("status", 400).put("response:","Este nombre de tarjeta ya existe");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			
		}else if("actualizar".equals(a)) {
			System.out.println("Comenzamos con la actualizacion de la tarjeta");
			try {
				arrayCard = db.LeerPersonaAdmin(data);
				if(arrayCard.size() == 1) {
					boolean status = db.ActualizarTarjeta(data);
					if (status) {
							mensaje.put("status", 200).put("response", "La tarjeta fue actualizada");
							System.out.println("La tarjeta fue actuializada");
					}else {
						mensaje.put("status", 500).put("response:","La tarjeta no fue actualizada");
					}
				}else {
					cardData = db.LeerTarjetaEspecifica(data);
					System.out.println("El id del creador es: " +cardData.getInt("user_id"));
					System.out.println("El id del usuario actual es: "+ data.getInt("id"));
					if((data.getInt("id")) == (cardData.getInt("user_id"))) {
						boolean status = db.ActualizarTarjeta(data);
						if (status) {
								mensaje.put("status", 200).put("response", "La tarjeta fue actualizada");
								System.out.println("La tarjeta fue actuializada");
						}else {
							mensaje.put("status", 500).put("response:","La tarjeta no fue actualizada");
						}	
					}else {
						mensaje.put("status", 409).put("response:","La tarjeta no fue actualiada");
					}
					
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			
		}else if("leer".equals(a)) {
			try {
				System.out.println("Comenzamos con la lectura de las tarjetas");
				arrayCard = db.LeerTarjetaColumna(data);
				if(!arrayCard.isEmpty()) {
					mensaje.put("status", 200).put("response",arrayCard);
					System.out.println("Se ha realizado la lectura de la tarjeta");
					System.out.println(arrayCard);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		out.println(mensaje.toString());
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		JSONObject cardData = new JSONObject();
		ArrayList <JSONObject> arrayCard = new ArrayList<JSONObject>();
		
		System.out.println("La date es: "+ data);
		
		try {
			arrayCard = db.LeerPersonaAdmin(data);
			if(arrayCard.size() == 1) {
				boolean status = db.BorrarTarjeta(data);
				if (status) {
					System.out.println("Fue borrada la tarjeta");
					mensaje.put("status", 200).put("response", "La tarjeta fue borrada");
				}else {
					mensaje.put("status", 500).put("response","No se puedo borrar");
				}
			}else {
				cardData = db.LeerTarjetaEspecifica(data);
				System.out.println("El id del creador es: " +cardData.getInt("user_id"));
				System.out.println("El id del usuario actual es: "+ data.getInt("id"));
				if((data.getInt("id")) == (cardData.getInt("user_id"))) {
					boolean status = db.BorrarTarjeta(data);
					if (status) {
						System.out.println("Fue borrada la tarjeta");
						mensaje.put("status", 200).put("response", "La tarjeta fue borrada");
					}else {
						mensaje.put("status", 500).put("response","No se puedo borrar");
					}
				}else {
					mensaje.put("status", 409).put("response","No es el creador de la tarjeta a borrar");
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		out.println(mensaje.toString());
	}


}
