package com.github.pelotrio.debugblocks.block;

import com.github.pelotrio.debugblocks.tile.TickBlockTile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TickBlock extends Block {

    public TickBlock() {
        super(Material.IRON);
        setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TickBlockTile();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
}
