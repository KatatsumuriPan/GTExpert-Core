package gtexpert;

import gregtech.api.GTValues;
import gtexpert.api.util.GTELog;
import gtexpert.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.jetbrains.annotations.NotNull;

@Mod(modid = "gtexpert",
        name = "GTExpert-Core",
        acceptedMinecraftVersions = "[1.12,1.13)",
        dependencies = GTValues.MOD_VERSION_DEP)
public class GTExpertMod {

    @SidedProxy(modId = "gtexpert", clientSide = "gtexpert.client.ClientProxy", serverSide = "gtexpert.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static GTExpertMod instance;

    @Mod.EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        GTELog.init(event.getModLog());

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
