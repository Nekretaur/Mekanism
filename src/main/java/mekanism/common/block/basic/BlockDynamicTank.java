package mekanism.common.block.basic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mekanism.common.block.interfaces.IHasInventory;
import mekanism.common.block.interfaces.IHasModel;
import mekanism.common.block.interfaces.IHasTileEntity;
import mekanism.common.tile.TileEntityDynamicTank;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockDynamicTank extends BlockBasicMultiblock implements IHasModel, IHasInventory, IHasTileEntity<TileEntityDynamicTank> {

    public BlockDynamicTank() {
        super("dynamic_tank");
    }

    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return new TileEntityDynamicTank();
    }

    @Override
    public int getInventorySize() {
        return 2;
    }

    @Nullable
    @Override
    public Class<? extends TileEntityDynamicTank> getTileClass() {
        return TileEntityDynamicTank.class;
    }
}