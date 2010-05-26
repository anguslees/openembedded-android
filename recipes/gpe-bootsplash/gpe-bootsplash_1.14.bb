inherit gpe

DEPENDS = "gtk+ libsvg-cairo"
SECTION = "gpe"
PRIORITY = "optional"
LICENSE = "GPL"
PR = "r7"

SRC_URI += "file://splash-p.svg file://splash-l.svg \
            file://c7x0-rotation.patch \
            file://cxx0-rotation.patch \
            file://cairofix.patch \
            file://no-strip-on-install.patch"

SRC_URI_append_mnci = " file://mnci.patch"

FILES_${PN} += "${datadir}/gpe"

do_install_append() {
	install -m 0644 ${WORKDIR}/splash-p.svg ${D}${datadir}/gpe/splash-p.svg
	install -m 0644 ${WORKDIR}/splash-l.svg ${D}${datadir}/gpe/splash-l.svg
	mv ${D}${sysconfdir}/rcS.d/S00bootsplash ${D}${sysconfdir}/rcS.d/S03bootsplash
}


SRC_URI[md5sum] = "cc11d81a1772cbf5629028dd39419abf"
SRC_URI[sha256sum] = "3926b7ef0ccd68d6bd83797ca925945673e4ca0a938302d265ed0e4b1eea5657"
