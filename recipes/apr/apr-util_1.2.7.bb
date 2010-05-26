DESCRIPTION = "Apache Portable Runtime (APR) companion library"
SECTION = "libs"
DEPENDS = "apr expat gdbm"
LICENSE = "Apache License, Version 2.0"

PR = "r3"

# apache mirrors?
SRC_URI = "${APACHE_MIRROR}/apr/${P}.tar.gz \
           file://configure_fixes.patch"

EXTRA_OECONF = "--with-apr=${STAGING_BINDIR_CROSS} --with-dbm=gdbm \
		--with-gdbm=${STAGING_DIR_HOST}${layout_prefix} \
		--without-sqlite2 \
		--without-sqlite3 \
		--with-expat=${STAGING_DIR_HOST}${layout_prefix}"


inherit autotools lib_package binconfig

OE_BINCONFIG_EXTRA_MANGLE = " -e 's:location=source:location=installed:'"

do_configure_prepend () {
	cp ${STAGING_DATADIR}/apr/apr_rules.mk ${S}/build/rules.mk
}

do_stage() {
	autotools_stage_all
}

SRC_URI[md5sum] = "c3702668a640be1695956115857ec22e"
SRC_URI[sha256sum] = "8cd84eb2031a91572e1be2975f4171730a9be72c4cd88718c4c40ac7dc4fd7d3"
