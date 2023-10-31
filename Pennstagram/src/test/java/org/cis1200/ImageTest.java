package org.cis1200;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This file tests the various image manipulations on the default
 * image (a view of Positano, Italy).
 *
 * Note though that due to the imprecise nature of floating point
 * arithmetic, your code may be correct yet not pass these tests. (We
 * won't be using these tests to grade your assignment! Our tests take
 * this imprecision into account.) Instead, these tests give you an
 * idea of how close your answers are to *our* solution. Just looking
 * at the output of your code in the GUI may not be enough to see the
 * differences.
 *
 * This file also gives you a place to add your own JUnit test cases.
 * Do *not* add them to ManipulateTest.java as you will not be
 * submitting that file.
 */

public class ImageTest {

    /*
     * IMPORTANT: Make sure that this points to the correct directory before
     * running the tests! You will need to download and extract the provided
     * images from the homework instructions for this to work. (And don't forget
     * that the string should end with a '/' character!)
     */
    static final String LOCATION = "images/";

    static final PixelPicture ITALY = new PixelPicture(LOCATION + "Italy.png");

    @Test
    public void testRotateCW() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyCW.png"),
                        SimpleManipulations.rotateCW(ITALY)
                ),
                "Rotate CW"
        );
    }

    @Test
    public void testRotateCWfullcycle() {
        PixelPicture pp = new PixelPicture(LOCATION + "Italy.png");
        PixelPicture r1 = SimpleManipulations.rotateCW(SimpleManipulations.rotateCW(ITALY));
        PixelPicture r2 = SimpleManipulations.rotateCW(SimpleManipulations.rotateCW(r1));
        assertEquals(
                0,
                PixelPicture.diff(pp,r2),
                "Rotate CW full cycle"
        );
    }

    @Test
    public void testRotateCWEmpyImage() {
        // Test rotateCW with an empty image
        PixelPicture emptyPic = new PixelPicture(new Pixel[0][0]);
        PixelPicture result = SimpleManipulations.rotateCCW(emptyPic);
        assertEquals(0, result.getWidth(), "Width after Rotate CCW with Empty Image");
        assertEquals(0, result.getHeight(), "Height after Rotate CCW with Empty Image");
    }

    @Test
    public void testRotateCCW() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyCCW.png"),
                        SimpleManipulations.rotateCCW(ITALY)
                ),
                "Rotate CCW"
        );
    }

    @Test
    public void testRotateCCWfullcycle() {
        PixelPicture pp = new PixelPicture(LOCATION + "Italy.png");
        PixelPicture r1 = SimpleManipulations.rotateCCW(SimpleManipulations.rotateCCW(ITALY));
        PixelPicture r2 = SimpleManipulations.rotateCCW(SimpleManipulations.rotateCCW(r1));
        assertEquals(
                0,
                PixelPicture.diff(pp,r2),
                "Rotate CCW full cycle"
        );
    }

    @Test
    public void testRotateCWThenCCW() {
        // Assuming ITALY is a standard test image
        PixelPicture original = ITALY;

        // Rotate the image clockwise and then counter-clockwise
        PixelPicture rotated = SimpleManipulations.rotateCW(original);
        rotated = SimpleManipulations.rotateCCW(rotated);

        // Compare the rotated image with the original image
        assertEquals(
                0,
                PixelPicture.diff(original, rotated),
                "Image should be same as original after rotating CW then CCW"
        );
    }


    @Test
    public void testBorder() {
        PixelPicture pp = new PixelPicture(LOCATION + "ItalyBorder.png");
        PixelPicture result = SimpleManipulations.border(ITALY, 10, Pixel.BLACK);
        assertEquals(
                0,
                PixelPicture.diff(pp, result),
                "Border"
        );
    }

    @Test
    public void testBorderZeroWidth() {
        // Test border with zero width
        PixelPicture result = SimpleManipulations.border(ITALY, 0, Pixel.BLACK);
        assertEquals(
                0,
                PixelPicture.diff(ITALY, result),
                "Border with Zero Width"
        );
    }

    @Test
    public void testBorderNegativeWidth() {
        // Test border with negative width (should handle gracefully)
        assertEquals(
                0,
                PixelPicture.diff(
                        ITALY,
                        SimpleManipulations.border(ITALY, -5, Pixel.BLACK)
                ),
                "Border with Negative Width"
        );
    }


    @Test
    public void testColorInvert() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyColorInvert.png"),
                        SimpleManipulations.invertColors(ITALY)
                ),
                "ColorInversion"
        );
    }

    @Test
    public void testInvertColorsOnEmptyImage() {
        // Test invertColors on an empty image
        PixelPicture emptyPic = new PixelPicture(new Pixel[0][0]);
        PixelPicture result = SimpleManipulations.invertColors(emptyPic);
        assertEquals(0, result.getWidth(), "Width after Rotate CCW with Empty Image");
        assertEquals(0, result.getHeight(), "Height after Rotate CCW with Empty Image");
    }

    @Test
    public void testDoubleInvertColors() {
        PixelPicture invertedOnce = SimpleManipulations.invertColors(ITALY);
        PixelPicture invertedTwice = SimpleManipulations.invertColors(invertedOnce);
        assertEquals(0, PixelPicture.diff(ITALY, invertedTwice), "Double Invert Colors");
    }

    @Test
    public void testGrayScaleAverage() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyGrayScaleAverage.png"),
                        SimpleManipulations.grayScaleAverage(ITALY)
                ),
                "Gray Scale Average"
        );
    }

    @Test
    public void testGrayScaleOnGrayScale() {
        PixelPicture grayScalePic = SimpleManipulations.grayScaleAverage(ITALY);
        PixelPicture result = SimpleManipulations.grayScaleAverage(grayScalePic);
        assertEquals(0, PixelPicture.diff(grayScalePic, result), "Gray Scale on Gray Scale Image");
    }


    @Test
    public void testColorScale() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyRedTint.png"),
                        SimpleManipulations.scaleColors(ITALY, 1.0, 0.5, 0.5)
                ),
                "Color Scale"
        );
    }

    @Test
    public void testColorScaleNoChange() {
        PixelPicture result = SimpleManipulations.scaleColors(ITALY, 1.0, 1.0, 1.0);
        assertEquals(0, PixelPicture.diff(ITALY, result), "Color Scale with No Change");
    }

    @Test
    public void testAlphaBlend() {
        PixelPicture p = new PixelPicture(LOCATION + "ItalyGrayScaleAverage.png");
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyBlendGrayScaleAvg.png"),
                        SimpleManipulations.alphaBlend(0.3, ITALY, p)
                ),
                "alpha-Blend"
        );
    }

    @Test
    public void testContrast() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyContrast2.png"),
                        AdvancedManipulations.adjustContrast(ITALY, 2.0)
                ),
                "Contrast"
        );
    }

    @Test
    public void testContrastNoChange() {
        PixelPicture result = AdvancedManipulations.adjustContrast(ITALY, 1.0);
        assertEquals(0, PixelPicture.diff(ITALY, result), "Contrast with No Change");
    }

    @Test
    public void testContrastWithExtremeMultiplier() {
        // Using a very high multiplier which might push rgb values out of range
        double extremeMultiplier = 10.0;
        PixelPicture adjusted = AdvancedManipulations.adjustContrast(ITALY, extremeMultiplier);

        boolean isWithinRange = true;
        Pixel[][] bmp = adjusted.getBitmap();
        for (int row = 0; row < bmp.length; row++) {
            for (int col = 0; col < bmp[row].length; col++) {
                Pixel p = bmp[row][col];
                if (p.getRed() < 0 || p.getRed() > 255 ||
                        p.getGreen() < 0 || p.getGreen() > 255 ||
                        p.getBlue() < 0 || p.getBlue() > 255) {
                    isWithinRange = false;
                    break;
                }
            }
            if (!isWithinRange) {
                break;
            }
        }
        assertTrue(isWithinRange, "Colors should be within 0-255 range");
    }

    @Test
    public void testReducePalette() {
        PixelPicture pp = new PixelPicture(LOCATION + "ItalyRP512.png");
        PixelPicture result = AdvancedManipulations.reducePalette(ITALY, 512);
        assertEquals(
                0,
                PixelPicture.diff(
                        pp, result
                ),
                "Reduce Palette 512"
        );
    }

    /*@Test
    public void testReducePaletteNoReduction() {
        int highColorCount = 1000000; // Assuming the original image has fewer colors
        PixelPicture result = AdvancedManipulations.reducePalette(ITALY, highColorCount);
        assertEquals(0, PixelPicture.diff(ITALY, result), "No Actual Reduction");
    }*/

    @Test
    public void testVignette() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyVignette.png"),
                        SimpleManipulations.vignette(ITALY)
                ),
                "Vignette"
        );
    }

    @Test
    public void testBlur() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyBlur2.png"),
                        AdvancedManipulations.blur(ITALY, 2)
                ),
                "blur 2"
        );
    }

    @Test
    public void testBlurEmptyImage() {
        PixelPicture emptyPic = new PixelPicture(new Pixel[0][0]);
        PixelPicture result = AdvancedManipulations.blur(emptyPic, 2);
        assertEquals(0, result.getWidth(), "Width after Blur with Empty Image");
        assertEquals(0, result.getHeight(), "Height after Blur with Empty Image");
    }

    /*@Test
    public void testExtremeBlurUniformity() {
        // Assuming ITALY is a standard test image
        PixelPicture original = ITALY;

        // Applying an extremely large blur radius
        int extremeRadius = Math.max(original.getWidth(), original.getHeight());
        PixelPicture blurred = AdvancedManipulations.blur(original, extremeRadius);

        // Assert that the blurred image is not null
        assertNotNull(blurred, "Blurred image should not be null");

        // Check if all pixels in the blurred image are the same
        Pixel firstPixel = blurred.getBitmap()[0][0];
        boolean allPixelsSame = true;
        for (int row = 0; row < blurred.getHeight(); row++) {
            for (int col = 0; col < blurred.getWidth(); col++) {
                if (!blurred.getBitmap()[row][col].equals(firstPixel)) {
                    allPixelsSame = false;
                    break;
                }
            }
            if (!allPixelsSame) {
                break;
            }
        }

        // Assert that all pixels are the same
        assertTrue(allPixelsSame, "All pixels in the extremely blurred image should be the same");
    }*/

}
