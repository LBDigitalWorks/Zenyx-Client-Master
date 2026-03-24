package com.client.graphics;

import com.client.Client;
import com.client.Sprite;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Loads fallback hitsplat sprites from bundled resources.
 * This provides a workaround for when sprites are missing from the JS5 cache.
 *
 * Sprite IDs:
 * - 1358: Block (blue)
 * - 1359: Damage (red)
 * - 1360: Poison (green)
 * - 1630: Block tinted
 * - 1631: Damage tinted
 * - 1632: Venom
 * - 1633: Disease
 * - 3571: Max damage
 */
@Slf4j
public class HitsplatFallbackSprites {

    private static final int[] HITSPLAT_SPRITE_IDS = {
        1358, // block_normal
        1359, // damage_normal
        1360, // poison_normal
        1630, // block_tinted
        1631, // damage_tinted
        1632, // venom_normal
        1633, // disease_normal
        3571  // damage_max
    };

    private static boolean initialized = false;

    /**
     * Loads fallback hitsplat sprites from resources and adds them to sprite overrides.
     * This should be called early in client initialization.
     */
    public static void loadFallbackSprites() {
        if (initialized) {
            return;
        }

        System.out.println("[HitsplatFallback] Loading fallback hitsplat sprites...");
        log.info("Loading fallback hitsplat sprites...");
        int loaded = 0;

        for (int spriteId : HITSPLAT_SPRITE_IDS) {
            try {
                // Check if already overridden (e.g., by a plugin)
                if (Client.spriteOverrides.containsKey(spriteId)) {
                    System.out.println("[HitsplatFallback] Sprite " + spriteId + " already overridden, skipping");
                    continue;
                }

                String resourcePath = "/hitsplats/" + spriteId + ".png";
                InputStream is = HitsplatFallbackSprites.class.getResourceAsStream(resourcePath);
                System.out.println("[HitsplatFallback] Loading " + resourcePath + " - stream: " + (is != null ? "found" : "NOT FOUND"));

                if (is == null) {
                    log.warn("Fallback hitsplat sprite not found: {}", resourcePath);
                    continue;
                }

                BufferedImage image = ImageIO.read(is);
                is.close();

                if (image == null) {
                    log.warn("Failed to read fallback hitsplat image: {}", spriteId);
                    continue;
                }

                Sprite sprite = createSpriteFromImage(image);
                if (sprite != null) {
                    Client.spriteOverrides.put(spriteId, sprite);
                    loaded++;
                    System.out.println("[HitsplatFallback] Added sprite " + spriteId + " to overrides (" + sprite.myWidth + "x" + sprite.myHeight + ")");
                    log.debug("Loaded fallback hitsplat sprite: {}", spriteId);
                }
            } catch (Exception e) {
                System.out.println("[HitsplatFallback] ERROR loading sprite " + spriteId + ": " + e.getMessage());
                log.error("Error loading fallback hitsplat sprite {}: {}", spriteId, e.getMessage());
            }
        }

        initialized = true;
        System.out.println("[HitsplatFallback] Completed - loaded " + loaded + " fallback sprites, spriteOverrides size: " + Client.spriteOverrides.size());
        log.info("Loaded {} fallback hitsplat sprites", loaded);
    }

    /**
     * Creates a Sprite from a BufferedImage.
     */
    private static Sprite createSpriteFromImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];

        // Get ARGB pixels
        image.getRGB(0, 0, width, height, pixels, 0, width);

        // Convert transparency: fully transparent pixels (alpha = 0) should be 0
        // The sprite draw routines check for == 0, not actual alpha
        for (int i = 0; i < pixels.length; i++) {
            if ((pixels[i] & 0xFF000000) == 0) {
                pixels[i] = 0;
            }
        }

        // Create sprite
        Sprite sprite = new Sprite(pixels, width, height);
        sprite.maxWidth = width;
        sprite.maxHeight = height;
        sprite.drawOffsetX = 0;
        sprite.drawOffsetY = 0;

        return sprite;
    }

    /**
     * Checks if fallback sprites have been loaded.
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * Resets the initialized flag (useful for testing).
     */
    public static void reset() {
        initialized = false;
    }
}