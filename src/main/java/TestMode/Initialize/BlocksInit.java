package TestMode.Initialize;

import TestMode.Blocks.BlockOre;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by User on 16.06.2017.
 */
public class BlocksInit {

    public static void register(){

        //GameRegistry.registerBlock(new PersonalMechanismBase(), "Personal Machine");

        BlockOre ore = (BlockOre) BlockOre.instance().setRegistryName("ore");
        Item item = new ItemBlock(ore).setRegistryName("ore");

        GameRegistry.register(ore);
        GameRegistry.register(item);

        ModelResourceLocation[] locations = new ModelResourceLocation[ore.ores.length];
        //ItemBlock item = new ItemBlock(ore);
        for (int i = 0; i < ore.ores.length; i++){
            locations[i] = new ModelResourceLocation(i + "");
        }
        ModelBakery.registerItemVariants(item, locations);
    }
}
