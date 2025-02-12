package gtexpert.integration.ffm;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;

import gtexpert.api.GTEValues;
import gtexpert.api.modules.GTEModule;
import gtexpert.integration.GTEIntegrationSubmodule;
import gtexpert.integration.ffm.loaders.FFMOreDictionaryLoader;
import gtexpert.modules.GTEModules;

@GTEModule(
           moduleID = GTEModules.MODULE_FFM,
           containerID = GTEValues.MODID,
           modDependencies = GTEValues.MODID_FFM,
           name = "GTExpert Forestry For Minecraft Module")
public class FFMModule extends GTEIntegrationSubmodule {

    @Override
    public void registerRecipesNormal(RegistryEvent.Register<IRecipe> event) {
        FFMOreDictionaryLoader.init();
    }
}
