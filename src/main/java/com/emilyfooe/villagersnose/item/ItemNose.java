package com.emilyfooe.villagersnose.item;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;

public class ItemNose extends ArmorItem implements IPlantable {
    public ItemNose(){
        super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, new Item.Properties().maxStackSize(64).rarity(Rarity.COMMON).group(ItemGroup.MISC));
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return null;
    }
}
