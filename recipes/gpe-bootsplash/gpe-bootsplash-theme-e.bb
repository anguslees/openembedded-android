DESCRIPTION = "An Enlightenment theme for gpe-bootsplash"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "GPL"
DEPENDS = "gpe-bootsplash"
RDEPENDS_${PN} = "gpe-bootsplash"
PR = "r2"

export THEME = "e"

SRC_URI = "file://splash-e-portrait.svg \
           file://splash-e-landscape.svg"

#FIXME: Please use $datadir instead of /usr/share
#FIXME: please use an gpe-bootsplash-theme.inc to reduce duplication

do_install() {
	install -d "${D}/usr/share/gpe"
	install -m 0644 ${WORKDIR}/splash-${THEME}* ${D}/usr/share/gpe
}

pkg_postinst() {
	update-alternatives --install /usr/share/gpe/splash-l.svg bootsplash-l /usr/share/gpe/splash-${THEME}-landscape.svg 20
	update-alternatives --install /usr/share/gpe/splash-p.svg bootsplash-p /usr/share/gpe/splash-${THEME}-portrait.svg 20
}

pkg_postrm() {
	update-alternatives --remove bootsplash-l /usr/share/gpe/splash-${THEME}-landscape.svg
	update-alternatives --remove bootsplash-p /usr/share/gpe/splash-${THEME}-portrait.svg
}

FILES_${PN} = "/usr/share/gpe/splash-${THEME}-*"
