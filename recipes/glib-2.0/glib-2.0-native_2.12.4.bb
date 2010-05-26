DESCRIPTION = "GLib is a general-purpose utility library, \
which provides many useful data types, macros, \
type conversions, string utilities, file utilities, a main \
loop abstraction, and so on. It works on many \
UNIX-like platforms, Windows, OS/2 and BeOS."
LICENSE = "LGPL"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS += "gtk-doc-native"
PR ="r1"

EXTRA_OECONF = "--disable-debug"

SRC_URI = "http://download.gnome.org/sources/glib/2.12/glib-${PV}.tar.bz2 \
           file://glib-gettextize-dir.patch \
           file://glibconfig-sysdefs.h"

S = "${WORKDIR}/glib-${PV}"

inherit autotools pkgconfig native gettext

acpaths = ""
do_configure_prepend () {
	install -m 0644 ${WORKDIR}/glibconfig-sysdefs.h .
}

do_stage () {
	install -m 0755 gobject/glib-mkenums ${STAGING_BINDIR}/
	install -m 0755 gobject/.libs/glib-genmarshal ${STAGING_BINDIR}/
	install -m 0755 glib-gettextize ${STAGING_BINDIR}/
	oe_libinstall -so -C glib libglib-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C gmodule libgmodule-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C gthread libgthread-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C gobject libgobject-2.0 ${STAGING_LIBDIR}
	autotools_stage_includes
	install -d ${STAGING_INCDIR}/glib-2.0/glib
	install -m 0755 ${S}/glibconfig.h ${STAGING_INCDIR}/glib-2.0/glibconfig.h
	install -d ${STAGING_DATADIR}/aclocal
	install -m 0644 ${S}/m4macros/glib-2.0.m4 ${STAGING_DATADIR}/aclocal/glib-2.0.m4
	install -m 0644 ${S}/m4macros/glib-gettext.m4 ${STAGING_DATADIR}/aclocal/glib-gettext.m4
	install -d ${STAGING_DATADIR}/glib-2.0/gettext/po
	install -m 0755 mkinstalldirs ${STAGING_DATADIR}/glib-2.0/gettext/
	install -m 0644 po/Makefile.in.in ${STAGING_DATADIR}/glib-2.0/gettext/po/
}

do_install () {
	:
}


SRC_URI[md5sum] = "ef7b9595fa788e245a0f883bddba8a25"
SRC_URI[sha256sum] = "1b1a0dc746a07c67aad15e22e3d751c3e7068c889d171efb1ab45efbab87c75d"
