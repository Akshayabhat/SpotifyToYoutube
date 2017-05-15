

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class GetPlaylists
 */
@WebServlet("/GetPlaylists")
public class GetPlaylists extends HttpServlet {
	 HashMap <String,String> playlists;
	 String user_id;
	 String token;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPlaylists() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public String getID(String id)
    {
    	return playlists.get(id);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		user_id = (String) request.getAttribute("user_id");
		token = (String) request.getAttribute("token");
		System.out.println(user_id + " " + token);
		URL url = new URL("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
		con.setRequestMethod("GET");
		String auth = "Bearer " + token;
		con.setRequestProperty("Authorization",auth);
		con.setDoOutput(true);
		con.setDoInput(true);
		int responseCode = con.getResponseCode();
		System.out.println("playlists: "+ responseCode);
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
		
		try {
			JSONObject o = (JSONObject)new JSONParser().parse(responseMessage.toString());
			JSONArray items = (JSONArray) o.get("items");
			int n = items.size();
			playlists = new HashMap <String,String>();
			for(int i = 0; i<n; i++)
			{
				
				JSONObject item = (JSONObject) items.get(i);
				
				JSONObject owner = (JSONObject)new JSONParser().parse(item.get("owner").toString());
				
				System.out.println(owner.get("id").toString());
				if(owner.get("id").toString().equals(user_id))
				   playlists.put(item.get("name").toString(),item.get("id").toString());
				
		    }
			
		for(String s : playlists.keySet())
		{
			System.out.println(s.toString() + "," + playlists.get(s).toString());
		}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("playlists",playlists);
		request.getRequestDispatcher("/SelectPlaylists.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] ar =  request.getParameterValues("id");
		
		for(String s: ar)
		{
			System.out.println("Playlist: " + s);
			String playlist_id = playlists.get(s);
			System.out.println(playlist_id);
			URL url = new URL("https://api.spotify.com/v1/users/"+user_id+"/playlists/"+playlist_id+"/tracks");
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
			con.setRequestMethod("GET");
			String auth = "Bearer " + token;
			con.setRequestProperty("Authorization",auth);
			con.setDoOutput(true);
			con.setDoInput(true);
			int responseCode = con.getResponseCode();
			System.out.println("tracks: "+ responseCode);
		
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
			
			//System.out.println(responseMessage.toString());
		
			    JSONObject o;
				try {
					o = (JSONObject)new JSONParser().parse(responseMessage.toString());
					JSONArray items = (JSONArray) o.get("items");
					int n = items.size();
				
					for(int i = 0; i<n ; i++)
					{   String track_item = "";
						JSONObject item = (JSONObject) items.get(i);
						JSONObject track = (JSONObject)new JSONParser().parse(item.get("track").toString());
					    String track_name = track.get("name").toString() ;
					    track_item = track_item + track_name;				
					
						JSONArray artist_items = (JSONArray) track.get("artists");
						int size = artist_items.size();
						for(int j=0;j<size;j++)
						{
							JSONObject artist_item = (JSONObject)artist_items.get(j);
							String artist_name = artist_item.get("name").toString();
							track_item = track_item+ " by " + artist_name;
						}
						
						JSONObject album = (JSONObject)new JSONParser().parse(track.get("album").toString());
						String album_name = album.get("name").toString();
						track_item = track_item + " - " + album_name;
						System.out.println(track_item);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
	
		
	}

}
}