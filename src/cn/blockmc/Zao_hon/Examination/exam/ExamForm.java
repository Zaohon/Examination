package cn.blockmc.Zao_hon.Examination.exam;

import java.math.BigDecimal;

import cn.blockmc.Zao_hon.Examination.Examination;
import cn.blockmc.Zao_hon.form.IFormWindow;
import cn.blockmc.Zao_hon.form.element.IButton;
import cn.blockmc.Zao_hon.form.event.IFormButtonClickedEvent;
import cn.blockmc.Zao_hon.form.listener.ButtonListener;
import cn.nukkit.Player;
import cn.nukkit.level.Sound;

public class ExamForm {
	private Exam exam;
	private int index = 0;
	private float record = 0f;
	private boolean started = false;
	private boolean allCorrect = true;
	private boolean allWrong = true;

	public ExamForm(Exam exam) {
		this.exam = exam;
	}

	public void open(Player player) {
		if (!started) {
			started = true;
			index = 0;
		}
		QueryForm form = new QueryForm(exam.getQueries().get(index));
		form.open(player);
	}

	private void complete(Player player) {
		BigDecimal bd = new BigDecimal(record);
		float r = bd.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue() * 100;
		ExamManager.getInstance().setPlayerRecord(player.getName(), exam.getName(), r);
		ExamManager.getInstance().getPlayerExams().remove(player.getName());
		player.sendMessage("考试结束,您的得分为§d" + r);

		if (allCorrect && !exam.getSettings().getAllCorrectCommand().equals(""))
			dispatchCommand(player, exam.getSettings().getAllCorrectCommand());

		if (allWrong && !exam.getSettings().getAllWrongCommand().equals(""))
			dispatchCommand(player, exam.getSettings().getAllWrongCommand());

		player.sendTip("awocaotip");
		player.sendTitle(exam.getSettings().getCompleteTitle().replace("{record}", record + ""),
				exam.getSettings().getCompleteSubTitle().replace("{record}", record + ""));
		player.sendPopup("popip","popsss");
		player.sendTranslation("it is a translation");
	}

	private void dispatchCommand(Player player, String cmd) {
		cmd = cmd.replace("{player}", player.getName());
		if (cmd.startsWith("@")) {
			cmd = cmd.substring(1);
			Examination.getInstance().getServer()
					.dispatchCommand(Examination.getInstance().getServer().getConsoleSender(), cmd);
		} else {
			Examination.getInstance().getServer().dispatchCommand(player, cmd);
		}
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
