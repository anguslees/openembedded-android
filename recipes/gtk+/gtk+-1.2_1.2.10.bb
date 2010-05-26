LICENSE = "LGPL"
DESCRIPTION = "GTK+1.2 is a deprecated library provided for running programs not yet converted to GTK+2.0"
HOMEPAGE = "http://www.gtk.org"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS = "glib-1.2 jpeg libpng libxext"
PR = "r3"

SRC_URI = "ftp://ftp.gtk.org/pub/gtk/v1.2/gtk+-${PV}.tar.gz \
           file://timezone-fix.patch \
           file://gtk+1.2-reconf-fix;apply=yes \
           file://no-xwc;apply=yes"
S = "${WORKDIR}/gtk+-${PV}"

inherit autotools pkgconfig flow-lossage

do_configure_prepend() {
	install -d m4
	rm -f ltconfig libtool ltmain.sh aclocal.m4
	sed -i -e s:AM_LC_MESSAGES:gt_LC_MESSAGES:g acinclude.m4
}

FILES_${PN} += "${datadir}/themes"
FILES_${PN}-dev += "${datadir}/gtk-1.2/include ${libdir}/gtk-1.2/include"


EXTRA_OECONF = "--enable-debug=no --disable-glibtest --disable-xim"
# --disable-cruft

LIBV = "1.2.10"

gtk_include = "fnmatch.h gtk.h gtkaccelgroup.h gtkaccellabel.h gtkadjustment.h gtkalignment.h gtkarg.h gtkarrow.h gtkaspectframe.h gtkbbox.h gtkbin.h gtkbindings.h gtkbox.h gtkbutton.h gtkcalendar.h gtkcheckbutton.h gtkcheckmenuitem.h gtkclist.h gtkcolorsel.h gtkcombo.h gtkcompat.h gtkcontainer.h gtkctree.h gtkcurve.h gtkdata.h gtkdebug.h gtkdialog.h gtkdnd.h gtkdrawingarea.h gtkeditable.h gtkentry.h gtkenums.h gtkeventbox.h gtkfeatures.h gtkfilesel.h gtkfixed.h gtkfontsel.h gtkframe.h gtkgamma.h gtkgc.h gtkhandlebox.h gtkhbbox.h gtkhbox.h gtkhpaned.h gtkhruler.h gtkhscale.h gtkhscrollbar.h gtkhseparator.h gtkimage.h gtkinputdialog.h gtkintl.h gtkinvisible.h gtkitem.h gtkitemfactory.h gtklabel.h gtklayout.h gtklist.h gtklistitem.h gtkmain.h gtkmarshal.h gtkmenu.h gtkmenubar.h gtkmenufactory.h gtkmenuitem.h gtkmenushell.h gtkmisc.h gtknotebook.h gtkobject.h gtkoptionmenu.h gtkpacker.h gtkpaned.h gtkpixmap.h gtkplug.h gtkpreview.h gtkprivate.h gtkprogress.h gtkprogressbar.h gtkradiobutton.h gtkradiomenuitem.h gtkrange.h gtkrc.h gtkruler.h gtkscale.h gtkscrollbar.h gtkscrolledwindow.h gtkselection.h gtkseparator.h gtksignal.h gtksocket.h gtkspinbutton.h gtkstatusbar.h gtkstyle.h gtktable.h gtktearoffmenuitem.h gtktext.h gtkthemes.h gtktipsquery.h gtktogglebutton.h gtktoolbar.h gtktooltips.h gtktree.h gtktreeitem.h gtktypebuiltins.h gtktypeutils.h gtkvbbox.h gtkvbox.h gtkviewport.h gtkvpaned.h gtkvruler.h gtkvscale.h gtkvscrollbar.h gtkvseparator.h gtkwidget.h gtkwindow.h makeenums.h stamp-gtkmarshal.h stamp-gtktypebuiltins.h"
gdk_include = "MwmUtil.h gdk.h gdkcursors.h gdki18n.h gdkinput.h gdkinputcommon.h gdkinputgxi.h gdkinputnone.h gdkinputxfree.h gdkkeysyms.h gdkprivate.h gdkrgb.h gdktypes.h gdkx.h gxid_lib.h gxid_proto.h"

do_stage () {
	oe_libinstall -so -C gtk libgtk ${STAGING_LIBDIR}
	oe_libinstall -so -C gdk libgdk ${STAGING_LIBDIR}

	mkdir -p ${STAGING_INCDIR}/gtk-1.2/gtk
	for i in ${gtk_include}; do
		install -m 0644 gtk/$i ${STAGING_INCDIR}/gtk-1.2/gtk/$i
	done

	mkdir -p ${STAGING_INCDIR}/gtk-1.2/gdk
	for i in ${gdk_include}; do
		install -m 0644 gdk/$i ${STAGING_INCDIR}/gtk-1.2/gdk/$i
	done

	mkdir -p ${STAGING_LIBDIR}/gtk-1.2/include

	install -m 0644 gtk.m4 ${STAGING_DATADIR}/aclocal/
	install -m 0755 gtk-config ${STAGING_BINDIR_CROSS}/
}

do_install_append () {
	install -d ${D}${sysconfdir}/gtk-1.2
}


SRC_URI[md5sum] = "4d5cb2fc7fb7830e4af9747a36bfce20"
SRC_URI[sha256sum] = "3fb843ea671c89b909fd145fa09fd2276af3312e58cbab29ed1c93b462108c34"
