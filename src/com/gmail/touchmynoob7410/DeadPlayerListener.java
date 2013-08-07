package com.gmail.touchmynoob7410;
/*
 *  Class: DeadPlayerListener
 *  
 *  Description: This class handles the event of a player death in the HardcoreRez plugin
 *  
 *  Author: Jim Gildersleeve
 *  
 *  Date: 6-30-2013 
 */


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;


public class DeadPlayerListener implements Listener {
	public Plugin main;
	public DeadPlayerListener(Resurrect plugin){
		main = plugin;
	}
	
	
	@EventHandler /* what to do on the death of a player */
	public void playerDeath(PlayerDeathEvent event){
		Player deadPlayer = event.getEntity(); //player
		
		int x = deadPlayer.getLocation().getBlockX();
		int y = deadPlayer.getLocation().getBlockY();
		int z = deadPlayer.getLocation().getBlockZ();
		World world = deadPlayer.getWorld();
		//components for creating a location 
		
		Location deathLoc = new Location(world,x,y,z); //creates a location for death
		
		Block deathBlock = deathLoc.getBlock();
					
		deathBlock.setTypeId(63); //turns it into sign
		
		Sign tombStone = (Sign) deathBlock.getState();	
		
		
		tombStone.setLine(0, ChatColor.BLUE + "[Reviver]");
		tombStone.setLine(1, deadPlayer.getName());
		tombStone.setLine(2, "is a sn00b");
		tombStone.update();
		//puts text onto sign
		
		deadPlayer.getInventory().setArmorContents(null);
		deadPlayer.getInventory().clear();
		
		String deathLocation = "<X: " + x + ", " + "Y: " + y + ", " + "Z: " + z + ">";   
		deadPlayer.getLocation().getBlockY();
		deadPlayer.getLocation().getBlockZ();
		
		event.setDeathMessage(event.getDeathMessage() + " " + deathLocation);
		
		deadPlayer.setBanned(true);
		main.getServer().banIP(deadPlayer.getAddress().toString());
		deadPlayer.kickPlayer("You died in Hardcore, please wait for a resurrection.");
	}
	

}
