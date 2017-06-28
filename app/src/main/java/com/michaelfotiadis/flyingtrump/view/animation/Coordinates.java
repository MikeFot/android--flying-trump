package com.michaelfotiadis.flyingtrump.view.animation;

class Coordinates {
    private static final int OFFSET_X = 120;
    private static final int OFFSET_Y = 120;

    private final int startX;
    private final int midX;
    private final int endX;
    private final int startY;
    private final int midY;
    private final int endY;

    public Coordinates(final Direction direction, final int width, final int height) {

        switch (direction) {

            case N:
                startX = width / 2 - OFFSET_X;
                midX = width / 2 - OFFSET_X;
                endX = width / 2 - OFFSET_X;
                startY = -OFFSET_Y;
                midY = height / 2 - OFFSET_Y;
                endY = height + OFFSET_Y;
                break;
            case NE:
                startX = width + OFFSET_X;
                midX = width / 2 - OFFSET_X;
                endX = -OFFSET_X;
                startY = -OFFSET_Y;
                midY = height / 2 - OFFSET_Y;
                endY = height + OFFSET_Y;
                break;
            case E:
                startX = width + OFFSET_X;
                midX = width / 2 - OFFSET_X;
                endX = -2 * OFFSET_X;
                startY = height / 2 - OFFSET_Y;
                midY = height / 2 - OFFSET_Y;
                endY = height / 2 + OFFSET_Y;
                break;
            case SE:
                startX = width + OFFSET_X;
                midX = width / 2 - OFFSET_X;
                endX = -2 * OFFSET_X;
                startY = height + OFFSET_Y;
                midY = height / 2 - OFFSET_Y;
                endY = -2 * OFFSET_Y;
                break;
            case S:
                startX = width / 2 - OFFSET_X;
                midX = width / 2 - OFFSET_X;
                endX = width / 2 - OFFSET_X;
                startY = height + OFFSET_Y;
                midY = height / 2 - OFFSET_Y;
                endY = -2 * OFFSET_Y;
                break;
            case SW:
                startX = -OFFSET_X;
                midX = width / 2 - OFFSET_X;
                endX = width + OFFSET_X;
                startY = height + OFFSET_Y;
                midY = height / 2 - OFFSET_Y;
                endY = -OFFSET_Y;
                break;
            case W:
                startX = -OFFSET_X;
                midX = width / 2 - OFFSET_X;
                endX = width + OFFSET_X;
                startY = height / 2 - OFFSET_Y;
                midY = height / 2 - OFFSET_Y;
                endY = height / 2 - OFFSET_Y;
                break;
            case NW:
                startX = -OFFSET_X;
                midX = width / 2 - OFFSET_X;
                endX = width + OFFSET_X;
                startY = -OFFSET_Y;
                midY = height / 2 - OFFSET_Y;
                endY = height + OFFSET_Y;
                break;
            default:
                startX = -OFFSET_X;
                midX = width / 2 - OFFSET_X;
                endX = width + OFFSET_X;
                startY = -OFFSET_Y;
                midY = height / 2 - OFFSET_Y;
                endY = height + OFFSET_Y;
        }

    }

    public int getStartX() {
        return startX;
    }

    public int getMidX() {
        return midX;
    }

    public int getEndX() {
        return endX;
    }

    public int getStartY() {
        return startY;
    }

    public int getMidY() {
        return midY;
    }

    public int getEndY() {
        return endY;
    }
}