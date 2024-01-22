Redirect is a handy beanshell script for slicing out parts of a preprocessed
file to other files.  For example, preprocessing this file,

#i=0;
#source("redirect.bsh");
#
#RedirectBegin("one");
$(++i)
#RedirectEnd("one");
#
#RedirectBegin("two");
$(++i)
#RedirectEnd("two");
#
#RedirectBegin("three");
$(++i)
#RedirectEnd("three");


will create three files (one, two and three) with the numbers 1, 2, and 3
in them.  The example RedirectExample also shows the syntax for appending
to a file.



