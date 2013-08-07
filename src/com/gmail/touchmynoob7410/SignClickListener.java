package com.gmail.touchmynoob7410;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

/*
 *  Class: DeadPlayerListener
 *  
 *  Description: This class handles the event of a player giving an item to a sign in the HardcoreRez plugin
 *  
 *  Author: Jim Gildersleeve
 *  
 *  Date: 6-30-2013 
 */

public class SignClickListener implements Listener {
	private Plugin main;
	private Player saint;
	private OfflinePlayer deadPlayer;

	public SignClickListener(Resurrect plugin) {
		main = plugin;
	}

	public Sign sign;
	public Block clickedBlock;

	@EventHandler
	public void signInteract(PlayerInteractEvent event) {

		clickedBlock = (Block) event.getClickedBlock();
		saint = event.getPlayer();
		final World world = saint.getWorld();
		final ItemStack handItem = event.getItem();

		/*
		 * This determines if the block that was left-clicked was a sign that
		 * contains the text "[Reviver]" on the first line It also checks if the
		 * item that is being used is a Golden Apple, Golden Carrot, or a cake.
		 * It the effects of the sign click will vary depending on the item used
		 * Then, it removes the player from the banned list and creates an
		 * explosion where the sign was
		 */

		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				&& clickedBlock.getTypeId() == 63) {

			sign = (Sign) clickedBlock.getState();
			sign.update();

			byte[] falseByte = { 91, 82, 101, 118, 105, 118, 101, 114, 93 }; // black text saying [Reviver], used to make phony signs
			byte[] correctByte = { -89, 57, 91, 82, 101, 118, 105, 118, 101,114, 93 }; // Blue text saying [Reviver]

			byte[] signByte = sign.getLine(0).getBytes();

			if (Arrays.equals(falseByte, signByte)) {
				saint.sendMessage("EXPLOITS ARE INTOLLERABLE!");
				saint.setFireTicks(200);
			}
			if (Arrays.equals(signByte, correctByte)) {

				if (handItem.getTypeId() == 322 
						|| handItem.getTypeId() == 354
						|| handItem.getTypeId() == 396) {

					int itemUsed = handItem.getTypeId();

					deadPlayer = Bukkit.getServer().getOfflinePlayer(sign.getLine(1).toString());

					switch (itemUsed) { // performs action depending on item in
										// hand

					case 322: // Golden Apple
						unbanPlayer(deadPlayer, handItem);
						saint.sendMessage("You have ten seconds until detonation");
						main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {

									@Override
									public void run() {

										world.createExplosion(sign.getLocation(),(int) (Math.random() * 30),true);
									}
								}, 200L);

						break;

					case 354: // Cake
						unbanPlayer(deadPlayer, handItem);
						shootFirework(sign.getLocation());
						shootFirework(sign.getLocation());
						shootFirework(sign.getLocation());
						shootFirework(sign.getLocation());
						shootFirework(sign.getLocation());

						break;
					case 396: // Golden Carrot
						unbanPlayer(deadPlayer, handItem);
						world.strikeLightning(sign.getLocation());
						saint.setHealth(saint.getHealth() - 4);
						world.setThundering(true);

						break;

					default:
						break;
					}

				} else {
					saint.sendMessage("Sorry, you must use an appropriate item to resurrect your fellow adventurer.");
				}
			}
		}
	}

	/*
	 * Method called every time a player is unbanned
	 */
	public void unbanPlayer(OfflinePlayer deadPlayer, ItemStack handItem) {

		
		int newAmount = (saint.getInventory().getItemInHand().getAmount() - 1);//used to remove a single item off a stack
		
		saint.getInventory().removeItem(saint.getItemInHand());
		
		if (newAmount != 0){
			saint.setItemInHand(handItem);
			saint.getItemInHand().setAmount(newAmount);
	
		}
				
		saint.giveExpLevels(5);

		deadPlayer.setBanned(false);

		saint.setDisplayName("saint" + saint.getName());
		Bukkit.getServer().broadcastMessage(deadPlayer.getName() + " has been resurrected from the dead by his saintly companion, "+ saint.getName() + "!");
		clickedBlock.setTypeId(0); // sets sign block to air

	}

	/*
	 * Method to create a firework given a location using the randomColor method
	 */
	private void shootFirework(Location loc) {
		Firework fw = (Firework) saint.getWorld().spawn(loc, Firework.class);
		FireworkMeta meta = fw.getFireworkMeta();
		int rtype = (int) (Math.random() * 5);
		Type type = Type.STAR;

		switch (rtype) {
		case 1:
			type = Type.BALL;
			break;
		case 2:
			type = Type.CREEPER;
			break;
		case 3:
			type = Type.STAR;
			break;
		case 4:
			type = Type.BALL_LARGE;
			break;
		case 5:
			type = Type.BURST;
			break;
		default:
			type = Type.STAR;

		}
		FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(randomColor()).withFade(randomColor()).trail(true).with(type).build();
		meta.addEffect(effect);
		meta.setPower(1);
		fw.setFireworkMeta(meta);
	}

	/*
	 * This method is a random color generator, able to return any color in the
	 * game
	 */
	public Color randomColor() {
		int randomInt = (int) (Math.random() * 17);
		switch (randomInt) {
		case 1:
			return Color.AQUA;
		case 2:
			return Color.BLACK;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.FUCHSIA;
		case 5:
			return Color.GRAY;
		case 6:
			return Color.GREEN;
		case 7:
			return Color.LIME;
		case 8:
			return Color.MAROON;
		case 9:
			return Color.NAVY;
		case 10:
			return Color.OLIVE;
		case 11:
			return Color.ORANGE;
		case 12:
			return Color.PURPLE;
		case 13:
			return Color.RED;
		case 14:
			return Color.SILVER;
		case 15:
			return Color.TEAL;
		case 16:
			return Color.WHITE;
		case 17:
			return Color.YELLOW;
		default:
			return Color.YELLOW;

		}
	}

}
