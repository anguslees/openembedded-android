DESCRIPTION = "Roadster aims to be easy-to-use and open-source mapping software."
LICENSE = "GPL"
HOMEPAGE = "http://linuxadvocate.org/projects/roadster/"
SECTION = "x11/navigation"
SRCDATE = "20060814"
PV = "0.0+cvs${SRCDATE}"
PR = "r1"

DEPENDS = "tcp-wrappers mysql gpsd gtk+ cairo libxml2 libgnomeui libglade"
RDEPENDS = "gpsd gtk+ cairo libxml2"

SRC_URI = "cvs://anoncvs@cvs.cairographics.org/cvs/cairo;module=roadster"
S = "${WORKDIR}/${PN}"

EXTRA_OECONF = " --with-gpsd=${STAGING_DIR_TARGET}${layout_exec_prefix} "
inherit autotools

CFLAGS += " -I{$STAGING_INCDIR} "
