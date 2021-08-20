package com.adri1711.randomevents.util;

import java.util.List;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

public class UtilsProtocolLib {

	public static void addGlow(RandomEvents plugin, Player player, List<Player> receivers) {

		// ---
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin,
				PacketType.Play.Server.ENTITY_METADATA, PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
			@Override
			public void onPacketSending(PacketEvent event) {
				for (Player player : receivers) {

					if (player.getEntityId() == event.getPacket().getIntegers().read(0)) {
						if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
							List<WrappedWatchableObject> watchableObjectList = event.getPacket()
									.getWatchableCollectionModifier().read(0);
							WrappedDataWatcher watcher = event.getPacket().getDataWatcherModifier().read(0);
							for (WrappedWatchableObject metadata : watchableObjectList) {
								if (metadata.getIndex() == 0) {
									byte b = (byte) metadata.getValue();
									b |= 0b01000000;
									metadata.setValue(b);
								}
								if (watcher.hasIndex(0)) {
									byte b = watcher.getByte(0);
									b |= 0b01000000;
									watcher.setObject(0, b);
								}
							}
						}
					}

				}
			}
		});
		// ---
		//
		// PacketContainer packet =
		// protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
		// packet.getIntegers().write(0, player.getEntityId());
		// WrappedDataWatcher watcher = new WrappedDataWatcher();
		// WrappedDataWatcher.Serializer serializer =
		// WrappedDataWatcher.Registry.get(Byte.class);
		// watcher.setEntity(player);
		// watcher.setObject(0, serializer, (byte) (0x40));
		// packet.getWatchableCollectionModifier().write(0,
		// watcher.getWatchableObjects());
		// try {
		// for (Player receiver : receivers) {
		// protocolManager.sendServerPacket(receiver, packet);
		// }
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// }
	}
	
	public static void removeGlow(RandomEvents plugin, Player player, List<Player> receivers) {

		// ---
		ProtocolLibrary.getProtocolManager().removePacketListener(new PacketAdapter(plugin,
				PacketType.Play.Server.ENTITY_METADATA, PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
			@Override
			public void onPacketSending(PacketEvent event) {
				for (Player player : receivers) {

					if (player.getEntityId() == event.getPacket().getIntegers().read(0)) {
						if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
							List<WrappedWatchableObject> watchableObjectList = event.getPacket()
									.getWatchableCollectionModifier().read(0);
							WrappedDataWatcher watcher = event.getPacket().getDataWatcherModifier().read(0);
							for (WrappedWatchableObject metadata : watchableObjectList) {
								if (metadata.getIndex() == 0) {
									byte b = (byte) metadata.getValue();
									b |= 0b01000000;
									metadata.setValue(b);
								}
								if (watcher.hasIndex(0)) {
									byte b = watcher.getByte(0);
									b |= 0b01000000;
									watcher.setObject(0, b);
								}
							}
						}
					}

				}
			}
		});
		// ---
		//
		// PacketContainer packet =
		// protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
		// packet.getIntegers().write(0, player.getEntityId());
		// WrappedDataWatcher watcher = new WrappedDataWatcher();
		// WrappedDataWatcher.Serializer serializer =
		// WrappedDataWatcher.Registry.get(Byte.class);
		// watcher.setEntity(player);
		// watcher.setObject(0, serializer, (byte) (0x40));
		// packet.getWatchableCollectionModifier().write(0,
		// watcher.getWatchableObjects());
		// try {
		// for (Player receiver : receivers) {
		// protocolManager.sendServerPacket(receiver, packet);
		// }
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// }
	}
}
