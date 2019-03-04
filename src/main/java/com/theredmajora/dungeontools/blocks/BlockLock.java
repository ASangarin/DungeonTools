package com.theredmajora.dungeontools.blocks;

import com.theredmajora.dungeontools.DungeonConfig;
import com.theredmajora.dungeontools.extra.IColorType;
import com.theredmajora.dungeontools.extra.IUnlockable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockLock extends BlockVanish implements IUnlockable, IColorType
{
	public String type;
	
	public BlockLock(String type)
	{
		super(Material.IRON, "lock_" + type, true);
        this.setBlockUnbreakable();
		this.type = type;
	}
	
	public String getType() { return this.type; }

    @Override
    public boolean getVanishFlag(IBlockState state)
    { 
    	boolean flag = DungeonConfig.connectedLocks ? state.getBlock() instanceof BlockVanishLock || state.getBlock() instanceof BlockVanishChains : false;
        return (state.getBlock() instanceof BlockLock || state.getBlock() instanceof BlockChains || flag) && ((IColorType) state.getBlock()).getType().equals(this.getType());
    }
	
	@Override
	public boolean unlock(World world, EntityPlayer player, BlockPos pos, IBlockState state) {
		world.setBlockState(pos, ((BlockVanish) state.getBlock()).getVanishedState(state));
		return true;
	}

    @Override
    public void registerBlockModel()
    {
	    ModelLoader.setCustomStateMapper(this, (new StateMap.Builder()).ignore(VANISH).build());
    	super.registerBlockModel();
    }
}