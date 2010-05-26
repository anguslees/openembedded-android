DESCRIPTION = "BitchX is a IRC client"
SECTION = "console/network"

LICENSE = "BSD"
#PR = "-r1"

SRC_URI = "http://bitchx.sourceforge.net/ircii-pana-${PV}-final.tar.gz \
	   file://gcc34.patch"
S = "${WORKDIR}/BitchX"

inherit autotools

PACKAGES += "${PN}-defmsgs ${PN}-help ${PN}-scripts ${PN}-translations"
FILES_${PN}-defmsgs = "${libdir}/bx/BitchX.*"
RDEPENDS_${PN}-defmsgs = "${PN}"
FILES_${PN}-help = "${libdir}/bx/help"
RDEPENDS_${PN}-help = "${PN}"
FILES_${PN}-scripts = "${libdir}/bx/script"
RDEPENDS_${PN}-scripts = "${PN}"
FILES_${PN}-translations = "${libdir}/bx/translation"
RDEPENDS_${PN}-translations = "${PN}"

do_configure() {
        gnu-configize
        oe_runconf
}

do_install() {
	oe_runmake install \
                    prefix=${D}${prefix} \
                    exec_prefix=${D}${exec_prefix} \
                    bindir=${D}${bindir} \
                    sbindir=${D}${sbindir} \
                    libexecdir=${D}${libexecdir} \
                    datadir=${D}${datadir} \
                    sysconfdir=${D}${sysconfdir} \
                    sharedstatedir=${D}${sharedstatedir} \
                    localstatedir=${D}${localstatedir} \
                    libdir=${D}${libdir} \
                    includedir=${D}${includedir} \
                    oldincludedir=${D}${oldincludedir} \
                    infodir=${D}${infodir} \
                    mandir=${D}${mandir}
	ln -sf ./BitchX-1.1-final ${D}${bindir}/BitchX
}


SRC_URI[md5sum] = "611d2dda222f00c10140236f4c331572"
SRC_URI[sha256sum] = "7464cd75a10f2d117a10cf0184e5d4b9ece44de03a226402c17bdd3f2c7eca57"
