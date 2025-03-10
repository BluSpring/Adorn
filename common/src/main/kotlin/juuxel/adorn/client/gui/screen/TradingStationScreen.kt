package juuxel.adorn.client.gui.screen

import com.mojang.blaze3d.systems.RenderSystem
import juuxel.adorn.AdornCommon
import juuxel.adorn.client.ClientNetworkBridge
import juuxel.adorn.menu.TradingStationMenu
import juuxel.adorn.util.Colors
import juuxel.adorn.util.logger
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.menu.Slot
import net.minecraft.text.Text

class TradingStationScreen(
    menu: TradingStationMenu,
    playerInventory: PlayerInventory,
    title: Text
) : AdornMenuScreen<TradingStationMenu>(menu, playerInventory, title) {
    init {
        backgroundHeight = 186
        playerInventoryTitleY = backgroundHeight - 94 // copied from MenuScreen.<init>
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE)
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun drawForeground(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        textRenderer.draw(matrices, title, titleX.toFloat(), titleY.toFloat(), Colors.WHITE)
        textRenderer.draw(matrices, playerInventoryTitle, playerInventoryTitleX.toFloat(), playerInventoryTitleY.toFloat(), Colors.WHITE)
        textRenderer.draw(matrices, SELLING_LABEL, 26f + 9f - textRenderer.getWidth(SELLING_LABEL) / 2, 25f, Colors.WHITE)
        textRenderer.draw(matrices, PRICE_LABEL, 26f + 9f - textRenderer.getWidth(PRICE_LABEL) / 2, 61f, Colors.WHITE)
    }

    /**
     * Updates the trade selling/price [stack] in the specified [slot].
     * This function is mostly meant for item viewer drag-and-drop interactions.
     */
    fun updateTradeStack(slot: Slot, stack: ItemStack) {
        if (!TradingStationMenu.isValidItem(stack)) {
            LOGGER.error("Trying to set invalid item {} for slot {} in trading station", stack, slot)
            return
        }

        slot.stack = stack
        ClientNetworkBridge.get().sendSetTradeStack(menu.syncId, slot.id, stack)
    }

    companion object {
        private val LOGGER = logger()
        private val BACKGROUND_TEXTURE = AdornCommon.id("textures/gui/trading_station.png")
        private val SELLING_LABEL: Text = Text.translatable("block.adorn.trading_station.selling")
        private val PRICE_LABEL: Text = Text.translatable("block.adorn.trading_station.price")
    }
}
