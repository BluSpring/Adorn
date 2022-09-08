package juuxel.adorn.compat.rei.client

import juuxel.adorn.client.ClientNetworkBridge
import juuxel.adorn.client.gui.screen.TradingStationScreen
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.drag.DraggableStack
import me.shedaniel.rei.api.client.gui.drag.DraggableStackVisitor
import me.shedaniel.rei.api.client.gui.drag.DraggableStackVisitor.BoundsProvider
import me.shedaniel.rei.api.client.gui.drag.DraggedAcceptorResult
import me.shedaniel.rei.api.client.gui.drag.DraggingContext
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes
import net.minecraft.client.gui.screen.Screen
import net.minecraft.menu.Slot
import java.util.stream.Stream
import kotlin.streams.asStream

object TradingStationDraggableStackVisitor : DraggableStackVisitor<TradingStationScreen> {
    private fun slots(context: DraggingContext<TradingStationScreen>): Sequence<Pair<Slot, Rectangle>> {
        val menu = context.screen.menu
        return sequenceOf(menu.sellingSlot, menu.priceSlot)
            .map { it to Rectangle(it.x, it.y, 16, 16) }
            .onEach { (_, rect) -> rect.translate(context.screen.panelX, context.screen.panelY) }
    }

    override fun <R : Screen?> isHandingScreen(screen: R & Any): Boolean =
        screen is TradingStationScreen

    override fun acceptDraggedStack(context: DraggingContext<TradingStationScreen>, stack: DraggableStack): DraggedAcceptorResult {
        val pos = context.currentPosition ?: return DraggedAcceptorResult.PASS
        val slot = slots(context).find { (_, rect) -> rect.contains(pos) }?.first

        if (slot != null) {
            val entryStack = stack.stack
            if (entryStack.type == VanillaEntryTypes.ITEM) {
                slot.stack = entryStack.castValue()
                val menu = context.screen.menu
                ClientNetworkBridge.get().sendSetTradeStack(menu.syncId, slot.id, slot.stack)
                return DraggedAcceptorResult.CONSUMED
            }
        }

        return DraggedAcceptorResult.PASS
    }

    override fun getDraggableAcceptingBounds(context: DraggingContext<TradingStationScreen>, stack: DraggableStack): Stream<BoundsProvider> {
        if (stack.stack.type != VanillaEntryTypes.ITEM) return Stream.empty()
        return slots(context)
            .map { (_, rect) -> rect }
            .map(BoundsProvider::ofRectangle)
            .asStream()
    }
}