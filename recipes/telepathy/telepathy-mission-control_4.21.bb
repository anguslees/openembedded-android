DESCRIPTION = "Telepathy Mission Control"
HOMEPAGE = "http://mission-control.sourceforge.net/"
LICENSE = "LGPL"
SECTION = "libs"
DEPENDS = "libtelepathy dbus-glib gconf"
PR = "r1"

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

SRC_URI[md5sum] = "2ad61079a79b0426c81593ad69f56ada"
SRC_URI[sha256sum] = "0b98c93fc64409aae43d8f2941784f8f11639ad68b67320417554fa471a86508"
