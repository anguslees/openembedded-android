require qpf.inc

DESCRIPTION = "Qt/Embedded fonts version ${PV}"
HOMEPAGE = "http://www.trolltech.com"
LICENSE = "GPL QPL"
PR = "r2"

PROVIDES  = "qte-font-helvetica-100 qte-font-helvetica-120"
PROVIDES += "qte-font-fixed-70 qte-font-fixed-120"
PROVIDES += "qte-font-helvetica-80 qte-font-helvetica-140 qte-font-helvetica-180 qte-font-helvetica-240"
PROVIDES += "qte-font-smallsmooth-90"
PROVIDES += "qte-font-micro-40"
PROVIDES += "qte-font-japanese-230"
PROVIDES += "qte-font-smoothmono-90 qte-font-smoothmono-100  qte-font-smoothmono-110 qte-font-smoothmono-120"
PROVIDES += "qte-font-smoothmono-140 qte-font-smoothmono-180  qte-font-smoothmono-240"
PROVIDES += "qte-font-smoothsans-90 qte-font-smoothsans-100  qte-font-smoothsans-110 qte-font-smoothsans-120"
PROVIDES += "qte-font-smoothsans-140 qte-font-smoothsans-180  qte-font-smoothsans-240"
PROVIDES += "qte-font-smoothserif-90 qte-font-smoothserif-100  qte-font-smoothserif-110 qte-font-smoothserif-120"
PROVIDES += "qte-font-smoothserif-140 qte-font-smoothserif-180  qte-font-smoothserif-240"
PROVIDES += "qte-font-smoothtimes-100  qte-font-smoothtimes-160 qte-font-smoothtimes-170"
PROVIDES += "qte-font-smoothtimes-220 qte-font-smoothtimes-250  qte-font-smoothtimes-440"
PROVIDES += "qte-font-unifont"
RPROVIDES_qte-font-unifont += "virtual-japanese-font"
RPROVIDES_qte-font-japanese += "virtual-japanese-font"

SRC_URI = "ftp://ftp.trolltech.com/pub/qt/source/qt-embedded-${PV}-free.tar.gz"
S = "${WORKDIR}/qt-${PV}"

QPF_PKGPATTERN = "qte-font-%s"
QPF_DESCRIPTION = "Qt/E font %s"

do_install() {
	install -d ${D}${palmqtdir}/lib/fonts/
	cp -pPR lib/fonts/* ${D}${palmqtdir}/lib/fonts/
}

SRC_URI[md5sum] = "1f7ad30113afc500cab7f5b2f4dec0d7"
SRC_URI[sha256sum] = "883363eb0c94de3d1e36f3ab9e09a8f127418d497213cc1a0ed1a1588ecd66b8"
