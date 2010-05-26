DESCRIPTION = "Matchbox keyboard"
LICENSE = "GPL"
DEPENDS = "libxtst"
SECTION = "x11/wm"
SRCREV = "1455"
PV = "0.2+svnr${SRCPV}"
PR = "r1"

SRC_URI = "svn://svn.o-hand.com/repos/matchbox/trunk;module=${PN};proto=http \
	   file://configure-fix.patch"

S = "${WORKDIR}/${PN}"

inherit autotools pkgconfig gettext

do_stage () {
        install -d ${STAGING_INCDIR}/fakekey	
        install -m 0644 ${S}/fakekey/fakekey.h ${STAGING_INCDIR}/fakekey
        oe_libinstall -so -C src libfakekey ${STAGING_LIBDIR}
}
