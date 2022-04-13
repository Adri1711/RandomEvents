package com.adri1711.randomevents.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.bbdd.Queries;
import com.adri1711.randomevents.bbdd.callback.Callback;
import com.adri1711.randomevents.bbdd.runnables.ExecuteBukkitRunnable;
import com.adri1711.randomevents.bbdd.runnables.QueryBukkitRunnable;
import com.adri1711.randomevents.bbdd.runnables.UpdateBukkitRunnable;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.placeholders.PlaceholderType;
import com.adri1711.randomevents.stats.Stats;

public class UtilsSQL {
	public static void checkTables(RandomEvents plugin) {
		new ExecuteBukkitRunnable(plugin.getHikari().getHikari(), Queries.CREATE_DATABASE_STATS,
				new Callback<Boolean, SQLException>() {
					@Override
					public void call(Boolean resultSet, SQLException thrown) {
						if (thrown == null) {

						} else {
						}
					}
				}).runTaskAsynchronously(plugin);

		new ExecuteBukkitRunnable(plugin.getHikari().getHikari(), Queries.CREATE_DATABASE_CREDITS,
				new Callback<Boolean, SQLException>() {
					@Override
					public void call(Boolean resultSet, SQLException thrown) {
						if (thrown == null) {

						} else {
						}
					}
				}).runTaskAsynchronously(plugin);

		new ExecuteBukkitRunnable(plugin.getHikari().getHikari(), Queries.PURGE_CREDITS,
				new Callback<Boolean, SQLException>() {
					@Override
					public void call(Boolean resultSet, SQLException thrown) {
						if (thrown == null) {

						} else {
						}
					}
				}).runTaskAsynchronously(plugin);
	}

	public static void getStats(Player p, RandomEvents plugin) {
		Stats estadistica = new Stats();
		String query = "";
		if (plugin.getReventConfig().isMysqlUUIDMode()) {
			query = Queries.SELECT_ALL_UUID_MODE.replaceAll(Queries.UUID, p.getUniqueId().toString());
		} else {
			query = Queries.SELECT_ALL_NAME_MODE.replaceAll(Queries.NAME, p.getName());

		}
		new QueryBukkitRunnable(plugin.getHikari().getHikari(), query, new Callback<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet resultSet, SQLException thrown) {
				if (thrown == null) {
					try {
						while (resultSet.next()) {
							estadistica.addTries(resultSet.getString("game"), resultSet.getInt("tries"));
							estadistica.addWins(resultSet.getString("game"), resultSet.getInt("wins"));
						}
						Bukkit.getServer().getScheduler().runTask((Plugin) plugin, new Runnable() {
							public void run() {
								p.openInventory(UtilsRandomEvents.createGUI(p.getName(), estadistica, plugin));
							}
						});
					} catch (SQLException e) {
						plugin.getLoggerP().info(e.toString());
					}
				} else {
				}
			}
		}).runTaskAsynchronously(plugin);
	}

	public static void getStats(Player p, String name, RandomEvents plugin) {
		Stats estadistica = new Stats();
		String query = "";

		query = Queries.SELECT_ALL_NAME_MODE.replaceAll(Queries.NAME, name);

		new QueryBukkitRunnable(plugin.getHikari().getHikari(), query, new Callback<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet resultSet, SQLException thrown) {
				if (thrown == null) {
					try {
						while (resultSet.next()) {
							estadistica.addTries(resultSet.getString("game"), resultSet.getInt("tries"));
							estadistica.addWins(resultSet.getString("game"), resultSet.getInt("wins"));
						}
						Bukkit.getServer().getScheduler().runTask((Plugin) plugin, new Runnable() {
							public void run() {
								p.openInventory(UtilsRandomEvents.createGUI(name, estadistica, plugin));
							}
						});
					} catch (SQLException e) {
						plugin.getLoggerP().info(e.toString());
					}
				} else {
				}
			}
		}).runTaskAsynchronously(plugin);
	}

	public static void updateTries(Player p, MinigameType minigame, RandomEvents plugin) {
		String query = "";
		if (plugin.getReventConfig().isMysqlEnabled()) {
			query = Queries.INSERT_UPDATE_TRIES.replaceAll(Queries.UUID, p.getUniqueId().toString())
					.replaceAll(Queries.NAME, p.getName()).replaceAll(Queries.GAME, minigame.getCodigo());

			new UpdateBukkitRunnable(plugin.getHikari().getHikari(), query, new Callback<Integer, SQLException>() {
				@Override
				public void call(Integer resultSet, SQLException thrown) {
					if (thrown == null) {

					} else {
						plugin.getLoggerP().info(thrown.toString());
					}
				}
			}).runTaskAsynchronously(plugin);
		}
	}

	public static void updateWins(Player p, MinigameType minigame, RandomEvents plugin) {
		String query = "";
		if (plugin.getReventConfig().isMysqlEnabled()) {

			query = Queries.INSERT_UPDATE_WINS.replaceAll(Queries.UUID, p.getUniqueId().toString())
					.replaceAll(Queries.NAME, p.getName()).replaceAll(Queries.GAME, minigame.getCodigo());

			new UpdateBukkitRunnable(plugin.getHikari().getHikari(), query, new Callback<Integer, SQLException>() {
				@Override
				public void call(Integer resultSet, SQLException thrown) {
					if (thrown == null) {

					} else {
						plugin.getLoggerP().info(thrown.toString());
					}
				}
			}).runTaskAsynchronously(plugin);
		}
	}

	public static Integer getAllStatsSync(Player p, PlaceholderType type, RandomEvents plugin) {
		Integer res = null;
		String query = "";
		if (plugin.getReventConfig().isMysqlUUIDMode()) {
			if (type == PlaceholderType.TRIES)
				query = Queries.SELECT_TRIES_ALL_UUID_MODE.replaceAll(Queries.UUID, p.getUniqueId().toString());
			else
				query = Queries.SELECT_WINS_ALL_UUID_MODE.replaceAll(Queries.UUID, p.getUniqueId().toString());

		} else {
			if (type == PlaceholderType.TRIES)
				query = Queries.SELECT_TRIES_ALL_NAME_MODE.replaceAll(Queries.NAME, p.getName());
			else
				query = Queries.SELECT_WINS_ALL_NAME_MODE.replaceAll(Queries.NAME, p.getName());
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.getHikari().getHikari().getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (type == PlaceholderType.TRIES)
					res = resultSet.getInt("tries");
				else
					res = resultSet.getInt("wins");

			}
		} catch (SQLException e) {
			// TODO

		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	public static Integer getGameStatsSync(Player p, PlaceholderType type, MinigameType minigameType,
			RandomEvents plugin) {
		Integer res = null;
		String query = "";
		if (plugin.getReventConfig().isMysqlUUIDMode()) {
			if (type == PlaceholderType.TRIES)
				query = Queries.SELECT_TRIES_GAME_UUID_MODE.replaceAll(Queries.UUID, p.getUniqueId().toString())
						.replaceAll(Queries.GAME, minigameType.getCodigo());
			else
				query = Queries.SELECT_WINS_GAME_UUID_MODE.replaceAll(Queries.UUID, p.getUniqueId().toString())
						.replaceAll(Queries.GAME, minigameType.getCodigo());

		} else {
			if (type == PlaceholderType.TRIES)
				query = Queries.SELECT_TRIES_GAME_NAME_MODE.replaceAll(Queries.NAME, p.getName())
						.replaceAll(Queries.GAME, minigameType.getCodigo());
			else
				query = Queries.SELECT_WINS_GAME_NAME_MODE.replaceAll(Queries.NAME, p.getName())
						.replaceAll(Queries.GAME, minigameType.getCodigo());
		}
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = plugin.getHikari().getHikari().getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (type == PlaceholderType.TRIES)
					res = resultSet.getInt("tries");
				else
					res = resultSet.getInt("wins");

			}
		} catch (SQLException e) {
			// TODO

		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	public static void getCreditsGUI(Player p, RandomEvents plugin) {
		Map<String, Integer> creditos = new HashMap<String, Integer>();

		if (plugin.getReventConfig().isMysqlEnabled()) {
			String query = "";
			if (plugin.getReventConfig().isMysqlUUIDMode()) {
				query = Queries.SELECT_ALL_CREDITS_UUID_MODE.replaceAll(Queries.UUID, p.getUniqueId().toString());
			} else {
				query = Queries.SELECT_ALL_CREDITS_NAME_MODE.replaceAll(Queries.NAME, p.getName());

			}
			new QueryBukkitRunnable(plugin.getHikari().getHikari(), query, new Callback<ResultSet, SQLException>() {
				@Override
				public void call(ResultSet resultSet, SQLException thrown) {
					if (thrown == null) {
						try {
							while (resultSet.next()) {
								if (creditos.containsKey(resultSet.getString("event"))) {
									creditos.put(resultSet.getString("event"),
											creditos.get(resultSet.getString("event")) + resultSet.getInt("credits"));

								} else {
									creditos.put(resultSet.getString("event"), resultSet.getInt("credits"));
								}

							}
							Bukkit.getServer().getScheduler().runTask((Plugin) plugin, new Runnable() {
								public void run() {
									p.openInventory(UtilsRandomEvents.createGUICredits(p, creditos, 0, plugin));
								}
							});
						} catch (SQLException e) {
							plugin.getLoggerP().info(e.toString());
						}
					} else {
					}
				}
			}).runTaskAsynchronously(plugin);
		} else {
			p.openInventory(UtilsRandomEvents.createGUICredits(p, creditos, 0, plugin));

		}
	}
	public static void getCreditsGUI(Player p, Integer page, RandomEvents plugin) {
		Map<String, Integer> creditos = new HashMap<String, Integer>();

		if (plugin.getReventConfig().isMysqlEnabled()) {
			String query = "";
			if (plugin.getReventConfig().isMysqlUUIDMode()) {
				query = Queries.SELECT_ALL_CREDITS_UUID_MODE.replaceAll(Queries.UUID, p.getUniqueId().toString());
			} else {
				query = Queries.SELECT_ALL_CREDITS_NAME_MODE.replaceAll(Queries.NAME, p.getName());

			}
			new QueryBukkitRunnable(plugin.getHikari().getHikari(), query, new Callback<ResultSet, SQLException>() {
				@Override
				public void call(ResultSet resultSet, SQLException thrown) {
					if (thrown == null) {
						try {
							while (resultSet.next()) {
								if (creditos.containsKey(resultSet.getString("event"))) {
									creditos.put(resultSet.getString("event"),
											creditos.get(resultSet.getString("event")) + resultSet.getInt("credits"));

								} else {
									creditos.put(resultSet.getString("event"), resultSet.getInt("credits"));
								}

							}
							Bukkit.getServer().getScheduler().runTask((Plugin) plugin, new Runnable() {
								public void run() {
									p.openInventory(UtilsRandomEvents.createGUICredits(p, creditos, page, plugin));
								}
							});
						} catch (SQLException e) {
							plugin.getLoggerP().info(e.toString());
						}
					} else {
					}
				}
			}).runTaskAsynchronously(plugin);
		} else {
			p.openInventory(UtilsRandomEvents.createGUICredits(p, creditos, page, plugin));

		}
	}

	public static void getCreditsText(Player p, Player playerBal, RandomEvents plugin) {
		Map<String, Integer> creditos = new HashMap<String, Integer>();
		String query = "";
		if (plugin.getReventConfig().isMysqlUUIDMode()) {
			query = Queries.SELECT_ALL_CREDITS_UUID_MODE.replaceAll(Queries.UUID, playerBal.getUniqueId().toString());
		} else {
			query = Queries.SELECT_ALL_CREDITS_NAME_MODE.replaceAll(Queries.NAME, playerBal.getName());

		}
		new QueryBukkitRunnable(plugin.getHikari().getHikari(), query, new Callback<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet resultSet, SQLException thrown) {
				if (thrown == null) {
					try {
						while (resultSet.next()) {
							if (creditos.containsKey(resultSet.getString("event"))) {
								creditos.put(resultSet.getString("event"),
										creditos.get(resultSet.getString("event")) + resultSet.getInt("credits"));

							} else {
								creditos.put(resultSet.getString("event"), resultSet.getInt("credits"));
							}

						}
						Bukkit.getServer().getScheduler().runTask((Plugin) plugin, new Runnable() {
							public void run() {
								UtilsRandomEvents.sendCreditsInfo(p, playerBal, creditos, plugin);
							}
						});
					} catch (SQLException e) {
						plugin.getLoggerP().info(e.toString());
					}
				} else {
				}
			}
		}).runTaskAsynchronously(plugin);
	}

	public static void addCredits(Player p, String event, Integer credits, RandomEvents plugin) {
		String query = "";
		if (plugin.getReventConfig().isMysqlEnabled()) {
			query = Queries.INSERT_UPDATE_ADD_CREDITS.replaceAll(Queries.UUID, p.getUniqueId().toString())
					.replaceAll(Queries.NAME, p.getName()).replaceAll(Queries.EVENT, event)
					.replaceAll(Queries.CREDITS, "" + credits);

			new UpdateBukkitRunnable(plugin.getHikari().getHikari(), query, new Callback<Integer, SQLException>() {
				@Override
				public void call(Integer resultSet, SQLException thrown) {
					if (thrown == null) {

					} else {
						plugin.getLoggerP().info(thrown.toString());
					}
				}
			}).runTaskAsynchronously(plugin);
		}
	}

	public static void removeCredits(Player p, String event, RandomEvents plugin) {
		String query = "";

		if (plugin.getReventConfig().isMysqlEnabled()) {

			if (plugin.getReventConfig().isMysqlUUIDMode()) {
				query = Queries.UPDATE_REMOVE_CREDITS_UUID_MODE.replaceAll(Queries.UUID, p.getUniqueId().toString())
						.replaceAll(Queries.EVENT, event);
			} else {
				query = Queries.UPDATE_REMOVE_CREDITS_NAME_MODE.replaceAll(Queries.NAME, p.getName())
						.replaceAll(Queries.EVENT, event);

			}
			new UpdateBukkitRunnable(plugin.getHikari().getHikari(), query, new Callback<Integer, SQLException>() {
				@Override
				public void call(Integer resultSet, SQLException thrown) {
					if (thrown == null) {

					} else {
						plugin.getLoggerP().info(thrown.toString());
					}
				}
			}).runTaskAsynchronously(plugin);
		}
	}

}
