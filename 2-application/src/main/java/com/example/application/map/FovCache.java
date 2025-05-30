package com.example.application.map;

public class FovCache {

    private final boolean[] isVisible;
    private final boolean[] isExplored;

    private final int width;
    private final int height;

    private boolean fovEnabled = true;

    public FovCache(int width, int height) {
        this.width = width;
        this.height = height;

        this.isVisible = new boolean[width * height];
        this.isExplored = new boolean[width * height];

        initialise();
    }

    public void clearVisibleCache() {
        int len = isVisible.length;

        if (len > 0){
            isVisible[0] = false;
        }

        //Value of i will be [1, 2, 4, 8, 16, 32, ..., len]
        for (int i = 1; i < len; i += i) {
            System.arraycopy(isVisible, 0, isVisible, i, Math.min((len - i), i));
        }
    }

    private void initialise() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                initialiseFovData(x, y);
            }
        }
    }

    private boolean isInvalidPosition(int x, int y) {
        return (x < 0) || (y < 0) || (x >= width) || (y >= height);
    }

    public void initialiseFovData(int x, int y) {
        if(isInvalidPosition(x, y)) {
            System.out.printf("Unable to initialise FOV for x=%d, y=%d because it is outside of FOV cache map: width=%d, height=%d", x, y, width, height);
            return;
        }

        isVisible[x + y * width] = false;
    }

    public void updateFovData(int x, int y, boolean isVisible) {
        if(isInvalidPosition(x, y)) {
            System.out.printf("Unable to initialise FOV for x=%d, y=%d because it is outside of FOV cache map: width=%d, height=%d", x, y, width, height);
            return;
        }

        this.isVisible[x + y * width] = isVisible;
        this.isExplored[x + y * width] = true;
    }

    public boolean isInFov(int x, int y) {
        if(isInvalidPosition(x, y)) {
            System.out.printf("Unable to determine FOV for x=%d, y=%d because it is outside of FOV cache map: width=%d, height=%d", x, y, width, height);
            return false;
        }

        if(!fovEnabled) return true;

        return isVisible[x + y * width];
    }

    public boolean isExplored(int x, int y) {
        if(isInvalidPosition(x, y)) {
            System.out.printf("Unable to determine IsExplored for x=%d, y=%d because it is outside of IsExplored cache map: width=%d, height=%d", x, y, width, height);
            return false;
        }

        if(!fovEnabled) return true;

        return isExplored[x + y * width];
    }
}
