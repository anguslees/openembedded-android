DESCRIPTION = "Mobile Broadband Service Provider Database"
SECTION = "network"
LICENSE = "CCPD"
SRCREV = "bc536218490377ccbd09c4e5858d37c91c2f03f7"
PV = "1.0.0+gitr${SRCPV}"
PE = "1"
PR = "r0"

SRC_URI = "git://git.gnome.org/mobile-broadband-provider-info;protocol=git"
S = "${WORKDIR}/git"

inherit autotools_stage
