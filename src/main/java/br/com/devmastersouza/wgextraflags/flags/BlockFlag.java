package br.com.devmastersouza.wgextraflags.flags;

import br.com.devmastersouza.wgextraflags.objects.BlockData;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;

/**
 * Project WG152ExtraFlags.
 * Create by DevMasterSouza - email: devmastersouza@gmail.com
 */
public class BlockFlag extends Flag<BlockData> {

    public BlockFlag(String name, @Nullable RegionGroup defaultGroup) {
        super(name, defaultGroup);
    }

    public BlockFlag(String name) {
        super(name);
    }

    @Override
    public BlockData parseInput(WorldGuardPlugin worldGuardPlugin, CommandSender commandSender, String s) throws InvalidFlagFormat {
        s = s.trim().replaceAll(" ", "");
        if(s.contains(":")) {
            String[] arrays = s.split(":");
            if(canParseInt(arrays[0])){
                Material m = Material.getMaterial(Integer.parseInt(arrays[0]));
                if(canParseInt(arrays[1])) {
                    int data = Integer.parseInt(arrays[1]);
                    if(m == null) return null;
                    return new BlockData(m, (byte)data);
                } else {
                    if(m == null) return null;
                    return new BlockData(m);
                }
            }else {
                Material m = Material.getMaterial(arrays[0]);
                if(m == null) return null;
                if(canParseInt(arrays[1])) {
                    int data = Integer.parseInt(arrays[1]);
                    if(m == null) return null;
                    return new BlockData(m, (byte)data);
                } else {
                    if(m == null) return null;
                    return new BlockData(m);
                }
            }
        }else{
            if(canParseInt(s)) {
                Material m = Material.getMaterial(Integer.parseInt(s));
                if(m == null) return null;
                return new BlockData(m);
            }else {
                Material m = Material.getMaterial(s);
                if(m == null) return null;
                return new BlockData(m);
            }

        }
    }

    @Override
    public BlockData unmarshal(@Nullable Object o) {
        if(o instanceof BlockData) {
            return (BlockData) o;
        }else{
            return null;
        }
    }

    @Override
    public Object marshal(BlockData blockData) {
        return blockData;
    }

    private boolean canParseInt(String str) {
        try{
            Integer.parseInt(str);
            return true;
        }catch (NumberFormatException ex) {return false;}
    }
}
