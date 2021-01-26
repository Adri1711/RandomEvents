package com.adri1711.randomevents.bbdd;

public class Queries {

	public static String UUID = "%field_UUID%";
	public static String NAME = "%field_name%";
	public static String GAME = "%field_game%";
	public static String EVENT = "%field_event%";
	public static String CREDITS = "%field_credits%";

	public static String CREATE_DATABASE_STATS = "CREATE TABLE IF NOT EXISTS revent_stats(UUID varchar(36), name VARCHAR(25),game VARCHAR(16),wins int, tries int, UNIQUE unique_index(UUID,name,game), INDEX index_uuid(UUID),INDEX index_name(name),INDEX index_game(game))";

	public static String INSERT_UPDATE_TRIES = "insert into revent_stats(UUID,name,game,wins,tries) values('%field_UUID%','%field_name%','%field_game%',0,1) ON DUPLICATE KEY UPDATE tries=tries+1";

	public static String INSERT_UPDATE_WINS = "insert into revent_stats(UUID,name,game,wins,tries) values('%field_UUID%','%field_name%','%field_game%',1,1) ON DUPLICATE KEY UPDATE wins=wins+1";

	public static String SELECT_ALL_UUID_MODE = "select * from revent_stats where UUID='%field_UUID%'";

	public static String SELECT_ALL_NAME_MODE = "select * from revent_stats where name='%field_name%'";

	public static String SELECT_GAME_UUID_MODE = "select * from revent_stats where UUID='%field_UUID%' and game='%field_game%'";

	public static String SELECT_GAME_NAME_MODE = "select * from revent_stats where name='%field_name%' and game='%field_game%'";

	public static String SELECT_TRIES_GAME_UUID_MODE = "select sum(tries) as tries from revent_stats where UUID='%field_UUID%' and game='%field_game%'";

	public static String SELECT_TRIES_GAME_NAME_MODE = "select sum(tries) as tries from revent_stats where name='%field_name%' and game='%field_game%'";

	public static String SELECT_WINS_GAME_UUID_MODE = "select sum(wins) as wins from revent_stats where UUID='%field_UUID%' and game='%field_game%'";

	public static String SELECT_WINS_GAME_NAME_MODE = "select sum(wins) as wins from revent_stats where name='%field_name%' and game='%field_game%'";

	public static String SELECT_TRIES_ALL_UUID_MODE = "select sum(tries) as tries from revent_stats where UUID='%field_UUID%'";

	public static String SELECT_TRIES_ALL_NAME_MODE = "select sum(tries) as tries from revent_stats where name='%field_name%'";

	public static String SELECT_WINS_ALL_UUID_MODE = "select sum(wins) as wins from revent_stats where UUID='%field_UUID%'";

	public static String SELECT_WINS_ALL_NAME_MODE = "select sum(wins) as wins from revent_stats where name='%field_name%'";

	public static String CREATE_DATABASE_CREDITS = "CREATE TABLE IF NOT EXISTS revent_credits(UUID varchar(36), name VARCHAR(25),event VARCHAR(50),credits int, UNIQUE unique_cred_index(UUID,name,event), INDEX index_cred_uuid(UUID),INDEX index_cred_name(name),INDEX index_cred_game(event))";

	public static String INSERT_UPDATE_ADD_CREDITS = "insert into revent_credits(UUID,name,event,credits) values('%field_UUID%','%field_name%','%field_event%', %field_credits%) ON DUPLICATE KEY UPDATE credits=credits+(%field_credits%)";

	public static String UPDATE_REMOVE_CREDITS_NAME_MODE = "UPDATE revent_credits SET credits=credits-1 where name='%field_name%' and event='%field_event%' and credits <> 0 limit 1";

	public static String UPDATE_REMOVE_CREDITS_UUID_MODE = "UPDATE revent_credits SET credits=credits-1 where UUID='%field_UUID%' and event='%field_event%' and credits <> 0 limit 1";

	public static String SELECT_CREDITS_EVENT_UUID_MODE = "select sum(credits) as credits from revent_credits where UUID='%field_UUID%' and event='%field_event%'";

	public static String SELECT_CREDITS_EVENT_NAME_MODE = "select sum(credits) as credits from revent_credits where name='%field_name%' and event='%field_event%'";

	public static String PURGE_CREDITS = "DELETE FROM revent_credits where credits=0";

	public static String SELECT_ALL_CREDITS_UUID_MODE = "select UUID,event,sum(credits) as credits from revent_credits where UUID='%field_UUID%' group by UUID,event";

	public static String SELECT_ALL_CREDITS_NAME_MODE = "select name,event,sum(credits) as credits from revent_credits where name='%field_name%' group by name,event";

}
