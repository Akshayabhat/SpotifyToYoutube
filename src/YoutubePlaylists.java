

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.omg.CORBA.portable.OutputStream;

/**
 * Servlet implementation class YoutubePlaylists
 */
@WebServlet("/YoutubePlaylists")
public class YoutubePlaylists extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String access_token;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YoutubePlaylists() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public String createPlaylist(String playlistName)
    {
    	//snippet object for request body
    	JSONObject snippet = new JSONObject ();
		JSONObject playlistCreds = new JSONObject();
		playlistCreds.put("title", playlistName);
		playlistCreds.put("description","From+your+Spotify+Playlist");
		snippet.put("snippet", playlistCreds);
		
		URL url;
		try {
			url = new URL("https://www.googleapis.com/youtube/v3/playlists?"
					+ "part=snippet%2Cstatus&"
					+ "key=AIzaSyBnsmsElZDJW_UFz6FxXpQxRwpburdtUJ8");
		System.out.println(snippet);
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
		
        con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
				
		con.setRequestMethod("POST");
        
        String auth = "Bearer " + access_token;
		con.setRequestProperty("Authorization",auth);
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        
		OutputStreamWriter os = 
			    new OutputStreamWriter(con.getOutputStream());	
		os.write(snippet.toString());
		os.flush();
		os.close();
    
		int responseCode = con.getResponseCode();
		//System.out.println("YoutubeSearch: "+ responseCode);
		System.out.println(responseCode);
		
		StringBuffer responseMessage = null;
		if(responseCode == 200)
		{
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
			String line;
			 responseMessage = new StringBuffer();	
			while((line=in.readLine())!= null)
			{
				responseMessage.append(line);
			}
		}
		
		System.out.println ("Playlist response message: " + responseMessage);
		try {
			JSONObject o = (JSONObject)new JSONParser().parse(responseMessage.toString());
			return o.get("id").toString(); // return playlist Id to calling function
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
       return null;
    }
    
    public void insertTrack (String playlistID, String videoID)
    {
    	//create snippet object for request body parameter
    	JSONObject resourceID = new JSONObject();
    	resourceID.put("kind", "youtube#video");
    	resourceID.put("videoId",videoID);
    	JSONObject snippetInner = new JSONObject ();
    	snippetInner.put("playlistId",playlistID);
		snippetInner.put("resourceId", resourceID);
    	JSONObject snippet = new JSONObject ();
        snippet.put("snippet", snippetInner);
        System.out.println(snippet.toString());
        URL url;
	
		try {
			
		url = new URL("https://www.googleapis.com/youtube/v3/playlistItems?"
				+ "part=snippet&"
				+ "key=AIzaSyBnsmsElZDJW_UFz6FxXpQxRwpburdtUJ8");
		
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
		
        con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
				
		con.setRequestMethod("POST");
        
       String auth = "Bearer " + access_token;
		con.setRequestProperty("Authorization",auth);
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        
		OutputStreamWriter os = 
			    new OutputStreamWriter(con.getOutputStream());	
		os.write(snippet.toString());
		os.flush();
		os.close();
    
		int responseCode = con.getResponseCode();
		//System.out.println("YoutubeSearch: "+ responseCode);
		System.out.println(responseCode);
		System.out.println(con.getResponseMessage());
		
		
      }catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		access_token = request.getParameter("access_token");
		ArrayList <String>  tracks = GetPlaylists.getTracks();
		int i;
		//search youTube for songs, store the top results in a list.
	for (String s: tracks)
		{
			String [] playlist = s.trim().split("/");
			
			String playlistName = playlist[0];
			//create playlist here.
			
			
			String playlistID = createPlaylist (playlistName);
			
			
			//Arraylist for tracks (URLS):
			 ArrayList <String> trackURLs = new ArrayList <String> ();
			for (i=1;i< playlist.length;i++)
			{
				String track = playlist[i].replaceAll(" ","+");
				
				URL url = new URL("https://www.googleapis.com/youtube/v3/search?"
						+ "part=snippet&maxResults=1&q="+track+"&type=video&"
						+ "key=AIzaSyBnsmsElZDJW_UFz6FxXpQxRwpburdtUJ8");
				HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
				con.setRequestMethod("GET");

				int responseCode = con.getResponseCode();
				//System.out.println("YoutubeSearch: "+ responseCode);
			
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
				
				JSONObject o;
			
					try {
					
					o = (JSONObject)new JSONParser().parse(responseMessage.toString());
					JSONArray items = (JSONArray) o.get("items");
					int n = items.size();
				
					for(int j = 0; j<n ; j++)
					{ 
						JSONObject item = (JSONObject) items.get(j);
						JSONObject id = (JSONObject)new JSONParser().parse(item.get("id").toString());
						
						String videoID = id.get("videoId").toString();
						insertTrack (playlistID,videoID);

					}
					}
					catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  
		}
		} 
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
