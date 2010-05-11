DESCRIPTION = "Central settings app for FSO/SHR distros"
HOMEPAGE = "http://git.freesmartphone.org"
AUTHOR = "Sebastian Spaeth (see AUTHORS)"
LICENSE  = "GPLv2"
DEPENDS = "vala-native elementary libeflvala"
SECTION = "x11/application"
SRCREV = "37dd7ac950e2bfd438801faf34c29fccfdbbaccf"
PV = "0.0.2+gitr${SRCPV}"
PR = "r5"

EXTRA_OECONF="--enable-vapidir=${STAGING_DATADIR}/vala/vapi"
inherit autotools

SRC_URI = "git://github.com/spaetz/shr-config.git;protocol=git;branch=master"
S = "${WORKDIR}/git"
