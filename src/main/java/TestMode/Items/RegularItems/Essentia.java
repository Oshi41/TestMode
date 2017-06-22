package TestMode.Items.RegularItems;

import TestMode.Items.IVariantBlock;
import TestMode.TestMode;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by User on 19.06.2017.
 */
public class Essentia extends Item implements IVariantBlock {

    public static Essentia instance(){
        return new Essentia();
    }
    public static String[] essenties = new String[]{
       "Forgotten", "Knowledge", "Wisdom"
    };

    public Essentia(){
        setMaxDamage(0);
        setCreativeTab(TestMode.stuffTab);
    }

    //region IVariantBlock
    @Override
    public ItemStack[] getItemVarians() {
        ItemStack[] stacks = new ItemStack[essenties.length];
        for (int i = 0; i < essenties.length; i++){
            stacks[i] = new ItemStack(new Essentia(), 1, i);
        }
        return stacks;
    }

    @Override
    public void register() {
        ItemStack[] stacks = getItemVarians();
        for (int i = 0; i < stacks.length; i++){
            Item item = stacks[i].getItem();
            String name = "item.essense" + i;

            item.setRegistryName(name);
            item.setUnlocalizedName(essenties[i]);

            GameRegistry.registerItem(item);
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(TestMode.Mod_ID + ":" +
                    name));
        }
    }
    //endregion
}
