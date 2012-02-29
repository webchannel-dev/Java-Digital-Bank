call install.bat

htmltools.exe "http://www.google.com" "%CD%\google_pdf.pdf"
htmltools.exe "http://www.google.com" "%CD%\google_ps.ps"
htmltools.exe -htmllevel 1 "http://www.verypdf.com/htmltools/html-tools.html" "%CD%\verypdf_site1.pdf"
htmltools.exe -margin 20x20x20x20 -nocenter -width 612 -height 792 -emfheight 780 -htmllevel 1 "http://www.verypdf.com/htmltools/html-tools.html" "%CD%\verypdf_site2.pdf"
htmltools.exe "http://www.google.com" "%CD%\google.txt"
htmltools.exe "http://www.google.com" "%CD%\google.tif"
htmltools.exe "http://www.google.com" "%CD%\google.jpg"
htmltools.exe "http://www.google.com" "%CD%\google.gif"
htmltools.exe "http://www.google.com" "%CD%\google.png"
htmltools.exe "http://www.google.com" "%CD%\google.bmp"
htmltools.exe "http://www.google.com" "%CD%\google.emf"
htmltools.exe "http://www.google.com" "%CD%\google.wmf"
htmltools.exe "http://www.google.com" "%CD%\google.pcx"
htmltools.exe "http://www.google.com" "%CD%\google.tga"
htmltools.exe "http://www.google.com" "%CD%\google.jp2"
htmltools.exe "http://www.google.com" "%CD%\google.pnm"

htmltools.exe "%CD%\example.rtf" "%CD%\example.pdf"
htmltools.exe "%CD%\example.rtf" "%CD%\example.emf"
htmltools.exe "%CD%\example.rtf" "%CD%\example.wmf"
htmltools.exe -bitcount 1 -xres 300 -yres 300 "%CD%\example.rtf" "%CD%\example.tif"
htmltools.exe -bitcount 1 -xres 300 -yres 300 "%CD%\example.rtf" "%CD%\example.bmp"
htmltools.exe "%CD%\example.rtf" "%CD%\example.gif"

htmltools.exe "%CD%\google.emf"  "%CD%\google_emf2png.png"
htmltools.exe "%CD%\google.emf"  "%CD%\google_emf2wmf.wmf"
htmltools.exe "%CD%\google.emf"  "%CD%\google_emf2gif.gif"

htmltools.exe -xres 600 -yres 600 "%CD%\google.emf"  "%CD%\google_emf2gif_600dpi.gif"
htmltools.exe -xres 600 -yres 600 "%CD%\google.emf"  "%CD%\google_emf2jpg_600dpi.jpg"

htmltools.exe -mergepdf "%CD%\verypdf_site1.pdf|%CD%\example.pdf"  "%CD%\merged.pdf"
htmltools.exe -burstpdf "%CD%\merged.pdf" "%CD%\burstout"

htmltools.exe "http://www.verypdf.com/pdfcamp/convert-html-to-pdf.html" "%CD%\verypdf-single-page.pdf"
htmltools.exe -margin 20x20x20x20 -nocenter -width 612 -height 792 -emfheight 792 "http://www.verypdf.com/pdfcamp/convert-html-to-pdf.html" "%CD%\verypdf-multi-page-001.pdf"
htmltools.exe -margin 20x20x20x20 -emfheight 792 -nocenter "http://www.verypdf.com/pdfcamp/convert-html-to-pdf.html" "%CD%\verypdf-multi-page-002.pdf"

htmltools.exe -mergepdf "%CD%\test-extraction.pdf*100-200*250-300" "%CD%\extracted.pdf"
