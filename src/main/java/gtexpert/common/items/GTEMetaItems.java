package gtexpert.common.items;

import gregtech.api.GTValues;
import gregtech.api.items.armor.ArmorMetaItem;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.covers.CoverConveyor;
import gregtech.common.covers.CoverPump;
import gregtech.common.covers.CoverRoboticArm;

import static gregtech.common.covers.CoverBehaviors.registerBehavior;
import static gtexpert.api.util.GTEUtility.gteId;

public final class GTEMetaItems {

    private GTEMetaItems() {}

    public static MetaItem<?>.MetaValueItem GTE_ME_FAKE_COMPONENT;
    public static MetaItem<?>.MetaValueItem MATRIX_CORE;
    public static MetaItem<?>.MetaValueItem PRIMITIVE_MOTOR;
    public static MetaItem<?>.MetaValueItem PRIMITIVE_PUMP;
    public static MetaItem<?>.MetaValueItem PRIMITIVE_CONVEYOR;
    public static MetaItem<?>.MetaValueItem PRIMITIVE_PISTON;
    public static MetaItem<?>.MetaValueItem PRIMITIVE_ROBOT_ARM;

    public static ArmorMetaItem<?>.ArmorMetaValueItem PISTON_BOOTS;

    public static void init() {
        GTEMetaItem1 metaItem1 = new GTEMetaItem1();
        metaItem1.setRegistryName("meta_item_1");
        GTEMetaArmor armor = new GTEMetaArmor();
        armor.setRegistryName("gte_armor");
    }
}
