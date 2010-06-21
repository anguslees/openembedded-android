DESCRIPTION = "Enchant Spell checker API Library"
PRIORITY    = "optional"
SECTION = "libs"
LICENSE     = "LGPL"
DEPENDS     = "aspell"
RDEPENDS_${PN}    = "aspell"

inherit autotools pkgconfig

PR = "r2"

S = "${WORKDIR}/enchant-${PV}"

SRC_URI = "http://www.abisource.com/downloads/enchant/${PV}/enchant-${PV}.tar.gz \
           file://configure.patch"

EXTRA_OECONF = "--with-aspell-prefix=${STAGING_DIR_HOST}${layout_prefix} --enable-aspell --disable-binreloc"

FILES_${PN} = "/usr/bin/* /usr/lib/enchant/*.so /usr/share/enchant /usr/lib/libenchant*.so.*"

do_stage() {
	oe_runmake install prefix=${STAGING_DIR_HOST}${layout_prefix} \
	       bindir=${STAGING_BINDIR} \
	       includedir=${STAGING_INCDIR} \
	       libdir=${STAGING_LIBDIR} \
	       datadir=${STAGING_DATADIR} \
	       mandir=${STAGING_DIR_HOST}${layout_mandir}
}

SRC_URI[md5sum] = "e64ec808ed2cb687c242ebb835faeb61"
SRC_URI[sha256sum] = "15348bf21125536b3bce1e477e50310e4b192c43c35476e949f41280bc7123d8"
