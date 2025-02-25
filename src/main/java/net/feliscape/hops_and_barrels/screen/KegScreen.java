package net.feliscape.hops_and_barrels.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class KegScreen extends AbstractContainerScreen<KegMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Brewery.MOD_ID, "textures/gui/keg.png");
    private static final String WATER_TOOLTIP_KEY = "container.hops_and_barrels.keg.water_tooltip";

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    public KegScreen(KegMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgress(guiGraphics, x, y);
        renderWater(guiGraphics, x, y);
    }

    private void renderProgress(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            int scaledProgress = menu.getScaledProgress();
            guiGraphics.blit(TEXTURE, x + 61, y + 19 + 45 - scaledProgress, 176, 45 - scaledProgress, 13, scaledProgress);
        }
    }
    private void renderWater(GuiGraphics guiGraphics, int x, int y) {
        int scaledWater = menu.getScaledWater();
        guiGraphics.blit(TEXTURE, x + 12, y + 11 + 60 - scaledWater, 189, 60 - scaledWater, 13, scaledWater);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
        renderCustomTooltips(guiGraphics, mouseX, mouseY);
    }

    private void renderCustomTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        if (this.isHovering(12, 11, 4, 60, (double)pMouseX, (double)pMouseY)){
            pGuiGraphics.renderTooltip(this.font, this.font.split(Component.translatable(WATER_TOOLTIP_KEY, this.menu.getWater()), 115), pMouseX, pMouseY);
        }
    }
}
