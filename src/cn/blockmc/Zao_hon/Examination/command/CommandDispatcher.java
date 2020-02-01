package cn.blockmc.Zao_hon.Examination.command;

import java.util.HashSet;
import java.util.Set;

import cn.blockmc.Zao_hon.Examination.lang.Message;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.data.CommandParameter;

public class CommandDispatcher extends Command {
	private Set<ICommand> commands = new HashSet<ICommand>();

	public CommandDispatcher(final String rootCmdName) {
		super(rootCmdName);
		this.addCommand(new HelpCommand());
	}

	public void loadParameters() {
		this.commandParameters.clear();
		Set<String> sCommands = new HashSet<String>();
		commands.forEach(command -> sCommands.add(command.getName()));
		this.commandParameters.put("default", new CommandParameter[] {
				new CommandParameter("skin", false, sCommands.toArray(new String[commands.size()])) });
	}

	public void addCommand(ICommand command) {
		commands.add(command);
	}

	@Override
	public boolean execute(CommandSender sender, String cmd, String[] args) {
		if (args.length == 0) {
			displayUsage(sender, null);
			return true;
		}

		String subcmd = args[0];
		ICommand subCommand = findCommand(subcmd);
		subCommand = subCommand == null ? findCommandByAliases(subcmd) : subCommand;

		if (subCommand == null) {
			Message.senderSendMessage(sender, Message.getString("command_subcmd_invalid"));
			return true;
		}

		if ((sender instanceof ConsoleCommandSender) && !subCommand.canBeConsole()) {
			Message.senderSendMessage(sender, Message.getString("command_error_player_only"));
			return true;
		}

		if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) {
			Message.senderSendMessage(sender, Message.getString("command_no_permission"));
			return true;
		}

		String[] subArgs = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			subArgs[i - 1] = args[i];
		}

		if (!subCommand.onCommand(sender, subArgs)) {
			displayUsage(sender, subCommand);
		}
		return true;
	}

	private ICommand findCommand(String cmdName) {
		for (ICommand command : commands) {
			if (command.getName().equals(cmdName))
				return command;
		}
		return null;
	}

	private ICommand findCommandByAliases(String cmdName) {
		for (ICommand command : commands) {
			String[] aliaces = command.getAliases();
			if (aliaces != null) {
				for (String aliace : command.getAliases()) {
					if (aliace.equals(cmdName))
						return command;
				}
			}
		}
		return null;
	}

	public void displayUsage(CommandSender sender, ICommand cmd) {
		Message.senderSendMessage(sender, Message.getString("command_heading"));
		if (cmd == null) {

			for (ICommand command : commands) {
				sender.sendMessage("/" + CommandDispatcher.this.getName() + " " + command.getName() + "  --"
						+ command.getDescription());
			}

		} else {
			for (String str : cmd.getUsageString(sender)) {
				sender.sendMessage(str);
			}
		}
	}

	private class HelpCommand implements ICommand {

		@Override
		public String getName() {
			return "help";
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
			return new String[] { "/" + CommandDispatcher.this.getName() + " help" };
		}

		@Override
		public String getDescription() {
			return Message.getString("command_description_help");
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
			if (args.length == 0) {
				displayUsage(sender, null);
			} else {
				String subcmd = args[0];
				ICommand subCommand = findCommand(subcmd);
				subCommand = subCommand == null ? findCommandByAliases(subcmd) : subCommand;
				if (subCommand == null) {
					Message.senderSendMessage(sender, Message.getString("command_subcmd_invalid"));
				} else {
					displayUsage(sender, subCommand);
				}
			}
			return true;
		}

	}

}
