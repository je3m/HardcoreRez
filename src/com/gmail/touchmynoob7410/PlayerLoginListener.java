package com.gmail.touchmynoob7410;
/*
 *  Class: DeadPlayerListener
 *  
 *  Description: This class handles the event of a player login in the HardcoreRez plugin
 *  
 *  Author: Jim Gildersleeve
 *  
 *  Date: 6-30-2013 
 */
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLoginListener implements Listener {
	
	@EventHandler
	public void loginListener(PlayerLoginEvent loginEvent){
		
		Player player = loginEvent.getPlayer();
		
		if (player.isBanned()){
			loginEvent.disallow(Result.KICK_BANNED, "You are currently dead. Please wait for a resurrection.");
		} else {
			loginEvent.allow();
		}
	}
}
