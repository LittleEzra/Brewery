package net.feliscape.hops_and_barrels.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class JuicerScreen extends AbstractContainerScreen<JuicerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Brewery.MOD_ID, "textures/gui/juicer.png");

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    public JuicerScreen(JuicerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 182;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (!menu.canCraft() && menu.hasIngredients())
            renderDeny(guiGraphics, x, y);
        if (menu.isCrafting())
            renderInputBlock(guiGraphics, x, y);
    }

    private void renderDeny(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(TEXTURE, x + 83, y + 62, 176, 54, 10, 10);
    }
    private void renderInputBlock(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(TEXTURE, x + 61, y + 8, 176, 0, 54, 54);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
