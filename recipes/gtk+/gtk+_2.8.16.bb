LICENSE = "LGPL"
DESCRIPTION = "GTK+ is a multi-platform toolkit for creating graphical user interfaces. Offering a complete \
set of widgets, GTK+ is suitable for projects ranging from small one-off projects to complete application suites."
HOMEPAGE = "http://www.gtk.org"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS = "glib-2.0 pango atk jpeg libpng libxext libxcursor gtk-doc libgcrypt cairo"
PR = "r5"

DEFAULT_PREFERENCE = "-1"

SRC_URI = "ftp://ftp.gtk.org/pub/gtk/v2.8/gtk+-${PV}.tar.bz2 \
           file://no-xwc.patch \
           file://automake-lossage.patch \
	   file://spinbutton.patch \
	   file://hardcoded_libtool.patch \
	   file://disable-tooltips.patch \
	   file://gtklabel-resize-patch;apply=yes \
	   file://gtktreeview-316689.patch \
	   file://menu-deactivate.patch \
	   file://xsettings.patch \
	   file://scroll-timings.patch \
	   file://small-gtkfilesel.patch \
	   file://migration.patch;striplevel=0 \
	   file://no-demos.patch \
	   file://gtk+-handhelds.patch \
	   file://single-click.patch"

inherit autotools pkgconfig

CFLAGS += " -lz "

FILES_${PN} = "${bindir}/gdk-pixbuf-query-loaders \
	${bindir}/gtk-query-immodules-2.0 \
	${bindir}/gtk-update-icon-cache \
	${libdir}/lib*.so.* \
	${datadir}/themes ${sysconfdir} \
	${libdir}/gtk-2.0/${LIBV}/engines/libpixmap.so"
FILES_${PN}-dev += " \
        ${datadir}/gtk-2.0/include \
        ${libdir}/gtk-2.0/include \
        ${libdir}/gtk-2.0/${LIBV}/loaders/*.la \
        ${libdir}/gtk-2.0/${LIBV}/immodules/*.la \
        ${libdir}/gtk-2.0/${LIBV}/engines/*.la \
        ${bindir}/gdk-pixbuf-csource"
FILES_${PN}-dbg += " \
        ${libdir}/gtk-2.0/${LIBV}/loaders/.debug/* \
        ${libdir}/gtk-2.0/${LIBV}/immodules/.debug/* \
        ${libdir}/gtk-2.0/${LIBV}/engines/.debug/*"

RRECOMMENDS_${PN} = "glibc-gconv-iso8859-1 ttf-dejavu-sans"

EXTRA_OECONF = "--without-libtiff --disable-xkb --disable-glibtest --enable-display-migration"
# --disable-cruft

LIBV = "2.4.0"

do_stage () {
	oe_libinstall -so -C gtk libgtk-x11-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C gdk libgdk-x11-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C contrib/gdk-pixbuf-xlib libgdk_pixbuf_xlib-2.0 ${STAGING_LIBDIR}
	oe_libinstall -so -C gdk-pixbuf libgdk_pixbuf-2.0 ${STAGING_LIBDIR}

	autotools_stage_includes

	mkdir -p ${STAGING_LIBDIR}/gtk-2.0/include
	install -m 0644 gdk/gdkconfig.h ${STAGING_LIBDIR}/gtk-2.0/include/gdkconfig.h

	install -m 0644 m4macros/gtk-2.0.m4 ${STAGING_DATADIR}/aclocal/
}

do_install_append () {
	install -d ${D}${sysconfdir}/gtk-2.0
}

postinst_prologue() {
if [ "x$D" != "x" ]; then
  exit 1
fi

}

PACKAGES_DYNAMIC = "gdk-pixbuf-loader-* gtk-immodule-*"

python populate_packages_prepend () {
	import os.path

	prologue = bb.data.getVar("postinst_prologue", d, 1)

	gtk_libdir = bb.data.expand('${libdir}/gtk-2.0/${LIBV}', d)
	loaders_root = os.path.join(gtk_libdir, 'loaders')
	immodules_root = os.path.join(gtk_libdir, 'immodules')

	do_split_packages(d, loaders_root, '^libpixbufloader-(.*)\.so$', 'gdk-pixbuf-loader-%s', 'GDK pixbuf loader for %s', prologue + 'gdk-pixbuf-query-loaders > /etc/gtk-2.0/gdk-pixbuf.loaders')
	do_split_packages(d, immodules_root, '^im-(.*)\.so$', 'gtk-immodule-%s', 'GTK input module for %s', prologue + 'gtk-query-immodules-2.0 > /etc/gtk-2.0/gtk.immodules')

        if (bb.data.getVar('DEBIAN_NAMES', d, 1)):
                bb.data.setVar('PKG_${PN}', 'libgtk-2.0', d)
}

SRC_URI[md5sum] = "6dea9b6f546bd858401729d12bbd5919"
SRC_URI[sha256sum] = "5073cb676347d7bfb4438795f586867fbcce68b197eb9c0683b0b82af77d5b8f"
