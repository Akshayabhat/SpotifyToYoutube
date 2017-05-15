

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Servlet implementation class UserDetails
 */
@WebServlet("/UserDetails")
public class UserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		URL url = new URL("https://api.spotify.com/v1/me");
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
		con.setRequestMethod("GET");
		String auth = "Bearer " + token;
		con.setRequestProperty("Authorization",auth);
		con.setDoOutput(true);
		con.setDoInput(true);
		int responseCode = con.getResponseCode();
		System.out.println("user: "+ responseCode);
		StringBuffer responseMessage = null;
		if(responseCode == 200)
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			 responseMessage = new StringBuffer();
			while((line=in.readLine())!= null)
			{
				responseMessage.append(line);
			}
		}
    String user_id="";
    
	try {
		JSONObject o = (JSONObject)new JSONParser().parse(responseMessage.toString());
		user_id = o.get("id").toString();
		System.out.println("user_id: " + user_id);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	RequestDispatcher rd = getServletContext().getRequestDispatcher("/GetPlaylists?user_id="+user_id+"$token="+token);
	request.setAttribute("user_id", user_id);
	request.setAttribute("token", token);
	rd.forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
