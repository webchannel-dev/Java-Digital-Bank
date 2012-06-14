' Read arguments
Set files = WScript.Arguments
if files.Count < 2 then
	WScript.Echo "Usage: SWFtoImage.vbs sourcefile destfile"
	WScript.Quit(1)
end if

Set SWFToImage = CreateObject("SWFToImage.SWFToImageObject")
SWFToImage.InitLibrary "steve@bright-interactive.com", "W8RB-CL9H-GPEM-K4YX"


SWFToImage.InputSWFFileName = files(0)
SWFToImage.ImageOutputType = 1 ' set output image type to Jpeg (0 = BMP, 1 = JPG, 2 = GIF)
SWFToImage.JPEGQuality=100
SWFToImage.ImageWidth=1000
SWFToImage.ImageHeight=1000
SWFToImage.Execute
SWFToImage.SaveToFile files(1)