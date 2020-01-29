package cn.blockmc.Zao_hon.Examination.storage;

import java.util.Map;

public abstract class DataStorager {
	public abstract Map<String,Map<String,Float>> getPlayerRecords();
	public abstract void setPlayerRecord(String name, String title,float record);

}
