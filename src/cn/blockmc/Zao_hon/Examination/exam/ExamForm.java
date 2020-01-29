package cn.blockmc.Zao_hon.Examination.exam;

import java.math.BigDecimal;
import cn.blockmc.Zao_hon.form.IFormWindow;
import cn.blockmc.Zao_hon.form.element.IButton;
import cn.blockmc.Zao_hon.form.event.IFormButtonClickedEvent;
import cn.blockmc.Zao_hon.form.listener.ButtonListener;
import cn.nukkit.Player;
import cn.nukkit.level.Sound;

public class ExamForm {
	private Exam exam;
//	private Map<Integer, Query> queries;
//	private Map<Integer, Integer> answers;
//	private int size = 0;
	private int index = 0;
	private float record = 0f;
//	private String title;
	private boolean started = false;
	private boolean allCorrect = true;
	private boolean allWrong = true;

	public ExamForm(Exam exam) {
		this.exam = exam;
//		this.title = exam.getName();
//		this.size = exam.getQueries().size();
//		this.queries = exam.getQueries();
//		this.answers = new HashMap<Integer, Integer>(size);
	}

//	public ExamForm(final String title, final Map<Integer, Query> queries) {
//		this.title = title;
//		this.size = queries.size();
//		this.queries = queries;
//		this.answers = new HashMap<Integer, Integer>(size);
//	}

	public void open(Player player) {
		if (!started) {
			started = true;
			index = 0;
		}
		QueryForm form = new QueryForm(exam.getQueries().get(index));
		form.open(player);
	}

//	public float caculateRecord() {
//		int correctNumber = 0;
//		for (Integer index : queries.keySet()) {
//			if (queries.get(index).getCorrectIndex() == answers.get(index))
//				correctNumber++;
//		}
//		float record = (float) correctNumber / size;
//		BigDecimal bd = new BigDecimal(record);
//		return bd.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
//	}

	private void complete(Player player) {
		BigDecimal bd = new BigDecimal(record);
		float r = bd.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
		ExamManager.getInstance().setPlayerRecord(player.getName(), exam.getName(), r);
		ExamManager.getInstance().getPlayerExams().remove(player.getName());
		player.sendMessage("考试结束,您的得分为§d" + record);

		if (allCorrect && !exam.getSettings().getAllCorrectCommand().equals("")) {
//			String cmd = exam.getSettings().getAllCorrectCommand();
//			String[] args = cmd.split(" ");
		}

		if (allWrong && !exam.getSettings().getAllWrongCommand().equals("")) {
//			String cmd = exam.getSettings().getAllCorrectCommand();
//			String[] args = cmd.split(" ");
		}

		player.sendTip("awocaotip");
		player.sendTitle(exam.getSettings().getCompleteTitle(), exam.getSettings().getCompleteSubTitle());
		player.sendPopup("popip");
		player.sendTranslation("it is a translation");
	}

	private class QueryButtonListener implements ButtonListener {
		private final int answerIndex;

		QueryButtonListener(final int answerIndex) {
			this.answerIndex = answerIndex;
		}

		@Override
		public void onClicked(IFormButtonClickedEvent event) {
			Player player = event.getPlayer();

			boolean correct = answerIndex == exam.getQueries().get(index).getCorrectIndex();
			record = correct ? record + (1f / exam.getQueries().size()) : record;
			String sound = correct ? exam.getSettings().getCorrectSound() : exam.getSettings().getWrongSound();

			if (!sound.equals(""))
				player.getLocation().getLevel().addSound(player.getLocation(), Sound.valueOf(sound));

			if (!correct && allCorrect)
				allCorrect = false;
			if (correct && allWrong)
				allWrong = false;

			if (index >= exam.getQueries().size() - 1) {
//				float record = caculateRecord();
				complete(player);
			} else {
				QueryForm form = new QueryForm(exam.getQueries().get(++index));
				form.open(player);
			}
		}

	}

	private class QueryForm extends IFormWindow {
		public QueryForm(Query query) {
			super(exam.getName(), query.getContext());
			for (int answerIndex = 0; answerIndex < query.getAnswers().size(); answerIndex++) {
				QueryButtonListener listener = new QueryButtonListener(answerIndex);
				IButton button = new IButton(query.getAnswers().get(answerIndex), listener);
				this.addButton(button);
			}
		}

	}

}
