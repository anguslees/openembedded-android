DESCRIPTION = "Precision Time Protocol (PTP) as defined by the IEEE 1588 standard"
HOMEPAGE = "http://sourceforge.net/projects/ptpd"
LICENSE = "BSD"
SECTION = "network"
PR = "r0"

SRC_URI = "http://downloads.sourceforge.net/project/ptpd/ptpd/${PV}/ptpd-${PV}.tar.gz" 
S = "${WORKDIR}/ptpd-${PV}/src"

do_install() {
        install -d ${D}${bindir} ${D}${mandir}/man8
        install -m 4555 ptpd ${D}${bindir}
        install -m 644 ptpd.8 ${D}${mandir}/man8
}

SRC_URI[md5sum] = "b112b2bedc7f6e6e11a838608b9e0357"
SRC_URI[sha256sum] = "8ac1fdcad1e246b0395097dd9af29966c6823533d7e6989aae91506048fb51bc"
