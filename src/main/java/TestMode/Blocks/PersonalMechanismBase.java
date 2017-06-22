package TestMode.Blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

/**
 * Created by User on 16.06.2017.
 */
public class PersonalMechanismBase extends BlockContainer {

    //region Fields
    private UUID _uniqueID;
    //endregion

    //region Constructor

    public PersonalMechanismBase() {
        super(Material.ROCK);
        setCreativeTab(CreativeTabs.MISC);
    }

    //endregion


    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityChest();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return true;
        if (playerIn.isSneaking()) return false;

        if (_uniqueID == null) _uniqueID = playerIn.getUniqueID();

        if (_uniqueID != playerIn.getUniqueID() && !playerIn.capabilities.isCreativeMode) {
            playerIn.addChatMessage(new TextComponentString("Sorry, you do not have an access"));
            return true;
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof  TileEntityChest){
            playerIn.displayGUIChest((TileEntityChest)tileEntity);
        }
        return true;
    }

    @Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        //super.onBlockHarvested(worldIn, pos, state, player);
//        ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
//        if (item.getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)){
//            worldIn.destroyBlock(pos, false);
//            dropBlockAsItem(worldIn, pos, state, 0);
//        }
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        super.onBlockClicked(worldIn, pos, playerIn);


    }
}
