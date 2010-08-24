DESCRIPTION = "Matchbox Window Manager Desktop"
LICENSE = "GPL"
SECTION = "x11/panels"
DEPENDS = "gtk+ startup-notification"
RDEPENDS_${PN} = "matchbox-common"
SRCREV = "2096"
PV = "2.0+svnr${SRCPV}"
PR = "r2"

inherit autotools pkgconfig

SRC_URI = "svn://svn.o-hand.com/repos/matchbox/trunk;module=${PN};proto=http \
	   file://fallback-folder.patch;striplevel=0 \
"
S = "${WORKDIR}/${PN}"

EXTRA_OECONF = "--program-transform-name='s/$/-2/'"
