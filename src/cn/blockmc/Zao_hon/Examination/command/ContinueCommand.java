package cn.blockmc.Zao_hon.Examination.command;

import cn.blockmc.Zao_hon.Examination.Examination;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

public class ContinueCommand implements ICommand {
	private Examination plugin;

	public ContinueCommand(Examination plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return "continue";
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
		return new String[] { "/exam continue" };
	}

	@Override
	public String getDescription() {
		return "继续某个未完成的问卷";
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
		if (plugin.getExamManager().getPlayerExams().containsKey(player.getName())) {
			plugin.getExamManager().getPlayerExams().get(player.getName()).open(player);
		} else {
			player.sendMessage("你没有正在进行的问卷");
		}
		return true;
	}

}
