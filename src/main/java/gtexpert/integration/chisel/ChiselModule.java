package gtexpert.integration.chisel;

import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;

import gtexpert.api.GTEValues;
import gtexpert.api.modules.GTEModule;
import gtexpert.integration.GTEIntegrationSubmodule;
import gtexpert.integration.chisel.loaders.ChiselOreDictionaryLoader;
import gtexpert.integration.chisel.metatileentities.ChiselMetaTileEntities;
import gtexpert.integration.chisel.recipes.ChiselBlocksRecipe;
import gtexpert.integration.chisel.recipes.ChiselToolsRecipe;
import gtexpert.modules.GTEModules;

@GTEModule(
           moduleID = GTEModules.MODULE_CHISEL,
           containerID = GTEValues.MODID,
           modDependencies = GTEValues.MODID_CHISEL,
           name = "GTExpert Chisel Module")
public class ChiselModule extends GTEIntegrationSubmodule {

    @Override
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        ChiselMetaTileEntities.init();
    }

    @Override
    public void registerRecipesNormal(RegistryEvent.Register<IRecipe> event) {
        ChiselOreDictionaryLoader.init();
    }

    @Override
    public void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        ChiselBlocksRecipe.init();
        ChiselToolsRecipe.init();
    }
}
