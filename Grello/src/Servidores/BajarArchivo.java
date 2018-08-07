package Servidores;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Grello.Queries;

/**
 * Servlet implementation class BajarArchivo
 */
@WebServlet("/BajarArchivo")
public class BajarArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BajarArchivo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Estamos en bajar archivos");
		Queries db = new Queries();
		JSONObject fileData = new JSONObject();
		
       
        Integer id = Integer.parseInt(request.getParameter("id"));
        try {
			fileData = db.ObtenerNombreArchivo(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        System.out.println("aqui: ");
        String name = fileData.getString("file_name");
        String []part = name.split("\\.");
        String tipo = part[1];
        System.out.println("tipo: "+tipo);
        
        response.setContentType("file");
        response.setHeader("Content-disposition","attachment; filename="+name+"."+tipo);
        /*if("jpg".equals(tipo)){
        	response.setHeader("Content-disposition","attachment; filename="+name+".jpg");
        }else if("pdf".equals(tipo)) {
        	response.setHeader("Content-disposition","attachment; filename="+name+".pdf");
        }*/
        
        
        
        System.out.println("Nombre del archivo: "+name);
        //File my_file = new File("C:\\Users\\Gressia\\Downloads\\Prueba\\"+ nombre);
        File my_file = new File("C:\\Users\\Gressia\\git\\Grello\\Grello\\WebContent\\subida\\"+ id + "." + tipo);
        //File my_file = new File("C:\\test\\"+name);

        // This should send the file to browser
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
