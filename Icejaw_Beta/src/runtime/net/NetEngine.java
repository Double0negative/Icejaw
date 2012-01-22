package runtime.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import runtime.main.Profile;
import runtime.main.index;
import runtime.util.Logger;

public class NetEngine{




	public String fetch(String url){

		URL data;
		String html = "";

		BufferedReader in = null;
		try {
			data = new URL(url);

			try {
				in = new BufferedReader(
						new InputStreamReader(data.openStream()));
			} catch (UnknownHostException tr) {
				Logger.warn("NO NETWORK CONNECTION");
				return "No Network " + tr.getMessage();
			}

			catch (IOException ex) {
				return "Exception" + ex.getMessage();
			}
			String inputLine = "";

			try {
				while ((inputLine = in.readLine()) != null) {

					/*if (inputLine.length() > 80) {
						inputLine = inputLine.substring(0, 80) + "\n"
								+ inputLine.substring(80);
					}*/
					html+= inputLine+ "\n";

				}

			} catch (IOException ex) {
				return "Exception" + ex.getMessage();

			}

		} catch (MalformedURLException u) {
			return "Exception" + u.getMessage();

		}
	
		return html;


	}

	public void send(Profile p,String text){

		String response = "";
		String data="";
		Logger.log(p.getUsername());
		try{
			data = URLEncoder.encode("user", "UTF-8") +"=" + URLEncoder.encode(p.getUsername(), "UTF-8");
			data += "&"+ URLEncoder.encode("hash", "UTF-8") + "=" + URLEncoder.encode(p.getPass(), "UTF-8");
			data += "&"+ URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(text, "UTF-8");
			URL url = new URL(p.getUrl()+"/icejaw/feed.php");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			   
			// Get The Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response = line;
			}
			Logger.log(data);
		}catch(Exception e){
			index.kill("Error Submting Message",e);
		}


	}
}


