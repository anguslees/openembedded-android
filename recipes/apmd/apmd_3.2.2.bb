DESCRIPTION = "Set of tools for managing notebook power consumption."
SECTION = "base"
PRIORITY = "required"
DEPENDS = "libtool-cross"
LICENSE = "GPL"
PR = "r12"

SRC_URI = "${DEBIAN_MIRROR}/main/a/apmd/apmd_${PV}.orig.tar.gz \
           file://debian.patch \
           file://workaround.patch \
           file://zaurus24.patch \
           file://unlinux.patch \
           file://libtool.patch \
           file://init \
           file://default \
           file://apmd_proxy \
           file://apmd_proxy.conf"

S = "${WORKDIR}/apmd-${PV}.orig"

inherit update-rc.d

INITSCRIPT_NAME = "apmd"
INITSCRIPT_PARAMS = "defaults"

do_compile() {
	oe_runmake "LIBTOOL=${STAGING_BINDIR_NATIVE}/${TARGET_PREFIX}libtool" apm apmd
}

do_install() {
	install -d ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/apm
	install -d ${D}${sysconfdir}/apm/event.d
	install -d ${D}${sysconfdir}/apm/other.d
	install -d ${D}${sysconfdir}/apm/suspend.d
	install -d ${D}${sysconfdir}/apm/resume.d
	install -d ${D}${sysconfdir}/apm/scripts.d
	install -d ${D}${sysconfdir}/default
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sbindir}
	install -d ${D}${bindir}
	install -d ${D}${libdir}
	install -d ${D}${datadir}/apmd
	install -d ${D}${includedir}

	install -m 4755 ${S}/.libs/apm ${D}${bindir}/apm
	install -m 0755 ${S}/.libs/apmd ${D}${sbindir}/apmd
	install -m 0755 ${WORKDIR}/apmd_proxy ${D}${sysconfdir}/apm/
	install -m 0644 ${WORKDIR}/apmd_proxy.conf ${D}${datadir}/apmd/
	install -m 0644 ${WORKDIR}/default ${D}${sysconfdir}/default/apmd
	oe_libinstall -so libapm ${D}${libdir}
	install -m 0644 apm.h ${D}${includedir}
        for i in `find ${D} -name "*.la"` ; do \
                sed -i -e s:${STAGING_LIBDIR}:${libdir}:g $i
                sed -i -e s:${STAGING_DIR_HOST}::g $i
        done

	cat ${WORKDIR}/init | sed -e 's,/usr/sbin,${sbindir},g; s,/etc,${sysconfdir},g;' > ${D}${sysconfdir}/init.d/apmd
	chmod 755 ${D}${sysconfdir}/init.d/apmd
}

PACKAGES =+ "libapm libapm-dev apm"

FILES_libapm = "${libdir}/libapm.so.*"
FILES_libapm-dev = "${libdir}/libapm.* ${includedir}"
FILES_apm = "${bindir}/apm*"

SRC_URI[md5sum] = "b1e6309e8331e0f4e6efd311c2d97fa8"
SRC_URI[sha256sum] = "7f7d9f60b7766b852881d40b8ff91d8e39fccb0d1d913102a5c75a2dbb52332d"
