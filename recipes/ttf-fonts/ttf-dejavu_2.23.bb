require ttf.inc

DESCRIPTION = "DejaVu font - TTF Edition"
HOMEPAGE = "http://dejavu.sourceforge.net/wiki/"
LICENSE = "Bitstream Vera"
# all subpackages except ${PN}-dbg and ${PN}-common itself rdepends on ${PN}-common
RDEPENDS_${PN}-sans = "${PN}-common"
RDEPENDS_${PN}-sans-mono = "${PN}-common"
RDEPENDS_${PN}-sans-condensed = "${PN}-common"
RDEPENDS_${PN}-serif = "${PN}-common"
RDEPENDS_${PN}-serif-condensed = "${PN}-common"
RDEPENDS_${PN}-common = ""
PR = "r3"

SRC_URI = "${SOURCEFORGE_MIRROR}/dejavu/dejavu-fonts-ttf-${PV}.tar.bz2 \
           file://30-dejavu-aliases.conf"

S = "${WORKDIR}/dejavu-fonts-ttf-${PV}/ttf"

do_install_append () { 
	install -d ${D}${sysconfdir}/fonts/conf.d/
	install -m 0644 ${WORKDIR}/30-dejavu-aliases.conf ${D}${sysconfdir}/fonts/conf.d/
}

PACKAGES = "${PN}-dbg \
            ${PN}-sans \
            ${PN}-sans-mono \
            ${PN}-sans-condensed \
            ${PN}-serif \
            ${PN}-serif-condensed \
            ${PN}-common"
RRECOMMENDS_${PN}-dbg = ""

FILES_${PN}-sans            = "${datadir}/fonts/truetype/DejaVuSans.ttf ${datadir}/fonts/truetype/DejaVuSans-*.ttf"
FILES_${PN}-sans-mono       = "${datadir}/fonts/truetype/DejaVuSansMono*.ttf"
FILES_${PN}-sans-condensed  = "${datadir}/fonts/truetype/DejaVuSansCondensed*.ttf"
FILES_${PN}-serif           = "${datadir}/fonts/truetype/DejaVuSerif.ttf ${datadir}/fonts/truetype/DejaVuSerif-*.ttf"
FILES_${PN}-serif-condensed = "${datadir}/fonts/truetype/DejaVuSerifCondensed*.ttf"
FILES_${PN}-common          = "${sysconfdir}"

SRC_URI[md5sum] = "ff871dff0b3e8a11cd5c54478f11073f"
SRC_URI[sha256sum] = "243642a1c3f4b6fd00125f5772ac5c8e4d0bb6586f5abb05829ead4b83ad5233"
