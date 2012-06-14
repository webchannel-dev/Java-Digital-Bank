#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <shlwapi.h>

#include <windows.h>

/*
 Copyright 2009 Bright Interactive, All Rights Reserved

 Ver Date           Who                 Comments
 ----------------------------------------------------------------------------
 d1  15-Apr-2009    Francis Devereux    Created
 d2  15-Apr-2009    Francis Devereux    Use _P_WAIT instead of _P_OVERLAY to avoid command prompt appearing before output from the .cmd (instead of after it) when runcmd.exe is run from a Windows Command Prompt
 d3  15-Apr-2009    Francis Devereux    Fixed bug in error handling
 d4  16-Apr-2009    Francis Devereux    Corrected cmdname argument of _spawnvp call
 d5  17-Apr-2009    Francis Devereux    Use CreateProcess instead of _swawnvp to fix problem when the path to the exe/cmd and the first argument both contain a space
 d6  18-Apr-2009    Francis Devereux    Escape many special chars, not just spaces
 d7  18-Apr-2009    Francis Devereux    Pass bInheritHandles=TRUE to CreateProcess to avoid "stdout going missing" problem on Win2003
 ----------------------------------------------------------------------------
*/

// Runs a .CMD file with the same name as the .EXE that this program is
// compiled into, passing it the same arguments

char *escape_arg(const char *const arg);

void report_windows_error(const char *const message);

int main(int argc, char *argv[])
{
    // Strip the extension from the command that was used to launch this
    // process, and append ".CMD"
    char *cmd_to_run = new char[strlen(argv[0]) + 5];
    strcpy(cmd_to_run, argv[0]);
    PathRemoveExtension(cmd_to_run);
    strcat(cmd_to_run, ".CMD");

    char **cmd_args = new char *[argc];

    cmd_args[0] = escape_arg(cmd_to_run);
    int total_args_length = strlen(cmd_args[0]);

    delete cmd_to_run;
    cmd_to_run = NULL;

    // Copy the args that were passed to this program to the end of the args
    // for the CMD script, and escape them
    for (int i = 1; i < argc; ++i)
    {
        cmd_args[i] = escape_arg(argv[i]);
        total_args_length += (1 + strlen(cmd_args[i]));
//         printf("%s -> %s\n", argv[i], cmd_args[i]);
    }

    total_args_length += 1; // for terminating NULL

    if (total_args_length > 32768)
    {
        fprintf(stderr, "%s: total command line length (%d) is greater 32768, which is the maximum allowed by CreateProcess\n", argv[0], total_args_length);
        return 1;
    }

    char lpCommandLine[total_args_length];

    strcpy(lpCommandLine, cmd_args[0]);
    for (int i = 1; i < argc; ++i)
    {
        strcat(lpCommandLine, " ");
        strcat(lpCommandLine, cmd_args[i]);
        delete cmd_args[i];
    }
    delete cmd_args;
    cmd_args = 0;

//     printf("lpCommandLine = \"%s\"\n", lpCommandLine);

    STARTUPINFO si;
    memset(&si, 0, sizeof(si));
    si.cb = sizeof(si);

    PROCESS_INFORMATION pi;

    // Run the CMD script.
    // Note that passing NULL for the first argument (lpApplicationName)
    // seems to be the only way to get it to work when the path to the
    // exe/cmd and the first argument both contain a space.
    BOOL success = CreateProcess(
        NULL,
        lpCommandLine,
        NULL,
        NULL,
        TRUE,
        0,
        NULL,
        NULL,
        &si,
        &pi);

    // exit code of child proc
    DWORD exitCode;

    if (!success)
    {
        report_windows_error("CreateProcess failed");
    }
    else
    {
        // Successfully created proc - wait for it to exit
        if (WaitForSingleObject(pi.hProcess, INFINITE) == WAIT_FAILED)
        {
            success = FALSE;
            report_windows_error("Waiting for process failed");
        }
        else
        {
            success = GetExitCodeProcess(pi.hProcess, &exitCode);
            if (!success)
            {
                report_windows_error("Getting exit code of process failed");
            }
        }

        CloseHandle(pi.hProcess);
        CloseHandle(pi.hThread);
    }

    // If all went well, return the exit code of the child process, otherwise return 1
    return success ? exitCode : 1;
}

/*
  Escapes an argument as required by MSVCRT's parser.

  Caller is responsible for freeing return value.

  Follows these rules, obtained from
  http://mail.python.org/pipermail/python-bugs-list/2006-December/036457.html
  http://technet.microsoft.com/en-gb/library/bb490880.aspx
  and reading crt/src/stdargv.c from MS Visual Studio 2008

  http://mail.python.org/pipermail/python-bugs-list/2006-December/036457.html:
  MSVCRT's parser requires spaces that are part of an
  argument to be enclosed in double-quotes. The
  double-quotes do not have to enclose the whole
  argument. Literal double-quotes must be escaped by
  preceding them with a backslash. If an argument
  contains literal backslashes before a literal or
  delimiting double-quote, those backslashes must be
  escaped by doubling them. If there is an unmatched
  enclosing double-quote then the parser behaves as if
  there was another double-quote at the end of the line.

  http://technet.microsoft.com/en-gb/library/bb490880.aspx:
  The following special characters require quotation marks: & < > [ ] { } ^ = ; ! ' + , ` ~ [white space]

  It seems that some of the above special characters don't actually require
  quotation marks (e.g. { and }) but this code adds them anyway - it doesn't
  seem to do any harm.
  
  The algorithm used is:
   1. if arg contains any special chars, put "s at the beginning and the end of out
   2. escape all "s in arg with \ (i.e. replace " with \")
   3. escape all \s in arg that precede "s (i.e. replace \" with \\" - note that rule 2. will end up replacing \\" with \\\")
*/
char *escape_arg(const char *const arg)
{
    // how many special characters are there?
    int special_cnt = 0;
    // how many double quotes are there?
    int dq_cnt = 0;
    // how many backslashes are there immediately before double quotes
    int bs_before_dq_cnt = 0;

    const char *c = arg;
    while(*c)
    {
        switch(*c)
        {
            case '&':
            case '<':
            case '>':
            case '[':
            case ']':
            case '{':
            case '}':
            case '^':
            case '=':
            case ';':
            case '!':
            case '\'':
            case '+':
            case ',':
            case '`':
            case '~':
            case ' ':
            case '\t':
            // don't think \n or \r can ever actually appear in command lines on Win32, but we treat them as special here just in case
            case '\n':
            case '\r':
                special_cnt++;
                break;

            case '"':
                dq_cnt++;
                if (c > arg && *(c-1) == '\\') bs_before_dq_cnt++;
                break;
        }
        c++;
    }

    int size_needed = 1 + c - arg;

    if (special_cnt > 0) size_needed += 2;

    size_needed += dq_cnt;
    size_needed += bs_before_dq_cnt;

    char *const out = new char[size_needed];

    char *o = out;
    c = arg;

    if (special_cnt > 0) *(o++) = '"';

    while(*c)
    {
        switch(*c)
        {
            case '"':
                *(o++) = '\\';
                *(o++) = '"';
                break;

            case '\\':
                // If it precedes a ", escape it

                // Note that using "*(c + 1)" is safe from going off the end
                // of the string, because if the current char is the last in
                // the string then "*(c + 1)" will be 0 because of the
                // terminating 0.

                if (*(c + 1) == '"') *(o++) = '\\';

                // always include the \ itself
                *(o++) = '\\';

                break;

            default:
                *(o++) = *c;
        }
        c++;
    }

    if (special_cnt > 0) *(o++) = '"';

    *(o++) = '\0';

    return out;
}

void report_windows_error(const char *const message)
{
    LPTSTR lpMessageBuffer = NULL;

    DWORD lastError = GetLastError();
    DWORD result = FormatMessage(
        FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM,
        NULL,
        lastError,
        0,
        (LPTSTR)&lpMessageBuffer,
        1,
        NULL);

    if (result == 0)
    {
        fprintf(stderr, "Error calling FormatMessage for %d\n", lastError);
    }
    else
    {
        fprintf(stderr, "%s: %s\n", message, lpMessageBuffer);
        LocalFree(lpMessageBuffer);
    }
}
