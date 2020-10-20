package com.adri1711.randomevents.bbdd;

public class Queries {
	
	public static String UUID="%field_UUID%";
	public static String NAME="%field_name%";
	public static String GAME="%field_game%";
	
	

	public static String CREATE_DATABASE_STATS = "CREATE TABLE IF NOT EXISTS revent_stats(UUID varchar(36), name VARCHAR(25),game VARCHAR(16),wins int, tries int, UNIQUE unique_index(UUID,name,game), INDEX index_uuid(UUID),INDEX index_name(name),INDEX index_game(game))";
	
	public static String INSERT_UPDATE_TRIES="insert into revent_stats(UUID,name,game,wins,tries) values('%field_UUID%','%field_name%','%field_game%',0,1) ON DUPLICATE KEY UPDATE tries=tries+1";

	public static String INSERT_UPDATE_WINS="insert into revent_stats(UUID,name,game,wins,tries) values('%field_UUID%','%field_name%','%field_game%',1,1) ON DUPLICATE KEY UPDATE wins=wins+1";

	public static String SELECT_ALL_UUID_MODE= "select * from revent_stats where UUID='%field_UUID%'";
	
	public static String SELECT_ALL_NAME_MODE= "select * from revent_stats where name='%field_name%'";
	
	public static String SELECT_GAME_UUID_MODE= "select * from revent_stats where UUID='%field_UUID%' and game='%field_game%'";
	
	public static String SELECT_GAME_NAME_MODE= "select * from revent_stats where name='%field_name%' and game='%field_game%'";
	
	
	
	
	public static String SELECT_TRIES_GAME_UUID_MODE= "select sum(tries) as tries from revent_stats where UUID='%field_UUID%' and game='%field_game%'";
	
	public static String SELECT_TRIES_GAME_NAME_MODE= "select sum(tries) as tries from revent_stats where name='%field_name%' and game='%field_game%'";
	
	public static String SELECT_WINS_GAME_UUID_MODE= "select sum(wins) as wins from revent_stats where UUID='%field_UUID%' and game='%field_game%'";
	
	public static String SELECT_WINS_GAME_NAME_MODE= "select sum(wins) as wins from revent_stats where name='%field_name%' and game='%field_game%'";
	
    public static String SELECT_TRIES_ALL_UUID_MODE= "select sum(tries) as tries from revent_stats where UUID='%field_UUID%'";	
    
	public static String SELECT_TRIES_ALL_NAME_MODE= "select sum(tries) as tries from revent_stats where name='%field_name%'";	
	
	public static String SELECT_WINS_ALL_UUID_MODE= "select sum(wins) as wins from revent_stats where UUID='%field_UUID%'";	
	
	public static String SELECT_WINS_ALL_NAME_MODE= "select sum(wins) as wins from revent_stats where name='%field_name%'";
	

	
}
