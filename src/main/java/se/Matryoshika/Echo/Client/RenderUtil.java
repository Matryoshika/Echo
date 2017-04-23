package se.Matryoshika.Echo.Client;

import java.util.List;

import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class RenderUtil {

	public static void renderModelTESR(List<BakedQuad> quads, VertexBuffer renderer, int brightness) {
		int l1 = (brightness >> 0x10) & 0xFFFF;
		int l2 = brightness & 0xFFFF;
		for (BakedQuad quad : quads) {
			int[] vData = quad.getVertexData();
			VertexFormat format = quad.getFormat();
			int size = format.getIntegerSize();
			int uv = format.getUvOffsetById(0) / 4;
			for (int i = 0; i < 4; ++i) {
				renderer.pos(Float.intBitsToFloat(vData[size * i]), Float.intBitsToFloat(vData[size * i + 1]),
						Float.intBitsToFloat(vData[size * i + 2])).color(255, 255, 255, 255)
						.tex(Float.intBitsToFloat(vData[size * i + uv]), Float.intBitsToFloat(vData[size * i + uv + 1]))
						.lightmap(l1, l2).endVertex();
			}

		}
	}

}
