package com.gmail.touchmynoob7410;

/*
 *  Class: DeadPlayerListener
 *  
 *  Description: This is the main class in the HardcoreRez plugin
 *  
 *  Author: Jim Gildersleeve
 *  
 *  Date: 6-30-2013 
 */
import org.bukkit.plugin.java.JavaPlugin;

public final class Resurrect extends JavaPlugin {
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new DeadPlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
		getServer().getPluginManager().registerEvents(new SignClickListener(this), this);
		getServer().getPluginManager().registerEvents(new signDamageListener(), this);
	}
	
	public void onDisable(){
		
	}
	
	
	
}
