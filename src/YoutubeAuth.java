

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class YoutubeAuth
 */
@WebServlet("/YoutubeAuth")
public class YoutubeAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YoutubeAuth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String code = request.getParameter("code");
		URL url = new URL("https://www.googleapis.com/oauth2/v4/token");
		    HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
		    con.setRequestMethod("POST");
		    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    con.setRequestProperty("Content-Language", "en-US");
			
			String params = "grant_type=authorization_code&" +
					"code=" + code +
					"&client_id=650827717019-elefh626vu76crm7jmc9m275gt2ok578.apps.googleusercontent.com"+
					"&client_secret=f0eLwLVIv_JT2hvbkzMSY6_k"+
					"&redirect_uri=" + "http%3A%2F%2Flocalhost%3A8080%2FSpotifyToYoutube%2FYoutubeAuth";
			con.setDoOutput(true);
			con.setDoInput(true);
			OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
			os.write(params);
			os.flush();
			os.close();
			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
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
		
		
		request.getRequestDispatcher("/YoutubePlaylists?access_token="+access_token).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
