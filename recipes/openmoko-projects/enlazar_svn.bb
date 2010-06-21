DESCRIPTION = "Enlazar - Network part for Assasin"
HOMEPAGE = "http://enlazar.projects.openmoko.org/"
LICENSE = "GPL"
DEPENDS = "evas edje ecore edbus"
RDEPENDS_${PN} = "networkmanager"
SRCREV = "37"
PV = "0.1+svnr${SRCPV}"
PR = "r5"
PE = "2"

SRC_URI = "svn://svn.projects.openmoko.org/svnroot/enlazar;module=trunk;proto=http"

S = "${WORKDIR}/trunk"

inherit autotools pkgconfig

EXTRA_OECONF = ""
