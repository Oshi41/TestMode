package TestMode.Items.Tools;

import TestMode.Items.IVariantBlock;
import TestMode.TestMode;
import com.sun.glass.ui.Size;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by User on 16.06.2017.
 */
public class Hammer extends ItemPickaxe implements IVariantBlock {

    //region Fields
    public Size _range;
    //public int Number;

    public final static info[] Hammers = {
            new info("Builder Hammer", new Size(2,1), ToolMaterial.STONE),
            new info("Light Hammer", new Size(2,2), ToolMaterial.IRON),
            new info("Mjöllnir", new Size(3,2), ToolMaterial.DIAMOND),
            new info("War Hammer", new Size(2,1), ToolMaterial.DIAMOND),
            new info("Legendary Hammer", new Size(5,3), ToolMaterial.DIAMOND)
    };
    //endregion

    //region Constructor
    public static Hammer instance(){
        return new Hammer(ToolMaterial.WOOD, new Size(0,0));
    }

    public Hammer(ToolMaterial material, Size range)  {
        super(material);
        _range = range;
        setCreativeTab(TestMode.toolTab);
        this.efficiencyOnProperMaterial /= 2;
    }

    //endregion

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        super.onCreated(stack, worldIn, playerIn);

        //if (stack.get)
        //if (stack.getUnlocalizedName().contains("war"))
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && canHarvestBlock(player.worldObj.getBlockState(pos))){
            return onBlockDestroyed(itemstack, player.worldObj, player.worldObj.getBlockState(pos),pos, player);
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if (worldIn.isRemote) return true;

        if (entityLiving instanceof EntityPlayer){
            if (((EntityPlayer)entityLiving).isSneaking()) return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);


            BlockPos[] toBreak = getBlockRange(worldIn, pos, (EntityPlayer)entityLiving);

            for (BlockPos pos1 : toBreak){

                Block block = worldIn.getBlockState(pos1).getBlock();
                //Проходим только по тому, что можем срубить
                if (ForgeHooks.canToolHarvestBlock(worldIn, pos1, stack)){

                    block.harvestBlock(worldIn, (EntityPlayer)entityLiving, pos1,
                            worldIn.getBlockState(pos1), worldIn.getTileEntity(pos1), stack);
                    worldIn.destroyBlock(pos1, false);
                }
            }
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        //return super.hitEntity(stack, target, attacker);
        if (!(attacker instanceof EntityPlayer)) return false;

        Random r = new Random();
        int damage = r.nextInt(_range.width + _range.height) + 2;
        if (damage > 18) damage = 18;
        DamageSource source = getRandomDamageSource((EntityPlayer)attacker);
        target.attackEntityFrom(source, damage);
        return true;
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.MAIN_HAND){
            if (!playerIn.isSneaking()) {
                for (ItemStack s : playerIn.inventory.mainInventory) {
                    if (s != null && s.stackSize > 0) {
                        Item item = s.getItem();
                        if (item != null && item instanceof ItemBlock && ((ItemBlock) item).block instanceof BlockTorch) {
                            return item.onItemUse(s, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
                        }
                    }
                }
            }
            else {
                for (ItemStack s : playerIn.inventory.mainInventory) {
                    if (s != null && s.stackSize > 0) {
                        Item item = s.getItem();
                        if (item != null && item instanceof ItemBlock && ((ItemBlock) item).canPlaceBlockOnSide(worldIn, pos, facing, playerIn, s)){
                            return item.onItemUse(s, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
//                            boolean res = ((ItemBlock) item).placeBlockAt(s, playerIn, worldIn, pos, facing, hitX, hitY, hitZ, worldIn.getBlockState(pos));
//                             int i = 0;
//                            return res ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
                        }
                    }
                }
            }
        }

        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D(){
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add("Efficiency " + this.efficiencyOnProperMaterial);
        tooltip.add("Scoops up range with " + _range.width + " sides and " + _range.height + " depth");
        tooltip.add(stack.getMaxDamage() + " uses");
    }


    //region    Helping Methods

    private BlockPos[] getBlockRange(World worldIn, BlockPos pos, EntityPlayer player){
        ArrayList<BlockPos> poses = new ArrayList<BlockPos>();
        BlockPos begin = pos, end = pos;
        RayTraceResult orientation = rayTrace(worldIn, player, false);


        int depth = _range.height - 1,
                maxY = (_range.width - 1) / 2,
                minY = _range.width - maxY - 1,
                minSizeSquare = minY,
                maxSizeSquare = maxY;
        
        if (minY > 1){
            int addition = minY - 1;
            minY -= addition;
            maxY += addition;
        }

        switch (orientation.sideHit){
            case DOWN:
                begin = begin.add(-minSizeSquare, 0, -minSizeSquare);
                end = end.add(maxSizeSquare, depth , maxSizeSquare);
                break;
            case UP:
                begin = begin.add(-minSizeSquare, 0, -minSizeSquare);
                end = end.add(maxSizeSquare, -depth, maxSizeSquare);
                break;
            case SOUTH:
                begin = begin.add(-minSizeSquare, -minY, 0);
                end = end.add(maxSizeSquare, maxY, -depth);
                break;
            case NORTH:
                begin = begin.add(-minSizeSquare, -minY, 0);
                end = end.add(maxSizeSquare, maxY, depth);
                break;
            case WEST:
                begin = begin.add(0, -minY, -minSizeSquare);
                end = end.add(depth, maxY, maxSizeSquare);
                break;
            case EAST:
                begin = begin.add(0, -minY, -minSizeSquare);
                end = end.add(-depth, maxY, maxSizeSquare);
                break;
        }


        int xMin = Math.min(begin.getX(), end.getX()),
                xMax = Math.max(begin.getX(), end.getX()),

                yMin = Math.min(begin.getY(), end.getY()),
                yMax = Math.max(begin.getY(), end.getY()),

                zMin = Math.min(begin.getZ(), end.getZ()),
                zMax = Math.max(begin.getZ(), end.getZ());

        for (int x = xMin; x <= xMax; x++)
            for (int y = yMin; y <= yMax; y++)
                for (int z = zMin; z <= zMax; z++){
                    BlockPos temp = new BlockPos(x,y,z);
                    if (!poses.contains(temp) && !worldIn.isAirBlock(temp)){
                        poses.add(temp);
                    }
                }


        BlockPos[] result = new BlockPos[poses.size()];
        result = poses.toArray(result);
        return result;
    }

    @Override
    protected RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids){
        return super.rayTrace(worldIn, playerIn, useLiquids);
    }

    private static ItemStack buildItem(int i){
        ItemStack stack = new ItemStack(new Hammer(Hammers[i].Material, Hammers[i].Size), 1, i);
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("Name", Hammers[i].Name);
        stack.setTagCompound(compound);
        return stack;
    }

    private DamageSource getRandomDamageSource(EntityPlayer player){
        Random r = new Random();
        int i = r.nextInt(31);

        if (i > 30)
            return DamageSource.magic;
        if (i > 15)
            return DamageSource.lightningBolt;

        return DamageSource.causePlayerDamage(player);

    }
    //endregion

    //region IVariantBlock

    public ItemStack[] getItemVarians() {
        ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();

        for (int i = 0; i < Hammers.length; i++){
            ItemStack stack = buildItem(i);
            //((Hammer)stack.getItem()).Number = i;
            subItems.add(stack.copy());
        }
        ItemStack[] result = new ItemStack[subItems.size()];
        result = subItems.toArray(result);
        return result;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void register() {
        ItemStack[] items = getItemVarians();
        int i = 0;
        for (ItemStack stack : items){
            Hammer item = (Hammer) stack.getItem();
            String name = "item.hammer" + item.getMetadata(stack);

            item.setRegistryName(name);
            item.setUnlocalizedName(Hammers[i].Name);

            GameRegistry.registerItem(item);
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(TestMode.Mod_ID + ":" +name));
            i++;
        }
    }


    //endregion

    //region Nested Class
    private static class info{
        public String Name;
        public Size Size;
        public ToolMaterial Material;
        public info(String name, Size size, ToolMaterial material){
            Name = name;
            Size = size;
            Material = material;
        }
    }
    //endregion
}
