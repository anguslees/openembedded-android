DESCRIPTION = "A gtk based image viewer"
SECTION = "x11/graphics"
DEPENDS = "gtk+"
LICENSE = "GPL"
HOMEPAGE = "http://gqview.sourceforge.net/"
RRECOMMENDS_${PN} = "gdk-pixbuf-loader-jpeg gdk-pixbuf-loader-png gdk-pixbuf-loader-gif"
PR = "r3"

SRC_URI = "${SOURCEFORGE_MIRROR}/gqview/gqview-2.1.5.tar.gz \
	   file://include-path-fix.patch \
	   file://gqview-rc-quotes.patch;striplevel=0 \
	   file://gqview-motion-hint.patch;striplevel=0 \
	   file://gqview-gimp.patch;striplevel=0"

inherit autotools

SRC_URI[md5sum] = "4644187d9b14b1dc11ac3bb146f262ea"
SRC_URI[sha256sum] = "12fea494e607a69eff31cdca13d306ef1b0b3b7850be0f8b236f267d8f2e9546"
