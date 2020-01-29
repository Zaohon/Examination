package cn.blockmc.Zao_hon.Examination.exam;

public class ExamSettings {
	private String allCorretCommand = "";
	private String allWrongCommand = "";
	private String correctSound = "";
	private String wrongSound = "";
	private String completeTitle = "";
	private String completeSubTitle = "";

	public String getAllCorrectCommand() {
		return allCorretCommand;
	}

	public void setAllCorrectCommand(String allCorrectCommand) {
		this.allCorretCommand = allCorrectCommand;
	}

	public String getAllWrongCommand() {
		return allWrongCommand;
	}

	public void setAllWrongCommand(String allWrongCommand) {
		this.allWrongCommand = allWrongCommand;
	}

	public String getCorrectSound() {
		return correctSound;
	}

	public void setCorrectSound(String correctSound) {
		this.correctSound = correctSound;
	}

	public String getWrongSound() {
		return wrongSound;
	}

	public void setWrongSound(String wrongSound) {
		this.wrongSound = wrongSound;
	}

	public String getCompleteTitle() {
		return completeTitle;
	}

	public void setCompleteTitle(String completeTitle) {
		this.completeTitle = completeTitle;
	}

	public String getCompleteSubTitle() {
		return completeSubTitle;
	}

	public void setCompleteSubTitle(String completeSubTitle) {
		this.completeSubTitle = completeSubTitle;
	}
}
