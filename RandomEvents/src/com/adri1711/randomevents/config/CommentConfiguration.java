package com.adri1711.randomevents.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/*
* Heyo! This little class was made by me, Brook! Or just KuramaStone if ya wanna find me. Use this however ya want, fam.
* I'm just writing this because I'm proud that I finally made this after a long time of wanting to.
*/
public class CommentConfiguration extends YamlConfiguration {

	/*
	 * comments.put("path.prior.to.comment", "comment")
	 */
	private final Map<String, List<String>> comments;
	private List<String> commentsAux;

	private String path = "";

	public CommentConfiguration() {
		comments = new HashMap<>();
		commentsAux = new ArrayList<String>();
	}

	@Override
	public String saveToString() {
		String contents = super.saveToString();

		List<String> list = new ArrayList<>();
		Collections.addAll(list, contents.split("\n"));

		int currentLayer = 0;
		StringBuilder currentPath = new StringBuilder();

		StringBuilder sb = new StringBuilder();

		int lineNumber = 0;
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); lineNumber++) {
			String line = iterator.next();


			if (!line.isEmpty()) {
				if (line.contains(":")) {
					String path = currentPath.toString();
					String key = getKeyFromLine(line);

					if (comments.containsKey(key)) {
						comments.get(key).forEach(string -> {
							sb.append(string);
							sb.append('\n');
						});
					}
					sb.append(line);
					sb.append('\n');
					int layerFromLine = getLayerFromLine(line, lineNumber);

					if (layerFromLine < currentLayer) {
						currentPath = new StringBuilder(
								regressPathBy(currentLayer - layerFromLine, currentPath.toString()));
					}


					if (currentLayer == 0) {
						currentPath = new StringBuilder(key);
					} else {
						currentPath.append("." + key);
					}

				}else{
					sb.append(line);
					sb.append('\n');
				}
			} else {
				sb.append(line);
				sb.append('\n');
			}

		}
		

		return sb.toString();
	}

	@Override
	public void loadFromString(String contents) throws InvalidConfigurationException {
		super.loadFromString(contents);

		List<String> list = new ArrayList<>();
		Collections.addAll(list, contents.split("\n"));

		int currentLayer = 0;
		String currentPath = "";
		path = "";

		int lineNumber = 0;
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); lineNumber++) {
			String line = iterator.next();
			String trimmed = line.trim();


			if (trimmed.startsWith("##") || trimmed.isEmpty()) {
				addCommentLine(line);
				continue;
			}

			if (!line.isEmpty()) {
				if (line.contains(":")) {

					int layerFromLine = getLayerFromLine(line, lineNumber);

					if (layerFromLine < currentLayer) {
						currentPath = regressPathBy(currentLayer - layerFromLine, currentPath);

					}

					String key = getKeyFromLine(line);

					if (currentLayer == 0) {
						currentPath = key;

					} else {
						currentPath += "." + key;

					}

					if (!currentPath.equals(path)) {
						setCommentLine(currentPath);
						path = currentPath;
					}
				}
			}
		}
	}

	private void setCommentLine(String currentPath) {
		List<String> lista = new ArrayList<String>();
		lista.addAll(commentsAux);
		comments.put(currentPath, lista);
		commentsAux.clear();
	}

	private void addCommentLine(String line) {
		commentsAux.add(line);
	}

	// private void addCommentLine(String currentPath, String line) {
	//
	// List<String> list = comments.get(currentPath);
	// if (list == null) {
	// list = new ArrayList<>();
	// }
	// list.add(line);
	//
	// comments.put(currentPath, list);
	// }

	private String getKeyFromLine(String line) {
		String key = null;

		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ':') {
				key = line.substring(0, i);
				break;
			}
		}

		return key == null ? null : key.trim();
	}

	private String regressPathBy(int i, String currentPath) {
		if (i <= 0) {
			return currentPath;
		}
		String[] split = currentPath.split("\\.");

		String rebuild = "";
		for (int j = 0; j < split.length - i; j++) {
			rebuild += split[j];
			if (j <= (split.length - j)) {
				rebuild += ".";
			}
		}

		return rebuild;
	}

	private int getLayerFromLine(String line, int lineNumber) {

		double d = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ') {
				d += 0.5;
			} else {
				break;
			}
		}

		return (int) d;

	}

}