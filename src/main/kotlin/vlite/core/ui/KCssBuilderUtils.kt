package vlite.core.ui

import kotlinx.css.*

/**
 * [How to Force Image Resize and Keep Aspect Ratio in HTML ?] (https://www.geeksforgeeks.org/how-to-force-image-resize-and-keep-aspect-ratio-in-html/)
 * [1. Using CSS max-width and max-height Property] (https://www.geeksforgeeks.org/how-to-force-image-resize-and-keep-aspect-ratio-in-html/#method-1-using-css-maxwidth-and-maxheight-property)
 * ```
 * Example: In this example, the .resizable-image class is applied to the image,
 * setting both max-width and max-height to 100%.
 * This ensures that the image won't exceed the size of its container while maintaining its aspect ratio.
 * ```
 * [2. Using the width Attribute] (https://www.geeksforgeeks.org/how-to-force-image-resize-and-keep-aspect-ratio-in-html/#method-2-using-the-width-attribute)
 * ```
 * You can use the width attribute directly on the img tag to specify the desired width.
 * The browser will automatically adjust the height to maintain the aspect ratio.
 * ```
 */
fun CssBuilder.resizeAndKeepAspectRatio(
    maxWidth: LinearDimension = 100.pct,
    maxHeight: LinearDimension = 100.pct,
) {
    this.maxWidth = maxWidth
    this.maxHeight = maxHeight
}
