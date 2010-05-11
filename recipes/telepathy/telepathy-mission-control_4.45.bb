DESCRIPTION = "Telepathy Mission Control"
HOMEPAGE = "http://mission-control.sourceforge.net/"
LICENSE = "LGPL"
SECTION = "libs"
DEPENDS = "libtelepathy dbus-glib gconf"
PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/mission-control/telepathy-mission-control-${PV}.tar.gz"

inherit autotools pkgconfig

PACKAGES =+ " \
	libmissioncontrol \
	libmissioncontrol-config \
	libmissioncontrol-server \
	libmissioncontrol-dev \
	libmissioncontrol-config-dev \
	libmissioncontrol-server-dev \
	libmissioncontrol-dbg \
	libmissioncontrol-config-dbg \
	libmissioncontrol-server-dbg \
"

FILES_${PN} += "${datadir}/dbus*"

FILES_libmissioncontrol = "${libdir}/libmissioncontrol.so.*"
FILES_libmissioncontrol-config = "${libdir}/libmissioncontrol-config.so.*"
FILES_libmissioncontrol-server = "${libdir}/libmissioncontrol-server.so.*"

FILES_libmissioncontrol-dev = "${libdir}/libmissioncontrol.* \
                               ${includedir}/libmissioncontrol/ \
                	       ${libdir}/pkgconfig/libmissioncontrol.pc"
FILES_libmissioncontrol-config-dev = "${libdir}/libmissioncontrol-config.*"
FILES_libmissioncontrol-server-dev = "${libdir}/libmissioncontrol-server.*"

FILES_libmissioncontrol-dbg += "${libdir}/.debug/libmissioncontrol.so.*"
FILES_libmissioncontrol-config-dbg += "${libdir}/.debug/libmissioncontrol-config.so.*"
FILES_libmissioncontrol-server-dbg += "${libdir}/.debug/libmissioncontrol-server.so.*"

do_stage() {
        autotools_stage_all
}

SRC_URI[md5sum] = "b7810e1a53579bc496796538aefcc014"
SRC_URI[sha256sum] = "cb5565a66fb233d7c0b9418bcef53fe330c8a5a0f904a9a04de087ad0b4d0be9"
