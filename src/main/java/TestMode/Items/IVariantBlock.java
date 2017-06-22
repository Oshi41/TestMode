package TestMode.Items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by User on 17.06.2017.
 */
public interface IVariantBlock {

    ItemStack[] getItemVarians();

    @SideOnly(Side.CLIENT)
    void register();
}
