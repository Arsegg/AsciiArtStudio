package com.arsegg.asciiartstudio.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public final class ASCIIFactory {
    public static Bitmap resize(Bitmap bitmap, int newWidth) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final int newHeight = (int) ((double) height * newWidth / width);
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
    }

    public static Bitmap toGrayscale(Bitmap bitmap) {
        Bitmap newBitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(newBitmap, 0, 0, paint);
        return newBitmap;
    }

    public static char toASCII(int color) {
        if (color < 50) {
            return '@';
        } else if (color < 70) {
            return '#';
        } else if (color < 100) {
            return '8';
        } else if (color < 130) {
            return '&';
        } else if (color < 160) {
            return 'o';
        } else if (color < 180) {
            return ':';
        } else if (color < 200) {
            return '*';
        } else if (color < 230) {
            return '.';
        } else {
            return ' ';
        }
    }

    public static String toASCII(Bitmap bitmap) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        StringBuilder ans = new StringBuilder();

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                ans.append(toASCII(Color.red(bitmap.getPixel(i, j))));
            }
            ans.append('\n');
        }
        return ans.toString();
    }

    private ASCIIFactory() {
    }
}
