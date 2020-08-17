package WindowsSettings;

import java.util.ArrayList;
import java.util.List;

public class Quad9 implements IdnsServers {

	
	private String name = "Quad9";
	private List<String> servers = new ArrayList<>();
	
	
	public Quad9() {
		servers.add("9.9.9.9");
		servers.add("149.112.112.112");
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
