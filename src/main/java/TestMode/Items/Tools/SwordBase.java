package TestMode.Items.Tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by User on 18.06.2017.
 */
public class SwordBase extends ItemSword {


    public SwordBase(ToolMaterial material) {
        super(material);
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (hasSpecialEffect()) specialEffect( stack, player,  entity);

        return super.onLeftClickEntity(stack, player, entity);
    }

//    @Override
//    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
//
//
//        return super.hitEntity(std


    public boolean hasSpecialEffect() {return  false;}
    public void specialEffect(ItemStack stack, EntityPlayer player, Entity entity){ }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        //super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add("Damage " + getDamageVsEntity());
        if (stack.getMaxDamage() < 0)
            tooltip.add("Infinity uses");
        else
            tooltip.add(stack.getMaxDamage() + " uses");
    }
}
