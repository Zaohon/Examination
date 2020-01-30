package cn.blockmc.Zao_hon.Examination;

import cn.blockmc.Zao_hon.Examination.command.CommandDispatcher;
import cn.blockmc.Zao_hon.Examination.command.ContinueCommand;
import cn.blockmc.Zao_hon.Examination.command.ListCommand;
import cn.blockmc.Zao_hon.Examination.command.ReloadCommand;
import cn.blockmc.Zao_hon.Examination.command.StartCommand;
import cn.blockmc.Zao_hon.Examination.exam.ExamManager;
import cn.blockmc.Zao_hon.Examination.lang.Message;
import cn.blockmc.Zao_hon.Examination.storage.DataStorager;
import cn.blockmc.Zao_hon.Examination.storage.FileStorager;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;

public class Examination extends PluginBase implements Listener {

	private CommandDispatcher commandDispatcher;
	private Message message;
	public static final String PREFIX = "[¡ìbExamination¡ìr]";
	private ExamManager examManager;
	private DataStorager dataStorager;

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		instance = this;

		dataStorager = new FileStorager(this);
		examManager = new ExamManager(this);

		message = new Message(this);
		message.setLanguage("zh_cn");

		commandDispatcher = new CommandDispatcher("exam");
		commandDispatcher.addCommand(new StartCommand(this));
		commandDispatcher.addCommand(new ContinueCommand(this));
		commandDispatcher.addCommand(new ListCommand(this));
		commandDispatcher.addCommand(new ReloadCommand(this));
		this.getServer().getCommandMap().register("exam", commandDispatcher);

		this.getServer().getPluginManager().registerEvents(this, this);
		PR("Examination has enabled successfully");
	}

	public void PR(String str) {
		this.getLogger().info(str);
	}

	public DataStorager getDataStorager() {
		return dataStorager;
	}

	public ExamManager getExamManager() {
		return examManager;
	}

	private static Examination instance;

	public static Examination getInstance() {
		return instance;
	}

}
