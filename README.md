# Toolbar (David Brockbank)
- Bar of icons between the JMenu and Image panel.
- The most commonly used button from each menu were chosen with the exception of the filter menu due to the lack of intuitive icon representation.
- The icons were chosen to represent the actions with minimal thinking needed. i.e tried to make the icon obviously show what it does
- The list of icons was then used for the dropdown icons for their respective action. This allows for a mental connection between the option name and the icon.
- For actions on the toolbar that had option boxes, their icon was used for their icon box.
- Tested visually. Making sure buttons correspond to the correct action.
- No known issues

# Exceptions handle (Morgan Harrison)
- Removed the System exit so the program doesn't crash when an error occurs
- Added a dialog box to potential errors that inform the user of the error

# Greyscale Conversion (Steve Mills)
- Accessed via: Colour menu (Greyscale option) and toolbar (black and white square with triangles within); Keyboard shortcut Ctrl-G
- Tested on both colour and already greyscale images. No formal testing framework was used, but test cases included both portrait (tall, narrow) and landscape (wide, shallow) images to make sure the loops over the pixels were correct. Test cases with both very dark and bright pixels were used to check for overflow between colour channels.
- No known issues

# Gaussian (Tommy Light)
- Accessed via: Filter menu and keyboard shortcut (Shift-G).
- Tested the kernel with JUnit and printing it to the console. Tested visually by applying the filter to an image.
- No known issues.

# Contrast (David Brockbank)
- Accessed via: Colour menu (Contrast option) and toolbar (Half dark/light circle); Keyboard shortcut: Ctrl + c
- Tested visually with both extremes of the contrast scale, black images, and white images.
- If the contrast is decreased too much, the image colours become inverted. I have prevented this by restricting the values the user can access.
- No other known issues

# Median Filter (Morgan Harrison)
- Accessed via: Filter Menu and the keyboard shortcut (Ctrl-M)
- Tested the Median Filter with different kernel sizes, it was also tested visually by applying the image to varying sizes of images.
- If the scale was negative then it threw an error, this was fixed by restricting the scale size.
- No known issues

# Brightness (Tyler Winmill)
- Accessed via: Colour Menu and Keyboard Shortcut (Shift + B)
- Issues: If the number is set to -10 then it will turn the image completely black. Didn't remove as thought it may ruin the aestheic by changing the numbers as well as if the user wants to blank and image they can

# Sharpen (Tyler Winmill)
- Accessed via: Filter menu and Keyboard Shortcut (Shift + S)
- Issues: Currently if the sharpen filter is set low, it will increase the brightness of the image by a lot. Was unable to find the cause, and any edits and trying different variables only increased the brightness


# Image flip (Tommy Light)
- Accessed via: Edit menu, toolbar (triangles either side of a line) and keyboard shortcuts (Ctrl-UpArrow for vertical and Ctrl-DownArrow for horizontal).
- Tested visually by applying them to an image.
- No known issues.

# Image rotation (David Brockbank)
- Accessed via: Edit menu (Rotate Anti-Clockwise and Rotate Clockwise option) and Toolbar (Square with circle arrows icons);
    Keyboard shortcut (Ctrl + left arrow for Clockwise, Ctrl + right arrow for Anti-Clockwise)
- No known issues

# Image Export (Morgan Harrison)
- This was teseted by exporting images to the different extensions available.
- You had to type in the extension or it wouldn't save as the file type. This was changed to auatomatically get the extension
- The file was able to be saved as a '.All Files' this was solved by not allowing the FileFilter to find 'All Files'
- No known issues

# Image resize (Kris Mao)
- Access via : Edit menu (Resize).
- issues: narrow the image small enough it'll not come back; and narrow down will make image become blur.

# Keyboard shortcuts (Tyler Winmill)
- Implemented Filter Shortcuts:
	Ctrl + M = Medium Filter
	Ctrl + B = Mean Filter
	Shift + S = Sharpen Filter
	Shift + G = Gaussian Filter
	Shift + E = Edge Detection Filter
	Shift + R = Emboss Filter
- Implemented Colour Shortcuts:
	Ctrl + G = Greyscale
	Shift + B = Brightness
	Ctrl + C = Contrast
- Implemented File Shortcuts:
	Ctrl + S = Save
	Ctrl + O = Open
	Ctrl + A = Save As
	Ctrl + E = Export Image
- Implemented View Shortcuts:
	Ctrl + + = Zoom In
	Ctrl + - = Zoom Out
	Ctrl + F = Zoom Full
- Implemented Edit Shortcuts:
	Ctrl + Z = Undo
	Ctrl + Y = Redo
	Ctrl + Left = Clockwise Rotation
	Ctrl + Right = Anticlockwise Rotation
	Ctrl + Up = Vertical Flip
	Ctrl + Down = Horizontal Flip
	Ctrl + R = Resize
	shift + c = selection and crop
- I chose to implement most features, only exluding the exit as I felt it unnecessary. 
- I have not found any current bugs or issue with the keyboard shortcut implementation

# Continuous Integration
- Test CI seemed to be unable to compile the code for the unit tests.
- The CI didn't seem like it was going to provide much benefit to the project and would've been implemented too close to the deadline and was excluded in case it broke the repo.

# Extended filters (Tommy Light)
- Each filter uses this class to apply their kernels except the median filter.
- Extended filter for median filter is applied directly within the median filter class.
- Tested by comparing filtered images with and without extended filter implementation.
- No known errors.

# Filters with negative results (Tommy Light)
- Applied within the extended filter class and only to filters (edge detect and emboss) that specify negative results.
- Tested the same as the extended filters.
- No known errors.

# Emboss filters (Tommy Light)
- Accessed via the filter tab and the shortcut Shift+R.
- Tested by applying the filter to images and printing the kernel to the console.
- At some point undoing the filter would raise exceptions without affecting the functionality but it no longer seems to.

# Edges detection filters (Tommy Light)
- Accessed via the filter tab and the shortcut Shift+E
- Tested by applying the filter to images.
- No known errors

# Posterise effect (David Brockbank)
 - Accessed via the Colour menu
 - Tested by applying the filter to various images with different amounts of colour and grouping of similar colors
 - Quick Posterise quantizes the colour values into a set of evenly spaced R, G, and B options. This does a very quick but inaccurate representation of the images colours
 - Posterise uses k-means clustering to sort the images colours into k-groups and takes the average R, G, and B values of that group to be the colour for every pixel in the cluster. This method although more representative of the image's colour pallet, is very slow on large images. It is suggested to use the resize function to make the image smaller before testing on large images.

 # Icons (David Brockbank)
- Icons are used for the toolbar, dropdown menus, and popup windows
- Attributions for the icons are found at "./src/cosc202/andie/toolbar_icons/Attributions.html"
- Tested visually
- No known issues

# Invert Colours (David Brockbank)
 - Accessed via the Colour menu
 - Originally a bug in the contrast filter, has now been modified into its own filter
 - Tested visually with a variety of images and colours
 - No known issues

# RGB Filters (David Brockbank)
 - Accessed via the Colour menu
 - Alters the image to be displayed in only Red, Green, or Blue values
 - Tested visually with a variety of images and colours
 - When two different colour filters are applied, the image turns black. This is expected behaviour
 - No known issues
# Mouse selection of rectangular regions(Kris Mao)
- Access via edit menu (Crop) and toolbar 
- if image is too large, the mouse and point can not match have a large gap. 
  
# Crop to selection (kris Mao)
- Access via edit menu (under Crop dialog 'crop it' button)
- Tested with filter applied and/or resized image.
- no Knew issues.
# Drawing Function - rectangle ellipse, line

# Macros (Tyler Winmill)
- Accessed via the Macro tab. Create Macro opens a macro editor
- Tested by creating various macros with different operation types
- No known issues 

# more

