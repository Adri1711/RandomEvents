package com.adri1711.randomevents.match.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	private Player beast;

	private Player playerContador;

	private Map<String, FastBoard> scoreboards;
	private Map<String, Scoreboard> oldScoreboards;

	private List<Player> goalPlayers;
	private Map<Player, Kit> playerKits;

	private Map<Integer, Set<Player>> equipos;

	private Map<Integer, Set<Player>> teamsCopy;

	public MatchPlayerHandler() {
		this.players = new ArrayList<String>();
		this.playersTotal = new ArrayList<String>();

		this.playersObj = new ArrayList<Player>();
		this.playersTotalObj = new ArrayList<Player>();
		this.playersGanadores = new ArrayList<Player>();
		this.playersSpectators = new ArrayList<Player>();
		this.playersVanish = new ArrayList<Player>();
		this.equipos = new HashMap<Integer, Set<Player>>();
		this.teamsCopy = new HashMap<Integer, Set<Player>>();
		this.goalPlayers = new ArrayList<Player>();
		this.scoreboards = new HashMap<String, FastBoard>();
		this.oldScoreboards = new HashMap<String, Scoreboard>();
		this.playerKits = new HashMap<Player, Kit>();
	}

	public MatchPlayerHandler(List<String> players, List<Player> playersGanadores, List<Player> playersSpectators) {
		this.players = players;
		this.playersTotal = new ArrayList<String>();
		playersTotal.addAll(players);
		this.playersTotalObj = new ArrayList<Player>();
		playersTotalObj.addAll(playersObj);
		this.playersObj = playersGanadores;
		this.playersGanadores = new ArrayList<Player>();
		this.playersSpectators = playersSpectators;
		this.playersVanish = new ArrayList<Player>();

		this.equipos = new HashMap<Integer, Set<Player>>();
		this.teamsCopy = new HashMap<Integer, Set<Player>>();

		this.goalPlayers = new ArrayList<Player>();
		scoreboards = new HashMap<String, FastBoard>();
		oldScoreboards = new HashMap<String, Scoreboard>();
		this.playerKits = new HashMap<Player, Kit>();

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

}
