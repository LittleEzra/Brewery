package net.feliscape.hops_and_barrels.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.registry.ModMobEffects;
import net.feliscape.hops_and_barrels.util.ColorUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SiphonHudOverlay {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Brewery.MOD_ID, "textures/gui/hud.png");

    public static final IGuiOverlay HUD_SIPHON = ((gui, guiGraphics, partialTick, width, height) -> {
        int x = width / 2;
        int y = height / 2;

        if (gui.getMinecraft().player.hasEffect(ModMobEffects.SIPHON.get())){
            int siphonedHearts = ClientSiphonData.getSiphonedHearts();
            RenderSystem.enableBlend();

            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, TEXTURE);

            RenderSystem.defaultBlendFunc();

            guiGraphics.blit(TEXTURE, x + 5, y - 4, 0, 0, 9, 9);
            guiGraphics.drawString(gui.getFont(), Integer.toString(siphonedHearts), x + 16, y - 3, ColorUtil.getIntColor("#ffffff"));
        }
    });
}
