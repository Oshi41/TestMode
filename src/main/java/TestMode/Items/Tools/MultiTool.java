package TestMode.Items.Tools;

import TestMode.Items.IVariantBlock;
import TestMode.Utils.Materials;
import TestMode.TestMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by User on 18.06.2017.
 */
public class MultiTool extends ItemTool implements IVariantBlock {

    //region Fields
    public info[] multiTools = new info[]{
        new info("Diamond multitool", ToolMaterial.DIAMOND),
            new info("Forgotten multitool", Materials.Forgotten),
            new info("Knowledge multitool", Materials.Knowledge),
            new info("Wisdom multitool", Materials.Wisdom, -1)
    };
    //endregion

    public static MultiTool instance(){
        return new MultiTool(ToolMaterial.WOOD);
    }
    public MultiTool(ToolMaterial material) {
        super(material, null);
        //harvestLevel = HarvestLevel;
        setMaxDamage(material.getMaxUses() * 2);
        setCreativeTab(TestMode.toolTab);
        setMaxStackSize(1);
    }
    public MultiTool(ToolMaterial material, int maxUses) {
        super(material, null);
        //harvestLevel = HarvestLevel;
        setMaxDamage(maxUses);
        setCreativeTab(TestMode.toolTab);
        setMaxStackSize(1);
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return blockIn.getBlock().getHarvestLevel(blockIn) <= toolMaterial.getHarvestLevel();
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        return this.toolMaterial.getEfficiencyOnProperMaterial();
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        return toolMaterial.getHarvestLevel();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add("Efficiency " + toolMaterial.getEfficiencyOnProperMaterial());
        if (stack.getMaxDamage() < 0)
            tooltip.add("Infinity uses");
        else
            tooltip.add(stack.getMaxDamage() + " uses");
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
            if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up()))
            {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH)
                {
                    this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }

                if (block == Blocks.DIRT)
                {
                    switch ((BlockDirt.DirtType)iblockstate.getValue(BlockDirt.VARIANT))
                    {
                        case DIRT:
                            this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                            return EnumActionResult.SUCCESS;
                        case COARSE_DIRT:
                            this.setBlock(stack, playerIn, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                            return EnumActionResult.SUCCESS;
                    }
                }
            }

            return EnumActionResult.PASS;
        }
    }

    protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (!worldIn.isRemote)
        {
            worldIn.setBlockState(pos, state, 11);
            stack.damageItem(1, player);
        }
    }

    //region IVariantBlock
    @Override
    public ItemStack[] getItemVarians() {
        ItemStack[] items = new ItemStack[multiTools.length];
        for (int i = 0; i < multiTools.length; i++){
            items[i] = new ItemStack(new MultiTool(multiTools[i].material, multiTools[i].usesCount), 1);
        }
        return items;
    }

    @Override
    public void register() {
        ItemStack[] items = getItemVarians();
        int i = 0;
        for (ItemStack stack : items){
            MultiTool item = (MultiTool)stack.getItem();
            String name = "item.multitool" + i;

            item.setRegistryName(name);
            item.setUnlocalizedName(multiTools[i].Name);

            GameRegistry.registerItem(item);
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(TestMode.Mod_ID + ":" +name));

            i++;
        }
    }
    //endregion

    //region Nested class
    class info {
        public String Name;
        public ToolMaterial material;
        public int usesCount;

        public info(String Name, ToolMaterial material){
            this.Name = Name;
            this.material = material;
            this.usesCount = (int)(material.getMaxUses() * 2.5);
        }
        public info(String Name, ToolMaterial material, int usesCount){
            this.Name = Name;
            this.material = material;
            this.usesCount = usesCount;
        }
    }
    //endregion
}
