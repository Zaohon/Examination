package cn.blockmc.Zao_hon.Examination.command;

import cn.blockmc.Zao_hon.Examination.Examination;
import cn.nukkit.command.CommandSender;

public class ReloadCommand implements ICommand {
	private Examination plugin;

	public ReloadCommand(Examination plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return "reload";
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
		return null;
	}

	@Override
	public String getDescription() {
		return "���ز��";
	}

	@Override
	public boolean canBeConsole() {
		return true;
	}

	@Override
	public boolean canBeCommandBlock() {
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		plugin.getExamManager().load();
		plugin.PR("����������");
		return true;
	}

}
