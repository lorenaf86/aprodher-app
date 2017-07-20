/**
 * @author RSGPTIv2
 * 
 * original script @ http=//www.goat1000.com/tagcanvas.php
 * 
 */
window.onload = function()
{
    TagCanvas.textFont = null;
    TagCanvas.textColour = '#000000';
    TagCanvas.textHeight = 40;
    TagCanvas.outlineColour ='#FFFFCC';
    TagCanvas.outlineThickness = 5;
    TagCanvas.outlineOffset = 1;
    TagCanvas.outlineMethod = 'block';
    TagCanvas.maxSpeed = 0.06;
    TagCanvas.depth = 0.95; 
    TagCanvas.pulsateTo = 0.2;
    TagCanvas.pulsateTime = 0.75;
    TagCanvas.decel = 0.9;
    TagCanvas.bgRadius = 40;
    TagCanvas.reverse = true;
    TagCanvas.hideTags = false;
    TagCanvas.shadow = '#ccf';
    TagCanvas.shadowBlur = 3;
    TagCanvas.wheelZoom = false;
    TagCanvas.fadeIn = 800;
    TagCanvas.initial = [0.3,-0.3];
    TagCanvas.outlineRadius = 8;
    /*TagCanvas.dragControl = true;*/
    
    try
    {
        // start script
        TagCanvas.Start('sgptiCanvas');
    }
    catch (e)
    {
        // something went wrong, hide the canvas container
        document.getElementById('sgptiCanvasContainer').style.display = 'none';
    }
};
