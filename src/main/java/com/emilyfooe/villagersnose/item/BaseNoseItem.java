package com.emilyfooe.villagersnose.item;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeItem;

import javax.annotation.Nonnull;

public class BaseNoseItem extends ArmorItem implements IForgeItem {
    public BaseNoseItem(ArmorMaterial material) {
        super(material, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_MISC).durability(0).stacksTo(64).rarity(Rarity.COMMON));
    }
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 64;
    }
    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity)
    {
        return armorType == EquipmentSlotType.HEAD && entity instanceof PlayerEntity;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide && player.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()){
            ItemStack newStack = (stack.copy());
            newStack.setCount(1);
            player.setItemSlot(EquipmentSlotType.HEAD, newStack);
            stack.shrink(1);
        }
        return ActionResult.success(stack);
    }

}
