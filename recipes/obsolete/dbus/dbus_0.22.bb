SECTION = "base"
PR = "r6"
HOMEPAGE = "http://www.freedesktop.org/Software/dbus"
DESCRIPTION = "message bus system for applications to talk to one another"
LICENSE = "GPL"
DEPENDS = "expat glib-2.0 virtual/libintl"
PROVIDES = "dbus-glib"

SRC_URI = "http://freedesktop.org/software/dbus/releases/dbus-${PV}.tar.gz \
	   file://cross.patch \
	   file://tmpdir.patch \
	   file://gettext.patch \
	   file://dbus-1.init"

inherit autotools pkgconfig update-rc.d gettext

INITSCRIPT_NAME = "dbus-1"
INITSCRIPT_PARAMS = "defaults"

CONFFILES_${PN} = "${sysconfdir}/dbus-1/system.conf ${sysconfdir}/dbus-1/session.conf"

FILES_${PN} = "${bindir}/dbus-daemon-1 ${bindir}/dbus-launch ${bindir}/dbus-cleanup-sockets ${bindir}/dbus-send ${bindir}/dbus-monitor ${sysconfdir} ${libdir}/dbus-1.0/services ${libdir}/lib*.so.*"
FILES_${PN}-dev += "${libdir}/dbus-1.0/include ${bindir}/dbus-glib-tool"

pkg_postinst_dbus() {
#!/bin/sh

# can't do adduser stuff offline
if [ "x$D" != "x" ]; then
  exit 1
fi

MESSAGEUSER=messagebus
MESSAGEHOME=/var/run/dbus

mkdir -p $MESSAGEHOME || true
chgrp "$MESSAGEUSER" "$MESSAGEHOME" 2>/dev/null || addgroup "$MESSAGEUSER"
chown "$MESSAGEUSER"."$MESSAGEUSER" "$MESSAGEHOME" 2>/dev/null || adduser --system --home "$MESSAGEHOME" --no-create-home --disabled-password --ingroup "$MESSAGEUSER" "$MESSAGEUSER"
}

EXTRA_OECONF = "--disable-qt --disable-gtk --disable-tests \
		--disable-checks --disable-xml-docs --disable-doxygen-docs \
		--with-xml=expat --without-x"

headers = "dbus-glib-error-enum.h dbus-shared.h dbus-address.h dbus-bus.h dbus-connection.h dbus-errors.h dbus-macros.h dbus-memory.h dbus-message.h dbus-pending-call.h dbus-protocol.h dbus-server.h dbus-threads.h dbus-types.h dbus.h"

do_stage () {
	oe_libinstall -so -C dbus libdbus-1 ${STAGING_LIBDIR}
	oe_libinstall -so -C glib libdbus-glib-1 ${STAGING_LIBDIR}

	mkdir -p ${STAGING_INCDIR}/dbus-1.0/dbus
	for i in ${headers}; do
		install -m 0644 dbus/$i ${STAGING_INCDIR}/dbus-1.0/dbus/$i
	done

	install -m 0644 dbus/dbus-glib.h ${STAGING_INCDIR}/dbus-1.0/dbus/
	install -m 0644 dbus/dbus-glib-lowlevel.h ${STAGING_INCDIR}/dbus-1.0/dbus/

	mkdir -p ${STAGING_LIBDIR}/dbus-1.0/include/dbus/
	install -m 0644 dbus/dbus-arch-deps.h ${STAGING_LIBDIR}/dbus-1.0/include/dbus/
}

do_install_append () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/dbus-1.init ${D}${sysconfdir}/init.d/dbus-1
	install -d ${D}${libdir}/dbus-1.0/services
}

python populate_packages_prepend () {
	if (bb.data.getVar('DEBIAN_NAMES', d, 1)):
		bb.data.setVar('PKG_dbus', 'dbus-1', d)
}

SRC_URI[md5sum] = "6b1c2476ea8b82dd9fb7f29ef857cb9f"
SRC_URI[sha256sum] = "a4d60f767b3ceb297c86423d5ce2af940f2699cce4426aeeaf09e0523d45c64d"
