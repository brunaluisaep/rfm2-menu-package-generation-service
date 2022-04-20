package com.rfm.packagegeneration.dto;

import java.util.List;

public class VolumeTable {
	private String volId;
	private String name;
	private String path;
	private List<Route> Routes;

	public String getVolId() {
		return volId;
	}

	public void setVolId(String volId) {
		this.volId = volId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Route> getRoutes() {
		return Routes;
	}

	public void setRoutes(List<Route> routes) {
		Routes = routes;
	}

	public class Route {
		private String id;
		private String source;
		private String routes;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getRoutes() {
			return routes;
		}

		public void setRoutes(String routes) {
			this.routes = routes;
		}

	}

}
