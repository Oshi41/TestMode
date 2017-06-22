package TestMode.Items.Tools.MeeleWeapon;

import TestMode.Items.Tools.RangeSword;
import TestMode.TestMode;
import TestMode.Utils.ToolEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by User on 18.06.2017.
 */
public class AngelicSword extends RangeSword {

    private long last;
    public AngelicSword() {
        super(ToolMaterial.DIAMOND, 10);
        setCreativeTab(TestMode.weaponTab);
        setUnlocalizedName("AngelicSword");
    }

    @Override
    public boolean hasSpecialEffect() {
        return true;
    }

    @Override
    public void specialEffect(ItemStack stack, EntityPlayer player, Entity entity) {
        if (System.currentTimeMillis() - last > 2000) {
            last = System.currentTimeMillis();
            ToolEffects.spawnLightnings(stack, player, entity);
        }
    }
}
