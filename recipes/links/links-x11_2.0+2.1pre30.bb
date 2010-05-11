require links.inc

DEPENDS += "virtual/libx11"
RCONFLICTS = "links"
PR = "r1"

SRC_URI += " file://links2.desktop \
             http://www.xora.org.uk/oe/links2.png;name=icon"

EXTRA_OECONF = "--enable-javascript --with-libfl --enable-graphics \
	        --with-ssl=${STAGING_LIBDIR}/.. --with-libjpeg \
	        --without-libtiff --without-svgalib --without-fb \
	        --without-directfb --without-pmshell --without-atheos \
	        --with-x --without-gpm --without-sdl"

do_install_append() {
        install -d ${D}/${datadir}/applications
        install -m 0644 ${WORKDIR}/links2.desktop ${D}/${datadir}/applications
        install -d ${D}/${datadir}/pixmaps
        install -m 0644 ${WORKDIR}/links2.png ${D}/${datadir}/pixmaps
}


SRC_URI[md5sum] = "f0f107cc824b71e43f0c6ab620209daf"
SRC_URI[sha256sum] = "f32314d851e86ec463967ddce78d051e3953b529878cbaeecf882c625ad29c75"
SRC_URI[icon.md5sum] = "477e8787927c634614bac01b44355a33"
SRC_URI[icon.sha256sum] = "eddcd8b8c8698aa621d1a453943892d77b72ed492e0d14e0dbac5c6a57e52f47"
