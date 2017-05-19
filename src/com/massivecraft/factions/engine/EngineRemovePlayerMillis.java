package com.massivecraft.factions.engine;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCoreRemovePlayerMillis;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.Map.Entry;

public class EngineRemovePlayerMillis extends Engine
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static EngineRemovePlayerMillis i = new EngineRemovePlayerMillis();
	public static EngineRemovePlayerMillis get() { return i; }
	
	// -------------------------------------------- //
	// REMOVE PLAYER MILLIS
	// -------------------------------------------- //
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void ageBonus(EventMassiveCoreRemovePlayerMillis event)
	{
		if (event.getColl() != MPlayerColl.get()) return;
		
		applyPlayerAgeBonus(event);
		applyFactionAgeBonus(event);
	}
	
	public void applyPlayerAgeBonus(EventMassiveCoreRemovePlayerMillis event)
	{
		// Skip if this bonus is totally disabled.
		// We don't want it showing up with 0 for everyone.
		if (MConf.get().removePlayerMillisPlayerAgeToBonus.isEmpty()) return;
		
		// Calculate First Played
		Long firstPlayed = event.getEntity().getFirstPlayed();
		Long age = 0L;
		if (firstPlayed != null)
		{
			age = System.currentTimeMillis() - firstPlayed;
		}
		
		// Calculate the Bonus!
		long bonus = 0;
		for (Entry<Long, Long> entry : MConf.get().removePlayerMillisPlayerAgeToBonus.entrySet())
		{
			Long key = entry.getKey();
			if (key == null) continue;
			
			Long value = entry.getValue();
			if (value == null) continue;
			
			if (age >= key)
			{
				bonus = value;
				break;
			}
		}
		
		// Apply
		event.getCauseMillis().put("Player Age Bonus", bonus);
	}
	
	public void applyFactionAgeBonus(EventMassiveCoreRemovePlayerMillis event)
	{
		// Skip if this bonus is totally disabled.
		// We don't want it showing up with 0 for everyone.
		if (MConf.get().removePlayerMillisFactionAgeToBonus.isEmpty()) return;
		
		// Calculate Faction Age
		Faction faction = ((MPlayer)event.getEntity()).getFaction();
		long age = 0;
		if ( ! faction.isNone())
		{
			age = faction.getAge();
		}
		
		// Calculate the Bonus!
		long bonus = 0;
		for (Entry<Long, Long> entry : MConf.get().removePlayerMillisFactionAgeToBonus.entrySet())
		{
			Long key = entry.getKey();
			if (key == null) continue;
			
			Long value = entry.getValue();
			if (value == null) continue;
			
			if (age >= key)
			{
				bonus = value;
				break;
			}
		}
		
		// Apply
		event.getCauseMillis().put("Faction Age Bonus", bonus);
	}
	
	
}
