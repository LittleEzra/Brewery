package net.feliscape.hops_and_barrels.registry;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.client.screen.JuicerMenu;
import net.feliscape.hops_and_barrels.client.screen.KegMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Brewery.MOD_ID);

    public static final RegistryObject<MenuType<KegMenu>> KEG_MENU =
            registerMenuType("keg_menu", KegMenu::new);
    public static final RegistryObject<MenuType<JuicerMenu>> JUICER_MENU =
            registerMenuType("juicer_menu", JuicerMenu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
