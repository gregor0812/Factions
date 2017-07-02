package com.massivecraft.factions.cmd.type;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import com.massivecraft.massivecore.Named;
import com.massivecraft.massivecore.command.type.TypeNameAbstract;
import org.bukkit.command.CommandSender;
import static com.massivecraft.factions.util.MiscUtil.substanceChars;

public class TypeFactionNameAbstract extends TypeNameAbstract
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public TypeFactionNameAbstract(boolean strict)
	{
		super(strict);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Named getCurrent(CommandSender sender)
	{
		MPlayer mPlayer = MPlayer.get(sender);
		Faction faction = mPlayer.getFaction();
		return faction;
	}
	
	@Override
	public boolean isNameTaken(String name)
	{
		return FactionColl.get().isNameTaken(name);
	}
	
	@Override
	public boolean isCharacterAllowed(char character)
	{
		return substanceChars.contains(String.valueOf(character));
	}
	
	@Override
	public Integer getLengthMin()
	{
		return MConf.get().factionNameLengthMin;
	}
	
	@Override
	public Integer getLengthMax()
	{
		return MConf.get().factionNameLengthMax;
	}
	
}
