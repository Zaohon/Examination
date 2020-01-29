package cn.blockmc.Zao_hon.Examination.exam;

import java.util.List;

public class Query {
	private final String context;
	private List<String> answers;
	private int correctIndex;

	public Query(final String context, int correctIndex, List<String> answers) {
		this.context = context;
		this.correctIndex = correctIndex;
		this.answers = answers;
	}

	public String getContext() {
		return context;
	}

	public List<String> getAnswers() {
		return answers;

	}

	public int getCorrectIndex() {
		return correctIndex;
	}

}
