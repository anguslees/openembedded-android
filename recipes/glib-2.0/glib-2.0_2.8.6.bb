DESCRIPTION = "GLib is a general-purpose utility library, \
which provides many useful data types, macros, \
type conversions, string utilities, file utilities, a main \
loop abstraction, and so on. It works on many \
UNIX-like platforms, Windows, OS/2 and BeOS."
LICENSE = "LGPL"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS += "glib-2.0-native gtk-doc"
DEPENDS += "virtual/libiconv virtual/libintl"
PACKAGES =+ "glib-2.0-utils "
PR = "r1"

LEAD_SONAME = "libglib-2.0.*"
FILES_${PN} = "${libdir}/lib*.so.*"
FILES_${PN}-dev += "${libdir}/glib-2.0/include ${datadir}/glib-2.0/gettext"
FILES_glib-2.0-utils = "${bindir}/*"

EXTRA_OECONF = "--disable-debug"

SRC_URI = "ftp://ftp.gtk.org/pub/gtk/v2.8/glib-${PV}.tar.bz2 \
           file://glibconfig-sysdefs.h \
           file://configure-libtool.patch"

S = "${WORKDIR}/glib-${PV}"

inherit autotools pkgconfig gettext

require glib-2.0.inc

acpaths = ""
do_configure_prepend () {
	install -m 0644 ${WORKDIR}/glibconfig-sysdefs.h .
}

do_stage () {
	autotools_stage_all
	install -d ${STAGING_INCDIR}/glib-2.0/glib
	install -m 0755 ${S}/glibconfig.h ${STAGING_INCDIR}/glib-2.0/glibconfig.h
	install -d ${STAGING_DATADIR}/aclocal
	install -m 0644 ${S}/m4macros/glib-2.0.m4 ${STAGING_DATADIR}/aclocal/glib-2.0.m4
	install -m 0644 ${S}/m4macros/glib-gettext.m4 ${STAGING_DATADIR}/aclocal/glib-gettext.m4
}

SRC_URI[md5sum] = "fce6835fd8c99ab4c3e5213bc5bcd0ed"
SRC_URI[sha256sum] = "e2da2eec8c87dccdbce16dcd77489d225b613074764f2f39f2815db15b5deeea"
