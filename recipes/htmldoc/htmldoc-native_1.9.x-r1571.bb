require htmldoc.inc
PR = "r2"


SRC_URI = "http://ftp.rz.tu-bs.de/pub/mirror/ftp.easysw.com/ftp/pub/htmldoc/snapshots/htmldoc-${PV}.tar.bz2 \
file://paths_1.9.x.patch"


inherit autotools native

EXTRA_AUTORECONF += "--exclude=autoheader"
EXTRA_OECONF += "--disable-localpng --disable-localjpeg --disable-localzlib \
		--with-gui=no"


do_compile() {
	cd htmldoc && oe_runmake all ; cd ${S}
}


do_stage () {
	install -d ${STAGING_DATADIR}/htmldoc/fonts
	install -m 0644 ${S}/fonts/*.afm ${STAGING_DATADIR}/htmldoc/fonts/
	install -m 0644 ${S}/fonts/*.pfa ${STAGING_DATADIR}/htmldoc/fonts/

	install -d ${STAGING_DATADIR}/htmldoc/data
	install -m 0644 ${S}/data/* ${STAGING_DATADIR}/htmldoc/data/

	install -d ${STAGING_DIR_HOST}${layout_mandir}/man1
	install -m 0644 ${S}/doc/htmldoc.man ${STAGING_DIR_HOST}${layout_mandir}/man1/

	install -d ${STAGING_BINDIR}
	install -m 0755 ${S}/htmldoc/htmldoc ${STAGING_BINDIR}/
}

SRC_URI[md5sum] = "eda75ba1abe14ed8e71c6f40438def85"
SRC_URI[sha256sum] = "b4c78ff6b47521e980533e52cbe46fe86874c75b3d6bc18bcc2500a2ba854c3e"
