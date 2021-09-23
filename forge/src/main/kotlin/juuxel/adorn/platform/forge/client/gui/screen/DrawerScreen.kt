package juuxel.adorn.platform.forge.client.gui.screen

import juuxel.adorn.AdornCommon
import juuxel.adorn.platform.forge.menu.DrawerMenu
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class DrawerScreen(
    menu: DrawerMenu,
    playerInventory: PlayerInventory,
    title: Text
) : AdornMenuScreen<DrawerMenu>(menu, playerInventory, title) {
    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        client!!.textureManager.bindTexture(BACKGROUND_TEXTURE)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    companion object {
        private val BACKGROUND_TEXTURE = AdornCommon.id("textures/gui/drawer.png")
    }
}