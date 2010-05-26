DESCRIPTION = "Apache Portable Runtime (APR) library"
SECTION = "libs"
LICENSE = "Apache License, Version 2.0"

PR = "r4"

SRC_URI = "${APACHE_MIRROR}/apr/${P}.tar.bz2;name=apr135tarbz2 \
           file://configure_fixes.patch \
#	   file://cleanup.patch \
           file://configfix.patch"

inherit autotools lib_package binconfig

OE_BINCONFIG_EXTRA_MANGLE = " -e 's:location=source:location=installed:'"

do_configure_prepend() {
	cd ${S}
	./buildconf
}

do_stage() {
	autotools_stage_all
	install -d ${STAGING_DATADIR}/apr
	cp ${S}/build/apr_rules.mk ${STAGING_DATADIR}/apr/
	sed -i s,apr_builddir=.*,apr_builddir=,g ${STAGING_DATADIR}/apr/apr_rules.mk
	sed -i s,apr_builders=.*,apr_builders=,g ${STAGING_DATADIR}/apr/apr_rules.mk
	sed -i s,LIBTOOL=.*,LIBTOOL=\$\(SHELL\)\ ${TARGET_PREFIX}libtool,g ${STAGING_DATADIR}/apr/apr_rules.mk
	sed -i s,\$\(apr_builders\),${STAGING_DATADIR}/apr/,g ${STAGING_DATADIR}/apr/apr_rules.mk
	cp ${S}/build/mkdir.sh ${STAGING_DATADIR}/apr/
	cp ${S}/build/make_exports.awk ${STAGING_DATADIR}/apr/
	cp ${S}/build/make_var_export.awk ${STAGING_DATADIR}/apr/
}

SRC_URI[apr135tarbz2.md5sum] = "9ac9a00eaa190937fdbbde7b4f03ac1e"
SRC_URI[apr135tarbz2.sha256sum] = "a33d360b70a9c7971651b3810513d7a85ca84d13ddfc1cb96d6cb0af76aab574"
