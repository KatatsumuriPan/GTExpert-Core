package gtexpert;
import gtexpert.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import codechicken.lib.CodeChickenLib;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "gtexpert",
        name = "GTExpert-Core",
        acceptedMinecraftVersions = "[1.12,1.13)",
        dependencies = "required:forge@[14.23.5.2847,);" + CodeChickenLib.MOD_VERSION_DEP + "after:jei@[4.15.0,);after:crafttweaker")
public class GTExpertMod {

    @SidedProxy(modId = "gtexpert", clientSide = "gtexpert.client.ClientProxy", serverSide = "gtexpert.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static GTExpertMod instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
