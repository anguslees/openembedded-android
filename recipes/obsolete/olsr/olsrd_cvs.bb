DESCRIPTION = "OLSR mesh routing daemon"
HOMEPAGE = "http://www.olsr.org"
DESCRIPTION_olsrd-libs = "OLSR mesh routing daemon -  optional libraries"
SECTION = "console/network"
PRIORITY = "optional"
LICENSE = "BSD"
SRCDATE = "20060814"
PV = "0.4.9+cvs${SRCDATE}"

DEFAULT_PREFERENCE = "-1"

SRC_URI="cvs://anonymous@olsrd.cvs.sourceforge.net/cvsroot/olsrd;module=olsrd-current \
	file://init \
	file://olsrd.conf"

PACKAGES =+ "olsrd-libs"
FILES_olsrd-libs = "${libdir}"

S = "${WORKDIR}/olsrd-current"

inherit update-rc.d

INITSCRIPT_NAME = "olsrd"
INITSCRIPT_PARAMS = "defaults"

do_compile() {
	touch .depend
	touch src/cfgparser/.depend
	oe_runmake OS=linux clean all libs
}

do_install () {
	oe_runmake OS=linux INSTALL_PREFIX=${D} install install_libs
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/olsrd
	install -m 644 ${WORKDIR}/olsrd.conf ${D}${sysconfdir}
}

CONFFILES_${PN} = "${sysconfdir}/olsrd.conf"
