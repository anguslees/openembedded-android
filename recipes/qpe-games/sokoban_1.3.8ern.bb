DESCRIPTION = "Pairs"
SECTION = "opie/games"
PRIORITY = "optional"
LICENSE = "GPL"
AUTHOR = "Anders Widell, Steve Dunham, Robert Ernst"
HOMEPAGE = "http://www.linux-solutions.at/projects/zaurus/games-Sokoban.html"


SRC_URI = "http://handhelds.org/~zecke/oe_packages/sokoban_V1.3.8ern.tar.gz \
           file://sokoban.patch"

PV = "1.3.8ern"
S = "${WORKDIR}/sokoban_V${PV}"

APPNAME = "sokoban"
APPTYPE = "binary"
APPDESKTOP = "${S}"

do_compile_prepend() {
	oe_runmake -C images
	oe_runmake -C levels
}

do_install () {
	install -d ${D}${palmtopdir}/pics/${APPNAME}/
	install -m 0644 ${S}/*.png ${D}${palmtopdir}/pics/${APPNAME}/

}

inherit opie


SRC_URI[md5sum] = "a362dc3f5f23d785990917103c76a43d"
SRC_URI[sha256sum] = "084f8286eb945455f3f1567c0a7e7df3a759f2a4e1aab3b881b8ef1bda5bdb21"
