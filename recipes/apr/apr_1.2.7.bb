DESCRIPTION = "Apache Portable Runtime (APR) library"
SECTION = "libs"
LICENSE = "Apache License, Version 2.0"

PR = "r2"

# apache mirrors?
SRC_URI = "${APACHE_MIRROR}/apr/${P}.tar.bz2 \
           file://configure_fixes.patch"

inherit autotools lib_package binconfig

OE_BINCONFIG_EXTRA_MANGLE = " -e 's:location=source:location=installed:'"

do_configure() {
  oe_runconf
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

SRC_URI[md5sum] = "e77887dbafc515c63feac84686bcb3bc"
SRC_URI[sha256sum] = "384437f3c4eb7d53ad27fdadce6cbc295ef16653b7f7739a480d91c784082ec9"
