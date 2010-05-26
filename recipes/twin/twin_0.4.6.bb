DESCRIPTION = "Twin is a text-mode windowing environment: it draws and manages text windows on a text-mode display, like X11 does for graphical windows. It has a built-in window manager and terminal emulator, and can be used as server for remote clients in the same style as X11. It can display on Linux console, on X11 and inside itself."
SECTION = "console/utils"

DEPENDS = "coreutils-native"
LICENSE = "GPL LGPL"
SRC_URI = "http://linuz.sns.it/~max/twin/twin-0.4.6.tar.gz \
	   file://cross_compile.patch"

EXTRA_OECONF = " --disable-tt-hw-x11 --disable-hw-x11 --disable-tt-hw-gtk"

inherit autotools

do_compile () {
	oe_runmake 'HOSTCC=${BUILD_CC}'
}

do_stage () {
	oe_soinstall libs/libTw/libTw.so.3.0.9 ${STAGING_LIBDIR}
	install -d ${STAGING_INCDIR}/Tw
	install -m 0644 include/Tw/*.h ${STAGING_INCDIR}/Tw/
}

SRC_URI[md5sum] = "92429bb5550a4c231085585a3473bf4d"
SRC_URI[sha256sum] = "67c4b7677469040b4fc37c084bc4f1ef4c365477e79862c3dc7c256c9f9257c2"
