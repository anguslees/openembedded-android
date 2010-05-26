require libtool_${PV}.bb

PR = "${INC_PR}.0"
PACKAGES = ""
SRC_URI_append = " file://rpath-control.patch \
                   file://libdir-la.patch \
                   file://libdir-la2.patch \
                   file://prefix.patch \
                   file://tag.patch \
                   file://install-path-check.patch \
		   file://nmedit_fix.patch \
		   file://nousrlib.patch"

S = "${WORKDIR}/libtool-${PV}"

prefix = "${STAGING_DIR_NATIVE}${prefix_native}"
exec_prefix = "${STAGING_DIR_NATIVE}${prefix_native}"
bindir = "${STAGING_BINDIR_NATIVE}"

do_configure_prepend () {
	rm -f ltmain.shT
	date=`/bin/sh ./mkstamp < ./ChangeLog` && \
        sed -e 's/@''PACKAGE@/libtool/' -e 's/@''VERSION@/1.5.10/' \
            -e "s%@""TIMESTAMP@%$date%" ./ltmain.in > ltmain.shT
	mv -f ltmain.shT ltmain.sh || \
	        (rm -f ltmain.sh && cp ltmain.shT ltmain.sh && rm -f ltmain.shT)
	cp ltmain.sh ./libltdl/
}

do_compile () {
	:
}

do_stage () {
        install -m 0755 ${HOST_SYS}-libtool ${bindir}/${HOST_SYS}-libtool
        install -m 0644 libltdl/ltdl.h ${STAGING_INCDIR}/
        install -d ${STAGING_DIR_HOST}${target_datadir}/libtool ${STAGING_DIR_HOST}${target_datadir}/aclocal
        install -c config.guess ${STAGING_DIR_HOST}${target_datadir}/libtool/
        install -c config.sub ${STAGING_DIR_HOST}${target_datadir}/libtool/
        install -c -m 0644 ltmain.sh ${STAGING_DIR_HOST}${target_datadir}/libtool/
        install -c -m 0644 libtool.m4 ${STAGING_DIR_HOST}${target_datadir}/aclocal/
        install -c -m 0644 ltdl.m4 ${STAGING_DIR_HOST}${target_datadir}/aclocal/
}

do_install () {
	:
}

SRC_URI[md5sum] = "e2093a85f6d48f1562c36920087502d6"
SRC_URI[sha256sum] = "6524e6d7a4adbda7fcda27ecd7b08bbeab88ad59d81bc6b166c617530f3dee1a"
