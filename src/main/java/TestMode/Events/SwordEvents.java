package TestMode.Events;

import TestMode.Items.Tools.MeeleWeapon.AngelicSword;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by User on 18.06.2017.
 */
public class SwordEvents {

//    @SubscribeEvent
//    public void LeftClickEmpty(EntityPlayer player, ItemStack stack){
//        Item item = stack.getItem();
//
//        if (item instanceof AngelicSword){
//            ((AngelicSword) item).findEntities(player);
//        }
//    }

    @SubscribeEvent
    public void onLeftClickInteract(PlayerInteractEvent.LeftClickEmpty event) {

        Item item = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem();
//
        if (item instanceof AngelicSword) {
            ((AngelicSword) item).findEntities(event.getEntityPlayer());
        }

//        //region Left Hand
//        EntityPlayer player = event.getEntityPlayer();
//        Item holdingItem = player.getHeldItem(EnumHand.MAIN_HAND).getItem();
//
//        if (holdingItem instanceof AngelicSword) {
//            ((AngelicSword) holdingItem).findEntities(player);
//        }
//        //endregion


    }


}
