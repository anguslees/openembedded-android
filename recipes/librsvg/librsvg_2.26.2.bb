DESCRIPTION = "Library for rendering SVG files"
SECTION = "x11/utils"
DEPENDS = "bzip2 gtk+ libcroco cairo libart-lgpl libxml2 popt"
LICENSE = "LGPL"

inherit gnome

SRC_URI[archive.md5sum] = "6bb1993f9180176e45d6084089f47aa8"
SRC_URI[archive.sha256sum] = "e82fb920901e211a54cff5df88c327bedec8565b324e2f529f428f492361c60f"

PACKAGES =+ "librsvg-gtk librsvg-gtk-dbg librsvg-gtk-dev rsvg"
FILES_${PN} = "${libdir}/*.so.*"
FILES_rsvg = "${bindir}/rsvg \
	      ${bindir}/rsvg-view \
	      ${bindir}/rsvg-convert \
	      ${datadir}/pixmaps/svg-viewer.svg"
FILES_librsvg-gtk = "${libdir}/gtk-2.0/*/*/*.so"
FILES_librsvg-gtk-dev += "${libdir}/gtk-2.0/*.la \
			  ${libdir}/gtk-2.0/*/*.la \
			  ${libdir}/gtk-2.0/*/*/*.la \
			  "
FILES_librsvg-gtk-dbg += "${libdir}/gtk-2.0/.debug \
                          ${libdir}/gtk-2.0/*/*/.debug"

pkg_postinst_librsvg-gtk() {
if [ "x$D" != "x" ]; then
        exit 1
fi
        gdk-pixbuf-query-loaders > /etc/gtk-2.0/gdk-pixbuf.loaders
}

