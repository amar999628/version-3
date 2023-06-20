package com.nkxgen.spring.orm.model;

public class Module {
	private int modl_id;
	private String modl_name;
	private String modl_desc;
	private int modl_project_id;

	public Module(int modl_id, String modl_name, String modl_desc, int modl_project_id) {

		this.modl_id = modl_id;
		this.modl_name = modl_name;
		this.modl_desc = modl_desc;
		this.modl_project_id = modl_project_id;
	}

	public int getModl_id() {
		return modl_id;
	}

	public void setModl_id(int modl_id) {
		this.modl_id = modl_id;
	}

	public String getModl_name() {
		return modl_name;
	}

	public void setModl_name(String modl_name) {
		this.modl_name = modl_name;
	}

	public String getModl_desc() {
		return modl_desc;
	}

	public void setModl_desc(String modl_desc) {
		this.modl_desc = modl_desc;
	}

	public int getModl_project_id() {
		return modl_project_id;
	}

	public void setModl_project_id(int modl_project_id) {
		this.modl_project_id = modl_project_id;
	}

	@Override
	public String toString() {
		return "Module{" + "module_id=" + getModl_id() + ", module_displayname='" + getModl_name() + "" + '\'' + '}';
	}

}
