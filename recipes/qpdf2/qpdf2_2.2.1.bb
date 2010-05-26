DESCRIPTION = "QPDF2 is a Viewer for PDF documents. An unnecessary fork based on opie-qpdf."
PRIORITY = "optional"
SECTION = "opie/applications"
HOMEPAGE = "http://qpdf2.sf.net"
LICENSE = "GPL"
DEPENDS = "t1lib freetype"
APPNAME = "qpdf"
APPTYPE = "binary"
APPDESKTOP = "${S}/ipkg-render-freetype/opt/QtPalmtop/apps/Applications"
PR = "r3"

SRC_URI = "${SOURCEFORGE_MIRROR}/qpdf2/qpdf2_2.2.1_20040217b.tgz \
           file://hack-the-hack.patch \
           file://fix-sigsegv.patch \
           file://fix_qtversion_check.patch \
           file://gcc4.patch"
S = "${WORKDIR}/qpdf2_${PV}"

inherit opie

QMAKE_PROFILES = "qpdf_render-freetype.pro"
EXTRA_QMAKEVARS_POST += "TARGET=qpdf"
export OE_QMAKE_LINK="${CXX}"

do_configure_prepend() {
	find . -name "Makefile"|xargs rm -f
	find . -name "*.o"|xargs rm -f
	find . -name "*.a"|xargs rm -f
	find . -name "*.la"|xargs rm -f
}

do_install() {
	install -d ${D}${palmtopdir}/pics/qpdf
	install -m 0644 ipkg-render-freetype/opt/QtPalmtop/pics/qpdf/*.* ${D}${palmtopdir}/pics/qpdf/
}


SRC_URI[md5sum] = "6b94eda9bd8e2b884c753d70fee04b3f"
SRC_URI[sha256sum] = "3378f7a92f3afbc3663cce8c99790092282eb01101ba21aed4709736cf875ad5"
