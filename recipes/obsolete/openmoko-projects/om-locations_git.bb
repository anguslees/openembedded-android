DESCRIPTION = "Locations is a GPS location based application that lets you save and send your favorite locations to friends."
HOMEPAGE = "http://wiki.openmoko.org/wiki/Om2008.8_Locations"
SECTION = "openmoko/applications"
LICENSE = "GPL"
DEPENDS = "eet evas edje ecore edbus etk"
RDEPENDS_${PN} = "diversity-daemon"
SRCREV = "942e88a1b689ffe3f11a2d982cce389cc965b2ec"
PV = "0.2+gitr${SRCPV}"
PE = "1"
PR = "r1.04"

SRC_URI = "git://git.openmoko.org/git/om-locations.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF = "--with-edje-cc=${STAGING_BINDIR_NATIVE}/edje_cc"

do_configure_prepend() {
       autopoint --force
}
