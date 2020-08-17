package WindowsSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//"runas /profile /user:Administrator \"cmd.exe /c Powrprof.dll,SetSuspendState\""

public class DNS implements Settings {

	private String myServer = "";
	private Runtime rt = Runtime.getRuntime();
	private Map<String, List<String>> routers = new HashMap<>();
	private List<String> Allservers = new ArrayList<>();
	
	public DNS() {
		initServers(Arrays.asList(new Cloudflare(), new Google(), new OpenDNS(), new Quad9()));	
	}
	
	/**
	 * Changes the myServer variable to the server of choosing
	 * @param path
	 * @return
	 */
	public boolean DNSpath(String path) {
		path.toLowerCase();
		if (routers.containsKey(path)) {
			myServer = path;
			return true;
		}
		return false;
		
	}
	
	public List<String> routers(){
		return Allservers;
	}
	/**
	 * Changes the current DNS to server to a server of choosing
	 */
	public boolean change() throws IOException {
		try {
			rt.exec("runas /profile /user:Administrator \"cmd.exe /c Powrprof.dll,SetSuspendState\"");	
			Process pross = rt.exec("ipconfig");	
			BufferedReader cmdlines = new BufferedReader(new InputStreamReader(pross.getInputStream()));
			String cnName = findMyConnection(cmdlines);
			if(!run(cnName)) {
				return false;
			}
			return true;
		}
		
		catch(IOException e){
			throw new IOException("Cannot find directory");
		}
	}
	/**
	 * Finds the current internet connection
	 * @param lines(from ipcondfiguration)
	 * @return
	 * @throws IOException
	 */
	public String findMyConnection(BufferedReader lines) throws IOException{
		List<List<String>> cnCentral = new ArrayList<>();
		List<String> cnTypes = new ArrayList<>();
		cnTypes.add("Ethernet");
		cnTypes.add("Wireless");
		String connectionname = "";
		String s = null;
		List<String> cnInfo = new ArrayList<>();
		while ((s = lines.readLine()) != null) {
			if(cnTypes.contains(s.split(" ")[0])) {
				cnCentral.add(cnInfo);
				cnInfo= new ArrayList<>();
			}
			for(String word : s.split(" ")) {
				cnInfo.add(word);
			}
		}
		cnCentral.add(cnInfo);
		for(List<String> networkInfo: cnCentral) {
			if(networkInfo.contains("disconnected") || networkInfo.contains("Local") || networkInfo.contains("Configuration")) {
				continue;
			}
			for(String word : networkInfo) {
				if(word.equals("adapter")) {
					connectionname = networkInfo.get(networkInfo.indexOf(word)+1);
					if(connectionname.contains(":")) {
						connectionname = connectionname.substring(0, connectionname.length()-1);
					}
					else {
						connectionname = connectionname + " " + networkInfo.get(networkInfo.indexOf(word)+2).substring(0, 
								networkInfo.get(networkInfo.indexOf(word)+2).length()-1);
					}
					break;
				}
			}
		}
		return "\""+connectionname + "\"";

}
	/**
	 * Initiate servers that your DNS can be changed to
	 * @param servers
	 */
	public void initServers(List<IdnsServers> servers) {
		for(IdnsServers server: servers) {
			Allservers.add(server.getName());
			routers.put(server.getName(), server.getServers());
		}
	}
	

	/**
	 * Changes the current DNS server to the server of choosing
	 * @param connection
	 * @return
	 * @throws IOException
	 */
	public boolean run(String connection) throws IOException {
		String Command = "netsh interface ip set dns" + " " + connection + " " + "static " + (routers.get(myServer)).get(0);
		Process pross = rt.exec(Command);
		BufferedReader lines = new BufferedReader(new InputStreamReader(pross.getInputStream()));
		String s = lines.readLine();
		if(s.length()>0){
			Command = "netsh interface ip set dns" + " " + connection + " " + "static" + " " + (routers.get(myServer)).get(1);
			pross = rt.exec(Command);
			lines = new BufferedReader(new InputStreamReader(pross.getInputStream()));
			s = lines.readLine();
			if(s.length()>0) {
				return false;
			}
		}
		return true;	
	}
}
