package ganymedes01.ganyssurface.client.renderer.item.vanilla;

import ganymedes01.ganyssurface.GlStateManager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class ItemMinecartRenderer implements IItemRenderer {

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/minecart.png");
	private final ModelBase model = new ModelMinecart();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		Object renderer = data[0];
		if (renderer instanceof RenderBlocks)
			switch (type) {
				case ENTITY:
					GlStateManager.scale(0.5, 0.5, 0.5);
					render(stack, 0.0F, -0.25F, 0.0F, (RenderBlocks) renderer);
					break;
				case EQUIPPED:
					render(stack, 0.5F, 0.75F, 0.5F, (RenderBlocks) renderer);
					break;
				case EQUIPPED_FIRST_PERSON:
					render(stack, -0.5F, 1.0F, 0.7F, (RenderBlocks) renderer);
					break;
				case INVENTORY:
					GlStateManager.scale(1.2, 1.2, 1.2);
					render(stack, -0.5F, -0.45F, -0.5F, (RenderBlocks) renderer);
					break;
				default:
					break;
			}
	}

	private void render(ItemStack stack, float x, float y, float z, RenderBlocks renderer) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		renderBlock(renderer);

		GlStateManager.rotate(90, 0, 1, 0);
		GlStateManager.scale(0.75, 0.75, 0.75);
		GlStateManager.scale(-1, -1, 1);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE);

		model.render(null, 0, 0, -0.1F, 0, 0, 1F / 16F);

		GlStateManager.popMatrix();
	}

	protected void renderBlock(RenderBlocks renderer) {

	}
}