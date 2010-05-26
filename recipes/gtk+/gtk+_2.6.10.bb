DESCRIPTION = "GTK+ is a multi-platform toolkit for creating graphical user interfaces. Offering a complete \
set of widgets, GTK+ is suitable for projects ranging from small one-off projects to complete application suites."
HOMEPAGE = "http://www.gtk.org"
SECTION = "libs"
LICENSE = "LGPL"
PRIORITY = "optional"
DEPENDS = "glib-2.0 pango atk jpeg libpng libxext libxcursor gtk-doc libgcrypt"
PR = "r11"

SRC_URI = "ftp://ftp.gtk.org/pub/gtk/v2.6/gtk+-${PV}.tar.bz2 \
           file://help.patch \
           file://no-demos.patch \
           file://no-xwc.patch \
           file://automake-lossage.patch \
           file://gtk+-handhelds.patch \
           file://spinbutton.patch \
           file://hardcoded_libtool.patch \
           file://disable-tooltips.patch \
           file://gtklabel-resize-patch;apply=yes \
           file://menu-deactivate.patch \
           file://xsettings.patch \
           file://scroll-timings.patch \
           file://small-gtkfilesel.patch \
           file://migration.patch;striplevel=0 \
           file://single-click.patch \
           file://menu-styling.patch \
           file://compile-against-newer-glib.patch \
           file://gtk.keynav.gtkcombobox.patch;striplevel=0 \
           file://gtk.keynav.gtkentry.patch;striplevel=0 \
           file://gtk.keynav.gtkiconview.patch;striplevel=0 \
           file://gtk.keynav.gtkradiobutton.patch;striplevel=0 \
           file://gtk.keynav.gtksettings.patch;striplevel=0 \
           file://gtk.keynav.gtktextview.patch;striplevel=0 \
           file://gtk.keynav.gtktreeview.patch;striplevel=0 \
           file://gtk.keynav.gtkwidget.patch;striplevel=0 \
           file://gtk+-2.6.10-bg.patch \
           file://filesel-fix-segfault.patch \
           "

inherit autotools pkgconfig

FILES_${PN} = "${bindir}/gdk-pixbuf-query-loaders \
	${bindir}/gtk-update-icon-cache \
	${bindir}/gtk-query-immodules-2.0 \
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
do_configure_prepend() {
        for i in `find . -name "Makefile.am"`   
        do
                sed -i -e s,-DG_DISABLE_DEPRECATED,-DSED_ROCKS_DUDES, $i
        done
}

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

SRC_URI[md5sum] = "520090ef291e35ba93397060e20f5025"
SRC_URI[sha256sum] = "d408b606c8dd414dfbf220ccc168a0bc85a419945439796792a5357a96ff02af"
