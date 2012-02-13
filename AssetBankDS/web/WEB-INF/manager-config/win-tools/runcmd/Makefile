CPPFLAGS=-Wall -O2

all:	runcmd dumpargs

runcmd:	runcmd.o
	$(CXX) $(LDFLAGS) $^ $(LOADLIBES) $(LDLIBS) -lshlwapi -o $@

runcmd.o:	runcmd.cc

# This target is just a quick-and-dirty hack for testing
install:	runcmd
	install runcmd.exe "C:/Apache Tomcat/apache-tomcat-6.0.18/webapps/asset-bank/WEB-INF/manager-config/win-tools/nice-imagemagick/convert.exe"

clean:
	rm *.o runcmd.exe dumpargs.exe
