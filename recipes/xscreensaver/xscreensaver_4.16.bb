# xscreensaver OE build file
# Copyright (C) 2004, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION = "*The* screensaver package for X11"
HOMEPAGE = "http://www.jwz.org/xscreensaver/"
SECTION = "x11-misc"
LICENSE = "BSD"
DEPENDS = "intltool virtual/libx11 gtk+ libxml2 libglade"
INC_PR = "r4"
PR = "${INC_PR}.0"

BROKEN = "1"
# upstream is at 4.22 now - patches do not apply

SRC_URI = "http://www.jwz.org/xscreensaver/xscreensaver-${PV}.tar.gz \
           file://fixes.patch \
           file://configure.patch \
           file://XScreenSaver"

# xscreensaver-demo is a glade app
LDFLAGS_append = " -Wl,--export-dynamic"

inherit autotools

export INTLTOOL_PERL="/usr/bin/env perl"

EXTRA_OECONF="--with-xml --with-gtk --disable-locking --without-pixbuf \
	--with-jpeg --with-xpm"

PACKAGES =+  " xscreensaver-demo xscreensaver-extra"

FILES_${PN}= "${bindir}/xscreensaver ${bindir}/xscreensaver-command \
	/usr/X11R6/lib/X11/app-defaults"

FILES_xscreensaver-demo="${bindir}/xscreensaver-demo ${datadir}/xscreensaver \
	${datadir}/pixmaps/"

FILES_xscreensaver-extra="${bindir}/xscreensaver-getimage*"

do_compile() {
	oe_runmake GNOME_DATADIR=${datadir} all
}

do_install() {
	unset KDEDIR
	oe_runmake -C ${S}/driver GNOME_DATADIR=${datadir} \
	install_prefix=${D} install

	oe_runmake -C ${S}/hacks install_prefix=${D} install-program

	# Install the defaults file
	install -d ${D}/usr/X11R6/lib/X11/app-defaults
	install -m 0644 ${WORKDIR}/XScreenSaver ${D}/usr/X11R6/lib/X11/app-defaults
}

PACKAGES_DYNAMIC = "xscreensaver-hack-*"

python populate_packages_prepend () {
	hackdir = bb.data.expand('${libdir}/xscreensaver', d)
	do_split_packages(d, hackdir, '^(.*)', 'xscreensaver-hack-%s', 'XScreensaver hack %s')
}

SRC_URI[md5sum] = "e715ca402fc1218a078d65b7e7922082"
SRC_URI[sha256sum] = "e428b88cb6719b4deedf505ffb98fb7cbfecb4340e81c29857801aeeef329528"
