package br.com.devmastersouza.wgextraflags;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/* ESTE PLUGIN PRECISA DE JAVA 1.8 */
public class WorldGuardUtils {

    private final WG152ExtraFlags plugin;
    private Plugin wgpl;

    private Class _WorldGuardPluginClass;

    private Method _152getRegionManager;
    private Method _152getApplicableRegions;

    private Class _179RegionContainerClass;
    private Method _179getRegionContainer;
    private Class _179RegionQueryClass;
    private Method _179createQuery;
    private Method _179getApplicableRegions;

    private Class _ApplicableRegionSetClass;
    private Class _StateFlagClass;
    private Method _allows;
    private Method _getFlag;
    private Method _getFlagWithLocalPlayer;
    private Method _wrapPlayer;

    public WorldGuardUtils(WG152ExtraFlags plugin) {
        this.plugin = plugin;
        wgpl = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if(wgpl != null) {
            try {
                _WorldGuardPluginClass = wgpl.getClass();
                if (wgpl.getDescription().getVersion().startsWith("6")) {
                    //NEW SYSTEM (1.7.9 ABOVE)
                    _179getRegionContainer = _WorldGuardPluginClass.getMethod("getRegionContainer");
                    _179RegionContainerClass = Class.forName("com.sk89q.worldguard.bukkit.RegionContainer");
                    _179createQuery = _179RegionContainerClass.getMethod("createQuery");
                    _179RegionQueryClass = Class.forName("com.sk89q.worldguard.bukkit.RegionQuery");
                    _179getApplicableRegions = _179RegionQueryClass.getMethod("getApplicableRegions", Location.class);
                } else {
                    //OLD SYSTEM (1.5.2)
                    _152getRegionManager = _WorldGuardPluginClass.getMethod("getRegionManager", World.class);
                    _152getApplicableRegions = RegionManager.class.getMethod("getApplicableRegions", Vector.class);
                }
                _ApplicableRegionSetClass = Class.forName("com.sk89q.worldguard.protection.ApplicableRegionSet");
                _StateFlagClass = Class.forName("com.sk89q.worldguard.protection.flags.StateFlag");
                _allows = _ApplicableRegionSetClass.getMethod("allows", _StateFlagClass);
                _getFlag = _ApplicableRegionSetClass.getMethod("getFlag", Flag.class);
                _getFlagWithLocalPlayer = _ApplicableRegionSetClass.getMethod("getFlag", Flag.class, LocalPlayer.class);
                _wrapPlayer = _WorldGuardPluginClass.getMethod("wrapPlayer", Player.class);

            }catch (Exception e) {e.printStackTrace();}
        }else{
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "WG152ExtraFlags - WorldGuard Faltando");
        }
    }

    /* Metodo para inserir as flags no WorldGuard */
    protected void addFlag(Flag<?> flag) {
        try {
            /* Field das Flags */
            Field f = DefaultFlag.class.getField("flagsList");

            /* Criando uma nova array com mais um espaço */
            Flag<?>[] flags = new Flag<?>[DefaultFlag.flagsList.length + 1];
            /* Copiando todos os Objetos da array do WorldGuard para nova Array */
            System.arraycopy(DefaultFlag.flagsList, 0, flags, 0, DefaultFlag.flagsList.length);

            /* Inserindo a nova flag na array */
            flags[DefaultFlag.flagsList.length] = flag;

            /* Verificando se a flag não é nula antes de colocar a nova array */
            if (flag == null) {
                throw new NullPointerException("flag null");
            }

            /* Modificando os modifiers da Class do WorldGuard*/
            Field modifier = Field.class.getDeclaredField("modifiers");
            modifier.setAccessible(true);
            modifier.setInt(f, f.getModifiers() & ~Modifier.FINAL);

            /* Inserindo na class do WorldGuard a nova array com nossa nova Flag */
            f.set(null, flags);

        } catch (Exception ex) {}
    }

    protected Object getApplicableRegions(Location location) {
        try{
            if (wgpl.getDescription().getVersion().startsWith("6")) {
                Object rgcontainer = _179getRegionContainer.invoke(wgpl);
                Object rgquery = _179createQuery.invoke(rgcontainer);
                return _179getApplicableRegions.invoke(rgquery, location);
            }else{
                Vector vector = new Vector(location.getX(), location.getY(), location.getZ());
                RegionManager rm = (RegionManager) _152getRegionManager.invoke(wgpl, location.getWorld());
                return _152getApplicableRegions.invoke(rm, vector);
            }
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    protected boolean allows(StateFlag flag, Location location) {
        try {
            Object ob = getApplicableRegions(location);
            return (boolean) _allows.invoke(ob, flag);
        }catch (Exception e){e.printStackTrace();return false;}
    }

    protected <T extends Flag<V>, V> V getFlag(T flag, Location location) {
        try {
            Object ob = getApplicableRegions(location);
            return (V) _getFlag.invoke(ob, flag);
        }catch (Exception e){e.printStackTrace();return null;}
    }
    protected <T extends Flag<V>, V> V getFlag(T flag, Location location, Player player) {
        try {
            Object ob = getApplicableRegions(location);
            Object localplayer = _wrapPlayer.invoke(wgpl, player);
            return (V) _getFlagWithLocalPlayer.invoke(ob, flag, localplayer);
        }catch (Exception e){e.printStackTrace();return null;}
    }

    /*protected ProtectedRegion getRegion(String name, World world) {
        return WGBukkit.getRegionManager(world).getRegion(name);
    }
    protected void saveWorldGuard(World world) {
        try {
            WGBukkit.getRegionManager(world).save();
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }*/
}
