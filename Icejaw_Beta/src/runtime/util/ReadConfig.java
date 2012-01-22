package runtime.util;
import java.net.*;
import java.io.*;

public class ReadConfig{

	public String[] parseConfig(String Server){
		URL data;


		Server = Server.trim();
		Server = Server.replaceAll(" ","");
		BufferedReader in=null;
		String inputLine = "";
		String html = "";
		String settings[] = {"","",""};

		try{
			data = new URL(Server+"/icejaw/wizard_config.ini");

			try{
				in = new BufferedReader(
						new InputStreamReader(
								data.openStream()));
			}
			catch (IOException ex) {

				return null; 

			}

			try{
				while((inputLine = in.readLine()) != null){
					html = inputLine;

					html.replaceAll(" ", "");

					if(html.indexOf("online")==0){
						html = html.substring(7);
						settings[0]=html;

					}
					if(html.indexOf("title")==0 || html.indexOf("Title")==0){
						html = html.replace("title=","");
						settings[1]=html;
					}
					if(html.indexOf("minVersion")==0){
						html = html.substring(11);
						settings[2]=html;
					}


				}


				in.close();
			}
			catch (IOException ex) {
				return null;
			}

		}
		catch(MalformedURLException u) {
			return null;
		}



		return settings;
	}
}


