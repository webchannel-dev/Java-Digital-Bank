#include <windows.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <io.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <conio.h>
#include <ctype.h>
#include <vector>
#include <string>
using namespace std;

__declspec(dllexport) int WINAPI RunCmd(char *lpszCmd);
#pragma comment( lib, "../htmlshell.lib" )

void GetModulePath(char *out_path,char *in_name)
{
	char *p;
	GetModuleFileName(NULL,out_path,256);
	p =strrchr(out_path,'\\');
	p[1]=0;
	strcat(out_path,in_name);
}

void main()
{
	char szInPDF[MAX_PATH];
	GetModulePath(szInPDF,"test-extraction.pdf");
	char szOutPDF[MAX_PATH];
	GetModulePath(szOutPDF,"test-extraction-out.pdf");
	
	char szCmd[1024];
	sprintf(szCmd, "-mergepdf \"%s*100-120\" \"%s\"", szInPDF, szOutPDF);
	RunCmd(szCmd);
	BOOL bRet = GetFileAttributes(szOutPDF) != -1;
	if(bRet)
		printf("Extraction PDF file successful.\n");
	else
		printf("Extraction PDF file failed.\n");
	return;
}
