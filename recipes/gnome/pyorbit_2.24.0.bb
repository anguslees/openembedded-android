DESCRIPTION = "Python Orbit bindings"
LICENSE = "LGPL"

PR = "r1"

inherit gnome distutils-base pkgconfig

SRC_URI += "file://acinclude.m4"

EXTRA_OECONF += "--with-python-includes=${STAGING_INCDIR}/../"
EXTRA_OEMAKE = "-e"

do_configure() {
	install -m 0644 ${WORKDIR}/acinclude.m4 ${S}/
	export HOST_SYS=${HOST_SYS}
	export BUILD_SYS=${BUILD_SYS}
	export CC=${TARGET_PREFIX}gcc
	autotools_do_configure
}

do_stage() {
	install -d ${STAGING_INCDIR}/pyorbit-2
	install -m 0644 src/pyorbit.h ${STAGING_INCDIR}/pyorbit-2
	autotools_stage_all
}

SRC_URI[archive.md5sum] = "574593815e75ee6e98062c75d6d1581f"
SRC_URI[archive.sha256sum] = "8754669b8220aa1f151b920360029d5ea28c42bec55769059676725fe28397b8"
