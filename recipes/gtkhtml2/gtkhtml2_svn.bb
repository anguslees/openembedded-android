SECTION = "libs"
DEPENDS = "gtk+ glib-2.0 libxml2"
DESCRIPTION = "A GTK+ HTML rendering library."
LICENSE = "GPL"
SRCREV = "1158"
PV = "2.11.0+svnr${SRCPV}"

SRC_URI = "svn://anonymous@svn.gnome.org/svn/gtkhtml2/;module=trunk \
	http://svn.o-hand.com/repos/web/trunk/patches/at-import_box-pos.patch;patch=1;pnum=0;maxrev=1157 \
	http://svn.o-hand.com/repos/web/trunk/patches/css-stylesheet-user.patch;patch=1;pnum=0;maxrev=1157 \
	http://svn.o-hand.com/repos/web/trunk/patches/css-media.patch;patch=1;pnum=0;maxrev=1157 \
	http://svn.o-hand.com/repos/web/trunk/patches/add-end-element-signal.patch;patch=1;pnum=0;maxrev=1157 \
	http://svn.o-hand.com/repos/web/trunk/patches/add-dom-functions.patch;patch=1;pnum=0;maxrev=1157 \
	http://svn.o-hand.com/repos/web/trunk/patches/iain-mem-leak.patch;patch=1;pnum=0;maxrev=1157"

S = "${WORKDIR}/trunk"

inherit pkgconfig autotools

EXTRA_OECONF = " --disable-accessibility"

do_stage() {
        oe_libinstall -so -C libgtkhtml libgtkhtml-2 ${STAGING_LIBDIR}
        install -d ${STAGING_INCDIR}/gtkhtml-2.0/libgtkhtml
	( for i in css document dom dom/core dom/events dom/html dom/traversal dom/views graphics layout layout/html util view; do install -d ${STAGING_INCDIR}/gtkhtml-2.0/libgtkhtml/$i; install -m 0644 ${S}/libgtkhtml/$i/*.h ${STAGING_INCDIR}/gtkhtml-2.0/libgtkhtml/$i; done )
	install -m 0644 ${S}/libgtkhtml/*.h ${STAGING_INCDIR}/gtkhtml-2.0/libgtkhtml
}

