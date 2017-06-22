package TestMode.Initialize.Proxies;

import TestMode.Events.SwordEvents;
import TestMode.Initialize.BlocksInit;
import TestMode.Initialize.ItemsInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;

/**
 * Created by User on 16.06.2017.
 */
public class ClientProxy extends ProxyBase {

    @Override
    public void onServerStarting(FMLServerStartingEvent event) {
        super.onServerStarting(event);
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
        MinecraftForge.EVENT_BUS.register(new SwordEvents());


        BlocksInit.register();
        ItemsInit.register();
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);

    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);
    }

    @Override
    public void onServerStopping(FMLServerStoppingEvent event) {
        super.onServerStopping(event);
    }
}
