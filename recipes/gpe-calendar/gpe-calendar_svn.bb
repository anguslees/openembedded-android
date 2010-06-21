DEFAULT_PREFERENCE = "-1"

DESCRIPTION = "GPE calendar"
SECTION = "gpe"
LICENSE = "GPL"

DEPENDS = "libhandoff libsoup libeventdb libschedule libxsettings libxsettings-client libgpepimc libdisplaymigration libgpevtype libsoundgen"
RDEPENDS_${PN} = "gpe-icons"

inherit autotools gpe


PV = "0.91+svn${SRCDATE}"
PR = "r1"

SRC_URI = "${GPE_SVN}"
S = "${WORKDIR}/${PN}"


PARALLEL_MAKE = ""


