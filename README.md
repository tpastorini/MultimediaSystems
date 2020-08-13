# Multimedia Systems, University of Granada

This project has as goals to work at different multimedia files like images, videos and sounds. You can apply transformations to images, draw different objects and play videos and sounds with the application. This application also has a move detector with a camera which take a snapshot when it detects a movement.

![Alt Multimedia Systems Application](/ImageApp.png)

### App's goals. 

1. Integration and management of different types of elements: graphics, images, sounds and videos using a main menu and toolbar.

2. Geometric shapes drawing like lines, rectangles, ellipses and round rectangles using different features for each of these shapes. These features would be colour, stroke, one color fill, gradient fill (vertical/horizontal), border render or opacity.

3. Image transformations.
   - Duplicate an image.
   - Modify the brightness of an image.
   - Apply fuzzy, focusing and embossed filters.
   - Apply normal, lighting or darkened filters.
   - Obtain a negative images.
   - Extraction of bands from an image.
   - Conversion to RGB, YCC and GRAY color spaces.
   - Image's rotations.
   - Image's scales.
   - Tint an image with a color.
   - Equalize an image.
   - Apply a sepia effect.
   - Apply a thresholding to the image.
   - Own image operations with a LookupOp, component to component and pixel to pixel operations.
   
 4. Sound and video players.
    - Playing audio files from your computer, .au and .wav extensions.
    - Save your recording audio files, .au extension.
    - Playing video files from your computer, .mpg and .mp4 extensions.
    - Take a snapshoot when your video is playing and apply an image transformations.
    - Take a snapshoot when your webcam catch a movement.

### Library and packages. My own library: SM.TDP.Biblioteca.

This library contains all graphics classes and some user interface elements. It contains three main packages call: *graficos, imagen, iu* where you can find classes related with visualitation, shape drawing and image transformations.

#### Main project's classes.

Main project contains five classes about app operation.

1. Ventana Principal's class. Contains a visualitation area and it allows managing an environment of internal windows.

![Alt MultimediaSystems_VentanaInternaClasses](/DefinicionClasesVentanaInterna.png)

2. Ventana Interna's class. A VentanaInternal's object contains a LienzoImagen2D's object which allow to apply image transformations and drawing. VentanaInternaImagen, VentanaInternaCamara and VentanaInternaVLCPlayer classes inherit from VentanaInterna class.

3. Establecer Tam Lienzo's class. This class allows users to create an drawinf area with specific or a default dimensions: width (px) and height (px).

4. Panel Relleno's class. Contains different types of  shape filling. It means without fill, one color fill or gradient fill (vertical/horizontal).

5. Redimensión Lienzo's class. This class allows to change dimensions of your drawing area.
