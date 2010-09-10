DESCRIPTION = "G(PE)^2 settings API library"
SECTION = "gpe/libs"
PRIORITY = "required"
LICENSE = "LiPS"
DEPENDS = "glib-2.0 gconf"
PV = "0.0+svnr-${SRCREV}"
PR = "r1"

DEFAULT_PREFERENCE = "-1"

inherit gpephone pkgconfig autotools

SRC_URI = "${GPEPHONE_SVN}"

S = "${WORKDIR}/${PN}"

FILES_${PN} += " ${libdir}/*.so.*"
FILES_${PN}-dbg += "${libdir}/.debug/*.so.*"
FILES_${PN}-dev += "${includedir} ${libdir}/*.la ${libdir}/*.so"

