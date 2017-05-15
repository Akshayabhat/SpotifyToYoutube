

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.omg.CORBA.portable.OutputStream;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;




/**
 * Servlet implementation class OAuth
 */
@WebServlet("/OAuth")
public class OAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OAuth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String code = request.getParameter("code");
	    URL url = new URL("https://accounts.spotify.com/api/token");
	    HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
	    con.setRequestMethod("POST");
	    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	   
	    con.setRequestProperty("Content-Language", "en-US");
		String clientCredentials = "0127ad93cafb4d818a21570ecfcbc874:26a729f69def4c249ac03b622dc090ac";
		String auth = "Basic " + new String(Base64.encode(clientCredentials.getBytes()));
		con.setRequestProperty("Authorization",auth);
		String params = "grant_type=authorization_code&" +
				"code=" + code +
				"&redirect_uri=" + "http%3A%2F%2Flocalhost%3A8080%2FSpotifyToYoutube%2FOAuth";
		con.setDoOutput(true);
		con.setDoInput(true);
		OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
		os.write(params);
		os.flush();
		os.close();
		int responseCode = con.getResponseCode();
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
    String access_token="";
	try {
		JSONObject o = (JSONObject)new JSONParser().parse(responseMessage.toString());
		access_token = o.get("access_token").toString();
		System.out.println(access_token);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	RequestDispatcher rd = getServletContext().getRequestDispatcher("/UserDetails?token="+access_token);
	rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
