package com.nythicalnorm.voxelspaceprogram.block.rocket_parts.entity;

public class EngineProperties {
    private final int blockSize;
    private final double pixelHeight;
    private final double pixelWidth;

    public EngineProperties(Builder builder) {
        this.blockSize = builder.blockSize;
        this.pixelHeight = builder.pixelHeight;
        this.pixelWidth = builder.pixelWidth;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public double getPixelHeight() {
        return pixelHeight;
    }

    public double getPixelWidth() {
        return pixelWidth;
    }

    public static class Builder {
        private int blockSize;
        private double pixelHeight;
        private double pixelWidth;

        public Builder BlockSize(int size) {
            this.blockSize = size;
            return this;
        }

        public Builder PixelHeight(int height) {
            this.pixelHeight = height;
            return this;
        }

        public Builder PixelWidth(int width) {
            this.pixelWidth = width;
            return this;
        }

        public EngineProperties build() {
            return new EngineProperties(this);
        }
    }
}
