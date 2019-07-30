package juuxel.adorn.gui.painter

import io.github.cottonmc.cotton.gui.client.BackgroundPainter
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import juuxel.adorn.Adorn
import juuxel.adorn.resources.ColorManager
import juuxel.adorn.util.Colors
import net.minecraft.util.Identifier

object Painters {
    private val SLOT_BG = Adorn.id("textures/gui/slot.png")

    /**
     * A background painter that paints LibGui-style slots.
     */
    val LIBGUI_STYLE_SLOT: BackgroundPainter = BackgroundPainter { left, top, panel ->
        for (x in 0 until panel.width step 18) {
            for (y in 0 until panel.height step 18) {
                val lo = ScreenDrawing.colorAtOpacity(0x000000, 0.2f)
                val bg = ScreenDrawing.colorAtOpacity(0x000000, 0.2f / 2.4f)
                val hi = ScreenDrawing.colorAtOpacity(0xFFFFFF, 0.2f)

                if (panel is WItemSlot && panel.isBigSlot) {
                    ScreenDrawing.drawBeveledPanel(
                        x + left - 4, y + top - 4, 24, 24,
                        lo, bg, hi
                    )
                } else {
                    ScreenDrawing.drawBeveledPanel(
                        x + left - 1, y + top - 1, 18, 18,
                        lo, bg, hi
                    )
                }
            }
        }
    }

    val TEXTURED_SLOT: BackgroundPainter = BackgroundPainter { left, top, panel ->
        for (x in 0 until panel.width step 18)
            for (y in 0 until panel.height step 18)
                ScreenDrawing.rect(SLOT_BG, left - 1 + x, top - 1 + y, 18, 18, Colors.WHITE)
    }

    fun palette(palette: Identifier, key: Identifier): BackgroundPainter =
        PaletteBackgroundPainter(ColorManager.getColors(palette), key)
}