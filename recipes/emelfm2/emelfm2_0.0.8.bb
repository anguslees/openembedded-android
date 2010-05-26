# emelfm2 OE build file
# Copyright (C) 2004-2006, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION="emelFM2 is a GTK2+ file manager that implements the popular \
	two-pane design."
HOMEPAGE="http://dasui.prima.de/e2wiki/"
SRC_URI="http://dasui.prima.de/~tooar/emelfm2-${PV}.tar.gz \
	file://makefile.patch"

SRC_URI[md5sum] = "70baf819021f2d733c122e3350764483"
SRC_URI[sha256sum] = "14955a6ef5115165dc8e74c68fdda8feda365ec45a3b2a3241ea3d2ba6b959da"

DEPENDS="gtk+"

FILES_${PN} += " /usr/lib/emelfm2/plugins/ /usr/share/pixmaps/emelfm2/"

do_compile() {
	oe_runmake PREFIX=/usr all
}

do_install() {
	oe_runmake BIN_DIR=${D}/usr/bin PREFIX=${D}/usr install
}
