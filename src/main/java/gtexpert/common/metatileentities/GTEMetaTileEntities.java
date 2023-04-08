package gtexpert.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.util.GTUtility;
import gtexpert.api.recipes.GTERecipeMaps;
import gtexpert.client.GTETextures;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import static gregtech.common.metatileentities.MetaTileEntities.*;

public class GTEMetaTileEntities {
    public static GTESimpleMachineMetaTileEntity[] EXTREME_MIXER = new GTESimpleMachineMetaTileEntity[1]; //ZPM Only
    public static MetaTileEntitySawmill SAWMILL;
    public static SimpleMachineMetaTileEntity[] VIAL_EXTRACTOR = new SimpleMachineMetaTileEntity[GTValues.V.length - 1];
    public static MetaTileEntityVoidOreMiner VOIDOREMINER;
    public static MetaTileEntityDraconiumFusion DRACONIUM_FUSION;
    public static MetaTileEntityDraconiumAwakenedFusion AWAKENED_DRACONIUM_FUSION;

    public static void init() {
        /*
         * FOR ADDON DEVELOPERS:
         *
         * GTCEu will not take more than 2000 IDs. Anything past ID 1999
         * is considered FAIR GAME, take whatever you like.
         *
         * If you would like to reserve IDs, feel free to reach out to the
         * development team and claim a range of IDs! We will mark any
         * claimed ranges below this comment. Max value is 32767.
         *
         * - Gregicality / Shadows of Greg: 2000-3999
         * - Gregification: 4000-4499
         * - GregTech Food Option: 8500-8999
         * - HtmlTech: 9000-9499
         * - PCM's Ore Addon: 9500-9999
         * - GCM: 10000-10099
         * - MechTech: 10100-10499
         * - MBT 10500 - 10999
         * - CT(MBT) 32000 - ~
         * - FREE RANGE 11000-32767
         */

        // blocks :11001~
        // EXTREME_MIXER 11007
        EXTREME_MIXER[0] = registerMetaTileEntity(11007, new GTESimpleMachineMetaTileEntity(gteId(String.format("%s.%s", "extreme_mixer", GTValues.VN[7].toLowerCase())), GTERecipeMaps.EXTREME_MIXER_RECIPES, GTETextures.EXTREME_MIXER_OVERLAY, 7, true, GTUtility.hvCappedTankSizeFunction));

        // VIAL_EXTRACTOR 11010~11022
        registerSimpleMetaTileEntity(VIAL_EXTRACTOR, 11010, "vial_extractor", GTERecipeMaps.VIAL_EXTRACTOR_RECIPES, GTETextures.VIAL_EXTRACTOR_OVERLAY, true, GTEMetaTileEntities::gteId, GTUtility.hvCappedTankSizeFunction);


        // multiblocks :12000~
        SAWMILL = registerMetaTileEntity(12001, new MetaTileEntitySawmill(gteId("sawmill")));
        // = registerMetaTileEntity(12002, new MetaTileEntityVoidOreMiner(gteId("####")));
        VOIDOREMINER = registerMetaTileEntity(12003, new MetaTileEntityVoidOreMiner(gteId("void_ore_miner")));
        DRACONIUM_FUSION = registerMetaTileEntity(12004, new MetaTileEntityDraconiumFusion(gteId("draconium_fusion")));
        AWAKENED_DRACONIUM_FUSION = registerMetaTileEntity(12005, new MetaTileEntityDraconiumAwakenedFusion(gteId("awakened_draconium_fusion")));
    }

    @NotNull
    private static ResourceLocation gteId(@NotNull String name) {
        return new ResourceLocation("gtexpert", name);
    }
}
