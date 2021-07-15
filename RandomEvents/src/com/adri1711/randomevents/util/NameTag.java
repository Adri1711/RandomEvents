package com.adri1711.randomevents.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.base.Preconditions;

public interface NameTag {

    static SimpleNameTag of(String text) {
        Preconditions.checkArgument(text.length() < 17, "Name length must not exceed 16 characters.");
        return new SimpleNameTag(text);
    }

    String getText();

    default void applyTo(Player player, Player pla) {
    	List<Player> players=new ArrayList<Player>();
    	players.add(pla);
		applyTo(player, players);
    	
    }
        default void applyTo(Player player, List<Player> players) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        int id = player.getEntityId();
//        int ping = ((CraftPlayer) player).getHandle().ping;

        Location location = player.getLocation();

        WrappedGameProfile wrappedGameProfile = WrappedGameProfile.fromPlayer(player);
        EnumWrappers.NativeGameMode nativeGameMode = EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode());

        WrappedChatComponent tabName = WrappedChatComponent.fromText(player.getPlayerListName());

        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(player);
//        WrappedSignedProperty wrappedSignedProperty = new PlayerInfoData(wrappedGameProfile, ping, nativeGameMode, tabName).getProfile().getProperties().get("textures").iterator().next();

        PacketContainer removePlayer = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        PacketContainer addPlayer = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);

        PacketContainer destroyEntity = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
        PacketContainer namedEntitySpawn = protocolManager.createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN);

        removePlayer.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        removePlayer.getPlayerInfoDataLists().write(0, NameTag.getPlayerInfoDataList(player));

        addPlayer.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
//
//        PlayerInfoData playerInfoData = new PlayerInfoData(wrappedGameProfile.withName(this.getText()), ping, nativeGameMode, tabName);
//        playerInfoData.getProfile().getProperties().clear();
//        playerInfoData.getProfile().getProperties().get("textures").add(wrappedSignedProperty);
//
//        addPlayer.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        destroyEntity.getIntegerArrays().write(0, new int[]{id});

        namedEntitySpawn.getIntegers().write(0, id);

        namedEntitySpawn.getUUIDs().write(0, player.getUniqueId());

        namedEntitySpawn.getDoubles().write(0, location.getX());
        namedEntitySpawn.getDoubles().write(1, location.getY());
        namedEntitySpawn.getDoubles().write(2, location.getZ());

        namedEntitySpawn.getBytes().write(0, (byte)((int)(location.getYaw() * 256.0F / 360.0F)));
        namedEntitySpawn.getBytes().write(1, (byte)((int)(location.getPitch() * 256.0F / 360.0F)));

        namedEntitySpawn.getDataWatcherModifier().write(0, dataWatcher);

        protocolManager.broadcastServerPacket(removePlayer);
        protocolManager.broadcastServerPacket(addPlayer);
        
        for(Player p: players){
        	if(!p.equals(player)){
        		 try {
                     protocolManager.sendServerPacket(p, destroyEntity);
                     protocolManager.sendServerPacket(p, namedEntitySpawn);
                 } catch (InvocationTargetException exception) {
                     exception.printStackTrace();
                 }
        	}
        }

//        Bukkit.getOnlinePlayers().stream().filter(Predicates.not(player::equals)).forEach(o -> {
//            try {
//                protocolManager.sendServerPacket(o, destroyEntity);
//                protocolManager.sendServerPacket(o, namedEntitySpawn);
//            } catch (InvocationTargetException exception) {
//                exception.printStackTrace();
//            }
//        });
    }
    
    
    public static  PlayerInfoData getPlayerInfoData(Player player) {
        return new PlayerInfoData(WrappedGameProfile.fromPlayer(player), 0, EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()), WrappedChatComponent.fromText(player.getDisplayName()));
    }

    public static  List<PlayerInfoData> getPlayerInfoDataList(Player player) {
        return Collections.singletonList(getPlayerInfoData(player));
    }
}
