package com.adri1711.randomevents.match.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;
import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

public class SongUtils {
	public static final int DEFAULT_BUFFER_SIZE = 8192;

	public static void playRecord(List<Player> player, Boolean record, RandomEvents plugin) {
		Song song = null;
		song = NBSDecoder.parse(plugin.getResource("Luzverde2.nbs"));
		// Create RadioSongPlayer.
		RadioSongPlayer rsp = new RadioSongPlayer(song);
		// Add player to SongPlayer so he will hear the song.
		for (Player p : player) {
			rsp.addPlayer(p);
			if (!record) {

				NoteBlockAPI.stopPlaying(p);
			}
		}
		// Start RadioSongPlayer playback
		rsp.setPlaying(record);
		rsp.setRepeatMode(RepeatMode.ALL);

	}

	public static void playRecord(Player player, Boolean record, RandomEvents plugin) {
		Song song = null;

		song = NBSDecoder.parse(plugin.getResource("Luzverde2.nbs"));
		// Create RadioSongPlayer.
		RadioSongPlayer rsp = new RadioSongPlayer(song);

		// Add player to SongPlayer so he will hear the song.
		rsp.addPlayer(player);
		if (!record) {

			NoteBlockAPI.stopPlaying(player);
		}
		// Start RadioSongPlayer playback
		rsp.setPlaying(record);
		rsp.setRepeatMode(RepeatMode.ALL);

	}

	private static File copyInputStreamToFile(InputStream inputStream) throws IOException {
		File file = new File("");
		// append = false
		try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
			int read;
			byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		}
		return file;

	}
}
