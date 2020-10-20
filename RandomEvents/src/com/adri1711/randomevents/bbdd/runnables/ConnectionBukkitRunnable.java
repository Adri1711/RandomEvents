package com.adri1711.randomevents.bbdd.runnables;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Nullable;
import javax.sql.DataSource;

import org.bukkit.scheduler.BukkitRunnable;

import com.adri1711.randomevents.bbdd.callback.Callback;

public class ConnectionBukkitRunnable extends BukkitRunnable {
	private final DataSource dataSource;
	private final Callback<Connection, SQLException> callback;

	public ConnectionBukkitRunnable(DataSource dataSource, @Nullable Callback<Connection, SQLException> callback) {
		if (dataSource == null) {
			// TODO: IllegalArgumentException
		}

		if (callback == null) {
			// TODO: IllegalArgumentException
		}

		this.dataSource = dataSource;
		this.callback = callback;
	}

	@Override
	public void run() {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			callback.call(connection, null);
		} catch (SQLException e) {
			callback.call(null, e);
		} finally {
			if (connection != null) {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}