package com.nythicalnorm.nythicalSpaceProgram.planettexgen.handlers;

import com.nythicalnorm.nythicalSpaceProgram.NythicalSpaceProgram;
import com.nythicalnorm.nythicalSpaceProgram.planettexgen.PlanetGradient;
import com.nythicalnorm.nythicalSpaceProgram.planettexgen.PlanetMapGen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public class WholePlanetTexGenTask implements Supplier<byte[]> {
    private final Path planetDir;
    private final String planetName;
    private final long seed;
    private final PlanetGradient gradient;

    public WholePlanetTexGenTask(Path planetDir, String planetName, long seed, PlanetGradient gradient) {
        this.planetDir = planetDir;
        this.planetName = planetName;
        this.seed = seed;
        this.gradient = gradient;
    }

    public static byte[] convertBufferedImageToPngBytes(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            boolean success = ImageIO.write(image, "png", outputStream);

            if (!success) {
                NythicalSpaceProgram.logError("There is no png writer in this environment, Well then this mod won't work now will it?");
                return null;
            }

            return outputStream.toByteArray();

        } catch (IOException e) {
            NythicalSpaceProgram.logError("Error writing planet texture to buffer");
            throw e;
        }
    }

    @Override
    public byte[] get() {
        Path planetTexPath = planetDir.resolve(planetName + ".png");
        File planetTexFileLocation = new File(planetTexPath.toUri());

        if (!planetTexFileLocation.exists()) {
            BufferedImage planetMap = PlanetMapGen.GenerateMap(seed, gradient);

            try (FileOutputStream fileWriter = new FileOutputStream(planetTexFileLocation)) {
                byte[] imageBytes = convertBufferedImageToPngBytes(planetMap);
                fileWriter.write(imageBytes);
                return imageBytes;
            } catch (IOException e) {
                NythicalSpaceProgram.logError("Can't write " + planetName + " planet's Textures to file");
            }
        } else {
            try {
                return Files.readAllBytes(planetTexPath);
            } catch (IOException e) {
                NythicalSpaceProgram.logError("Can't load " + planetName + " planet's Textures");
            }
        }
        return null;
    }
}
