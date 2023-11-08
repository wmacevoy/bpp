#!/bin/bash

for bpp in *.bpp
do
    root=${bpp%%.bpp}
    if [ -f "$root.bsh.verify" ] ; then
	if [ -x "$root.bpp.run" ] ; then
	    echo "custom execution from $root.bpp.run..."
	    ./"$root.bpp.run"
	else
	    bpp -b "$root.bpp"
	fi

	if ! diff --strip-trailing-cr "$root.bsh" "$root.bsh.verify"
	then
	echo "$root.bsh" and "$root.bsh.verify" differ.
	exit 1
	fi
    fi

    if [ -f "$root.verify" ] ; then
	if [ -x "$root.bpp.run" ] ; then
	    echo "custom execution from $root.bpp.run..."
	    ./"$root.bpp.run"
	else
	    bpp $args "$root.bpp"
	fi

	if ! diff --strip-trailing-cr "$root" "$root.verify"
	then
	    echo "$root" and "$root.verify" differ.
	    exit 1
	fi
    fi
    echo "$root.bpp passed."
done

echo "all tests pass."

