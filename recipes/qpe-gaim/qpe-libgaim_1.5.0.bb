DESCRIPTION = "A multi protocol instant messager library, Qt/Embedded based Palmtop Environments Edition"
SECTION = "opie/applications"
DEPENDS = "glib-2.0 gnutls"
RDEPENDS = "libgaim-plugins"
LICENSE = "GPL"
HOMEPAGE = "http://gaim.sourceforge.net/"
PR = "r3"

SRC_URI = "${SOURCEFORGE_MIRROR}/gaim/gaim-${PV}.tar.bz2 \
	   file://dont-look-for-gtk.patch        \
	   file://libgaim.patch "

S = "${WORKDIR}/gaim-${PV}"

inherit autotools

EXTRA_OE_CONF = "--disable-audio --disable-gtkspell --disable-perl \
		 --disable-screensaver --disable-sm --disable-glibtest \
		 --disable-gtktest --disable-startup-notification \
		 --disable-gevolution --disable-aotest --disable-audiofiletest \
		 --disable-x --without-x --without-gtk --disable-gtk \
		 --enable-gnutls=y"

CFLAGS_append = " -I${STAGING_INCDIR}/glib-2.0"

do_stage() {
	oe_libinstall -so -C src libgaim ${QTDIR}/lib

	# install headers
	GAIM_DIR=${STAGING_INCDIR}/gaim
	install -d $GAIM_DIR

	for header in account.h accountopt.h away.h blist.h buddyicon.h cmds.h config.h connection.h conversation.h core.h debug.h eventloop.h ft.h gaim.h imgstore.h log.h md5.h network.h notify.h plugin.h pluginpref.h pounce.h prefix.h prefs.h privacy.h proxy.h prpl.h request.h roomlist.h server.h sha.h signals.h sound.h sslconn.h status.h stringref.h util.h value.h version.h xmlnode.h
	do
		if [ -e ${S}/src/$header ]; then
			install -m 0644 ${S}/src/$header $GAIM_DIR
		fi
		if [ -e ${S}/$header ]; then
			install -m 0644 ${S}/$header $GAIM_DIR
		fi
	done
}

PACKAGES_DYNAMIC = "libgaim-protocol-*"

#FIXME: use do_packages to create individual packages for each of the plugins
python populate_packages_prepend () {
	plugindir = bb.data.expand('${libdir}/gaim', d)
	do_split_packages(d, plugindir, '^lib(.*)\.so$', 'libgaim-protocol-%s', 'GAIM plugin for %s protocol', extra_depends='' )
}

PACKAGES += "libgaim-plugins"
FILES_libgaim-plugins = "${libdir}/gaim/autorecon.so ${libdir}/gaim/s*.so"


SRC_URI[md5sum] = "9205321ac11fad271c90f2f0d7c5e7ce"
SRC_URI[sha256sum] = "58e3b0340cfc9e54e46f8d8835e5a02e31201c9ed8820bbb4cea36c59b9682aa"
