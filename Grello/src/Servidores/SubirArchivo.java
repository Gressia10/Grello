package Servidores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import Grello.Queries;


/**
 * Servlet implementation class SubirArchivo
 */


@WebServlet("/SubirArchivo")
@MultipartConfig
public class SubirArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubirArchivo() {
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
		ArrayList <JSONObject> arrayFile = new ArrayList<JSONObject>();
		
		System.out.println("Estoy en el metodo get de Subir archivp");
		Integer card_id = Integer.parseInt(request.getParameter("card_id"));
		System.out.println("El card_id es "+ card_id);
		
		try {
			System.out.println("Comenzamos con la lectura de los archivos");
			arrayFile = db.LeerArchivo(card_id);
			if(!arrayFile.isEmpty()) {
				mensaje.put("status", 200).put("response",arrayFile);
				System.out.println("Se ha realizado la lectura de los archivos");
				System.out.println(arrayFile);
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
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject file_id = new JSONObject();
		boolean status = false;
		
		Queries db = new Queries();
		Part file = request.getPart("file");
		InputStream filecontent = file.getInputStream();
		OutputStream os = null;
		Integer id = Integer.parseInt(request.getParameter("user_id"));
		
		
		System.out.println("Estamos Aqui FileUp");
		Integer user_id = Integer.parseInt(request.getParameter("user_id"));
		Integer card_id = Integer.parseInt(request.getParameter("card_id"));
		String file_name = getFileName(file);
		String file_url = file.getName();
		System.out.println("user_id: "+user_id+" card_id: "+card_id+" file_name: "+file_name+" file_url: "+file_url);
		try {
			//String baseDir = "C:/Users/Gressia/Downloads/Prueba";
			String baseDir = "C:/Users/Gressia/git/Grello/Grello/WebContent/subida";
			//os = new FileOutputStream(baseDir );
			
			if(!db.VerificarArchivo(card_id, file_name)) {
				if(db.AgregarArchivo(card_id, user_id, file_url, file_name)) {
					status = true;
					file_id = db.ObtenerIdArchivo(card_id, file_name);
					System.out.println("El id del archivo es: " + file_id.getInt("file_id"));
				}
			
			String []part = file_name.split("\\.");
	        String tipo = part[1];
			os = new FileOutputStream(baseDir + "/" + file_id.getInt("file_id") + "." + tipo);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			}
			if(status) {
				mensaje.put("status", 200).put("response","La subida de archivo se realizo con exito");
				System.out.println("La subida de archivo se realizo con exito");
				}else {
					mensaje.put("status", 409).put("response","Ya existe el nombre del archivo en la tarjeta");
				}
			System.out.println("Todo Bien");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (filecontent != null) {
				filecontent.close();
			}
			if (os != null) {
				os.close();
			}
		}
		out.println(mensaje.toString());
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		ArrayList <JSONObject> arrayFile = new ArrayList<JSONObject>();
		JSONObject file_name = new JSONObject();
		
		JSONObject FileData = new JSONObject();
		
		System.out.println("la data es: "+data);
		
		
		try {
			file_name= db.ObtenerNombreArchivo(data.getInt("file_id"));
			String []part = file_name.getString("file_name").split("\\.");
	        String tipo = part[1];
	        File fichero = new File("C:\\Users\\Gressia\\git\\Grello\\Grello\\WebContent\\subida\\" + data.getInt("file_id") + "." + tipo);
	        if(db.SeleccionarAdmin(data.getInt("id"))) {
				System.out.println("Admin de la pagina");
				if (db.BorrarArchivo(data)) {
					if(fichero.delete()) {
						mensaje.put("status", 200).put("response", "El archivo fue borrado");
					}else {
						mensaje.put("status", 500).put("response","No se puedo borrar");
					}
				}else {
					mensaje.put("status", 500).put("response","No se puedo borrar el archivo de la base de datos");
				}
	        }else {
		        arrayFile = db.LeerPersonaAdmin(data);
				if(arrayFile.size() == 1) {
					System.out.println("Admin");
					if (db.BorrarArchivo(data)) {
						if(fichero.delete()) {
							mensaje.put("status", 200).put("response", "El archivo fue borrado");
						}else {
							mensaje.put("status", 500).put("response","No se puedo borrar");
						}
					}else {
						mensaje.put("status", 500).put("response","No se puedo borrar el archivo de la base de datos");
					}
				}else {
					System.out.println("Invitado");
					FileData = db.LeerArchivoEspecifico(data);
					System.out.println("El id del creador es: " +FileData.getInt("user_id"));
					System.out.println("El id del usuario actual es: "+ data.getInt("id"));
					if((data.getInt("id")) == (FileData.getInt("user_id"))) {
						boolean status = db.BorrarArchivo(data);
						if (status) {
							mensaje.put("status", 200).put("response", "El archivo fue borrado");
						}else {
							mensaje.put("status", 500).put("response","No se puedo borrar");
						}
					}else {
						mensaje.put("status", 409).put("response","No es el que subio el archivo a borrar");
						System.out.println("No es el creador del archivo a borrar");
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
	
	private String getFileName(Part part) {
		for(String content : part.getHeader("content-disposition").split(";")) {
			if(content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}
