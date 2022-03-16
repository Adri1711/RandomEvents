package com.adri1711.randomevents.match.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import com.adri1711.randomevents.match.Kit;
import com.adri1711.util.FastBoard;

public class MatchPlayerHandler {

	private List<String> players;

	private List<String> playersTotal;

	private List<Player> playersTotalObj;

	private List<Player> playersObj;

	private List<Player> playersGanadores;

	private List<Player> playersSpectators;

	private List<Player> playersVanish;
	
	private Map<String,Long> playersInvincible;

	private Player beast;

	private Player playerContador;

	private Map<Player, Long> playersContadores;

	private Map<String, String> playersPrefix;

	private Map<String, FastBoard> scoreboards;
	private Map<String, Scoreboard> oldScoreboards;

	private List<Player> goalPlayers;

	private Set<Player> paintPlayers;
	private Set<Player> playerToKill;

	private Map<Player, Set<Location>> paintedLocations;

	private Map<Player, Kit> playerKits;

	private Map<Integer, Set<Player>> equipos;

	private Map<Integer, Set<Player>> teamsCopy;

	public MatchPlayerHandler() {
		this.playersInvincible = new HashMap<String,Long>();
		this.players = new ArrayList<String>();
		this.playersTotal = new ArrayList<String>();
		this.playersPrefix = new HashMap<String, String>();
		this.playersObj = new ArrayList<Player>();
		this.playersTotalObj = new ArrayList<Player>();
		this.playersGanadores = new ArrayList<Player>();
		this.playersSpectators = new ArrayList<Player>();
		this.playersVanish = new ArrayList<Player>();
		this.equipos = new HashMap<Integer, Set<Player>>();
		this.teamsCopy = new HashMap<Integer, Set<Player>>();
		this.paintPlayers = new HashSet<Player>();
		this.playerToKill = new HashSet<Player>();
		this.goalPlayers = new ArrayList<Player>();
		this.scoreboards = new HashMap<String, FastBoard>();
		this.oldScoreboards = new HashMap<String, Scoreboard>();
		this.playerKits = new HashMap<Player, Kit>();
		this.playersContadores = new HashMap<Player, Long>();
		this.paintedLocations = new HashMap<Player, Set<Location>>();
	}

	public MatchPlayerHandler(List<String> players, List<Player> playersGanadores, List<Player> playersSpectators) {
		this.playersInvincible = new HashMap<String,Long>();

		this.players = players;
		this.playersTotal = new ArrayList<String>();
		this.playersObj=new ArrayList<Player>();
		playersTotal.addAll(players);
		this.playersTotalObj = new ArrayList<Player>();
		this.playersObj = playersGanadores;
		playersTotalObj.addAll(playersObj);
		this.playersGanadores = new ArrayList<Player>();
		this.playersSpectators = playersSpectators;
		this.playersVanish = new ArrayList<Player>();
		this.paintPlayers = new HashSet<Player>();
		this.playerToKill=new HashSet<Player>();
		this.playersPrefix = new HashMap<String, String>();

		this.equipos = new HashMap<Integer, Set<Player>>();
		this.teamsCopy = new HashMap<Integer, Set<Player>>();

		this.goalPlayers = new ArrayList<Player>();
		scoreboards = new HashMap<String, FastBoard>();
		oldScoreboards = new HashMap<String, Scoreboard>();
		this.playerKits = new HashMap<Player, Kit>();
		this.playersContadores = new HashMap<Player, Long>();
		this.paintedLocations = new HashMap<Player, Set<Location>>();

	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public List<Player> getPlayersObj() {
		return playersObj;
	}

	public void setPlayersObj(List<Player> playersObj) {
		this.playersObj = playersObj;
	}

	public Player getPlayerContador() {
		return playerContador;
	}

	public void setPlayerContador(Player playerContador) {
		this.playerContador = playerContador;
	}

	public List<Player> getPlayersSpectators() {
		return playersSpectators;
	}

	public void setPlayersSpectators(List<Player> playersSpectators) {
		this.playersSpectators = playersSpectators;
	}

	public List<Player> getPlayersGanadores() {
		return playersGanadores;
	}

	public void setPlayersGanadores(List<Player> playersGanadores) {
		this.playersGanadores = playersGanadores;
	}

	public Player getBeast() {
		return beast;
	}

	public void setBeast(Player beast) {
		this.beast = beast;
	}

	public List<String> getPlayersTotal() {
		return playersTotal;
	}

	public void setPlayersTotal(List<String> playersTotal) {
		this.playersTotal = playersTotal;
	}

	public Map<Integer, Set<Player>> getEquipos() {
		return equipos;
	}

	public void setEquipos(Map<Integer, Set<Player>> equipos) {
		this.equipos = equipos;
	}

	public Map<String, FastBoard> getScoreboards() {
		return scoreboards;
	}

	public void setScoreboards(Map<String, FastBoard> scoreboards) {
		this.scoreboards = scoreboards;
	}

	public Map<String, Scoreboard> getOldScoreboards() {
		return oldScoreboards;
	}

	public void setOldScoreboards(Map<String, Scoreboard> oldScoreboards) {
		this.oldScoreboards = oldScoreboards;
	}

	public List<Player> getGoalPlayers() {
		return goalPlayers;
	}

	public void setGoalPlayers(List<Player> goalPlayers) {
		this.goalPlayers = goalPlayers;
	}

	public List<Player> getPlayersVanish() {
		return playersVanish;
	}

	public void setPlayersVanish(List<Player> playersVanish) {
		this.playersVanish = playersVanish;
	}

	public Map<Player, Kit> getPlayerKits() {
		return playerKits;
	}

	public void setPlayerKits(Map<Player, Kit> playerKits) {
		this.playerKits = playerKits;
	}

	public Map<Integer, Set<Player>> getTeamsCopy() {
		return teamsCopy;
	}

	public void setTeamsCopy(Map<Integer, Set<Player>> teamsCopy) {
		this.teamsCopy = teamsCopy;
	}

	public List<Player> getPlayersTotalObj() {
		return playersTotalObj;
	}

	public void setPlayersTotalObj(List<Player> playersTotalObj) {
		this.playersTotalObj = playersTotalObj;
	}

	public Map<Player, Long> getPlayersContadores() {
		return playersContadores;
	}

	public void setPlayersContadores(Map<Player, Long> playersContadores) {
		this.playersContadores = playersContadores;
	}

	public Set<Player> getPaintPlayers() {
		return paintPlayers;
	}

	public void setPaintPlayers(Set<Player> paintPlayers) {
		this.paintPlayers = paintPlayers;
	}

	public Set<Player> getPlayerToKill() {
		return playerToKill;
	}

	public void setPlayerToKill(Set<Player> playerToKill) {
		this.playerToKill = playerToKill;
	}

	public Map<Player, Set<Location>> getPaintedLocations() {
		return paintedLocations;
	}

	public void setPaintedLocations(Map<Player, Set<Location>> paintedLocations) {
		this.paintedLocations = paintedLocations;
	}

	public Map<String, String> getPlayersPrefix() {
		return playersPrefix;
	}

	public void setPlayersPrefix(Map<String, String> playersPrefix) {
		this.playersPrefix = playersPrefix;
	}

	public Map<String,Long> getPlayersInvincible() {
		return playersInvincible;
	}

	public void setPlayersInvincible(Map<String,Long> playersInvincible) {
		this.playersInvincible = playersInvincible;
	}
	

}
