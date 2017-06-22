package TestMode.Initialize;

import TestMode.Items.RegularItems.Essentia;
import TestMode.Items.Tools.Hammer;
import TestMode.Items.Tools.MeeleWeapon.AngelicSword;
import TestMode.Items.Tools.MultiTool;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by User on 17.06.2017.
 */
public class ItemsInit {
    public final static String[] Maces = {
        "Thorn Mace", "Dragon Mace"
    };
    public final static String[] Swprds = {
        "Oblivion SwordBase", "Angelic SwordBase", "Evil SwordBase"
    };



    public static Hammer hammer = Hammer.instance();
    public static MultiTool multiTool = MultiTool.instance();
    public static Essentia essentia = Essentia.instance();
    public static AngelicSword angelicSword = new AngelicSword();


    public static void register(){
        hammer.register();
        multiTool.register();
        essentia.register();

        String name = "item.sword";
        GameRegistry.registerItem(angelicSword, name);
//        ModelLoader.setCustomModelResourceLocation(angelicSword, 0, new ModelResourceLocation(TestMode.Mod_ID + ":" +name));
    }
}
