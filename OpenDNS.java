package WindowsSettings;

import java.util.ArrayList;
import java.util.List;

public class OpenDNS implements IdnsServers {

	private String name = "OpenDNS";
	private List<String> servers = new ArrayList<>();
	
	public OpenDNS() {
		servers.add("208.67.222.222");
		servers.add("208.67.220.220");
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getServers() {
		return servers;
	}

}
