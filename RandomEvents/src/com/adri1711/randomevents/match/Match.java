package com.adri1711.randomevents.match;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.match.utils.InventoryPers;

public class Match implements Comparable<Match> {

	private String name;
	
	private String permission;

	private InventoryPers inventory;

	private Integer amountPlayers;

	private Integer amountPlayersMin;

	private Location playerSpawn;

	private MinigameType minigame;

	private List<Location> spawns;

	private List<Location> entitySpawns;

	private List<Location> spectatorSpawns;

	private Double secondsMobSpawn;

	private Integer secondsToBegin;

	private Location location1;

	private Location location2;
	
	private Location auxLocation1;

	private Location auxLocation2;

	private EntityType mob;

	private Location eventSpawn;

	private List<String> rewards;

	private Integer tiempoPartida;
	
	private Integer timeRefill;

	private InventoryPers inventoryBeast;

	private InventoryPers inventoryRunners;

	private InventoryPers inventoryChests;
	
	private Boolean useOwnInventory;
	
	private Location beastSpawn;

	private String material;

	private List<MaterialData> datas;
	
	private Boolean allMaterialAllowed;

	private List<String> scenes;
	
	private List<String> kits;
	private List<String> commandsOnStart;
	private List<String> commandsOnKill;
	private List<String> commandsOnElimination;

	private Boolean enabled;
	private Boolean enabledSchedule;
	
	private Integer numberOfTeams;
	private Integer numberOfSeekers;
	private Integer NPCId;
	
	private Integer shrinkBlocks;
	
	private GameMode gamemode;
	
	private ItemStack head;
	
	private Integer blockTimer;
	private Integer blockDecreaseTimer;
	private Integer colorTimer;
	private Integer colorDecreaseTimer;

	public Match() {
		super();

		// TODO this.equipment=new ItemStack[0];
		this.rewards = new ArrayList<String>();
		this.entitySpawns = new ArrayList<Location>();
		this.spawns = new ArrayList<Location>();
		this.spectatorSpawns = new ArrayList<Location>();
		this.datas = new ArrayList<MaterialData>();
		this.scenes = new ArrayList<String>();
		this.kits = new ArrayList<String>();
		this.commandsOnStart = new ArrayList<String>();
		this.commandsOnKill = new ArrayList<String>();
		this.commandsOnElimination = new ArrayList<String>();
		this.useOwnInventory=false;
		this.allMaterialAllowed=false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InventoryPers getInventory() {
		return inventory;
	}

	public void setInventory(InventoryPers inventory) {
		this.inventory = inventory;
	}

	// public ItemStack[] getEquipment() {
	// return equipment;
	// }
	//
	//
	// public void setEquipment(ItemStack[] equipment) {
	// this.equipment = equipment;
	// }

	public Boolean getAllMaterialAllowed() {
		return allMaterialAllowed;
	}

	public void setAllMaterialAllowed(Boolean allMaterialAllowed) {
		this.allMaterialAllowed = allMaterialAllowed;
	}

	public Integer getAmountPlayers() {
		return amountPlayers;
	}

	public void setAmountPlayers(Integer amountPlayers) {
		this.amountPlayers = amountPlayers;
	}

	public Integer getAmountPlayersMin() {
		return amountPlayersMin;
	}

	public void setAmountPlayersMin(Integer amountPlayersMin) {
		this.amountPlayersMin = amountPlayersMin;
	}

	public Location getPlayerSpawn() {
		return playerSpawn;
	}

	public void setPlayerSpawn(Location playerSpawn) {
		this.playerSpawn = playerSpawn;
	}

	public MinigameType getMinigame() {
		return minigame;
	}

	public void setMinigame(MinigameType minigame) {
		this.minigame = minigame;
	}

	public List<Location> getSpawns() {
		return spawns;
	}

	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}

	public Double getSecondsMobSpawn() {
		return secondsMobSpawn;
	}

	public void setSecondsMobSpawn(Double secondsMobSpawn) {
		this.secondsMobSpawn = secondsMobSpawn;
	}

	public Location getEventSpawn() {
		return eventSpawn;
	}

	public void setEventSpawn(Location eventSpawn) {
		this.eventSpawn = eventSpawn;
	}

	public List<String> getRewards() {
		return rewards;
	}

	public void setRewards(List<String> rewards) {
		this.rewards = rewards;
	}

	public Integer getTiempoPartida() {
		return tiempoPartida;
	}

	public void setTiempoPartida(Integer tiempoPartida) {
		this.tiempoPartida = tiempoPartida;
	}

	public Integer getTimeRefill() {
		return timeRefill;
	}

	public void setTimeRefill(Integer timeRefill) {
		this.timeRefill = timeRefill;
	}

	public Location getLocation1() {
		return location1;
	}

	public void setLocation1(Location location1) {
		this.location1 = location1;
	}

	public Location getLocation2() {
		return location2;
	}

	public void setLocation2(Location location2) {
		this.location2 = location2;
	}

	public EntityType getMob() {
		return mob;
	}

	public void setMob(EntityType mob) {
		this.mob = mob;
	}

	public List<Location> getSpectatorSpawns() {
		return spectatorSpawns;
	}

	public void setSpectatorSpawns(List<Location> spectatorSpawns) {
		this.spectatorSpawns = spectatorSpawns;
	}

	public List<Location> getEntitySpawns() {
		return entitySpawns;
	}

	public void setEntitySpawns(List<Location> entitySpawns) {
		this.entitySpawns = entitySpawns;
	}

	public InventoryPers getInventoryBeast() {
		return inventoryBeast;
	}

	public void setInventoryBeast(InventoryPers inventoryBeast) {
		this.inventoryBeast = inventoryBeast;
	}

	public InventoryPers getInventoryRunners() {
		return inventoryRunners;
	}

	public void setInventoryRunners(InventoryPers inventoryRunners) {
		this.inventoryRunners = inventoryRunners;
	}

	public Location getBeastSpawn() {
		return beastSpawn;
	}

	public void setBeastSpawn(Location beastSpawn) {
		this.beastSpawn = beastSpawn;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public List<MaterialData> getDatas() {
		return datas;
	}

	public void setDatas(List<MaterialData> datas) {
		this.datas = datas;
	}

	public InventoryPers getInventoryChests() {
		return inventoryChests;
	}

	public void setInventoryChests(InventoryPers inventoryChests) {
		this.inventoryChests = inventoryChests;
	}

	public Integer getSecondsToBegin() {
		return secondsToBegin;
	}

	public void setSecondsToBegin(Integer secondsToBegin) {
		this.secondsToBegin = secondsToBegin;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getScenes() {
		return scenes;
	}
	

	public Boolean getEnabledSchedule() {
		return enabledSchedule;
	}

	public void setEnabledSchedule(Boolean enabledSchedule) {
		this.enabledSchedule = enabledSchedule;
	}

	public void setScenes(List<String> scenes) {
		this.scenes = scenes;
	}
	
	

	public List<String> getKits() {
		return kits;
	}

	public void setKits(List<String> kits) {
		this.kits = kits;
	}

	
	public Integer getNPCId() {
		return NPCId;
	}

	public void setNPCId(Integer nPCId) {
		NPCId = nPCId;
	}

	
	public Boolean getUseOwnInventory() {
		return useOwnInventory;
	}

	public void setUseOwnInventory(Boolean useOwnInventory) {
		this.useOwnInventory = useOwnInventory;
	}

	@Override
	public String toString() {
		return "Match [name=" + name + ", inventory=" + inventory + ", amountPlayers=" + amountPlayers
				+ ", amountPlayersMin=" + amountPlayersMin + ", playerSpawn=" + playerSpawn + ", minigame=" + minigame
				+ ", spawns=" + spawns + ", entitySpawns=" + entitySpawns + ", spectatorSpawns=" + spectatorSpawns
				+ ", secondsMobSpawn=" + secondsMobSpawn + ", location1=" + location1 + ", location2=" + location2
				+ ", mob=" + mob + ", eventSpawn=" + eventSpawn + ", rewards=" + rewards + ", tiempoPartida="
				+ tiempoPartida + ", inventoryBeast=" + inventoryBeast + ", inventoryRunners=" + inventoryRunners
				+ ", beastSpawn=" + beastSpawn + ", material=" + material + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amountPlayers == null) ? 0 : amountPlayers.hashCode());
		result = prime * result + ((amountPlayersMin == null) ? 0 : amountPlayersMin.hashCode());
		result = prime * result + ((beastSpawn == null) ? 0 : beastSpawn.hashCode());
		result = prime * result + ((entitySpawns == null) ? 0 : entitySpawns.hashCode());
		result = prime * result + ((eventSpawn == null) ? 0 : eventSpawn.hashCode());
		result = prime * result + ((inventory == null) ? 0 : inventory.hashCode());
		result = prime * result + ((inventoryBeast == null) ? 0 : inventoryBeast.hashCode());
		result = prime * result + ((inventoryRunners == null) ? 0 : inventoryRunners.hashCode());
		result = prime * result + ((location1 == null) ? 0 : location1.hashCode());
		result = prime * result + ((location2 == null) ? 0 : location2.hashCode());
		result = prime * result + ((auxLocation1 == null) ? 0 : auxLocation1.hashCode());
		result = prime * result + ((auxLocation2 == null) ? 0 : auxLocation2.hashCode());
		result = prime * result + ((material == null) ? 0 : material.hashCode());
		result = prime * result + ((minigame == null) ? 0 : minigame.hashCode());
		result = prime * result + ((mob == null) ? 0 : mob.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((playerSpawn == null) ? 0 : playerSpawn.hashCode());
		result = prime * result + ((rewards == null) ? 0 : rewards.hashCode());
		result = prime * result + ((secondsMobSpawn == null) ? 0 : secondsMobSpawn.hashCode());
		result = prime * result + ((spawns == null) ? 0 : spawns.hashCode());
		result = prime * result + ((spectatorSpawns == null) ? 0 : spectatorSpawns.hashCode());
		result = prime * result + ((tiempoPartida == null) ? 0 : tiempoPartida.hashCode());
		result = prime * result + ((timeRefill == null) ? 0 : timeRefill.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (amountPlayers == null) {
			if (other.amountPlayers != null)
				return false;
		} else if (!amountPlayers.equals(other.amountPlayers))
			return false;
		if (amountPlayersMin == null) {
			if (other.amountPlayersMin != null)
				return false;
		} else if (!amountPlayersMin.equals(other.amountPlayersMin))
			return false;
		if (beastSpawn == null) {
			if (other.beastSpawn != null)
				return false;
		} else if (!beastSpawn.equals(other.beastSpawn))
			return false;
		if (entitySpawns == null) {
			if (other.entitySpawns != null)
				return false;
		} else if (!entitySpawns.equals(other.entitySpawns))
			return false;
		if (eventSpawn == null) {
			if (other.eventSpawn != null)
				return false;
		} else if (!eventSpawn.equals(other.eventSpawn))
			return false;
		if (inventory == null) {
			if (other.inventory != null)
				return false;
		} else if (!inventory.equals(other.inventory))
			return false;
		if (inventoryBeast == null) {
			if (other.inventoryBeast != null)
				return false;
		} else if (!inventoryBeast.equals(other.inventoryBeast))
			return false;
		if (inventoryRunners == null) {
			if (other.inventoryRunners != null)
				return false;
		} else if (!inventoryRunners.equals(other.inventoryRunners))
			return false;
		if (location1 == null) {
			if (other.location1 != null)
				return false;
		} else if (!location1.equals(other.location1))
			return false;
		if (location2 == null) {
			if (other.location2 != null)
				return false;
		} else if (!location2.equals(other.location2))
			return false;
		if (material != other.material)
			return false;
		if (minigame != other.minigame)
			return false;
		if (mob != other.mob)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (playerSpawn == null) {
			if (other.playerSpawn != null)
				return false;
		} else if (!playerSpawn.equals(other.playerSpawn))
			return false;
		if (rewards == null) {
			if (other.rewards != null)
				return false;
		} else if (!rewards.equals(other.rewards))
			return false;
		if (secondsMobSpawn == null) {
			if (other.secondsMobSpawn != null)
				return false;
		} else if (!secondsMobSpawn.equals(other.secondsMobSpawn))
			return false;
		if (spawns == null) {
			if (other.spawns != null)
				return false;
		} else if (!spawns.equals(other.spawns))
			return false;
		if (spectatorSpawns == null) {
			if (other.spectatorSpawns != null)
				return false;
		} else if (!spectatorSpawns.equals(other.spectatorSpawns))
			return false;
		if (tiempoPartida == null) {
			if (other.tiempoPartida != null)
				return false;
		} else if (!tiempoPartida.equals(other.tiempoPartida))
			return false;
		return true;
	}

	@Override
	public int compareTo(Match match) {

		return Integer.valueOf(minigame.ordinal()).compareTo(Integer.valueOf(match.getMinigame().ordinal()));
	}

	public Integer getNumberOfTeams() {
		return numberOfTeams;
	}

	public void setNumberOfTeams(Integer numberOfTeams) {
		this.numberOfTeams = numberOfTeams;
	}

	
	public Integer getNumberOfSeekers() {
		return numberOfSeekers;
	}

	public void setNumberOfSeekers(Integer numberOfSeekers) {
		this.numberOfSeekers = numberOfSeekers;
	}

	public ItemStack getHead() {
		return head;
	}

	public void setHead(ItemStack head) {
		this.head = head;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<String> getCommandsOnStart() {
		return commandsOnStart;
	}

	public void setCommandsOnStart(List<String> commandsOnStart) {
		this.commandsOnStart = commandsOnStart;
	}

	public List<String> getCommandsOnKill() {
		return commandsOnKill;
	}

	public void setCommandsOnKill(List<String> commandsOnKill) {
		this.commandsOnKill = commandsOnKill;
	}

	public List<String> getCommandsOnElimination() {
		return commandsOnElimination;
	}

	public void setCommandsOnElimination(List<String> commandsOnElimination) {
		this.commandsOnElimination = commandsOnElimination;
	}

	public Integer getShrinkBlocks() {
		return shrinkBlocks;
	}

	public void setShrinkBlocks(Integer shrinkBlocks) {
		this.shrinkBlocks = shrinkBlocks;
	}

	public Integer getBlockTimer() {
		return blockTimer;
	}

	public void setBlockTimer(Integer blockTimer) {
		this.blockTimer = blockTimer;
	}

	public Integer getBlockDecreaseTimer() {
		return blockDecreaseTimer;
	}

	public void setBlockDecreaseTimer(Integer blockDecreaseTimer) {
		this.blockDecreaseTimer = blockDecreaseTimer;
	}

	public Integer getColorTimer() {
		return colorTimer;
	}

	public void setColorTimer(Integer colorTimer) {
		this.colorTimer = colorTimer;
	}

	public Integer getColorDecreaseTimer() {
		return colorDecreaseTimer;
	}

	public void setColorDecreaseTimer(Integer colorDecreaseTimer) {
		this.colorDecreaseTimer = colorDecreaseTimer;
	}

	public Location getAuxLocation1() {
		return auxLocation1;
	}

	public void setAuxLocation1(Location auxLocation1) {
		this.auxLocation1 = auxLocation1;
	}

	public Location getAuxLocation2() {
		return auxLocation2;
	}

	public void setAuxLocation2(Location auxLocation2) {
		this.auxLocation2 = auxLocation2;
	}

	
	

}
