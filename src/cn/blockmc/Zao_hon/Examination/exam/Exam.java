package cn.blockmc.Zao_hon.Examination.exam;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;

public class Exam {
	private final ExamSettings settings;
	private Map<Integer, Query> queries;
	private String name;

	public Exam(String name,final ExamSettings settings) {
		this(name, settings,new HashMap<Integer, Query>());
	}

	public Exam(String name, final ExamSettings settings , Map<Integer, Query> queries) {
		this.queries = queries;
		this.settings = settings;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Map<Integer, Query> getQueries() {
		return queries;
	}
	public ExamSettings getSettings() {
		return settings;
	}
	
	public void start(Player player) {
		ExamForm exam = new ExamForm(this);
		exam.open(player);
	}
}
