#include <stdio.h>

/*
  Simple test program that dumps the arguments passed to it to stdout
*/
int main(int argc, char *argv[])
{
    for (int i = 0; i < argc; ++i)
    {
        printf("argv[%d] = %s\n", i, argv[i]);
    }

    return 0;
}
