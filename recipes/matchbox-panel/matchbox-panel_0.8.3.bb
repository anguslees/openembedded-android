DESCRIPTION = "Matchbox Window Manager Panel"
LICENSE = "GPL"
DEPENDS = "libmatchbox virtual/libx11 libxext libxpm apmd startup-notification virtual/kernel"
SECTION = "x11/wm"
PR = "r1"

SRC_URI = "http://projects.o-hand.com/matchbox/sources/matchbox-panel/0.8/matchbox-panel-${PV}.tar.bz2 \
	file://make-batteryapp-less-strict.patch \
	file://wifi-location.patch"
S = "${WORKDIR}/matchbox-panel-${PV}"

inherit autotools pkgconfig gettext

EXTRA_OECONF = "--enable-startup-notification --enable-dnotify --enable-small-icons"

FILES_${PN} = "${bindir} \
	       ${datadir}/applications \
	       ${datadir}/pixmaps"


SRC_URI[md5sum] = "7e37e776d63c2f2596d786500ca4138e"
SRC_URI[sha256sum] = "4fb8b6801dde49d4d37dadc3a5b73bc124bdb3798a554ff1c372c9eb3496bbcd"
