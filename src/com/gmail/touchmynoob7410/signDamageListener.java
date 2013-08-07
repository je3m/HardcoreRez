package com.gmail.touchmynoob7410;



import java.util.Arrays;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

/*
*  Class: DeadPlayerListener
*  
*  Description: This class keeps signs from being destroyed in the HardcoreRez plugin
*  
*  Author: Jim Gildersleeve
*  
*  Date: 6-30-2013 
*/
public class signDamageListener implements Listener {
	@EventHandler
	public void BlockDamageListener(BlockDamageEvent event){
		if (event.getBlock().getTypeId() == 63){
			Sign sign = (Sign) event.getBlock().getState();
			byte[] correctByte = {-89, 57, 91, 82, 101, 118, 105, 118, 101, 114, 93}; //Blue text [Reviver]
			byte[] signBytes = sign.getLine(0).getBytes();
			if (Arrays.equals(signBytes, correctByte)){
				event.setCancelled(true);
			}
		}
	}
}
