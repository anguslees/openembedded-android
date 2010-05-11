DESCRIPTION = "ConsoleKit is a framework for defining and tracking users, login sessions, and seats."
LICENSE = "GPLv2"
DEPENDS = "policykit libpam dbus"

PR = "r3"

inherit gnome

SRC_URI = "http://people.freedesktop.org/~mccann/dist/ConsoleKit-${PV}.tar.bz2"
S = "${WORKDIR}/ConsoleKit-${PV}"

EXTRA_OECONF += "--enable-pam-module --with-pam-module-dir=${libdir}/security"

do_stage () {
	autotools_stage_all
}

PACKAGES += "pam-plugin-ck-connector"
FILES_${PN} += "${libdir}/ConsoleKit ${datadir}/dbus-1 ${datadir}/PolicyKit"
FILES_${PN}-dbg += ""${libdir}/security/.debug/*.so"
FILES_pam-plugin-ck-connector += "${libdir}/security/*.so"
RDEPENDS_pam-plugin-ck-connector += "${PN}"



SRC_URI[md5sum] = "43b02a52212330b54cfb34c4044d9ce0"
SRC_URI[sha256sum] = "cbcfbb3df2dc76855c99c1cdff04a257ec5270fa62e1d21b0d057b08d88c0fd4"
