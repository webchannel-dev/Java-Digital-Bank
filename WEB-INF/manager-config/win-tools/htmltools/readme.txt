C:\>htmltools.exe
VeryPDF HTML Converter v2.1
Web: http://www.verypdf.com
Web: http://www.verydoc.com
Email: support@verypdf.com
Release Date: Jun  4 2009
-------------------------------------------------------
Description:
Convert *.EMF, *.WMF, *.RTF files to PDF files
EMF to PDF,PS,TIF,JPG,GIF,PNG,BMP,WMF,EMF,PCX,TGA,JP2,PNM
WMF to PDF,PS,TIF,JPG,GIF,PNG,BMP,WMF,EMF,PCX,TGA,JP2,PNM
RTF to PDF,PS,TIF,JPG,GIF,PNG,BMP,WMF,EMF,PCX,TGA,JP2,PNM
HTM to PDF,PS,TXT,TIF,JPG,GIF,PNG,BMP,WMF,EMF,PCX,TGA,JP2,PNM
Usage: htmltools [options] <EMF-WMF-HTML-URL-RTF-file> [<PDF-PS-Image-file>]
  -width <int>           : Set page width to PDF file
  -height <int>          : Set page height to PDF file
  -bwidth <int>          : Set web browser's width for HTML conversion
  -bheight <int>         : Set web browser's height for HTML conversion
  -multipagetif          : Create multipage TIFF format
  -xres <int>            : Set X resolution to image file
  -yres <int>            : Set Y resolution to image file
  -bitcount <int>        : Set color depth for image conversion
  -rotate <int>          : Rotate pages, 90, 180, 270
  -margin <string>       : Set page margin to PDF file
        -margin 10          : Set margin to 10pt to left
        -margin 10x10       : Set margin to 10pt to left,top
        -margin 10x10x10    : Set margin to 10pt to left,top,right
        -margin 10x10x10x10 : Set margin to 10pt to left,top,right,bottom
  -view                  : View PDF file after creation
  -append <int>          :
        -append 0: Overwrite if PDF file exists
        -append 1: Insert before first page if PDF file exists
        -append 2: Append to last page if PDF file exists
        -append 3: Rename filename if PDF file exists
  -postdata <string>     : Set the data to be posted to the URL. Use this function for some urls protected by forms.
  -postdatafile <string> : Read long postdata parameter from a disk file
  -producer <string>     : Set 'producer' to PDF file
  -creator <string>      : Set 'creator' to PDF file
  -subject <string>      : Set 'subject' to PDF file
  -title <string>        : Set 'title' to PDF file
  -author <string>       : Set 'author' to PDF file
  -keywords <string>     : Set 'keywords' to PDF file
  -openpwd <string>      : Set 'open password' to PDF file
  -ownerpwd <string>     : Set 'owner password' to PDF file
  -keylen <int>          : Key length (40 or 128 bit)
        -keylen 0:  40 bit RC4 encryption (Acrobat 3 or higher)
        -keylen 1: 128 bit RC4 encryption (Acrobat 5 or higher)
        -keylen 2: 128 bit RC4 encryption (Acrobat 6 or higher)
  -encryption <int>      : Restrictions
        -encryption    0: Encrypt the file only
        -encryption 3900: Deny anything
        -encryption    4: Deny printing
        -encryption    8: Deny modification of contents
        -encryption   16: Deny copying of contents
        -encryption   32: No commenting
        ===128 bit encryption only -> ignored if 40 bit encryption is used
        -encryption  256: Deny FillInFormFields
        -encryption  512: Deny ExtractObj
        -encryption 1024: Deny Assemble
        -encryption 2048: Disable high res. printing
        -encryption 4096: Do not encrypt metadata
  -unicode               : Enable Unicode conversion
  -rclbounds             : Use rclBounds instead of rclFrame
  -repairwmf             : Repair some special WMF files
  -activex               : Enable ActiveX for HTML conversion
  -java                  : Enable Java for HTML conversion
  -script                : Enable script for HTML conversion
  -bookmark <int>        : Create bookmark to PDF file
        -bookmark 1: Use short filename as bookmarks
        -bookmark 2: Use full filename as bookmarks
        -bookmark 3: Use short filename without suffix as bookmarks
  -mergepdf <string>     : Merge two PDF files into one PDF file
        -mergepdf "file1|file2|file3|...": Merge several PDF files into one
        -mergepdf "C:\filelist.txt": Merge PDF files from a .txt file
        -mergepdf "C:\test.pdf*100-200*250-300": Extract page ranges from PDF file
        -mergepdf "C:\*.pdf": Merge PDF files into one PDF file
  -burstpdf <string>     : Burst PDF file into single page PDF files
  -pdfx                  : Create PDF/X compliance PDF file
  -pdfa                  : Create PDF/A compliance PDF file
  -timeout <int>         : Set timeout in milliseconds for HTML conversion
  -delaytime <int>       : Set delay time in milliseconds for HTML conversion
  -htmllevel <int>       : Set link's level for HTML conversion
  -emfheight <int>       : Split a long EMF file by this height
  -pageheight <int>      : Split a long HTML page by page height, same as -emfheight
  -pageh <int>           : same as -pageheight
  -ph <int>              : same as -pageheight
  -imgfilter <int>       : Set compression filter which is used to compress images
   -1 : set compression filter automatically, default
    0 : Flate compression
    1 : JPEG compression
    2 : B&W CCITT Fax G3 compression
    3 : B&W CCITT Fax G4 compression
    7 : JPEG 2000 compression
  -quality <int>         : Set quality which is used to compress images
  -nosplitimg            : Don't break images in HTML file
  -forcesplitemf         : Force split EMF file by emfheight
  -playemf2              : Use PlayEnhMetaFileEx function to play EMF file
  -disablelinks          : Disable hyperlinks within HTML to PDF conversion
  -nocenter              : Don't center the contents in the PDF file
  -noempty               : Delete empty pages from PDF file
  -newexe                : Convert HTML to PDF in an new EXE process
  -newexe2               : Convert HTML to PDF in an new EXE process from a special user account
  -exeusername <string>  : Run current EXE application from a user account
  -exeuserpass <string>  : Password of specified user account
  -wtext <string>        : watermark on printed document
  -wtype <int>           : type of watermark
    0 : normal watermark
    1 : watermark on header
    2 : watermark on footer
  -wf <string>           : font name of watermark
  -wh <int>              : font size of watermark
  -wb                    : specify bold font
  -wi                    : specify an italic font
  -wu                    : specify an underlined font
  -ws                    : specify a strikeout font
  -wa <int>              : angle of watermark
  -wbox <string>         : a rectangle to output formatted text, it is only useful for "-walign" option, eg:
        -wbox "0,0,595,842"
        -wbox "0,0,612,792"
  -walign <int>          : set text align
    1 : left
    2 : center
    3 : right
  -wc <string>           : color of watermark,
    FF0000: Red color
    00FF00: Green color
    0000FF: Blue color
    HexNum: Other colors
  -wx <int>              : X offset of watermark
  -wy <int>              : Y offset of watermark
  -psmode <int>          : create vector Postscript file
    -psmode 0: create Postscript via printer driver
    -psmode 1: create Postscript via local interpreter 1 (slower)
    -psmode 2: create Postscript via local interpreter 2 (faster)
    default is "-psmode 2"
  -svgnoclip             : remove clipping during SVG output
  -log <string>          : output log into a file
  -v                     : Print copyright and version info
  -h                     : Print usage information
  -help                  : Print usage information
  --help                 : Print usage information
  -?                     : Print usage information
  -$ <string>            : Input registration key
  -pdf2ps <string>       : Convert PDF file to PS (Postscript) file
  -htm2pdf2              : New method to render HTML to PDF file
  -html2pdf2             : Same as -htm2pdf2 parameter
Example:
   htmltools.exe C:\in.emf C:\out.pdf
   htmltools.exe -pdfa C:\in.emf C:\out.pdf
   htmltools.exe -pdfx C:\in.emf C:\out.pdf
   htmltools.exe C:\*.emf C:\*.pdf
   htmltools.exe -margin 100x100x100x100 C:\in.rtf C:\out.pdf
   htmltools.exe -append 2 -width 612 -height 792 C:\*.emf C:\out.pdf
   htmltools.exe -append 2 C:\*.emf C:\out.pdf
   htmltools.exe C:\filelist.txt C:\out.pdf
   htmltools.exe -width 612 -height 792 C:\in.emf C:\out.pdf
   htmltools.exe -append 1 C:\in.emf C:\out.pdf
   htmltools.exe -append 2 C:\in.emf C:\out.pdf
   htmltools.exe -subject "subject" C:\in.emf C:\out.pdf
   htmltools.exe -ownerpwd 123 -keylen 2 -encryption 3900 C:\in.emf C:\out.pdf
   htmltools.exe -bookmark 3 C:\in.emf C:\out.pdf
   htmltools.exe -mergepdf "C:\1.pdf|C:\2.pdf|C:\3.pdf" C:\out.pdf
   htmltools.exe -mergepdf "C:\*.pdf" C:\out.pdf
   htmltools.exe -mergepdf "C:\filelist.txt" C:\out.pdf
   htmltools.exe -mergepdf "C:\test-extraction.pdf*10-15 C:\out10-15.pdf
   htmltools.exe -mergepdf "C:\test-extraction.pdf*500-501 C:\out500-501.pdf
   htmltools.exe -bwidth 100 -bheight 100 C:\in.emf C:\out.pdf
   htmltools.exe "http://www.verypdf.com" C:\out.pdf
   htmltools.exe -htmllevel 1 "http://www.verypdf.com" C:\out.pdf
   htmltools.exe "http://www.verypdf.com" C:\out.ps
   htmltools.exe "http://www.verypdf.com" C:\out.emf
   htmltools.exe "http://www.verypdf.com" C:\out.jpg
   htmltools.exe "http://www.verypdf.com" C:\out.gif
   htmltools.exe "http://www.verypdf.com" C:\out.txt
   htmltools.exe "C:\in.html" C:\out.gif
   htmltools.exe -disablelinks "C:\in.html" C:\out.pdf
   htmltools.exe -postdatafile C:\postdata.txt "C:\in.html" C:\out.pdf
   htmltools.exe -multipagetif C:\test.rtf C:\test.tif
   htmltools.exe -multipagetif -bitcount 1 -xres 300 -yres 300 C:\test.rtf C:\test.tif

Add watermarks into PDF files:
   htmltools.exe -wtext "VeryPDF" "C:\in.html" C:\out.pdf
   htmltools.exe -wtext "VeryPDF" -wc "0000FF" "C:\in.html" C:\out.pdf
   htmltools.exe -wtext "VeryPDF" -wx 100 -wy 100 "C:\in.html" C:\out.pdf
   htmltools.exe -wtext "VeryPDF" -wtype 1 "C:\in.html" C:\out.pdf
   htmltools.exe -wtext "VeryPDF" -wtype 2 "C:\in.html" C:\out.pdf
   htmltools.exe -wtext "VeryPDF" -wtype 0 -wa 45 "C:\in.html" C:\out.pdf
   htmltools.exe -wtext "VeryPDF" -wf "Arial" "C:\in.html" C:\out.pdf
   htmltools.exe -wtext "VeryPDF" -wf "Arial" -wh 20 "C:\in.html" C:\out.pdf
   htmltools.exe -wtext "VeryPDF" -wf "Arial" -wh 20 -wb -wi -wu -ws "C:\in.html" C:\out.pdf
   htmltools.exe -wtext "VeryPDF" -wf "Arial" -walign 3 -wh 20 -wbox "0,770,612,792" "C:\in.html" C:\out.pdf

C:\>