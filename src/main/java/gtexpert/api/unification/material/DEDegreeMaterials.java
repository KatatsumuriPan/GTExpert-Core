package gtexpert.api.unification.material;

import gregicality.multiblocks.api.fluids.fluidType.GCYMFluidTypes;
import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty.GasTier;
import gregtech.api.unification.material.properties.ToolProperty;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gtexpert.api.unification.Elements.*;
import static gtexpert.api.unification.material.GTEMaterials.*;

public class DEDegreeMaterials {
    /**
     * 24176 - 24200
     */
    public static void init() {
        // Cryotheum
        CRYOTHEUM = new Material.Builder(24176, "cryotheum")
                .fluid(FluidTypes.LIQUID, false).fluidTemp(203)
                .color(0x00B6FF).iconSet(MaterialIconSet.FLUID)
                .flags(NO_WORKING, DISABLE_DECOMPOSITION)
                .components(Electrotine, 1, EnderPearl, 1, Ice, 4)
                .build();

        // Pyrotheum
        PYROTHEUM = new Material.Builder(24177, "pyrotheum")
                .fluid(GCYMFluidTypes.MOLTEN, false).fluidTemp(10273)
                .color(0xE42C13).iconSet(MaterialIconSet.BRIGHT)
                .flags(NO_WORKING, DISABLE_DECOMPOSITION)
                .components(Redstone, 1, Sulfur, 1, Blaze, 16)
                .build();

        // Dragon Dust
        DRAGON = new Material.Builder(24178, "dragon")
                .dust()
                .color(0x000000).iconSet(MaterialIconSet.ROUGH)
                .flags(NO_WORKING, DISABLE_DECOMPOSITION)
                .components(END_STEEL, 1, Iridium, 1, EnderEye, 1, SaltWater, 1)
                .build();

        // Draconium
        DRACONIUM = new Material.Builder(24180, "draconium")
                .ore(true)
                .ingot(5)
                .fluid(FluidTypes.LIQUID, false).fluidTemp(1200)
                .color(0x7C46B0).iconSet(MaterialIconSet.METALLIC)
                .blastTemp(7200, GasTier.HIGHER, VA[LuV], 600)
                .flags(GENERATE_ROD, GENERATE_FRAME, GENERATE_FINE_WIRE, GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES, EXCLUDE_BLOCK_CRAFTING_RECIPES, EXCLUDE_PLATE_COMPRESSOR_RECIPE, DISABLE_DECOMPOSITION)
                .components(DRAGON, 1, Obsidian, 1, EnderPearl, 4, LiquidEnderAir, 8)
                .toolStats(ToolProperty.Builder.of(80.0F, 2.0F, 3072, 5)
                        .enchantability(14).build())
                .rotorStats(18.0f, 4.0f, 3072)
                .element(De)
                .build();

        // Awakened Draconium
        AWAKENED_DRACONIUM = new Material.Builder(24181, "awakened_draconium")
                .ingot(6)
                .fluid(FluidTypes.LIQUID, false).fluidTemp(1200)
                .color(0xFF6C00).iconSet(MaterialIconSet.METALLIC)
                .blastTemp(7200, GasTier.HIGHEST, VA[LuV], 600)
                .flags(GENERATE_FRAME, GENERATE_FINE_WIRE, GENERATE_PLATE, GENERATE_ROD, GENERATE_RING, EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES, EXCLUDE_BLOCK_CRAFTING_RECIPES, EXCLUDE_PLATE_COMPRESSOR_RECIPE, DISABLE_DECOMPOSITION)
                .toolStats(ToolProperty.Builder.of(100.0F, 2.0F, 5120, 6)
                        .enchantability(18).build())
                .rotorStats(22.0f, 6.0f, 5120)
                .element(De1)
                .build();

        // Chaos Dust
        CHAOS = new Material.Builder(24179, "chaos")
                .dust()
                .color(0x000000).iconSet(MaterialIconSet.ROUGH)
                .flags(NO_WORKING, DISABLE_DECOMPOSITION)
                .components(DRAGON, 8, AWAKENED_DRACONIUM, 8, PYROTHEUM, 8)
                .build();
    }
}
