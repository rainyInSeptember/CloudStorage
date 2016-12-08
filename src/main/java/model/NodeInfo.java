package model;

public class NodeInfo {

	private Integer id;
	private String nodename;
	private String nodeip;
	private String capacity;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	public String getNodeip() {
		return nodeip;
	}
	public void setNodeip(String nodeip) {
		this.nodeip = nodeip;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}	
}
