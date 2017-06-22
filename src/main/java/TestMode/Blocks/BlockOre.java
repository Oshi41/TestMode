package TestMode.Blocks;

import TestMode.Initialize.ItemsInit;
import TestMode.TestMode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by User on 19.06.2017.
 */
public class BlockOre extends Block {

    private final OreType currentType;

    public static BlockOre instance(){
        return new BlockOre(OreType.KNOWLEDGE);
    }

    public BlockOre(OreType type) {
        super(Material.ROCK);
        currentType = type;
        setCreativeTab(TestMode.stuffTab);
        setHarvestLevel("pickaxe", type.harvestLevel);
        setHardness(type.hardness);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return this.currentType.getMetadata();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {


        if (currentType == OreType.COSMIC)
            worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)pos.getX() + 0.5D, (double)pos.getY() + 2.0D, (double)pos.getZ() + 0.5D, (double)((float) rand.nextFloat()) - 0.5D, (double)((float) rand.nextFloat() - 1.0F), (double)((float) rand.nextFloat()) - 0.5D, new int[0]);
    }

    @Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return super.getItemDropped(state, rand, fortune);
    }

    public static enum OreType{
        FORGOTTEN("oreForgotten",  2, 10, 0, new Ess),
        KNOWLEDGE("oreKnowledge", 3, 11, 1),
        WISDOM("oreWisdom", 3, 12, 2),
        COSMIC("oreCosmic", 4, 7, 3);

        private final String name;
        private final int harvestLevel;
        private final int metadata;
        private final float hardness;

        private OreType(String name, int harvestLevel, float hardness, int metadata, Item drop){
            this.name = name;
            this.harvestLevel = harvestLevel;
            this.hardness = hardness;
            this.metadata = metadata;
        }

        public int getMetadata() {
            return metadata;
        }
    }
}
