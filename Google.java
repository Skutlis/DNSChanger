package WindowsSettings;

import java.util.ArrayList;
import java.util.List;

public class Google implements IdnsServers {

	private String name = "Google";
	private List<String> servers = new ArrayList<>();
	
	public Google() {
		servers.add("8.8.8.8");
		servers.add("8.8.4.4");
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
