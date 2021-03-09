package com.adri1711.randomevents.bbdd;

import com.zaxxer.hikari.HikariDataSource;

public class HikariCP {

	private HikariDataSource hikari;

	public HikariCP(String ip, String port, String database, String username, String password) {
		super();

		hikari = new HikariDataSource();
		
		hikari.setJdbcUrl("jdbc:mysql://"+ip+":"+port+"/"+database);
		hikari.setDriverClassName("com.mysql.jdbc.Driver");
		hikari.addDataSourceProperty("serverName", ip);
		hikari.addDataSourceProperty("port", port);
		hikari.addDataSourceProperty("databaseName", database);
		hikari.addDataSourceProperty("user", username);
		hikari.addDataSourceProperty("password", password);
		hikari.addDataSourceProperty("useSSL", false);
		
		
		hikari.setMinimumIdle(0);
		hikari.setConnectionTimeout(30000);
		hikari.setIdleTimeout(35000);
		hikari.setMaxLifetime(45000);
	}

	public void close() {
		if (hikari != null)
			hikari.close();
	}

	public HikariDataSource getHikari() {
		return hikari;
	}

	public void setHikari(HikariDataSource hikari) {
		this.hikari = hikari;
	}
	

}
