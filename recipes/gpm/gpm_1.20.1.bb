DESCRIPTION = "GPM (General Purpose Mouse) is a mouse server \
for the console and xterm, with sample clients included \
(emacs, etc)."
SECTION = "console/utils"
LICENSE = "GPL"
DEPENDS = "ncurses"

PR = "r3"
PARALLEL_MAKE = ""

SRC_URI = "ftp://arcana.linux.it/pub/gpm/gpm-${PV}.tar.bz2 \
	   file://configure.patch \
	   file://no-docs.patch \
	   file://gpm-OPEN_MAX.patch \
	   file://init"

inherit autotools update-rc.d

INITSCRIPT_NAME = "gpm"
INITSCRIPT_PARAMS = "defaults"

export LIBS = "-lm"

do_configure_prepend () {
	cp aclocal.m4 acinclude.m4
}

do_stage () {
	install -m 0644 src/headers/gpm.h ${STAGING_INCDIR}/
	oe_libinstall -so -a -C src/lib libgpm ${STAGING_LIBDIR}
}

do_install () {
	oe_runmake 'ROOT=${D}' install
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}/${sysconfdir}/init.d/gpm
	cd ${D}${libdir} && ln -sf libgpm.so.1.19.0 libgpm.so.1
}

SRC_URI[md5sum] = "2c63e827d755527950d9d13fe3d87692"
SRC_URI[sha256sum] = "11fabe7f27a205ff1ea6aee23e1dc2bb2dc5dbfc45ff0320fca0cd559806a936"
