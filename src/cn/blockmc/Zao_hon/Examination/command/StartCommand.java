package cn.blockmc.Zao_hon.Examination.command;

import cn.blockmc.Zao_hon.Examination.Examination;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

public class StartCommand implements ICommand {
	private Examination plugin;

	public StartCommand(Examination plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return "start";
	}

	@Override
	public String[] getAliases() {
		return null;
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public String[] getUsageString(CommandSender sender) {
		return new String[] { "/exam start [ExamName]" };
	}

	@Override
	public String getDescription() {
		return "开始一个问卷";
	}

	@Override
	public boolean canBeConsole() {
		return false;
	}

	@Override
	public boolean canBeCommandBlock() {
		return false;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length != 1)
			return false;

		String examName = args[0];
		if (plugin.getExamManager().getPlayerExams().containsKey(player.getName())) {
			player.sendMessage("你还有一份未完成的问卷,使用/exam continue继续作答");
			return true;
		}

		if (plugin.getExamManager().getExams().containsKey(examName)) {
			plugin.getExamManager().startExam(player, examName);
		} else {
			player.sendMessage("不存在这个问卷");
		}
		return true;
	}

}
