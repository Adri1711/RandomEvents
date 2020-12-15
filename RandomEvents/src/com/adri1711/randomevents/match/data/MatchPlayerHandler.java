package com.adri1711.randomevents.match.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scoreboard.Scoreboard;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.utils.Cuboid;
import com.adri1711.util.FastBoard;
import com.adri1711.util.enums.AMaterials;

public class MatchPlayerHandler {

	private List<String> players;

	private List<String> playersTotal;

	private List<Player> playersObj;

	private List<Player> playersGanadores;

	private List<Player> playersSpectators;

	private Player beast;

	private Player playerContador;
	
	private Map<String, FastBoard> scoreboards;
	private Map<String, Scoreboard> oldScoreboards;
	
	private List<Player> goalPlayers;

	private Map<Integer, List<Player>> equipos;

	public MatchPlayerHandler() {
		this.players = new ArrayList<String>();
		this.playersTotal = new ArrayList<String>();

		this.playersObj = new ArrayList<Player>();
		this.playersGanadores = new ArrayList<Player>();
		this.playersSpectators = new ArrayList<Player>();
		
		this.equipos = new HashMap<Integer, List<Player>>();
		this.goalPlayers = new ArrayList<Player>();
		this.scoreboards = new HashMap<String, FastBoard>();
		this.oldScoreboards = new HashMap<String, Scoreboard>();
	}

	public MatchPlayerHandler(List<String> players, List<Player> playersGanadores, List<Player> playersSpectators) {
		this.players = players;
		this.playersTotal = new ArrayList<String>();
		playersTotal.addAll(players);

		this.playersObj = playersGanadores;
		this.playersGanadores = new ArrayList<Player>();
		this.playersSpectators = playersSpectators;
		
		this.equipos = new HashMap<Integer, List<Player>>();

		this.goalPlayers = new ArrayList<Player>();
		scoreboards = new HashMap<String, FastBoard>();
		oldScoreboards = new HashMap<String, Scoreboard>();
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

	public Map<Integer, List<Player>> getEquipos() {
		return equipos;
	}

	public void setEquipos(Map<Integer, List<Player>> equipos) {
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
	
	
	
	
}
