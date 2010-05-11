DESCRIPTION = "Clone of the classic Paradroid from C64 - SDL version."
SECTION = "opie/games"
PRIORITY = "optional"
LICENSE = "GPL"
HOMEPAGE = "http://freedroid.sourceforge.net/"
PR = "r2"

APPIMAGE = "${WORKDIR}/freedroid.png"

SRC_URI = "${SOURCEFORGE_MIRROR}/freedroid/freedroid-${PV}.tar.gz \
           file://freedroid.png"

inherit autotools sdl

do_compile() {
	oe_runmake pkgdatadir=${datadir}/freedroid
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 src/freedroid ${D}${bindir}
        install -d ${D}${datadir}/freedroid/
	cp -pPR graphics map sound ${D}${datadir}/freedroid/
}

SRC_URI[md5sum] = "585a65f61c2cd308ab45d5c514f695dc"
SRC_URI[sha256sum] = "0934bd29fb2ad0367ea3bdfdce47537179f9af6aa960cbcc897c40da2e1a0ee3"
