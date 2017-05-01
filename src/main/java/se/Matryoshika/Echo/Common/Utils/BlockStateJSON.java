package se.Matryoshika.Echo.Common.Utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.fml.common.Loader;
import se.Matryoshika.Echo.Echo;
import se.Matryoshika.Echo.Common.BlockStateGetter;

public class BlockStateJSON {
	
	public static List<IBlockState> states = new ArrayList<IBlockState>();
	public static Map<IBlockState,Byte> tiers = null;

	public static void createJSON() {
		File blockStates = new File(Echo.getConfigFolder(), "allowed_blocks_archive.json");
		Map<String,Byte> default_states = Maps.newHashMap();
		
		
		default_states.put("{Properties:{variant:\"dirt\",snowy:\"false\"},Name:\"minecraft:dirt\"}", (byte) 6);
		default_states.put("{Properties:{variant:\"stone\"},Name:\"minecraft:stone\"}", (byte) 6);
		default_states.put("{Properties:{variant:\"diorite\"},Name:\"minecraft:stone\"}", (byte)6);
		default_states.put("{Properties:{variant:\"granite\"},Name:\"minecraft:stone\"}", (byte)6);
		default_states.put("{Properties:{variant:\"andesite\"},Name:\"minecraft:stone\"}", (byte)6);
		default_states.put("{Name:\"minecraft:cobblestone\"}", (byte)6);
		default_states.put("{Name:\"minecraft:gravel\"}", (byte)6);
		default_states.put("{Name:\"minecraft:clay\"}", (byte)6);
		default_states.put("{Properties:{variant:\"stonebrick\"},Name:\"minecraft:stonebrick\"}", (byte)4);
		default_states.put("{Properties:{variant:\"mossy_stonebrick\"},Name:\"minecraft:stonebrick\"}", (byte)4);
		default_states.put("{Properties:{variant:\"cracked_stonebrick\"},Name:\"minecraft:stonebrick\"}",(byte) 4);
		default_states.put("{Properties:{variant:\"chiseled_stonebrick\"},Name:\"minecraft:stonebrick\"}", (byte)4);
		default_states.put("{Properties:{variant:\"sand\"},Name:\"minecraft:sand\"}", (byte)6);
		default_states.put("{Properties:{type:\"sandstone\"},Name:\"minecraft:sandstone\"}", (byte)6);
		default_states.put("{Name:\"minecraft:redstone_block\"}", (byte)6);
		default_states.put("{Name:\"minecraft:lapis_block\"}", (byte)6);
		default_states.put("{Properties:{variant:\"default\"},Name:\"minecraft:quartz_block\"}",(byte) 4);
		default_states.put("{Name:\"minecraft:iron_block\"}", (byte)6);
		default_states.put("{Name:\"minecraft:gold_block\"}", (byte)6);
		default_states.put("{Name:\"minecraft:diamond_block\"}",(byte) 6);
		default_states.put("{Name:\"minecraft:emerald_block\"}", (byte)6);
		default_states.put("{Name:\"minecraft:soul_sand\"}", (byte)6);
		default_states.put("{Name:\"minecraft:netherrack\"}", (byte)6);
		default_states.put("{Name:\"minecraft:glowstone\"}", (byte)6);
		default_states.put("{Name:\"minecraft:nether_brick\"}",(byte) 4);
		default_states.put("{Name:\"minecraft:end_stone\"}", (byte)6);
		default_states.put("{Name:\"minecraft:coal_block\"}", (byte)4);
		default_states.put("{Name:\"minecraft:nether_wart_block\"}", (byte)4);
		default_states.put("{Name:\"minecraft:obsidian\"}", (byte)4);
		default_states.put("{Properties:{explode:\"false\"},Name:\"minecraft:tnt\"}", (byte)3);
		
		if(Loader.isModLoaded("Botania")){
			default_states.put("{Properties:{variant:\"default\"},Name:\"botania:livingwood\"}", (byte) 4);
			default_states.put("{Properties:{variant:\"default\"},Name:\"botania:livingrock\"}",(byte) 4);
			default_states.put("{Properties:{variant:\"manasteel\"},Name:\"botania:storage\"}",(byte) 4);
			default_states.put("{Properties:{variant:\"elementium\"},Name:\"botania:storage\"}",(byte) 4);
			default_states.put("{Properties:{variant:\"terrasteel\"},Name:\"botania:storage\"}",(byte) 3);
		}
		
		try {
			if(blockStates.createNewFile())
			try (Writer writer = new FileWriter(blockStates)) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				gson.toJson(new Converter(default_states), writer);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Map<String,Byte> getAllowedStatesMap(){
		Map<String,Byte> allowed = Maps.newHashMap();
		try{
			allowed = new Gson().fromJson(new FileReader(new File(Echo.getConfigFolder(), "allowed_blocks_archive.json")), Converter.class).map;
		}
		catch(JsonSyntaxException | JsonIOException | FileNotFoundException e){
			e.printStackTrace();
		}
		return allowed;
	}
	
	public static List<IBlockState> getAllowedStates(){
		if(!states.isEmpty())
			return states;
		
		for(String key : getAllowedStatesMap().keySet()){
			String raw = key.replaceAll("\\\\", "");
			NBTTagCompound nbt = new NBTTagCompound();
			try {
				nbt = (NBTTagCompound) JsonToNBT.getTagFromJson(raw);
			} catch (NBTException e) {
				e.printStackTrace();
			}
			IBlockState state = NBTUtil.func_190008_d(nbt);
			if(state != null && state.isFullBlock() && !BlockStateGetter.blacklist.contains(state.getBlock()))
				states.add(state);
		}
		
		
		return states;
	}
	
	public static void reload(){
		
		states.clear();
		for(String key : getAllowedStatesMap().keySet()){
			String raw = key.replaceAll("\\\\", "");
			NBTTagCompound nbt = new NBTTagCompound();
			try {
				nbt = (NBTTagCompound) JsonToNBT.getTagFromJson(raw);
			} catch (NBTException e) {
				e.printStackTrace();
			}
			IBlockState state = NBTUtil.func_190008_d(nbt);
			if(state != null && state.isFullBlock())
				states.add(state);
		}
		
		tiers = new HashMap<IBlockState,Byte>();
		for(String key : getAllowedStatesMap().keySet()){
			String raw = key.replaceAll("\\\\", "");
			NBTTagCompound nbt = new NBTTagCompound();
			try {
				nbt = (NBTTagCompound) JsonToNBT.getTagFromJson(raw);
			} catch (NBTException e) {
				e.printStackTrace();
			}
			IBlockState state = NBTUtil.func_190008_d(nbt);
			tiers.put(state, getAllowedStatesMap().get(key));
		}
		
	}
	
	public static byte getTiers(IBlockState from){
		if(tiers != null)
			return tiers.get(from);
		
		tiers = new HashMap<IBlockState,Byte>();
		for(String key : getAllowedStatesMap().keySet()){
			String raw = key.replaceAll("\\\\", "");
			NBTTagCompound nbt = new NBTTagCompound();
			try {
				nbt = (NBTTagCompound) JsonToNBT.getTagFromJson(raw);
			} catch (NBTException e) {
				e.printStackTrace();
			}
			IBlockState state = NBTUtil.func_190008_d(nbt);
			tiers.put(state, getAllowedStatesMap().get(key));
		}
		return tiers.get(from);
	}
	
	private static class Converter{
		Map<String,Byte> map;
		public Converter(Map<String,Byte> map){
			this.map = map;
		}
	}
	
	public static boolean addBlockStateToJSON(IBlockState state, byte maxTier){
		File blockStates = new File(Echo.getConfigFolder(), "allowed_blocks_archive.json");
		Map<String, Byte> map = getAllowedStatesMap();
		
		map.put(NBTUtil.func_190009_a(new NBTTagCompound(), state).toString(), maxTier);
		
		
		try (Writer writer = new FileWriter(blockStates)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			
			gson.toJson(new Converter(map), writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

}
