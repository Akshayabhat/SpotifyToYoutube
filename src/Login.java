

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Login() {
        super();
      
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*System.out.println("Hello");
		//URL authURL = new URL("https://accounts.spotify.com/authorize?client_id=0127ad93cafb4d818a21570ecfcbc874&response_type=code&redirect_uri=https%3A%2F%2Flocalhost%3A11000%2F");
	   //  HttpURLConnection authConnection = (HttpURLConnection) authURL.openConnection();
	   
	      authConnection.connect();
	      System.out.println(authConnection.getResponseCode());*/
	      //System.out.println(authConnection.);
	      System.out.println("Something");
	      //httpServletResponse.sendRedirect("https://accounts.spotify.com/authorize?client_id=0127ad93cafb4d818a21570ecfcbc874&response_type=code&redirect_uri=https%3A%2F%2Flocalhost%3A11000%2F");
	     
	     response.sendRedirect("https://accounts.spotify.com/authorize/?client_id=0127ad93cafb4d818a21570ecfcbc874&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FSpotifyToYoutube%2FOAuth");
		//System.out.println("hello");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
