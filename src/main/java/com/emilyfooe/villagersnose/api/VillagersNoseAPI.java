package com.emilyfooe.villagersnose.api;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class VillagersNoseAPI {
    public static IArmorMaterial FLESH_ARMOR_MATERIAL = new IArmorMaterial() {
        @Override
        public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
            return 0;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlotType p_200902_1_) {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }

        @Override
        public String getName() {
            return "flesh";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };
}
