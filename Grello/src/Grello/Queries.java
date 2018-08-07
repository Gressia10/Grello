package Grello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

import org.json.JSONException;
import org.json.JSONObject;

public class Queries extends BDConexion {
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private ArrayList <JSONObject> array = new ArrayList<JSONObject>();
	
	public Queries() {
		super();
	}
	
	private JSONObject getData() throws SQLException {
		JSONObject userData = new JSONObject();
		if(this.rs.next()) {		
			this.rsmd = rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				userData.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
		}
		return userData;
	}
	
	private ArrayList<JSONObject> getArray() throws SQLException {
		
		int f = 0;
		
		while(this.rs.next()) {	
			JSONObject userData = new JSONObject();
			this.rsmd = rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				userData.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
			System.out.println("contenido: "+ userData);
			array.add( f,userData);
			f++;	
			
		}
		System.out.println("array f: "+ array);
		return array;	
	}

	//Verificar el usuario
	public boolean VerificarUsuario(String value) throws SQLException{
		this.rs = executeStatement("BuscarUsuario", value);
		return this.rs.next();
	}
	
	//Verificar el email
	public boolean VerificarCorreo(String value) throws SQLException{
		this.rs = executeStatement("BuscarCorreo", value);
		return this.rs.next();
	}
	
	//Verifica si el usuario existe y retorna sus datos
	public JSONObject ObtenerDatos (JSONObject user)throws SQLException{
		String encriptada = Encriptamiento.HashPassword(user.getString("user_password"));
		this.rs = executeStatement("VerificarIngreso", user.getString("user_username"), encriptada);
		return this.getData();
	}
	//Verficar si una persona es admin de la pagina
	public boolean SeleccionarAdmin(int value) throws SQLException{
		this.rs = executeStatement("SeleccionarAdmin", value);
		return this.rs.next();
	}
	
	
	//Registrar la cuenta
	public boolean Registrar(JSONObject data) throws SQLException{
		String encriptada = Encriptamiento.HashPassword(data.getString("user_password"));
		int i = executeUpdate("IngresarUsuario", data.getString("user_name"), data.getString("user_last_name"),
				data.getString("user_username"), encriptada, data.getString("user_email"));
	
		return (i == 1)?true:false;
	}
	
	//Cerrar los recursos	public void closeResources() {
	public void closeResources() {
		try {
			closeMainResource();
			if(this.rs != null)
				this.rs.close();
		} catch (SQLException e) {
			System.out.println("Problema al cerrar los recursos");
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------Tableros---------------------------------------------------
	
	//Buscar Tablero mediante el nombre 
	public boolean BuscarTablero(String value) throws SQLException{
		this.rs = executeStatement("BuscarTablero", value);
		return this.rs.next();
	}
	
	//	Traer la informacion del tablero
	public JSONObject InformacionTablero (JSONObject user)throws SQLException{
		this.rs = executeStatement("BuscarTablero", user.getString("board_name"));
		return this.getData();
	}
	
	//Crear Tablero
	public boolean CrearTablero(JSONObject data) throws SQLException{
		int i = executeUpdate("CrearTablero", data.getString("board_name"), data.getInt("user_id"));
	
		return (i == 1)?true:false;
	}
	
	//Leer Tablero
	public ArrayList<JSONObject> LeerTablero(int user_id)throws SQLException{
		this.rs = executeStatement("LeerTablero", user_id);
		return this.getArray();
	}
	
	//Leer el tipo de tablero si es privado publico o en equipo
	public JSONObject LeerTipoTablero (JSONObject user)throws SQLException{
		this.rs = executeStatement("LeerTipoTablero", user.getInt("board_id"));
		return this.getData();
	}
	
	//Actualizar Tablero
		public boolean ActualizarTablero(JSONObject data) throws SQLException{
			int i = executeUpdate("ActualizarTablero", data.getString("board_name"), data .getInt("board_id"));
			return (i == 1)?true:false;
		}
	
	//Borrar Tablero
	public boolean BorrarTablero(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarTablero", data.getInt("board_id"));
		return (i == 1)?true:false;
	}
	
	
	//Tabla user_board
	
	//Insertar en la tabla de user_board
	public boolean InsertarUsuTa(int value, JSONObject data) throws SQLException{
		int i = executeUpdate("InsertarUsuTa", value, data .getInt("user_id"));
		return (i == 1)?true:false;
	}
	public boolean BorrarUsuTa(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarUsuTa", data.getInt("board_id"), data.getInt("id"));
		return (i == 1)?true:false;
	}
	
	
	//-------------------------------------------Columna-----------------------------------------------
	
	//Buscar Columna
	public boolean BuscarColumna(JSONObject user) throws SQLException{
		this.rs = executeStatement("BuscarColumna", user.getString("column_name"), user.getInt("board_id"));
		return this.rs.next();
	}
	//Traer la informacion de la columna
	public JSONObject InformacionColumna (JSONObject user)throws SQLException{
		this.rs = executeStatement("BuscarColumna", user.getString("column_name"),  user.getInt("board_id"));
		return this.getData();
	}
	
	//Crear Columna 
	public boolean CrearColumna(JSONObject data) throws SQLException{
		int i = executeUpdate("CrearColumna", data.getInt("board_id"), data .getString("column_name"), data.getInt("user_id"));
		return (i == 1)?true:false;
	}
	//Leer Columna recien ingresada
		public JSONObject LeerColumna(JSONObject user)throws SQLException{
			this.rs = executeStatement("LeerColumna", user.getString("column_name"));
			return this.getData();
		}
	
	//Leer Columna de los tableros
	public ArrayList<JSONObject> LeerColumnaTablero(int board_id)throws SQLException{
		this.rs = executeStatement("LeerColumnaTablero", board_id);
		return this.getArray();
	}
	
	//Leer Columna para saber quien la creo
	public JSONObject LeerColumnaEspecifica (JSONObject data)throws SQLException{
		this.rs = executeStatement("LeerColumnaEspecifica", data.getInt("column_id"));
		return this.getData();
	}
	
	
	//Actualizar Columna 
	public boolean ActualizarColumna(JSONObject data) throws SQLException{
		int i = executeUpdate("ActualizarColumna", data.getString("column_name"), data .getInt("column_id"));
		return (i == 1)?true:false;
	}
	
	//Borrar Columna
		public boolean BorrarColumna(JSONObject data) throws SQLException{
			int i = executeUpdate("BorrarColumna", data.getInt("column_id"));
			return (i == 1)?true:false;
		}
		
		
	
	//---------------------------------------------Tarjeta------------------------------------------------------------------
	
	//Buscar Tarjeta
	public boolean BuscarTarjeta(String value) throws SQLException{
		this.rs = executeStatement("BuscarTarjeta", value);
		return this.rs.next();
	}
	
	//Crear Tarjeta
	public boolean CrearTarjeta(JSONObject data) throws SQLException{
		int i = executeUpdate("CrearTarjeta", data.getInt("column_id"), data.getInt("user_id"), data .getString("card_name")
				, data .getString("card_description"));
		return (i == 1)?true:false;
	}
	
	//Leer Tarjetas de la columna
		public JSONObject LeerTarjeta(JSONObject user)throws SQLException{
			this.rs = executeStatement("LeerTarjeta", user.getString("card_name"));
			return this.getData();
		}
	
	//Leer Tarjetas de la columna
	public ArrayList<JSONObject> LeerTarjetaColumna(int column_id)throws SQLException{
		this.rs = executeStatement("LeerTarjetaColumna", column_id);
		return this.getArray();
	}
	
	//Leer Columna para saber quien la creo
		public JSONObject LeerTarjetaEspecifica (JSONObject data)throws SQLException{
			this.rs = executeStatement("LeerTarjetaEspecifica", data.getInt("card_id"));
			return this.getData();
		}
	
	//Actualizar Tarjeta
	public boolean ActualizarTarjeta(JSONObject data) throws SQLException{
		int i = executeUpdate("ActualizarTarjeta", data.getString("card_name"), data.getString("card_description"),data .getInt("card_id"));
		return (i == 1)?true:false;
	}
	
		
	//Borrar Tarjeta
	public boolean BorrarTarjeta(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarTarjeta", data.getInt("card_id"));
		return (i == 1)?true:false;
	}
	
	
	//--------------------------------------Permisologia------------------------------------------------------------
	//Permisologia de los tableros
	public boolean ActualizarEstado(JSONObject data) throws SQLException{
		int i = executeUpdate("ActualizarEstado", data.getString("type_board_user_desccription"), data .getInt("board_id"));
		return (i == 1)?true:false;
	}
	
	//lee el estado del usuario en el tablero si es admin o invitado
	public boolean LeerEstado(JSONObject data) throws SQLException{
		this.rs= executeStatement("LeerEstado", data.getInt("id"),data .getInt("board_id"));
		return this.rs.next();
	}
	public boolean LeerEstadoPersona(JSONObject data) throws SQLException{
		this.rs= executeStatement("LeerEstado", data.getInt("user_id"),data .getInt("board_id"));
		return this.rs.next();
	}
	
	
	//----------------------------------Buscar tableros en el buscador----------------------------------------------
	public ArrayList<JSONObject> Buscador(String board_name)throws SQLException{
		this.rs = executeStatement("Buscador", board_name);
		return this.getArray();
	}
	public ArrayList<JSONObject> BuscadorAdmin(String board_name)throws SQLException{
		this.rs = executeStatement("BuscadorAdmin", board_name);
		return this.getArray();
	}
	
	//-----------------------------------Invitado--------------------------------------------------------
	public boolean AgregarInvitado(JSONObject data) throws SQLException{
		int i = executeUpdate("AgregarInvitado", data.getInt("board_id"), data.getString("user_username"));
		return (i == 1)?true:false;
	}
	public ArrayList<JSONObject> LeerTableroInvitado(int board_id)throws SQLException{
		this.rs = executeStatement("LeerTableroInvitado", board_id);
		return this.getArray();
	}
	public boolean ActualizarInvitado(JSONObject data) throws SQLException{
		int i = executeUpdate("ActualizarInvitado",data.getString("type_board_user_desccription"), data.getInt("user_id"), data.getInt("board_id"));
		return (i == 1)?true:false;
	}
	public boolean BorrarInvitado(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarInvitado", data.getInt("user_id"));
		return (i == 1)?true:false;
	}
	public boolean BorrarPersona(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarPersona", data.getInt("user_id"));
		return (i == 1)?true:false;
	}
	
	public ArrayList <JSONObject> LeerAdmin(JSONObject data) throws SQLException{
		this.rs = executeStatement("LeerAdmin", data.getInt("board_id"));
		return this.getArray();
	}
	
	public ArrayList <JSONObject> LeerPersonaAdmin(JSONObject data) throws SQLException{
		this.rs = executeStatement("LeerPersonaAdmin", data.getInt("id"), data.getInt("board_id"));
		return this.getArray();
	}
	
	public ArrayList <JSONObject> LeerUsuario(JSONObject data) throws SQLException{
		this.rs = executeStatement("LeerUsuario", data.getInt("board_id"));
		return this.getArray();
	}
	
	//--------------------------------------------------------Comentarios------------------------------------------------
	public boolean CrearComentario(JSONObject data) throws SQLException{
		int i = executeUpdate("CrearComentario", data.getInt("card_id"), data.getInt("user_id"), data.getString("comment_text"));
		return (i == 1)?true:false;
	}
	public ArrayList<JSONObject> LeerComentario(int card_id)throws SQLException{
		this.rs = executeStatement("LeerComentario", card_id);
		return this.getArray();
	}
	public JSONObject LeerComentarioEspecifico (JSONObject data)throws SQLException{
		this.rs = executeStatement("LeerComentarioEspecifico", data.getInt("comment_id"));
		return this.getData();
	}
	public boolean ActualizarComentario(JSONObject data) throws SQLException{
		int i = executeUpdate("ActualizarComentario",data.getString("comment_text"), data.getInt("comment_id"));
		return (i == 1)?true:false;
	}
	public boolean BorrarComentario(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarComentario", data.getInt("comment_id"));
		return (i == 1)?true:false;
	}
	
	
	//---------------------------------------------------Archivos---------------------------------------------------------------------
	public boolean AgregarArchivo(int card_id, int user_id, String file_url, String file_name) throws SQLException{
		int i = executeUpdate("AgregarArchivo", card_id, user_id, file_url, file_name);
		return (i == 1)?true:false;
	}
	public ArrayList<JSONObject> LeerArchivo(int card_id)throws SQLException{
		this.rs = executeStatement("LeerArchivo", card_id);
		return this.getArray();
	}
	public JSONObject LeerArchivoEspecifico (JSONObject data)throws SQLException{
		this.rs = executeStatement("LeerArchivoEspecifico", data.getInt("file_id"));
		return this.getData();
	}
	public JSONObject ObtenerNombreArchivo (Integer id)throws SQLException{
		this.rs = executeStatement("ObtenerNombreArchivo", id);
		return this.getData();
	}
	public JSONObject ObtenerIdArchivo (Integer card_id, String file_name)throws SQLException{
		this.rs = executeStatement("ObtenerIdArchivo", card_id, file_name);
		return this.getData();
	}
	public boolean BorrarArchivo(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarArchivo", data.getInt("file_id"));
		return (i == 1)?true:false;
	}
	public boolean VerificarArchivo(int card_id, String file_name) throws SQLException{
		this.rs = executeStatement("VerificarArchivo",card_id, file_name);
		return this.rs.next();
	}
	
	
}
