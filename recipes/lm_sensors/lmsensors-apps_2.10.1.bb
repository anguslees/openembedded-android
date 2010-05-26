DESCRIPTION = "Hardware health monitoring applications"
HOMEPAGE = "http://www.lm-sensors.org/"
DEPENDS = "sysfsutils virtual/libiconv"
LICENSE = "GPL"
PR = "r3"

SRC_URI = "http://dl.lm-sensors.org/lm-sensors/releases/lm_sensors-${PV}.tar.gz;name=archive \
           file://prefix-fix.patch \
           file://add-sysfs-ldflags.patch"

SRC_URI_append_uclibc = "file://iconv.patch"
SRC_URI[archive.md5sum] = "cdc857b78e813b88cbf8be92441aa299"
SRC_URI[archive.sha256sum] = "a332cacfa9d0eed6e9158c394db714e536f38c27451d7df08b9634952118fa1b"

S = "${WORKDIR}/lm_sensors-${PV}"

do_compile() {
	oe_runmake user LINUX=${STAGING_KERNEL_DIR} EXLDFLAGS="${LDFLAGS}" PROG_EXTRA=sensors MACHINE=${TARGET_ARCH}
}

do_install() {
	oe_runmake user_install DESTDIR=${D}

	install -d ${D}/.usr
	mv ${D}/* ${D}/.usr
	mv ${D}/.usr ${D}/usr

	install -d ${D}${sysconfdir}
	mv ${D}/usr/etc/sensors.conf ${D}${sysconfdir}
	# move manuals into proper place
	install -d ${D}${mandir}
	rm -rf ${D}${mandir}/*
	mv ${D}/usr/man/* ${D}${mandir}


PACKAGES =+  "libsensors libsensors-dev libsensors-dbg libsensors-doc"
PACKAGES =+ "lmsensors-sensors lmsensors-sensors-dbg lmsensors-sensors-doc"
PACKAGES =+ "lmsensors-scripts"

FILES_lmsensors-scripts = "${bindir}/*.pl ${bindir}/ddcmon ${sbindir}/fancontrol* ${sbindir}/pwmconfig ${sbindir}/sensors-detect"
RDEPENDS_lmsensors-scripts += "lmsensors-sensors perl bash"

FILES_lmsensors-sensors = "${bindir}/sensors ${sysconfdir}"
FILES_lmsensors-sensors-dbg += "${bindir}/.debug/sensors"
FILES_lmsensors-sensors-doc = "${mandir}/man1 ${mandir}/man5"
FILES_libsensors = "${libdir}/libsensors.so.*"
FILES_libsensors-dbg += "${libdir}/.debug"
FILES_libsensors-dev = "${libdir}/libsensors.so ${libdir}/libsensors.a ${includedir}"
FILES_libsensors-doc = "${mandir}/man3"

