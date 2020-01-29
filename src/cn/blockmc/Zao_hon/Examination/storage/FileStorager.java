package cn.blockmc.Zao_hon.Examination.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.blockmc.Zao_hon.Examination.Examination;
import cn.nukkit.utils.Config;

public class FileStorager extends DataStorager {
	private Examination plugin;
	private final File recordFolder;

	public FileStorager(Examination plugin) {
		this.plugin = plugin;
		recordFolder = new File(plugin.getDataFolder(), "records");
		if (!recordFolder.exists()) {
			recordFolder.mkdir();
		}

	}

	@Override
	public void setPlayerRecord(String name, String title, float record) {
		try {
			File file = new File(recordFolder, title);
			if (!file.exists())
				file.createNewFile();
			boolean exist = false;
			List<String> newLines = new ArrayList<String>();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
			while (reader.ready()) {
				String line = reader.readLine();
				plugin.PR(line);
				String newLine = line;
				if (line.contains(name)) {
					newLine = name + ":" + record;
					exist = true;
				}
				newLines.add(newLine);
				plugin.PR(newLine);
			}
			if (!exist)
				newLines.add(name + ":" + record);
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8));
			for (String s : newLines) {
				writer.append(s);
				writer.newLine();
			}
			writer.flush();
			reader.close();
			writer.close();

		} catch (IOException e) {
			plugin.PR("创建记录文件失败，请联系作者");
			return;
		}
	}

	@Override
	public Map<String, Map<String, Float>> getPlayerRecords() {
		Map<String, Map<String, Float>> map = new HashMap<String, Map<String, Float>>();
		for (File file : recordFolder.listFiles()) {
			String title = file.getName();
			Config config = new Config(file);
			Map<String, Float> mmap = new HashMap<String, Float>();
			config.getKeys(false).forEach(key -> {
				mmap.put(key, (float) config.getDouble(key));
			});
			map.put(title, mmap);
		}
		return map;
	}
}
