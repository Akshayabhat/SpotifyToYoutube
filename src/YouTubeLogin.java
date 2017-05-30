

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class YouTubeLogin
 */
@WebServlet("/YouTubeLogin")
public class YouTubeLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YouTubeLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	 ArrayList <String> tracks = (ArrayList<String>) request.getAttribute("tracks");
	 System.out.println("Enters");
	 response.sendRedirect("https://accounts.google.com/o/oauth2/v2/auth?" +
     "scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyoutube%20"
     + "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyoutubepartner&" +
     "response_type=code&" + 
     "include_granted_scopes=true&" +
     "state=state_parameter_passthrough_value&" +
     "redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FSpotifyToYoutube%2FYoutubeAuth&" +
     "client_id=650827717019-elefh626vu76crm7jmc9m275gt2ok578.apps.googleusercontent.com");
 
		
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
