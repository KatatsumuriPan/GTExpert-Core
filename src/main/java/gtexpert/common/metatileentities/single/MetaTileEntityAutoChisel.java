package gtexpert.common.metatileentities.single;

import java.util.function.Function;

import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gtexpert.api.gui.GTEGuiTextures;
import net.minecraft.util.ResourceLocation;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;

import gtexpert.api.capability.SingleblockRecipeLogicNoCache;
import org.jetbrains.annotations.NotNull;

public class MetaTileEntityAutoChisel extends SimpleMachineMetaTileEntity {

    public MetaTileEntityAutoChisel(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer,
                                    int tier, boolean hasFrontFacing, Function<Integer, Integer> tankScalingFunction) {
        super(metaTileEntityId, recipeMap, renderer, tier, hasFrontFacing, tankScalingFunction);
        new SingleblockRecipeLogicNoCache(this, recipeMap, () -> this.energyContainer);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAutoChisel(this.metaTileEntityId, this.workable.getRecipeMap(), this.renderer,
                this.getTier(), this.hasFrontFacing(), this.getTankScalingFunction());
    }

    @Override
    protected @NotNull TextureArea getLogo() {
        return GTEGuiTextures.GTE_LOGO;
    }

    @Override
    protected @NotNull TextureArea getXmasLogo() {
        return GTEGuiTextures.GTE_LOGO_XMAS;
    }
}
