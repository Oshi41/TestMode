package TestMode;

import TestMode.Initialize.Proxies.ProxyBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

/**
 * Created by User on 16.06.2017.
 */
@Mod(modid = "testmode")
public class TestMode {

    //region Fields
    public static final String Mod_ID = "TM";

    @Mod.Instance(TestMode.Mod_ID)
    public static TestMode instance;

    @SidedProxy(clientSide = "TestMode.Initialize.Proxies.ClientProxy", serverSide = "TestMode.Initialize.Proxies.ServerProxy")
    public static ProxyBase proxy;

    //region Creative Tabs
    public static final CreativeTabs toolTab = new CreativeTabs("Tools") {
        @Override
        public Item getTabIconItem() {
            return Items.IRON_PICKAXE;
        }
    };
    public static final CreativeTabs weaponTab = new CreativeTabs("Weapons") {
        @Override
        public Item getTabIconItem() {
            return Items.IRON_SWORD;
        }
    };
    public static final CreativeTabs stuffTab = new CreativeTabs("Miscellaneous") {
        @Override
        public Item getTabIconItem() {
            return Items.END_CRYSTAL;
        }
    };

    //endregion

    //region Loading Events

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        proxy.onServerStarting(event);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.onPreInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.onInit(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.onPostInit(event);
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        proxy.onServerStopping(event);
    }

    //endregion
}
