package juuxel.adorn.platform.forge.client.renderer

import juuxel.adorn.client.renderer.KitchenSinkRenderer
import juuxel.adorn.platform.forge.block.entity.KitchenSinkBlockEntityForge
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraftforge.client.RenderProperties

class KitchenSinkRendererForge(context: BlockEntityRendererFactory.Context) :
    KitchenSinkRenderer<KitchenSinkBlockEntityForge>(context) {
    override fun getFluidSprite(entity: KitchenSinkBlockEntityForge): Sprite {
        val stack = entity.tank.fluid
        val atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
        return atlas.apply(RenderProperties.get(stack.fluid).getStillTexture(stack))
    }

    override fun getFluidColor(entity: KitchenSinkBlockEntityForge): Int {
        val stack = entity.tank.fluid
        return RenderProperties.get(stack.fluid).getColorTint(stack.fluid.defaultState, entity.world, entity.pos)
    }

    override fun getFluidLevel(entity: KitchenSinkBlockEntityForge): Double =
        entity.tank.fluidAmount.toDouble()

    override fun isEmpty(entity: KitchenSinkBlockEntityForge): Boolean =
        entity.tank.isEmpty
}
