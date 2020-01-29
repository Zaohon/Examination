package cn.blockmc.Zao_hon.Examination.exam;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.blockmc.Zao_hon.Examination.Examination;
import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;

public class ExamManager {
	private static ExamManager instance;
	private Examination plugin;
//	private Map<String, Map<Integer, Query>> examMaps = new HashMap<String, Map<Integer, Query>>();
	private Map<String, ExamForm> playerExams = new HashMap<String, ExamForm>();

	private Map<String, Exam> exams = new HashMap<String, Exam>();

	private Map<String, Map<String, Float>> playerRecords = new HashMap<String, Map<String, Float>>();

	public ExamManager(Examination plugin) {
		instance = this;
		this.plugin = plugin;
		this.load();
	}
	public void load() {
		this.playerRecords = plugin.getDataStorager().getPlayerRecords();
//		this.loadQueries();
		this.loadExams();
	}

	public void loadExams() {
		File folder = getQueryFolder();
		int i = 0;
		for (File examFile : folder.listFiles()) {
			String fileName = examFile.getName();
			String examName = fileName.substring(0, fileName.lastIndexOf("."));
			Config config = new Config(examFile);
			ExamSettings settings = getExamSettings(config.getSection("Settings"));
			Map<Integer, Query> queryMap = getQueryMap(config.getSection("Queries"));
			Exam exam = new Exam(examName, settings, queryMap);
			exams.put(examName, exam);
			playerRecords.putIfAbsent(examName, new HashMap<String, Float>());
			i++;
		}
		plugin.PR("共加载"+i+"个问卷");
	}

//	public void loadQueries() {
//		File folder = getQueryFolder();
//		for (File queryFile : folder.listFiles()) {
//			String fileName = queryFile.getName();
//			String queryName = fileName.substring(0, fileName.lastIndexOf("."));
//			Map<Integer, Query> queryMap = getQueryMap(queryFile);
//			examMaps.put(queryName, queryMap);
//			playerRecords.putIfAbsent(queryName, new HashMap<String, Float>());
//		}
//	}
	public File getQueryFolder() {
		File file = new File(plugin.getDataFolder(), "exams");
		if (!file.exists()) {
			file.mkdir();
			plugin.saveResource("Exams/ExampleExam.yml", false);
		}

		return file;
	}

	public ExamSettings getExamSettings(ConfigSection section) {
		ExamSettings settings = new ExamSettings();
		settings.setAllCorrectCommand(section.getString("AllCorretCommand"));
		settings.setAllWrongCommand(section.getString("AllWrongCommand"));
		settings.setCorrectSound(section.getString("CorrectSound"));
		settings.setWrongSound(section.getString("WrongSound"));
		settings.setCompleteTitle(section.getString("CompleteTitle"));
		settings.setCompleteSubTitle(section.getString("CompleteSubTitle"));
		return settings;
	}

	public Map<Integer, Query> getQueryMap(ConfigSection section) {
		Map<Integer, Query> map = new HashMap<Integer, Query>();
		//
		int index = 0;
		for (String key : section.getKeys(false)) {
			// ConfigSection sec = config.getSection(key);
			String question = section.getString(key + ".question");
			List<String> answers = section.getStringList(key + ".answers");
			int correctIndex = section.getInt(key + ".correctIndex");
			Query query = new Query(question, correctIndex, answers);
			map.put(index, query);
			index++;
		}
		// TODO edit getKeys
		return map;
	}
	

	public void startExam(Player player, String title) {
		exams.get(title).start(player);
	}

	public Map<String, ExamForm> getPlayerExams() {
		return playerExams;
	}

//	public Map<String, Map<Integer, Query>> getExamMaps() {
//		return examMaps;
//	}
	public Map<String, Exam> getExams() {
		return exams;
	}


	public void setPlayerRecord(String name, String title, Float record) {
		playerRecords.get(title).put(name, record);
		plugin.getDataStorager().setPlayerRecord(name, title, record);
	}

	public static ExamManager getInstance() {
		return instance;
	}
}
