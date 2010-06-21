DEPENDS = "curl db"
RDEPENDS_${PN} = "dpkg"

require apt.inc

SRC_URI += "file://no-ko-translation.patch \
            file://use-host.patch "
PR = "r4"

SRC_URI += "file://nodoc.patch"

require apt-package.inc

FILES_${PN} += "${bindir}/apt-key"
apt-manpages += "doc/apt-key.8"

do_stage() {
	install -d ${STAGING_LIBDIR} ${STAGING_INCDIR}/apt-pkg
	eval `cat environment.mak | grep ^GLIBC_VER | sed -e's, = ,=,'`
	oe_libinstall -so -C bin libapt-pkg$GLIBC_VER-6 ${STAGING_LIBDIR}/
	ln -sf libapt-pkg$GLIBC_VER-6.so ${STAGING_LIBDIR}/libapt-pkg.so
	oe_libinstall -so -C bin libapt-inst$GLIBC_VER-6 ${STAGING_LIBDIR}/
	ln -sf libapt-inst$GLIBC_VER-6.so ${STAGING_LIBDIR}/libapt-inst.so

	install -m 0644 include/apt-pkg/*.h ${STAGING_INCDIR}/apt-pkg/
}

SRC_URI[md5sum] = "0ef50176aea36cb0cce633a9b62dc7eb"
SRC_URI[sha256sum] = "c928f5eb2baffb50e7ccf02d07a16daf867945c8aa542d500bbbbaff7bbcef42"

