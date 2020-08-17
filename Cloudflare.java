package WindowsSettings;

import java.util.ArrayList;
import java.util.List;

public class Cloudflare implements IdnsServers {

	
	private String name = "Cloudflare";
	private List<String> servers = new ArrayList<>();
	
	public Cloudflare() {
		servers.add("1.1.1.1");
		servers.add("1.0.0.1");
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
