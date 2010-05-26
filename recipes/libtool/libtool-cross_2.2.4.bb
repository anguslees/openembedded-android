require libtool_${PV}.bb
PR = "${INC_PR}.0"

SRC_URI += "\
  file://cross_compile.patch \
  file://prefix.patch \
"

DEPENDS += "libtool-native"

DOLT_PATCH = ""
DOLT_PATCH_arm = " file://add_dolt.patch"
DOLT_PATCH_i586 = " file://add_dolt.patch"

#SRC_URI_append_linux = "${DOLT_PATCH}"
#SRC_URI_append_linux-gnueabi = "${DOLT_PATCH}"

S = "${WORKDIR}/libtool-${PV}"

prefix = "${STAGING_DIR_NATIVE}${prefix_native}"
exec_prefix = "${STAGING_DIR_NATIVE}${prefix_native}"
bindir = "${STAGING_BINDIR_NATIVE}"

do_compile () {
	:
}

do_stage () {
	install -m 0755 ${HOST_SYS}-libtool ${bindir}/${HOST_SYS}-libtool
	install -d ${STAGING_DIR_HOST}${target_datadir}/libtool ${STAGING_DIR_HOST}${target_datadir}/aclocal
	install -c ${S}/libltdl/config/config.guess ${STAGING_DIR_HOST}${target_datadir}/libtool/
	install -c ${S}/libltdl/config/config.sub ${STAGING_DIR_HOST}${target_datadir}/libtool/
	install -c -m 0644 ${S}/libltdl/config/ltmain.sh ${STAGING_DIR_HOST}${target_datadir}/libtool/
	install -c -m 0644 ${S}/libltdl/m4/libtool.m4 ${STAGING_DIR_HOST}${target_datadir}/aclocal/
	install -c -m 0644 ${S}/libltdl/m4/ltdl.m4 ${STAGING_DIR_HOST}${target_datadir}/aclocal/
	if [ -e ${WORKDIR}/dolt.m4 ] ; then
		install -c -m 0644 ${WORKDIR}/dolt.m4 ${STAGING_DIR_HOST}${target_datadir}/aclocal/
	fi
}


do_install () {
	:
}

PACKAGES = ""

SRC_URI[md5sum] = "0b87e84c7aad3b5e979bbe35312fd4c1"
SRC_URI[sha256sum] = "b8839214df514d560a202f54c30d1b2e2f443f0798193c9e926c9f9e11218b9c"
