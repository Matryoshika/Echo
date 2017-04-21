package se.Matryoshika.Echo.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.ResourceLocation;
import se.Matryoshika.Echo.Echo;

public class ModelCreator {

	private Map<Integer, String> type = Maps.newHashMap();

	public ModelCreator(String name, int tier, ResourceLocation tex) {
		type.put(1, "default_block_1");
		type.put(2, "default_block_2");
		createBlockState(name+tier, tier);
		createObjModel(name+tier, tier);
		createMTL(name+tier, tier, tex);
	}

	private void createBlockState(String name, int tier) {
		File file = new File(Echo.getDirectory() + "/src/main/resources/assets/echo/blockstates/" + type.get(tier) + ".json");
		File destination = new File(Echo.getDirectory() + "/src/main/resources/assets/echo/blockstates/" + name + ".json");
		copyFile(file, destination);

		Path path = Paths.get(Echo.getDirectory() + "/src/main/resources/assets/echo/blockstates/" + name + ".json");
		Charset charset = StandardCharsets.UTF_8;

		try {
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll(type.get(tier), name);
			Files.write(path, content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void copyFile(File source, File dest) {
		
		if(!source.exists())
			try {
				source.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/*
		if (!dest.exists()) {
			try {
				dest.createNewFile();

				FileChannel sourceC = null;
				FileChannel destC = null;

				try {
					sourceC = new RandomAccessFile(source, "rw").getChannel();
					destC = new RandomAccessFile(dest, "rw").getChannel();

					long pos = 0;
					long count = sourceC.size();

					sourceC.transferTo(pos, count, destC);
				} finally {
					if (sourceC != null)
						sourceC.close();
					if (destC != null)
						destC.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
	}

	private void createObjModel(String name, int tier) {
		File file = new File(Echo.getDirectory() + "/src/main/resources/assets/echo/models/block/" + type.get(tier) + ".obj");
		File destination = new File(
				Echo.getDirectory() + "/src/main/resources/assets/echo/models/block/" + name + ".obj");
		copyFile(file, destination);

		Path path = Paths.get(Echo.getDirectory() + "/src/main/resources/assets/echo/models/block/" + name + ".obj");
		Charset charset = StandardCharsets.UTF_8;

		try {
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll("mtllib " + type.get(tier) + ".mtl", "mtllib " + name + ".mtl");
			Files.write(path, content.getBytes(charset));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createMTL(String name, int tier, ResourceLocation tex) {
		File file = new File(
				Echo.getDirectory() + "/src/main/resources/assets/echo/models/block/" + type.get(tier) + ".mtl");
		File destination = new File(
				Echo.getDirectory() + "/src/main/resources/assets/echo/models/block/" + name + ".mtl");
		copyFile(file, destination);

		Path path = Paths.get(Echo.getDirectory() + "/src/main/resources/assets/echo/models/block/" + name + ".mtl");
		Charset charset = StandardCharsets.UTF_8;

		try {
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll("map_Kd minecraft:blocks/bedrock", "map_Kd " + tex.toString());
			Files.write(path, content.getBytes(charset));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
